package com.promaze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.promaze.BlockType;
import com.promaze.Maze;
import com.promaze.generateAlgorithms.Sidewinder;
import com.promaze.mazepro;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Maze maze = new Maze(9, 9);
		maze.applyAlgorithm(new Sidewinder());
		maze.printMaze();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new mazepro(), config);
	}
}
