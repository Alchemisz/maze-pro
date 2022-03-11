package com.promaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;

public class mainGui {

    private ShapeRenderer shapeRenderer;

    public int block_size = 10; // SZEROKOSC I WYSOKOSC W JEDNYM NO LOL TO PRZECIE KWADRAT

    public mainGui() {
        shapeRenderer = new ShapeRenderer();
    }

    public void draw(Maze maze)
    {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawMaze(calculate_center_width_for_maze(maze),calculate_center_height_for_maze(maze),maze);
        shapeRenderer.end();
    }



    private void drawMaze(int x, int y ,Maze maze)
    {
        calculate_optimal_block_size(maze);

        for (Block bArray[]:maze.getMazeGrid()){
            for(Block b:bArray) {
                if(b.getBlockType().equals(BlockType.WALL))shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                        new Color(0.0f,0.0f,0.0f,1.0f),
                        new Color(0.0f,0.0f,0.0f,1.0f),
                        new Color(0.0f,0.0f,0.0f,1.0f),
                        new Color(0.0f,0.0f,0.0f,1.0f));
                else shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                        new Color(1.0f,1.0f,1.0f,1.0f),
                        new Color(1.0f,1.0f,1.0f,1.0f),
                        new Color(1.0f,1.0f,1.0f,1.0f),
                        new Color(1.0f,1.0f,1.0f,1.0f));
            }
        }
    }

    //Raczej tymczasowe funkcje, potem pewnie zrobie lepsze skalowanie

    private void calculate_optimal_block_size(Maze maze)
    {
        block_size = ((720 - 20) / maze.getMazeGrid().length);
    }
    private int calculate_center_height_for_maze(Maze maze)
    {
        return (720 - (maze.getMazeGrid().length)*block_size)/2;
    }
    private int calculate_center_width_for_maze(Maze maze)
    {
        return (1280 - (maze.getMazeGrid().length)*block_size)/2;
    }

}
