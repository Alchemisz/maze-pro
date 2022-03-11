package com.promaze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.promaze.BlockType;
import com.promaze.Maze;
import com.promaze.generateAlgorithms.Sidewinder;
import com.promaze.mazepro;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.title = "MAZE PRO TESTOWA WERSJA. NACISNIJ SPACJE ABY WYGENEROWAC NOWY LABIRYNT";
		new LwjglApplication(new mazepro(), config);
	}
}
