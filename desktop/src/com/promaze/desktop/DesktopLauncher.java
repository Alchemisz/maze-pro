package com.promaze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.promaze.BlockType;
import com.promaze.Maze;
import com.promaze.generateAlgorithms.Sidewinder;
import com.promaze.mazepro;
import net.spookygames.gdx.nativefilechooser.desktop.DesktopFileChooser;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.resizable = true;
		config.title = "MAZE PRO";
		new LwjglApplication(new mazepro(new DesktopFileChooser()), config);
	}
}
