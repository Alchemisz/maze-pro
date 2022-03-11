package com.promaze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.promaze.generateAlgorithms.Sidewinder;
import com.promaze.gui.mainGui;

import java.awt.*;

public class mazepro extends ApplicationAdapter {
	SpriteBatch batch;
	mainGui gui;
	Maze maze;


	int temp = 11;

	@Override
	public void create () {
		batch = new SpriteBatch();
		maze = new Maze(51, 51);
		maze.applyAlgorithm(new Sidewinder());
		gui = new mainGui();
	}

	@Override
	public void render () {
		gui.draw(maze);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) //TESTOWANIE
		{
			maze = new Maze(temp,temp);
			maze.applyAlgorithm(new Sidewinder());
			maze.printMaze();
			temp+=2;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
