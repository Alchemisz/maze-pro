package com.promaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
            ,saveMazeBtn,loadMazeBtn,solveBtn,clearStatisticsButton, sortStatisticsButton,
            editAgentBtn,editDestinationBtn,editModeBtn,clearPathBtn,repositionButton,algoMenuButton,
            algoSelectButton,algoSelectLeft,algoSelectRight,generatorSelectButton,generatorSelectLeft,generatorSelectRight,solveImpossible;
    //Algo submenu
    public OurButton iterSelect, iterSelectLeft, iterSelectRight;
    public OurButton distanceSelect, distanceSelectLeft, distanceSelectRight;
    public OurButton evaporateSelect, evaporateLeft, evaporateRight;
    public OurButton pheromoneSelect, pheromoneLeft, pheromoneRight;
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

        PositionHelper editPH = new PositionHelper(650,10);
        PositionHelper algoPH = new PositionHelper(650,10);

        editButton = new OurButton(10,650,135,50,"EDIT MODE",shapeRenderer);
        editButton.enable();
        algoMenuButton = new OurButton(10 + 135,650,135,50,"ALGO MODE",shapeRenderer);
        algoMenuButton.disable();

        generatorSelectButton = new OurButton(45,editPH.getNewRelativePosition(50),200,50,"SIDEWINDER",shapeRenderer);
        generatorSelectLeft = new OurButton(10,editPH.getRelativePosition(),35,50,"<",shapeRenderer);
        generatorSelectRight = new OurButton(245,editPH.getRelativePosition(),35,50,">",shapeRenderer);
        generatorSelectButton.isHoverable =false;

        generateButton = new OurButton(45,editPH.getNewRelativePosition(50),200,50,"GENERATE (SIZE 11)",shapeRenderer);
        genDecButton = new OurButton(10,editPH.getRelativePosition(),35,50,"-",shapeRenderer);
        genIncButton = new OurButton(245,editPH.getRelativePosition(),35,50,"+",shapeRenderer);

        saveMazeBtn = new OurButton(10,editPH.getNewRelativePosition(50),135,50,"SAVE MAZE",shapeRenderer);
        loadMazeBtn = new OurButton(10 + 135,editPH.getRelativePosition(),135,50,"LOAD MAZE",shapeRenderer);

        editModeBtn = new OurButton(10,editPH.getNewRelativePosition(50),90,50,"EDIT",shapeRenderer);
        editAgentBtn = new OurButton(10 + 90 ,editPH.getRelativePosition(),90,50,"SET\nAGENT",shapeRenderer);
        editDestinationBtn = new OurButton(10 + 90 * 2,editPH.getRelativePosition(),90,50,"SET\nTARGET",shapeRenderer);

        clearPathBtn = new OurButton(10 ,editPH.getNewRelativePosition(35),270,35,"CLEAR PATHS",shapeRenderer);


        editModeBtn.setEnabled(true);
        editAgentBtn.setTextOffsetY(10);
        editDestinationBtn.setTextOffsetY(10);

        //relacje przycisków
        editButton.addChild(generateButton);
        editButton.addChild(genDecButton);
        editButton.addChild(genIncButton);
        editButton.addChild(saveMazeBtn );
        editButton.addChild(loadMazeBtn );
        editButton.addChild(editModeBtn );
        editButton.addChild(editAgentBtn);
        editButton.addChild(editDestinationBtn);
        editButton.addChild(clearPathBtn);
        editButton.addChild(editModeBtn);
        editButton.addChild(editAgentBtn);
        editButton.addChild(editDestinationBtn);
        editButton.addChild(generatorSelectRight);
        editButton.addChild(generatorSelectButton);
        editButton.addChild(generatorSelectLeft);


        solveBtn = new OurButton(10,algoPH.getNewRelativePosition(50),270,50,"SOLVE",shapeRenderer);
        solveImpossible = new OurButton(10,algoPH.getRelativePosition(),270,50,"SOLVE (CLEAR STATISTICS)",shapeRenderer);
        solveBtn.deactivate();
        solveImpossible.deactivate();
        solveImpossible.setDarkTheme();
        solveImpossible.isHoverable = false;

        algoSelectButton = new OurButton(45,algoPH.getNewRelativePosition(50),200,50,"ANT COLONY",shapeRenderer);
        algoSelectLeft = new OurButton(10,algoPH.getRelativePosition(),35,50,"<",shapeRenderer);
        algoSelectRight = new OurButton(245,algoPH.getRelativePosition(),35,50,">",shapeRenderer);

        repositionButton = new OurButton(10,algoPH.getNewRelativePosition(50),270,50,"REPOSITION ACTORS",shapeRenderer);

        repositionButton.deactivate();
        algoSelectButton.deactivate();
        algoSelectButton.isHoverable = false;
        algoSelectLeft.deactivate();
        algoSelectRight.deactivate();

        //Algo SubMenu

        iterSelect = new OurButton(45,editPH.getNewRelativePosition(50),200,50,"5000",shapeRenderer);
        iterSelectLeft = new OurButton(10,editPH.getRelativePosition(),35,50,"<",shapeRenderer);
        iterSelectRight = new OurButton(245,editPH.getRelativePosition(),35,50,">",shapeRenderer);
        iterSelect.isHoverable =false;
        iterSelect.setLabel("ITERATION:");

        algoMenuButton.addChild(iterSelect);
        algoMenuButton.addChild(iterSelectLeft);
        algoMenuButton.addChild(iterSelectRight);

        distanceSelect = new OurButton(45,editPH.getNewRelativePosition(50),200,50,"0.02",shapeRenderer);
        distanceSelectLeft = new OurButton(10,editPH.getRelativePosition(),35,50,"<",shapeRenderer);
        distanceSelectRight = new OurButton(245,editPH.getRelativePosition(),35,50,">",shapeRenderer);
        distanceSelect.isHoverable =false;
        distanceSelect.setLabel("DISTANCE:");

        algoMenuButton.addChild(distanceSelect);
        algoMenuButton.addChild(distanceSelectLeft);
        algoMenuButton.addChild(distanceSelectRight);

        evaporateSelect = new OurButton(45,editPH.getNewRelativePosition(50),200,50,"0.002",shapeRenderer);
        evaporateLeft = new OurButton(10,editPH.getRelativePosition(),35,50,"<",shapeRenderer);
        evaporateRight = new OurButton(245,editPH.getRelativePosition(),35,50,">",shapeRenderer);
        evaporateSelect.isHoverable =false;
        evaporateSelect.setLabel("EVAPORATION:");

        algoMenuButton.addChild(evaporateSelect);
        algoMenuButton.addChild(evaporateLeft);
        algoMenuButton.addChild(evaporateRight);

        pheromoneSelect = new OurButton(45,editPH.getNewRelativePosition(50),200,50,"1",shapeRenderer);
        pheromoneLeft = new OurButton(10,editPH.getRelativePosition(),35,50,"<",shapeRenderer);
        pheromoneRight = new OurButton(245,editPH.getRelativePosition(),35,50,">",shapeRenderer);
        pheromoneSelect.isHoverable =false;
        pheromoneSelect.setLabel("PHEROMONE:");

        algoMenuButton.addChild(pheromoneSelect);
        algoMenuButton.addChild(pheromoneLeft);
        algoMenuButton.addChild(pheromoneRight);

        //-----------

        algoMenuButton.addChild(solveBtn);
        algoMenuButton.addChild(solveImpossible);
        algoMenuButton.addChild(algoSelectButton);
        algoMenuButton.addChild(algoSelectLeft);
        algoMenuButton.addChild(algoSelectRight);
        algoMenuButton.addChild(repositionButton);

        algoMenuButton.deactivateChildButtons();

        clearStatisticsButton = new OurButton(1002, 57, 266, 50, "CLEAR STATISTICS", shapeRenderer);
        sortStatisticsButton = new OurButton(1002, 10, 266, 50, "SORT STATISTICS", shapeRenderer);
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

    public void setEditMode(boolean mode)
    {
        edit_mode = mode;
    }



    public void draw(Maze maze)
    {
        calculate_optimal_block_size(maze);
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawMaze(calculate_center_width_for_maze(maze),calculate_center_height_for_maze(maze),maze);
        statisticsGui.draw(1000,10);
        editButton.draw(batch);
        generateButton.draw(batch);
        genIncButton.draw(batch);
        genDecButton.draw(batch);
        saveMazeBtn.draw(batch);
        loadMazeBtn.draw(batch);
        solveImpossible.draw(batch);
        solveBtn.draw(batch);
        sortStatisticsButton.draw(batch);
        clearStatisticsButton.draw(batch);
        generatorSelectButton.draw(batch);
        generatorSelectLeft.draw(batch);
        generatorSelectRight.draw(batch);
        editModeBtn.draw(batch);
        editAgentBtn.draw(batch);
        editDestinationBtn.draw(batch);
        clearPathBtn.draw(batch);
        algoMenuButton.draw(batch);
        algoSelectButton.draw(batch);
        algoSelectLeft.draw(batch);
        algoSelectRight.draw(batch);
        repositionButton.draw(batch);


        //ALG submenu

        if(algoSelectButton.getText().equals("ANT COLONY")) {
            iterSelect.draw(batch);
            iterSelectLeft.draw(batch);
            iterSelectRight.draw(batch);

            distanceSelect.draw(batch);
            distanceSelectLeft.draw(batch);
            distanceSelectRight.draw(batch);

            evaporateSelect.draw(batch);
            evaporateLeft.draw(batch);
            evaporateRight.draw(batch);

            pheromoneSelect.draw(batch);
            pheromoneLeft.draw(batch);
            pheromoneRight.draw(batch);
        }
        shapeRenderer.end();
    }

    public void update()
    {
        if(editButton.isPressed()) {
            setEditMode(true);
            editButton.enable();
            if(editButton.isEnabled)
                editButton.activateChildButtons();
            else editButton.deactivateChildButtons();
            algoMenuButton.disable();
            algoMenuButton.deactivateChildButtons();
        }
        if(clearStatisticsButton.isPressed())
        {
            solveBtn.activate();
            statistics.clearStatistics();
        }
        if(sortStatisticsButton.isPressed())
        {
            statistics.sort();
        }

        if(algoMenuButton.isPressed())
        {
            algoMenuButton.enable();
            editButton.disable();
            setEditMode(false);
            editButton.deactivateChildButtons();
            if(algoMenuButton.isEnabled)
                algoMenuButton.activateChildButtons();
            else algoMenuButton.deactivateChildButtons();

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
        if(clearPathBtn.isPressed()) return "CLEAR_PATH";
        if(algoSelectLeft.isPressed()) return "ALGO_LEFT";
        if(algoMenuButton.isPressed()) return "ALGO_MENU";
        if(algoSelectRight.isPressed()) return "ALGO_RIGHT";
        if(repositionButton.isPressed()) return "REPOSITION";
        if(generatorSelectRight.isPressed()) return "GEN_RIGHT";
        if(generatorSelectLeft.isPressed()) return "GEN_LEFT";
        if(iterSelectLeft.isPressed()) return "ITER_LEFT";
        if(iterSelectRight.isPressed()) return "ITER_RIGHT";
        if(distanceSelectRight.isPressed()) return "DISTANCE_RIGHT";
        if(distanceSelectLeft.isPressed()) return "DISTANCE_LEFT";
        if(evaporateLeft.isPressed()) return "EVAPORATE_LEFT";
        if(evaporateRight.isPressed()) return "EVAPORATE_RIGHT";
        if(pheromoneLeft.isPressed()) return "PHEROMONE_LEFT";
        if(pheromoneRight.isPressed()) return "PHEROMONE_RIGHT";
        return "";
    }

    private void drawMaze(float x, float y ,Maze maze)
    {
        Block highlighted = new Block(-1,-1); //do zapisu aktualnie podswietlonego bloczku, do wyswietlenia potem
        BitmapFont font = new BitmapFont();
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
                else if(b.getBlockType().equals(BlockType.PHEROMONE)) {
                    shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
                            new Color(b.getIntensity()*1.0f,0.0f,1.0f,1.0f),
                            new Color(b.getIntensity()*1.0f,0.0f,1.0f,1.0f),
                            new Color(b.getIntensity()*1.0f,0.0f,1.0f,1.0f),
                            new Color(b.getIntensity()*1.0f,0.0f,1.0f,1.0f));
                    /*shapeRenderer.end();
                    batch.begin();
                    font.draw(batch,String.format("%.2f", b.getIntensity()),b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size + 30,block_size,1,false);
                    batch.end();
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);*/
                } else shapeRenderer.rect(b.getY()*block_size + x,720 - (b.getX()*block_size) - y - block_size,block_size,block_size,
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
    //UPDATE 28.05.2022 XD NO NA PEWNO

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
