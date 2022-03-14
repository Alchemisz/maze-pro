package com.promaze.fileManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.promaze.Block;
import com.promaze.BlockType;
import com.promaze.Maze;
import net.spookygames.gdx.nativefilechooser.NativeFileChooser;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserCallback;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserConfiguration;
import org.graalvm.compiler.hotspot.stubs.OutOfBoundsExceptionStub;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Arrays;

public class FileManager {

    private NativeFileChooser fileChooser;

    public FileManager(NativeFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public void loadMaze(final Maze maze){
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();

        conf.directory = Gdx.files.absolute(System.getProperty("user.home"));

        conf.mimeFilter = "audio/*";
        conf.nameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("ogg");
            }
        };

        conf.title = "Choose file";

        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                Block[][] blocks = loadFromFile(file);
                maze.setMazeGrid(blocks);

            }

            @Override
            public void onCancellation() {

            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

    public void saveMaze(Maze maze){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFileChooser fileChooser = new JFileChooser();

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                //if not end with .txt then set the .txt extension
                if (!file.getName().endsWith(".txt")){
                    file = new File(file.getAbsolutePath() + ".txt");
                }

                saveToFile(file, maze);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(File file, Maze maze){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            for (int row = 0; row < maze.getMazeGrid().length; row++) {
                for (int column = 0; column < maze.getMazeGrid().length; column++) {
                    writer.write(maze.getMazeGrid()[row][column].toString());
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("ERROR! saveToFile method");
        }
    }

    private Block[][] loadFromFile(FileHandle file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.file().getAbsolutePath()));

            int counter = 0;
            while (reader.readLine() != null){
                counter++;
            }
            int length = (int) Math.sqrt(counter);

            reader = new BufferedReader(new FileReader(file.file().getAbsolutePath()));

            Block[][] newMazeGrid = new Block[length][length];

            for (int row = 0; row < length; row++) {
                for (int column = 0; column < length; column++) {
                    String line = reader.readLine();
                    String[] singleBlockElements = line.split(";");
                    newMazeGrid[row][column] = new Block(singleBlockElements);
                }
            }

            return newMazeGrid;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
