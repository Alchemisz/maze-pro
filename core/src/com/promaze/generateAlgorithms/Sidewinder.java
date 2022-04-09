package com.promaze.generateAlgorithms;

import com.promaze.Block;
import com.promaze.BlockType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Sidewinder implements MazeGenerator{

    private String name = "SIDEWINDER";

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void generate(Block[][] mazeGrid) {

        Random random = new Random();

        int rows = mazeGrid.length;
        int columns = mazeGrid[0].length;

        //first row must be clear
        for (int column = 1; column < columns-1; column++) {
            mazeGrid[1][column].setBlockType(BlockType.AIR);
        }

        for (int row = 3; row < rows; row+= 2) {
            for (int column = 1; column < columns - 1;) {

                boolean cutRight = true;
                List<Integer> indexes = new LinkedList<>();

                while (cutRight){
                    mazeGrid[row][column].setBlockType(BlockType.AIR);
                    indexes.add(column);
                    cutRight = random.nextBoolean();

                    if ((column + 1) == (columns-1)){
                        cutRight = false;
                    }

                    if (cutRight){
                        column++;
                        continue;
                    }else{
                        Integer indexToCutOnNorth = indexes.get(random.nextInt(indexes.size()));
                        mazeGrid[row-1][indexToCutOnNorth].setBlockType(BlockType.AIR);

                        if (mazeGrid[row-2][indexToCutOnNorth].getBlockType() == BlockType.WALL){
                            mazeGrid[row-2][indexToCutOnNorth].setBlockType(BlockType.AIR);
                        }
                        column += 2;
                        break;
                    }
                }

            }
        }

    }

}
