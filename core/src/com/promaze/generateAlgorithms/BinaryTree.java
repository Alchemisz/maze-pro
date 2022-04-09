package com.promaze.generateAlgorithms;

import com.promaze.Block;
import com.promaze.BlockType;

import java.util.Random;

public class BinaryTree implements MazeGenerator {

    private String name = "BINARY TREE";

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void generate(Block[][] mazeGrid) {

        Random random = new Random();

        for (int row = mazeGrid.length - 2; row > 0; row-=2) {
            for (int column = mazeGrid.length - 2; column > 0; column-=2) {
                mazeGrid[row][column].setBlockType(BlockType.AIR);

                if (random.nextBoolean()){
                    if ((row - 1) != 0)
                        cutNorth(mazeGrid, row, column);
                    else
                        if ((column - 1) != 0)
                            cutWest(mazeGrid, row, column);
                }else{
                    if ((column - 1) != 0)
                        cutWest(mazeGrid, row, column);
                    else
                        if ((row - 1) != 0)
                            cutNorth(mazeGrid, row, column);
                }
            }
        }
    }

    private void cutNorth(Block[][] mazeGrid, Integer row, Integer column){
        mazeGrid[row - 1][column].setBlockType(BlockType.AIR);
    }

    private void cutWest(Block[][] mazeGrid, Integer row, Integer column){
        mazeGrid[row][column - 1].setBlockType(BlockType.AIR);
    }

}
