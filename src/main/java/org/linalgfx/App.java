package org.linalgfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

/**
 * JavaFX Application which handles the layout of the GUI
 */
public class App extends Application {



    /**
     * The start method instantiates the canvaspanes, and starts the canvas animation loops, as well as
     * define the other visible elemements like the toolBar and the inputField
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("program.fxml"))));
            scene.getStylesheets().add(resourceURL("stylesheets/style.css"));
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
            stop();
        }
    }
    /**
     * Stops the javafx application and terminates gui
     */
    @Override
    public void stop(){
        System.exit(0);
    }

    /**
     * Returns the full external path of a resource relative to the resource folder for this class (App)
     */
    public static String resourceURL(String path){
        return App.class.getResource(path).toExternalForm();
    }

    /**
     * Launches the program
     */
    public static void main(String[] args) {
        launch();
    }
}