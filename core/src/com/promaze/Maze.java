package com.promaze;

import com.promaze.generateAlgorithms.MazeGenerator;

import java.util.Arrays;

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

    public Block[][] getMazeGrid() {
        return mazeGrid;
    }

    public void setMazeGrid(Block[][] mazeGrid) {
        this.mazeGrid = mazeGrid;
    }
}
