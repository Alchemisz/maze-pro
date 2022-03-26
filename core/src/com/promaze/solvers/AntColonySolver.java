package com.promaze.solvers;

import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;

import java.util.*;

public class AntColonySolver implements Solver {
    private Random rand;
    float evaporateRate = 0.02f;

    public AntColonySolver() {
        rand = new Random();
        rand.setSeed(new Date().getTime());
    }

    protected class Ant {
        public int x, y;
        public int parentX, parentY;
        private int initX, initY;
        public int state = 0;
        public Stack<Integer[]> path = new Stack<>();
        float alpha;
        float beta;
        public int distance;
        public float distanceInc;

        public Ant(int x, int y) {
            this.state = 0;
            this.x = x;
            this.y = y;
            this.parentX = -1;
            this.initX = this.x;
            this.initY = this.y;
            this.parentY = -1;
            this.alpha = 0.5f;
            this.beta = 0.5f;
            this.distance = 0;
            this.distanceInc = 1.0f;
        }

        public void changeState() {
            this.distance = 0;
            this.distanceInc = 1.0f;
            if(this.state == 1) {
                this.state = 0;
            } else {
                this.state = 1;
            }

        }

        public void respawn() {
            this.state = 0;
            this.x = this.initX;
            this.y = this.initY;
            this.parentX = this.x;
            this.parentY = this.y;
            this.alpha = 0.5f;
            this.beta = 0.5f;
            this.path.clear();
            this.distance = 0;
            this.distanceInc = 1.0f;
        }

        public void incrementDistance() {
            this.distance += 1;
            this.distanceInc += 0.5f;
        }

        public float getPheromone() {
            if(this.state == 0) {
                return 0.02f;
            } else {
                return 0.02f + 1.4f/distanceInc;
            }
        }

    }

    @Override
    public List<Maze> solve(Maze maze) {
        List<Maze> list = new ArrayList<>();
        list.add(maze);

        Ant[] ants = new Ant[50];
        Block agent = maze.getAgentPosition();

        if(agent.getY() == -1) {
            return list;
        }
        for(int i=0; i<ants.length; i++) {
            ants[i] = new Ant(agent.getX(), agent.getY());
        }
        float[][] pheromoneMap = generatePheromoneMap(maze);
        Block[][] grid = maze.getMazeGrid();

        for(int i=0; i<128000; i++) {
            for(Ant ant : ants) {
                moveAnt(ant, grid, pheromoneMap);
            }
            updatePheromones(pheromoneMap, ants);
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
                if(pheromoneMap[i][j] > 0.01f && !grid[i][j].getBlockType().equals(BlockType.END) && !grid[i][j].getBlockType().equals(BlockType.AGENT)) {
                    float tmp = pheromoneMap[i][j];
                    tmp = 2.857f * tmp * tmp * tmp - 3.5f * tmp * tmp + 1.64f * tmp;
                    grid[i][j].setIntensity(tmp);
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

    private void updatePheromones(float[][] pheromoneMap, Ant[] ants) {
        for(Ant ant : ants) {
            pheromoneMap[ant.x][ant.y] += ant.getPheromone();

        }
        for(int i=0; i<pheromoneMap.length; i++) {
            for(int j=0; j<pheromoneMap[0].length; j++) {
                pheromoneMap[i][j] *= (1 - evaporateRate);
                if(pheromoneMap[i][j] > 25.0f) {
                    pheromoneMap[i][j] = 25.0f;
                }
                if(pheromoneMap[i][j] < 1.0f) {
                    pheromoneMap[i][j] = 1.0f;
                }
            }
        }
    }

    private void moveAnt(Ant ant, Block[][] grid, float[][] pheromoneMap) {
        ant.incrementDistance();

        if (ant.state == 1) {
            Integer[] block = ant.path.pop();
            if((block[0] == ant.initX && block[1] == ant.initY) || ant.path.empty()) {
                ant.respawn();
                return;
            }
            ant.x = block[0];
            ant.y = block[1];
            return;
        } else if(ant.state == 0) {
            if(ant.distance > grid.length * 10) {
                ant.respawn();
                return;
            }
        }

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
                            //ant.state = 1;
                            ant.changeState();
                            continue;
                        }
                    } /*else if (grid[_x][_y].getBlockType().equals(BlockType.AGENT)) {
                        if (ant.state == 1) {
                            ant.state = 0;
                            continue;
                        }
                    }*/
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

        ant.parentX = ant.x;
        ant.parentY = ant.y;
        ant.path.add(new Integer[]{ant.x, ant.y});
        ant.x = _x;
        ant.y = _y;
    }

    private float[][] generatePheromoneMap(Maze maze) {
        Block[][] grid = maze.getMazeGrid();
        float[][] pheromoneMap = new float[grid.length][grid[0].length];
        for(int i=0; i<pheromoneMap.length; i++) {
            for(int j=0; j<pheromoneMap[0].length; j++) {
                pheromoneMap[i][j] = 1.0f;
            }
        }
        return pheromoneMap;
    }
}
