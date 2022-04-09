package com.promaze;

import com.promaze.generateAlgorithms.MazeGenerator;

import java.util.Arrays;
import java.util.Random;

public class Maze{

    private Integer rows;
    private Integer columns;
    private Block[][] mazeGrid;

    public Maze(Block[][] mazeGrid) {
        this.rows = mazeGrid.length;
        this.columns = mazeGrid[0].length;
        this.mazeGrid = new Block[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.mazeGrid[i][j] = mazeGrid[i][j].clone();
            }
        }
    }

    public int getSize()
    {
        return rows;
    }

    public Maze(Integer rows, Integer columns) {
        this.mazeGrid = new Block[rows][columns];
        this.rows = rows;
        this.columns = columns;
        initEmptyGrid();
    }

    private void initEmptyGrid(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.mazeGrid[i][j] = new Block(i, j);
            }
        }
    }

    public Block getAgentPosition() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block currentBlock = this.mazeGrid[i][j];
                if(currentBlock.getBlockType().equals(BlockType.AGENT)) {
                    return currentBlock;
                }
            }
        }
        return new Block(-1, -1);
    }

    public void applyAlgorithm(MazeGenerator mazeGenerator){
        mazeGenerator.generate(mazeGrid);
    }

    public void printMaze(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block block = this.mazeGrid[i][j];
                System.out.print(block.getBlockType().ordinal() + " ");
            }
            System.out.print('\n');
        }
    }

    public void removeAgents()
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block currentBlock = this.mazeGrid[i][j];
                if(currentBlock.getBlockType().equals(BlockType.AGENT)) {
                    currentBlock.setBlockType(BlockType.AIR);
                }
            }
        }
    }
    public void removeEnds()
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block currentBlock = this.mazeGrid[i][j];
                if(currentBlock.getBlockType().equals(BlockType.END)) {
                    currentBlock.setBlockType(BlockType.AIR);
                }
            }
        }
    }
    public void removePaths()
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block currentBlock = this.mazeGrid[i][j];
                if(currentBlock.getBlockType().equals(BlockType.PATH) || currentBlock.getBlockType().equals(BlockType.VISITED) ||currentBlock.getBlockType().equals(BlockType.PHEROMONE)) {
                    currentBlock.setBlockType(BlockType.AIR);
                }
            }
        }

    }

    public void repositionActors()
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Block currentBlock = this.mazeGrid[i][j];
                if(!currentBlock.getBlockType().equals(BlockType.WALL)) {
                    currentBlock.setBlockType(BlockType.AIR);
                }
            }
        }

        Block[][] grid = this.getMazeGrid();
        Random random = new Random();
        int _x1,_y1,_x2,_y2;
        do {
            _x1 = Math.abs(random.nextInt()) % grid.length;
            _x2 = Math.abs(random.nextInt()) % grid.length;
            _y1 = Math.abs(random.nextInt()) % grid.length;
            _y2 = Math.abs(random.nextInt()) % grid.length;
        }while(!grid[_x1][_y1].getBlockType().equals(BlockType.AIR) || !grid[_x2][_y2].getBlockType().equals(BlockType.AIR));
        grid[_x1][_y1].setBlockType(BlockType.AGENT);
        grid[_x2][_y2].setBlockType(BlockType.END);

    }


    public Block[][] getMazeGrid() {
        return mazeGrid;
    }

    public void setMazeGrid(Block[][] mazeGrid) {
        this.mazeGrid = mazeGrid;
    }
}
