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
	int mazeSize = 11;

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
		if(gui.buttonListener().equals("GENINC")) {
			mazeSize += 2;
			gui.generateButton.setText("GENERATE (SIZE "+ mazeSize +")");
		}
		if(gui.buttonListener().equals("GENDEC")) {
			mazeSize = (mazeSize >= 7) ? (mazeSize - 2) : mazeSize;
			gui.generateButton.setText("GENERATE (SIZE "+ mazeSize +")");
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || gui.buttonListener().equals("GENBUTTON")) //TESTOWANIE
		{
			maze = new Maze(mazeSize, mazeSize);
			maze.applyAlgorithm(new Sidewinder());
			gui.generateButton.setText("GENERATE (SIZE "+ mazeSize +")");
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
