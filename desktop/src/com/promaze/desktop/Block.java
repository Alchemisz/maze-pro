package com.promaze.desktop;

import lombok.Data;

@Data
public class Block {

    private BlockType blockType;
    private int x, y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        blockType = BlockType.WALL;
    }
}
