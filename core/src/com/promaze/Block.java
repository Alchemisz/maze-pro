package com.promaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Data;

//@Data
public class Block extends ClickListener {

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


    public void updateBlockType(float x, float y, float size)
    {
        if(!Gdx.input.justTouched())return;

        float x2 = x+size;
        float y2 = y+size;
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        if(x<mouseX && mouseX<x2)
            if(y<mouseY && mouseY<y2)
                this.blockType = this.blockType.equals(BlockType.AIR)?BlockType.WALL:BlockType.AIR;

    }

    public boolean isHighlighted(float x, float y, float size)
    {
        float x2 = x+size;
        float y2 = y+size;
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        if(x<mouseX && mouseX<x2)
            if(y<mouseY && mouseY<y2)
                return true;
        return false;
    }

    public Block clone() {
        Block block = new Block(this.x, this.y);
        block.setBlockType(this.blockType);
        return block;
    }
}
