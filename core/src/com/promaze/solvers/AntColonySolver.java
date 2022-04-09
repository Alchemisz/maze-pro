package com.promaze.solvers;

import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;

import java.util.*;

public class AntColonySolver implements Solver {
    public String name = "ANT COLONY";

    @Override
    public String getName()
    {
        return name;
    }

    private Random rand;
    float evaporateRate = 0.005f;

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
        float basePheromone;
        float baseReturnPheromone;
        float returnPheromone;
        float distanceModifier;
        public int distance;
        public float distanceInc;
        int init = 1;

        public Ant(int x, int y, int gridLength) {
            float modifier = 1.0f * (float)gridLength;
            this.state = 0;
            this.x = x;
            this.y = y;
            this.parentX = -1;
            this.initX = this.x;
            this.initY = this.y;
            this.parentY = -1;

            if(modifier < 25f) {
                this.distanceModifier = 0.8f;
                this.basePheromone = 0f;//0.1f + modifier * 0.05f;
                this.baseReturnPheromone = 1f;//0.3f + modifier * 0.1f;
                this.returnPheromone = 0f;
            } else if (modifier < 45f){
                this.distanceModifier = 0.8f;
                this.basePheromone = 0f;//0.1f + modifier * 0.05f;
                this.baseReturnPheromone = 3f;//0.3f + modifier * 0.1f;
                this.returnPheromone = 0f;//modifier*0.01f;
            } else {
                this.distanceModifier = 0.2f;
                this.basePheromone = 0f;//0.1f + modifier * 0.05f;
                this.baseReturnPheromone = 1f;//0.3f + modifier * 0.1f;
                this.returnPheromone = 0f;//modifier*0.01f;
            }

            this.distance = 0;
            this.distanceInc = 1.0f;
        }

        public void changeState() {
            this.distance = 0;
            this.distanceInc = 1.0f;
            this.init = 0;
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
            this.path.clear();
            this.distance = 0;
            this.distanceInc = 1.0f;
        }

        public void incrementDistance() {
            this.distance += 1;
            this.distanceInc += this.distanceModifier;
        }

        public float getPheromone() {
            if(this.init == 1) {
                return 0f;
            }
            if(this.state == 0) {
                return 0f + this.basePheromone/distanceInc;//0.13f ;//+ 0.5f/distanceInc;//+ 0.9f/distanceInc;
            } else {
                return this.returnPheromone + this.baseReturnPheromone/distanceInc;
            }
        }

    }

    @Override
    public List<Maze> solve(Maze maze) {
        List<Maze> list = new ArrayList<>();
        list.add(maze);

        final float[][] pheromoneMap = generatePheromoneMap(maze);
        Block[][] grid = maze.getMazeGrid();

        Ant[] ants;

        if(grid.length < 100) {
            ants = new Ant[grid.length * 8 + 50];
        } else {
            ants = new Ant[850];
        }

        Block agent = maze.getAgentPosition();

        if(agent.getY() == -1) {
            return list;
        }


        for(int i=0; i<ants.length; i++) {
            ants[i] = new Ant(agent.getX(), agent.getY(), grid.length);
        }


        for(int i=0; i<50000; i++) {
            for(Ant ant : ants) {
                moveAnt(ant, grid, pheromoneMap);
            }
            //Arrays.stream(ants).parallel().forEach(e -> moveAnt(e, grid, pheromoneMap));
            updatePheromones(pheromoneMap, ants);
        }
        float[] minMax = minMax(pheromoneMap);
        float min = minMax[0];
        float max = minMax[1];

        float range = max - min;

        for(int i=0; i<pheromoneMap.length; i++) {
            for(int j=0; j<pheromoneMap[0].length; j++) {
                if(range == 0) {
                    pheromoneMap[i][j] = 0;
                } else {
                    pheromoneMap[i][j] = pheromoneMap[i][j] - min;
                    pheromoneMap[i][j] /= range;
                }
                System.out.print(pheromoneMap[i][j] + " ");
                if(pheromoneMap[i][j] > 0.01f && !grid[i][j].getBlockType().equals(BlockType.END) && !grid[i][j].getBlockType().equals(BlockType.AGENT)) {
                    float tmp = pheromoneMap[i][j];
                    //tmp = 2.857f * tmp * tmp * tmp - 3.5f * tmp * tmp + 1.64f * tmp;
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
                if(pheromoneMap[i][j] > 100.0f) {
                    pheromoneMap[i][j] = 100.0f;
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
            if(ant.distance > grid.length * 8) {
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
                            ant.path.add(new Integer[]{ant.x, ant.y});
                            continue;
                        }
                    } /*else if (grid[_x][_y].getBlockType().equals(BlockType.AGENT)) {
                        ant.respawn();
                        return;
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
                pheromoneMap[i][j] = 5.0f;
            }
        }
        return pheromoneMap;
    }
}
