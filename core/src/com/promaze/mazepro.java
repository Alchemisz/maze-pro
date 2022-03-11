package com.promaze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.promaze.generateAlgorithms.Sidewinder;
import com.promaze.gui.MainGui;

public class mazepro extends ApplicationAdapter {
	SpriteBatch batch;
	MainGui gui;
	Maze maze;


	int temp = 11;

	@Override
	public void create () {
		batch = new SpriteBatch();
		maze = new Maze(51, 51);
		maze.applyAlgorithm(new Sidewinder());
		gui = new MainGui(batch);
	}

	@Override
	public void render () {
		gui.update();
		gui.draw(maze);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || gui.checkGenerateButton()) //TESTOWANIE
		{
			maze = new Maze(temp,temp);
			maze.applyAlgorithm(new Sidewinder());
			temp+=2;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
