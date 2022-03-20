package com.promaze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.promaze.fileManager.FileManager;
import com.promaze.generateAlgorithms.Sidewinder;
import com.promaze.gui.MainGui;
import com.promaze.solvers.RecurrentSolver;
import com.promaze.solvers.Solver;
import com.promaze.statistics.StatisticItem;
import com.promaze.statistics.Statistics;
import com.promaze.tools.stopwatch.Stopwatch;
import com.promaze.tools.stopwatch.StopwatchImpl;
import com.promaze.tools.stopwatch.TimeCycle;
import net.spookygames.gdx.nativefilechooser.NativeFileChooser;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserCallback;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Random;

public class mazepro extends ApplicationAdapter{
	SpriteBatch batch;
	MainGui gui;
	Maze maze;
	int mazeSize = 11;
	Statistics statistics;

	NativeFileChooser fileChooser;
	public mazepro(NativeFileChooser fileChooser) {
		super();
		this.fileChooser = fileChooser;
		statistics = new Statistics();
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		maze = new Maze(51, 51);
		maze.applyAlgorithm(new Sidewinder());
		gui = new MainGui(batch,statistics);
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

		if (gui.buttonListener().equals("SAVE_MAZE")){
			new FileManager(fileChooser).saveMaze(maze);
		}

		if (gui.buttonListener().equals("LOAD_MAZE")){
			new FileManager(fileChooser).loadMaze(maze);
		}

		if (gui.buttonListener().equals("SOLVE_MAZE")){
			Solver solver = new RecurrentSolver();

			Stopwatch stopwatch = new StopwatchImpl();
			stopwatch.start();
			List<Maze> steps = solver.solve(maze);
			TimeCycle timeCycle = stopwatch.stop();
			System.out.println(timeCycle);


			this.statistics.addStatisticItem(new StatisticItem(timeCycle,maze.getSize()));
			System.out.println(statistics);

			if(!steps.isEmpty()) {
				maze = steps.get(steps.size()-1);
			}
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || gui.buttonListener().equals("GENBUTTON")) //TESTOWANIE
		{
			maze = new Maze(mazeSize, mazeSize);
			maze.applyAlgorithm(new Sidewinder());
			//UWAGA BOMBA
			Block[][] grid = maze.getMazeGrid();
			Random random = new Random();
			int _x1 = Math.abs(random.nextInt())%grid.length;
			int _x2 = Math.abs(random.nextInt())%grid.length;
			int _y1 = Math.abs(random.nextInt())%grid.length;
			int _y2 = Math.abs(random.nextInt())%grid.length;
			grid[_x1][_y1].setBlockType(BlockType.AGENT);
			grid[_x2][_y2].setBlockType(BlockType.END);
			//KONIEC
			gui.generateButton.setText("GENERATE (SIZE "+ mazeSize +")");
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
