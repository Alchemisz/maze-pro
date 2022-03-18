package com.promaze.solvers;

import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class RecurrentSolver implements Solver {

    @Override
    public List<Maze> solve(Maze maze) {
        int[] neighbours = {0,1,
                1, 0,
                0, -1,
                -1, 0};

        List<Maze> steps = new ArrayList<>();
        Block[][] grid = maze.getMazeGrid();
        int[][] visitedMap = generateVisitedMap(maze);
        int[][][] parentMap = generateParentMap(maze);

        for(int i=0; i<visitedMap.length; i++) {
            for(int j=0; j<visitedMap[0].length; j++) {
                visitedMap[i][j] = 0;
                parentMap[i][j][0] = -1;
                parentMap[i][j][1] = -1;
            }
        }

        Stack<Block> path = new Stack<>();
        Stack<Block> queue = new Stack<>();
        Block agent = maze.getAgentPosition();
        if(agent.getX() == -1) {
            return steps;
        }
        visitedMap[agent.getX()][agent.getY()] = 1;
        queue.add(agent);
        path.add(agent);
        while(!queue.empty()) {
            Block currentBlock = queue.pop();
            grid[agent.getX()][agent.getY()].setBlockType(BlockType.VISITED);

            if(currentBlock.getBlockType().equals(BlockType.END)) {
                grid[agent.getX()][agent.getY()].setBlockType(BlockType.AGENT);
                currentBlock = agent;
                int _x = currentBlock.getX();
                int _y = currentBlock.getY();
                int parentX = parentMap[_x][_y][0];
                int parentY = parentMap[_x][_y][1];
                while(parentX != -1) {
                    grid[parentX][parentY].setBlockType(BlockType.PATH);
                    currentBlock = grid[parentX][parentY];
                    _x = currentBlock.getX();
                    _y = currentBlock.getY();
                    parentX = parentMap[_x][_y][0];
                    parentY = parentMap[_x][_y][1];
                }
                steps.add(new Maze(grid));
                return steps;
            }
            agent = currentBlock;
            grid[agent.getX()][agent.getY()].setBlockType(BlockType.AGENT);

            Maze currentMaze = new Maze(grid);
            steps.add(currentMaze);

            for(int i=0; i<8; i+=2) {
                int _x = agent.getX() + neighbours[i];
                int _y = agent.getY() + neighbours[i+1];

                if((_x >= 0 && _y >= 0 && _x < grid.length && _y < grid[0].length) && visitedMap[_x][_y] < 1) {
                    if(!grid[_x][_y].getBlockType().equals(BlockType.WALL)) {
                        visitedMap[_x][_y] = 1;
                        parentMap[_x][_y][0] = agent.getX();
                        parentMap[_x][_y][1] = agent.getY();
                        queue.add(grid[_x][_y]);
                        if(grid[_x][_y].getBlockType().equals(BlockType.END)) {
                            break;
                        }
                    }
                }
            }
        }
        return steps;
    }

    private int[][] generateVisitedMap(Maze maze) {
        Block[][] grid = maze.getMazeGrid();
        int[][] visitedMap = new int[grid.length][grid[0].length];
        return visitedMap;
    }
    private int[][][] generateParentMap(Maze maze) {
        Block[][] grid = maze.getMazeGrid();
        int[][][] visitedMap = new int[grid.length][grid[0].length][2];
        return visitedMap;
    }
}
