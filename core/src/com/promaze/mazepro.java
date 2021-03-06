package com.promaze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.promaze.fileManager.FileManager;
import com.promaze.generateAlgorithms.BinaryTree;
import com.promaze.generateAlgorithms.MazeGenerator;
import com.promaze.generateAlgorithms.Sidewinder;
import com.promaze.gui.MainGui;
import com.promaze.gui.solver_type;
import com.promaze.solvers.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class mazepro extends ApplicationAdapter{
	SpriteBatch batch;
	MainGui gui;
	Maze maze;
	int mazeSize = 11;
	Statistics statistics;

	ArrayList<Solver> solvers = new ArrayList<Solver>();
	int solvers_index = 0;

	ArrayList<MazeGenerator> generators = new ArrayList<MazeGenerator>();
	int generators_index = 0;

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
		maze.applyAlgorithm(new BinaryTree());
		gui = new MainGui(batch,statistics);
		solvers.add(new AntColonySolver());
		solvers.add(new RecurrentSolver());
		generators.add(new Sidewinder());
		generators.add(new BinaryTree());

		maze = new Maze(mazeSize, mazeSize);
		maze.applyAlgorithm(generators.get(generators_index));
		//UWAGA BOMBA
		Block[][] grid = maze.getMazeGrid();
		Random random = new Random();
		int _x1,_y1,_x2,_y2;
		do {
			_x1 = Math.abs(random.nextInt()) % grid.length;
			_x2 = Math.abs(random.nextInt()) % grid.length;
			_y1 = Math.abs(random.nextInt()) % grid.length;
			_y2 = Math.abs(random.nextInt()) % grid.length;
		}while(!grid[_x1][_y1].getBlockType().equals(BlockType.AIR) || !grid[_x2][_y2].getBlockType().equals(BlockType.AIR));
		grid[_x1][_y1].setBlockType(BlockType.AGENT);
		grid[_x2][_y2].setBlockType(BlockType.END);
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

		if (gui.buttonListener().equals("CLEAR_PATH")){
			maze.removePaths();
		}

		if (gui.buttonListener().equals("SOLVE_MAZE")){
			maze.removePaths();
			if(!maze.areActorsPlaced())maze.repositionActors();
			Solver solver = this.solvers.get(solvers_index);
			Stopwatch stopwatch = new StopwatchImpl();
			stopwatch.start();
			List<Maze> steps = solver.solve(maze);
			TimeCycle timeCycle = stopwatch.stop();
			System.out.println(timeCycle);


			this.statistics.addStatisticItem(new StatisticItem(timeCycle,maze.getSize(),solver.getName(),solver.getLength()));
			System.out.println(statistics);

			if(statistics.getStatisticItems().size()==20)
			{
				gui.solveBtn.deactivate();
			}
			if(!steps.isEmpty()) {
				maze = steps.get(steps.size()-1);
			}
		}

		if (gui.buttonListener().equals("ALGO_LEFT")){
			solvers_index = (solvers_index == 0)?(solvers_index):(solvers_index-1);
			gui.algoSelectButton.setText(solvers.get(solvers_index).getName());
		}
		if (gui.buttonListener().equals("ALGO_RIGHT")){
			solvers_index = (solvers_index == solvers.size() - 1)?(solvers_index):(solvers_index+1);
			gui.algoSelectButton.setText(solvers.get(solvers_index).getName());
		}
		if (gui.buttonListener().equals("GEN_LEFT")){
			generators_index = (generators_index == 0)?(generators_index):(generators_index-1);
			gui.generatorSelectButton.setText(generators.get(generators_index).getName());
		}
		if (gui.buttonListener().equals("GEN_RIGHT")){
			generators_index = (generators_index == generators.size() - 1)?(generators_index):(generators_index+1);
			gui.generatorSelectButton.setText(generators.get(generators_index).getName());
		}
		if (gui.buttonListener().equals("REPOSITION")){
			maze.repositionActors();
		}

		//ALG SUbMenu

		if(gui.buttonListener().equals("ITER_LEFT")) {
			int currentIter = Integer.parseInt(gui.iterSelect.getText());
			if(currentIter > 5000) {
				currentIter -= 5000;
				AntColonySolver.iterCount = currentIter;
				gui.iterSelect.setText(String.valueOf(currentIter));
			}
		}

		if(gui.buttonListener().equals("ITER_RIGHT")) {
			int currentIter = Integer.parseInt(gui.iterSelect.getText());
			if(currentIter < 80000) {
				currentIter += 5000;
				AntColonySolver.iterCount = currentIter;
				gui.iterSelect.setText(String.valueOf(currentIter));
			}
		}

		if(gui.buttonListener().equals("DISTANCE_LEFT")) {
			float currentIter = Float.parseFloat(gui.distanceSelect.getText());
			if(currentIter > 0.002f) {
				currentIter -= 0.002f;
				AntColonySolver.distanceModifier = currentIter;
				gui.distanceSelect.setText(String.valueOf(currentIter));
			}
		}

		if(gui.buttonListener().equals("ALGO_MENU")) {
			if(statistics.getStatisticItems().size()==20)
			{
				gui.solveBtn.deactivate();
			}
		}

		if(gui.buttonListener().equals("DISTANCE_RIGHT")) {
			float currentIter = Float.parseFloat(gui.distanceSelect.getText());
			if(currentIter < 1f) {
				currentIter += 0.002f;
				AntColonySolver.distanceModifier = currentIter;
				gui.distanceSelect.setText(String.valueOf(currentIter));
			}
		}

		if(gui.buttonListener().equals("EVAPORATE_LEFT")) {
			float currentIter = Float.parseFloat(gui.evaporateSelect.getText());
			if(currentIter > 0.002f) {
				currentIter -= 0.001f;
				AntColonySolver.evaporateRate = currentIter;
				gui.evaporateSelect.setText(String.valueOf(currentIter));
			}
		}

		if(gui.buttonListener().equals("EVAPORATE_RIGHT")) {
			float currentIter = Float.parseFloat(gui.evaporateSelect.getText());
			if(currentIter < 0.01f) {
				currentIter += 0.001f;
				AntColonySolver.evaporateRate = currentIter;
				gui.evaporateSelect.setText(String.valueOf(currentIter));
			}
		}

		if(gui.buttonListener().equals("PHEROMONE_LEFT")) {
			float currentIter = Float.parseFloat(gui.pheromoneSelect.getText());
			if(currentIter > 0.6f) {
				currentIter -= 0.3f;
				AntColonySolver.baseReturnPheromone = currentIter;
				gui.pheromoneSelect.setText(String.valueOf(currentIter));
			}
		}

		if(gui.buttonListener().equals("PHEROMONE_RIGHT")) {
			float currentIter = Float.parseFloat(gui.pheromoneSelect.getText());
			if(currentIter < 15f) {
				currentIter += 0.3f;
				AntColonySolver.baseReturnPheromone = currentIter;
				gui.pheromoneSelect.setText(String.valueOf(currentIter));
			}
		}

		//--------


		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || gui.buttonListener().equals("GENBUTTON")) //TESTOWANIE
		{
			maze = new Maze(mazeSize, mazeSize);
			maze.applyAlgorithm(generators.get(generators_index));
			//UWAGA BOMBA
			Block[][] grid = maze.getMazeGrid();
			Random random = new Random();
			int _x1,_y1,_x2,_y2;
			do {
				_x1 = Math.abs(random.nextInt()) % grid.length;
				_x2 = Math.abs(random.nextInt()) % grid.length;
				_y1 = Math.abs(random.nextInt()) % grid.length;
				_y2 = Math.abs(random.nextInt()) % grid.length;
			}while(!grid[_x1][_y1].getBlockType().equals(BlockType.AIR) || !grid[_x2][_y2].getBlockType().equals(BlockType.AIR));
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
