package com.promaze;

import com.promaze.generateAlgorithms.MazeGenerator;

public class Maze{

    private Integer rows;
    private Integer columns;
    private Block[][] mazeGrid;

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
}
