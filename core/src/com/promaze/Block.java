package com.promaze;

import lombok.Data;

//@Data
public class Block {

    private BlockType blockType;
    private int x, y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.blockType = BlockType.WALL;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
