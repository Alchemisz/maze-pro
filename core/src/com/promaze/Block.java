package com.promaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Data;

//@Data
public class Block extends ClickListener {

    private BlockType blockType;
    private float intensity = 1f;
    private int x, y;


    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.blockType = BlockType.WALL;
    }

    public Block(String[] singleBlockElements) {
        this.blockType = BlockType.valueOf(singleBlockElements[0]);
        this.x = Integer.parseInt(singleBlockElements[1]);
        this.y = Integer.parseInt(singleBlockElements[2]);
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
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

        float scaledMouseX = ((float) Gdx.input.getX() / (float) Gdx.graphics.getWidth()) * 1280;
        float scaledMouseY = ((float)Gdx.input.getY() / (float)Gdx.graphics.getHeight()) * 720;


        float x2 = x+size;
        float y2 = y+size;
        float mouseX = scaledMouseX;
        float mouseY = scaledMouseY;

        if(x<mouseX && mouseX<x2)
            if(y<mouseY && mouseY<y2)
                this.blockType = this.blockType.equals(BlockType.AIR)?BlockType.WALL:BlockType.AIR;

    }

    public void updateBlockTypeSetActor(float x, float y, float size ,Maze maze)
    {
        if(!Gdx.input.justTouched())return;

        float scaledMouseX = ((float) Gdx.input.getX() / (float) Gdx.graphics.getWidth()) * 1280;
        float scaledMouseY = ((float)Gdx.input.getY() / (float)Gdx.graphics.getHeight()) * 720;


        float x2 = x+size;
        float y2 = y+size;
        float mouseX = scaledMouseX;
        float mouseY = scaledMouseY;

        System.out.println(this.blockType);
        if(x<mouseX && mouseX<x2)
            if(y<mouseY && mouseY<y2) {
                maze.removeAgents();
                this.blockType = this.blockType.equals(BlockType.AIR) ? BlockType.AGENT : this.blockType;
            }

    }

    public void updateBlockTypeSetEnd(float x, float y, float size, Maze maze)
    {
        if(!Gdx.input.justTouched())return;

        float scaledMouseX = ((float) Gdx.input.getX() / (float) Gdx.graphics.getWidth()) * 1280;
        float scaledMouseY = ((float)Gdx.input.getY() / (float)Gdx.graphics.getHeight()) * 720;

        float x2 = x+size;
        float y2 = y+size;
        float mouseX = scaledMouseX;
        float mouseY = scaledMouseY;

        if(x<mouseX && mouseX<x2)
            if(y<mouseY && mouseY<y2) {
                maze.removeEnds();
                this.blockType = this.blockType.equals(BlockType.AIR) ? BlockType.END : this.blockType;
            }

    }


    public boolean isHighlighted(float x, float y, float size)
    {
        float scaledMouseX = ((float) Gdx.input.getX() / (float) Gdx.graphics.getWidth()) * 1280;
        float scaledMouseY = ((float)Gdx.input.getY() / (float)Gdx.graphics.getHeight()) * 720;

        float x2 = x+size;
        float y2 = y+size;
        float mouseX = scaledMouseX;
        float mouseY = scaledMouseY;

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

    @Override
    public String toString() {
        return this.blockType.name() + ";" + this.x + ";" + this.y + "\n";
    }
}
