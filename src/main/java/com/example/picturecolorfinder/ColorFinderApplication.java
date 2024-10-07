package com.example.picturecolorfinder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.picturecolorfinder.DimensionsConstants.WINDOW_HEIGHT_GAP;

public class ColorFinderApplication extends Application {

    private final double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private final double screenHeight = Screen.getPrimary().getBounds().getHeight();
    public static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(ColorFinderApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight - WINDOW_HEIGHT_GAP.value());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}