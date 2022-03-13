package com.promaze.fileManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.promaze.Maze;
import net.spookygames.gdx.nativefilechooser.NativeFileChooser;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserCallback;
import net.spookygames.gdx.nativefilechooser.NativeFileChooserConfiguration;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class FileManager {

    private NativeFileChooser fileChooser;

    public FileManager(NativeFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public void loadMaze(Maze maze){
        // Configure
        NativeFileChooserConfiguration conf = new NativeFileChooserConfiguration();
        // Starting from user's dir
        conf.directory = Gdx.files.absolute(System.getProperty("user.home"));

        // Filter out all files which do not have the .ogg extension and are not of an audio MIME type - belt and braces
        conf.mimeFilter = "audio/*";
        conf.nameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("ogg");
            }
        };

        // Add a nice title
        conf.title = "Choose file";

        fileChooser.chooseFile(conf, new NativeFileChooserCallback() {
            @Override
            public void onFileChosen(FileHandle file) {
                // Do stuff with file, yay!
                System.out.println("No implementation!");
            }

            @Override
            public void onCancellation() {
                // Warn user how rude it can be to cancel developer's effort
            }

            @Override
            public void onError(Exception exception) {
                // Handle error (hint: use exception type)
            }
        });
    }

    public void saveMaze(Maze maze){
        JFileChooser fileChooser = null;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            fileChooser = new JFileChooser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            String str = "Hello";
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(str);

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
