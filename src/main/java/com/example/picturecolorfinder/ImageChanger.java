package com.example.picturecolorfinder;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class ImageChanger {

    private TextField sourceFolder;
    private ChoiceBox<String> picturesList;
    private AnchorPane pictureContainer;
    private ImageView pictureView;
    private MainController controller = ColorFinderApplication.fxmlLoader.getController();

    public ImageChanger(TextField sourceFolder,
                        ChoiceBox<String> picturesList,
                        AnchorPane pictureContainer,
                        ImageView pictureView) {
        this.sourceFolder = sourceFolder;
        this.picturesList = picturesList;
        this.pictureView = pictureView;
        this.pictureContainer = pictureContainer;
    }

    public void setImageChangerHandler(){

        picturesList.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            Image image = new Image(sourceFolder.getText() + File.separator + newValue);
            pictureView.setImage(image);
            controller.getColorPicker().setPixelReader(image.getPixelReader());
            pictureContainer.setMaxWidth(pictureView.getFitWidth());
            pictureContainer.setMaxHeight(pictureView.getFitHeight());
            ImageResizer imageResizer = controller.getImageResizer();
            imageResizer.setImage(image);
            imageResizer.setImageViewDimension(pictureContainer);
        });
    }



}
