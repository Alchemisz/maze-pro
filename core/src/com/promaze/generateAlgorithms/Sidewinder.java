package com.promaze.generateAlgorithms;

import com.promaze.Block;
import com.promaze.BlockType;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Sidewinder implements MazeGenerator{

    @Override
    public void generate(Block[][] mazeGrid) {

        Random random = new Random();

        int rows = mazeGrid.length;
        int columns = mazeGrid[0].length;

        //first row must be clear
        for (int i = 1; i < columns-1; i++) {
            mazeGrid[1][i].setBlockType(BlockType.AIR);
        }

//        Jesli u góry jeden przytnij (1 w góre i w prawo)

        for (int i = 3; i < rows-1; i+= 2) {
            for (int j = 1; j < columns-1;) {
                boolean clear = true;
                List<Integer> indexesCleared = new LinkedList<>();
                while (clear){
                    if (j > (columns - 2)){
                        break;
                    }

                    mazeGrid[i][j].setBlockType(BlockType.AIR);
                    indexesCleared.add(j);
                    clear = random.nextBoolean();
                    if (clear){
                        j++;
                        continue;
                    }else{

                        //Extra condition
//                        if ((j+2) > (columns - 2)){
//                            mazeGrid[i][j+1].setBlockType(BlockType.AIR);
//                            indexesCleared.add(j+1);
//                        }
                        //

                        int clearNorthIndex = random.nextInt(indexesCleared.size());
                        int northIndex = indexesCleared.get(clearNorthIndex);
                        mazeGrid[i-1][northIndex].setBlockType(BlockType.AIR);

                        //Extra condition
//                        if (mazeGrid[i-1][northIndex].getBlockType() == BlockType.WALL){
//                            mazeGrid[i-1][northIndex-columns].setBlockType(BlockType.AIR);
//                        }
                        //

                        j+= 2;
                        indexesCleared = new LinkedList<>();
                        break;
                    }
                }
            }
        }

    }

}
