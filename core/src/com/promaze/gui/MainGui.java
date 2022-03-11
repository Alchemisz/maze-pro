package com.promaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;
import com.promaze.generateAlgorithms.Sidewinder;

public class MainGui {

    private ShapeRenderer shapeRenderer;
    public OurButton editButton,generateButton,genIncButton,genDecButton;
    public float block_size = 10; // SZEROKOSC I WYSOKOSC W JEDNYM NO LOL TO PRZECIE KWADRAT
    private boolean edit_mode = true;
    private SpriteBatch batch;

    public MainGui(SpriteBatch batch) {
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        editButton = new OurButton(10,650,270,50,"EDIT MODE:ON",shapeRenderer);
        generateButton = new OurButton(45,590,200,50,"GENERATE (SIZE 11)",shapeRenderer);
        genDecButton = new OurButton(10,590,35,50,"-",shapeRenderer);
        genIncButton = new OurButton(245,590,35,50,"+",shapeRenderer);

    }

    public void setEdit_mode(boolean edit_mode) {
        this.edit_mode = edit_mode;
    }

    public void changeEditMode()
    {
        edit_mode = !edit_mode;
    }

    public void draw(Maze maze)
    {
        calculate_optimal_block_size(maze);
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawMaze(calculate_center_width_for_maze(maze),calculate_center_height_for_maze(maze),maze);
        editButton.draw(batch);
        generateButton.draw(batch);
        genIncButton.draw(batch);
        genDecButton.draw(batch);
        shapeRenderer.end();
    }

    public void update()
    {
        if(editButton.isPressed()) {
            changeEditMode();
            editButton.setText((edit_mode)?"EDIT MODE:ON":"EDIT MODE:OFF");
            generateButton.isActive = !generateButton.isActive;
            genIncButton.isActive = !genIncButton.isActive;
            genDecButton.isActive = !genDecButton.isActive;
        }

    }

    public String buttonListener()
    {
        //dla funkcji niezwiazanych z gui (WIEM ZE BEZNADZIEJNE KACPI PROSZE NIE WYZYWAJ JEST 23 52 KIEDY PISZE TEN KOD)
        if(generateButton.isPressed())return "GENBUTTON";
        if(genDecButton.isPressed())return "GENDEC";
        if(genIncButton.isPressed())return "GENINC";
        return "";
    }

    private void drawMaze(float x, float y ,Maze maze)
    {
        Block highlighted = new Block(-1,-1); //do zapisu aktualnie podswietlonego bloczku, do wyswietlenia potem

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

                if(edit_mode) {
                    if (b.isHighlighted(b.getY() * block_size + x, b.getX() * block_size + y, block_size))
                        highlighted = b;
                    b.updateBlockType(b.getY() * block_size + x, b.getX() * block_size + y, block_size);
                }
            }
        }

        //musi byc rysowany podswietlony dopiero potem, bo inaczej nastepne go pokrywaja
        if(edit_mode && highlighted.getX()!=-1)
        {
            Gdx.gl.glLineWidth(block_size/15);
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(highlighted.getY() * block_size + x, 720 - (highlighted.getX() * block_size) - y - block_size, block_size, block_size,
                    new Color(1.0f, 0.0f, 0.0f, 1.0f),
                    new Color(1.0f, 0.0f, 0.0f, 1.0f),
                    new Color(1.0f, 0.0f, 0.0f, 1.0f),
                    new Color(1.0f, 0.0f, 0.0f, 1.0f));
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        }

    }

    //Raczej tymczasowe funkcje, potem pewnie zrobie lepsze skalowanie

    private void calculate_optimal_block_size(Maze maze)
    {
        block_size = ((720f - 20f) / (float) maze.getMazeGrid().length);
    }
    private float calculate_center_height_for_maze(Maze maze)
    {
        return (720 - (maze.getMazeGrid().length)*block_size)/2;
    }
    private float calculate_center_width_for_maze(Maze maze)
    {
        return (1280 - (maze.getMazeGrid().length)*block_size)/2;
    }

    private void drawTarget(float x, float y, float block_size, Block b)
    {
        shapeRenderer.rectLine(b.getY()*block_size + x,
                720 - (b.getX()*block_size) - y - block_size,
                block_size + b.getY()*block_size + x,
                block_size + 720 - (b.getX()*block_size) - y - block_size,
                block_size/10,
                new Color(1.0f,0.0f,0.0f,0.0f),
                new Color(1.0f,0.0f,0.0f,0.0f));
        shapeRenderer.rectLine(block_size + b.getY()*block_size + x,
                720 - (b.getX()*block_size) - y - block_size,
                b.getY()*block_size + x,
                block_size + 720 - (b.getX()*block_size) - y - block_size,
                block_size/10,
                new Color(1.0f,0.0f,0.0f,0.0f),
                new Color(1.0f,0.0f,0.0f,0.0f));
    }
}
