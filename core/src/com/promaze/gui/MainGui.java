package com.promaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;
import com.promaze.statistics.Statistics;

import static com.promaze.gui.edit_type.EDIT;

public class MainGui {

    private ShapeRenderer shapeRenderer;
    public OurButton editButton,generateButton,genIncButton,genDecButton
            ,saveMazeBtn,loadMazeBtn,solveBtn,clearStatisticsButton, sortStatisticsButton, editAgentBtn,editDestinationBtn,editModeBtn;
    public float block_size = 10; // SZEROKOSC I WYSOKOSC W JEDNYM NO LOL TO PRZECIE KWADRAT
    private boolean edit_mode = true;
    private edit_type edit_mode_type = EDIT;
    private SpriteBatch batch;
    private StatisticsGui statisticsGui;
    private Statistics statistics;

    public MainGui(SpriteBatch batch, Statistics statistics) {
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        editButton = new OurButton(10,650,270,50,"EDIT MODE:ON",shapeRenderer);
        generateButton = new OurButton(45,590,200,50,"GENERATE (SIZE 11)",shapeRenderer);
        genDecButton = new OurButton(10,590,35,50,"-",shapeRenderer);
        genIncButton = new OurButton(245,590,35,50,"+",shapeRenderer);

        saveMazeBtn = new OurButton(10,530,135,50,"SAVE MAZE",shapeRenderer);
        loadMazeBtn = new OurButton(10 + 135,530,135,50,"LOAD MAZE",shapeRenderer);

        editModeBtn = new OurButton(10,470,90,50,"EDIT",shapeRenderer);
        editAgentBtn = new OurButton(10 + 90 ,470,90,50,"SET\nAGENT",shapeRenderer);
        editDestinationBtn = new OurButton(10 + 90 * 2,470,90,50,"SET\nTARGET",shapeRenderer);

        editModeBtn.setEnabled(true);
        editAgentBtn.setTextOffsetY(10);
        editDestinationBtn.setTextOffsetY(10);

        solveBtn = new OurButton(10,400,270,50,"SOLVE",shapeRenderer);
        clearStatisticsButton = new OurButton(10, 340, 270, 50, "CLEAR STATISTICS", shapeRenderer);
        sortStatisticsButton = new OurButton(10, 280, 270, 50, "SORT STATISTICS", shapeRenderer);
        this.statistics = statistics;
        this.statisticsGui = new StatisticsGui(shapeRenderer,batch,statistics,700,270);
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
        saveMazeBtn.draw(batch);
        loadMazeBtn.draw(batch);
        solveBtn.draw(batch);
        sortStatisticsButton.draw(batch);
        clearStatisticsButton.draw(batch);
        editModeBtn.draw(batch);
        editAgentBtn.draw(batch);
        editDestinationBtn.draw(batch);
        statisticsGui.draw(1000,10);
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
        if(clearStatisticsButton.isPressed())
        {
            statistics.clearStatistics();
        }
        if(sortStatisticsButton.isPressed())
        {
            statistics.sort();
        }
        if(editModeBtn.isPressed())
        {
            edit_mode_type = EDIT;
            editModeBtn.setEnabled(true);
            editAgentBtn.setEnabled(false);
            editDestinationBtn.setEnabled(false);
        }
        if(editAgentBtn.isPressed())
        {
            edit_mode_type = edit_type.SETAGENT;
            editModeBtn.setEnabled(false);
            editAgentBtn.setEnabled(true);
            editDestinationBtn.setEnabled(false);
        }
        if(editDestinationBtn.isPressed())
        {
            edit_mode_type = edit_type.SETTARGET;
            editModeBtn.setEnabled(false);
            editAgentBtn.setEnabled(false);
            editDestinationBtn.setEnabled(true);
        }

    }

    public String buttonListener()
    {
        //dla funkcji niezwiazanych z gui (WIEM ZE BEZNADZIEJNE KACPI PROSZE NIE WYZYWAJ JEST 23 52 KIEDY PISZE TEN KOD)
        if(generateButton.isPressed())return "GENBUTTON";
        if(genDecButton.isPressed())return "GENDEC";
        if(genIncButton.isPressed())return "GENINC";
        if (saveMazeBtn.isPressed()) return "SAVE_MAZE";
        if (loadMazeBtn.isPressed()) return "LOAD_MAZE";
        if(solveBtn.isPressed()) return "SOLVE_MAZE";
        return "";
    }

    private void drawMaze(float x, float y ,Maze maze)
    {
        Block highlighted = new Block(-1,-1); //do zapisu aktualnie podswietlonego bloczku, do wyswietlenia potem

        for (Block bArray[]:maze.getMazeGrid()){
            for(Block b:bArray) {
                if(b.getBlockType().equals(BlockType.WALL))
                    shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                        new Color(0.0f,0.0f,0.0f,1.0f),
                        new Color(0.0f,0.0f,0.0f,1.0f),
                        new Color(0.0f,0.0f,0.0f,1.0f),
                        new Color(0.0f,0.0f,0.0f,1.0f));
                else if(b.getBlockType().equals(BlockType.AGENT))
                    shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                            new Color(0.0f,1.0f,0.0f,1.0f),
                            new Color(0.0f,1.0f,0.0f,1.0f),
                            new Color(0.0f,1.0f,0.0f,1.0f),
                            new Color(0.0f,1.0f,0.0f,1.0f));
                else if(b.getBlockType().equals(BlockType.VISITED))
                    shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                            new Color(1.0f,0.7f,0.0f,1.0f),
                            new Color(1.0f,0.7f,0.0f,1.0f),
                            new Color(1.0f,0.7f,0.0f,1.0f),
                            new Color(1.0f,0.7f,0.0f,1.0f));
                else if(b.getBlockType().equals(BlockType.END))
                    shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                            new Color(1.0f,1.0f,0.0f,1.0f),
                            new Color(1.0f,1.0f,0.0f,1.0f),
                            new Color(1.0f,1.0f,0.0f,1.0f),
                            new Color(1.0f,1.0f,0.0f,1.0f));
                else if(b.getBlockType().equals(BlockType.PATH))
                    shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                            new Color(1.0f,0.0f,0.0f,1.0f),
                            new Color(1.0f,0.0f,0.0f,1.0f),
                            new Color(1.0f,0.0f,0.0f,1.0f),
                            new Color(1.0f,0.0f,0.0f,1.0f));
                else shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                        new Color(1.0f,1.0f,1.0f,1.0f),
                        new Color(1.0f,1.0f,1.0f,1.0f),
                        new Color(1.0f,1.0f,1.0f,1.0f),
                        new Color(1.0f,1.0f,1.0f,1.0f));

                if(edit_mode) {
                    if (b.isHighlighted(b.getY() * block_size + x, b.getX() * block_size + y, block_size))
                        highlighted = b;
                    switch(edit_mode_type){
                        case EDIT:
                            b.updateBlockType(b.getY() * block_size + x, b.getX() * block_size + y, block_size);
                            break;
                        case SETAGENT:
                            b.updateBlockTypeSetActor(b.getY() * block_size + x, b.getX() * block_size + y, block_size,maze);
                            break;
                        case SETTARGET:
                            b.updateBlockTypeSetEnd(b.getY() * block_size + x, b.getX() * block_size + y, block_size,maze);
                            break;
                    }

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
