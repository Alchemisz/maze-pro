package com.promaze.generateAlgorithms;

import com.promaze.Block;

public interface MazeGenerator {

    void generate(Block[][] mazeGrid);
    public String getName();
}
