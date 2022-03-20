package com.promaze.solvers;

import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;

import java.util.*;

public class AntColonySolver implements Solver {
    private Random rand;

    public AntColonySolver() {
        rand = new Random();
        rand.setSeed(new Date().getTime());
    }

    protected class Ant {
        public int x, y;
        public int parentX, parentY;
        public int state = 0;
        float alpha;
        float beta;

        public Ant(int x, int y) {
            this.state = 0;
            this.x = x;
            this.y = y;
            this.parentX = -1;
            this.parentY = -1;
            this.alpha = 0.5f;
            this.beta = 0.5f;
        }
    }

    @Override
    public List<Maze> solve(Maze maze) {
        List<Maze> list = new ArrayList<>();
        list.add(maze);

        Ant[] ants = new Ant[30];
        Block agent = maze.getAgentPosition();

        if(agent.getY() == -1) {
            return list;
        }
        for(int i=0; i<ants.length; i++) {
            ants[i] = new Ant(agent.getX(), agent.getY());
        }
        float[][] pheromoneMap = generatePheromoneMap(maze);
        Block[][] grid = maze.getMazeGrid();

        for(int i=0; i<20000; i++) {
            for(Ant ant : ants) {
                moveAnt(ant, grid, pheromoneMap);
            }
            updatePheromones(pheromoneMap);
        }
        float[] minMax = minMax(pheromoneMap);
        float min = minMax[0];
        float max = minMax[1];

        float range = max - min;

        for(int i=0; i<pheromoneMap.length; i++) {
            for(int j=0; j<pheromoneMap[0].length; j++) {
                System.out.print(pheromoneMap[i][j] + " ");
                if(range == 0) {
                    pheromoneMap[i][j] = 0;
                } else {
                    pheromoneMap[i][j] = pheromoneMap[i][j] - min;
                    pheromoneMap[i][j] /= range;
                }
                if(pheromoneMap[i][j] > 0.05f && !grid[i][j].getBlockType().equals(BlockType.END) && !grid[i][j].getBlockType().equals(BlockType.AGENT)) {
                    grid[i][j].setIntensity(pheromoneMap[i][j]);
                    grid[i][j].setBlockType(BlockType.PHEROMONE);
                }
            }
            System.out.println();
        }

        return list;
    }

    float[] minMax(float[][] pheromoneMap) {
        float min = pheromoneMap[0][0];
        float max = pheromoneMap[1][0];
        for(int i=0; i<pheromoneMap.length; i++) {
            for(int j=0; j<pheromoneMap[0].length; j++) {
                if(min > pheromoneMap[i][j]) {
                    min = pheromoneMap[i][j];
                }
                if(max < pheromoneMap[i][j]) {
                    max = pheromoneMap[i][j];
                }
            }
        }
        return new float[]{min, max};
    }

    private void updatePheromones(float[][] pheromoneMap) {
        for(int i=0; i<pheromoneMap.length; i++) {
            for(int j=0; j<pheromoneMap[0].length; j++) {
                pheromoneMap[i][j] *= 0.92f;
                if(pheromoneMap[i][j] < 0.3f) {
                    pheromoneMap[i][j] = 0.3f;
                }
            }
        }
    }

    private void moveAnt(Ant ant, Block[][] grid, float[][] pheromoneMap) {
        int[] neighbours = {0,1,
                1, 0,
                0, -1,
                -1, 0};

        int[] possibilities = {0, 0, 0, 0};
        float[] probabilities = {0f,0f,0f,0f};
        float sum = 0f;


        for(int i=0; i<4; i++) {
            int _x = ant.x + neighbours[i*2];
            int _y = ant.y + neighbours[i*2+1];

            if((_x >= 0 && _y >= 0 && _x < grid.length && _y < grid[0].length)) {
                if (!grid[_x][_y].getBlockType().equals(BlockType.WALL)) {
                    if (grid[_x][_y].getBlockType().equals(BlockType.END)) {
                        if (ant.state == 0) {
                            ant.state = 1;
                            continue;
                        }
                    } else if (grid[_x][_y].getBlockType().equals(BlockType.AGENT)) {
                        if (ant.state == 1) {
                            ant.state = 0;
                            continue;
                        }
                    }
                    if(_x == ant.parentX && _y == ant.parentY) {
                        continue;
                    }
                    possibilities[i] = 1;
                    probabilities[i] = pheromoneMap[_x][_y];
                    sum += probabilities[i];
                }
            }
        }
        float tempSum = 0f;

        float random = rand.nextFloat()*sum;
        int _x = 0;
        int _y = 0;
        if(sum == 0f) {
            _x = ant.parentX;
            _y = ant.parentY;
        }
        for(int i=0; i<4; i++) {
            if(possibilities[i] == 1) {
                tempSum += probabilities[i];
                if(tempSum > random) {
                    _x = ant.x + neighbours[i * 2];
                    _y = ant.y + neighbours[i * 2 + 1];
                    break;
                }
            }
        }
        if(ant.state == 1) {
            pheromoneMap[ant.x][ant.y] *= 1.3;
            if(pheromoneMap[ant.x][ant.y] > 1.27f) {
                pheromoneMap[ant.x][ant.y] = 1.27f;
            }
        } else if(ant.state == 0) {
            pheromoneMap[ant.x][ant.y] *= 1.08f;
            if(pheromoneMap[ant.x][ant.y] > 1.27f) {
                pheromoneMap[ant.x][ant.y] = 1.27f;
            }
        }
        ant.parentX = ant.x;
        ant.parentY = ant.y;
        ant.x = _x;
        ant.y = _y;
    }

    private float[][] generatePheromoneMap(Maze maze) {
        Block[][] grid = maze.getMazeGrid();
        float[][] pheromoneMap = new float[grid.length][grid[0].length];
        for(int i=0; i<pheromoneMap.length; i++) {
            for(int j=0; j<pheromoneMap[0].length; j++) {
                pheromoneMap[i][j] = 0.301f;
            }
        }
        return pheromoneMap;
    }
}
