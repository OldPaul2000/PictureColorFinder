package com.example.picturecolorfinder;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

public class ImageMover {

    private AnchorPane pictureContainer;
    private MainController controller = ColorFinderApplication.fxmlLoader.getController();

    public ImageMover(AnchorPane pictureContainer) {
        this.pictureContainer = pictureContainer;
    }

    private double cursorXPosition;
    private double cursorYPosition;

    public void setPositioningHandler(){
        pictureContainer.setOnMouseDragged(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                pictureContainer.setLayoutX(mouseEvent.getSceneX() - cursorXPosition);
                pictureContainer.setLayoutY(mouseEvent.getSceneY() - cursorYPosition);
            }
        });

        pictureContainer.setOnMousePressed(mouseEvent -> {
           ColorPicker colorPicker = controller.getColorPicker();
            if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                cursorXPosition = mouseEvent.getX();
                cursorYPosition = mouseEvent.getY();
                pictureContainer.setCursor(Cursor.CROSSHAIR);
                if(colorPicker != null){
                    colorPicker.getCursorColorsHint().setOpacity(0);
                }
            }
        });

        pictureContainer.setOnMouseReleased(mouseEvent -> {
            ColorPicker colorPicker = controller.getColorPicker();
            if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                pictureContainer.setCursor(Cursor.DEFAULT);
                if(colorPicker != null){
                    colorPicker.getCursorColorsHint().setOpacity(1);
                }
            }
        });
    }
}
