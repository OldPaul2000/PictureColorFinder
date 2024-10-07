package com.example.picturecolorfinder;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.io.File;
import java.nio.file.FileSystems;

import static com.example.picturecolorfinder.DimensionsConstants.*;

public class MainController {


    @FXML
    private AnchorPane mainContainer;

    @FXML
    private AnchorPane pictureContainer;
    @FXML
    private ImageView pictureView;

    @FXML
    private AnchorPane projectContainer;

    @FXML
    private AnchorPane picturePickerContainer;
    @FXML
    private TextField picturesFolderSource;
    @FXML
    private ChoiceBox<String> picturesList;

    @FXML
    private ScrollPane pickedColors;


    public final double screenWidth = Screen.getPrimary().getBounds().getWidth();
    public final double screenHeight = Screen.getPrimary().getBounds().getHeight();
    private Image currentImage;
    private double initialHeightPercentage = INIT_HEIGHT_PERCENTAGE.value();
    private ImageResizer imageResizer;


    public void initialize(){

        initializePictures();
        initializeMainContainerDimensions();

        setImageResizingHandler();
        setImageMovingHandler();

        setProjectContainerDimensions();
        setProjectContainerPosition();
        setPicturePickerContainerDimensions();
        setImageChanger();

        setPickedColorsDimensions();
        setPickedColorsLayout();
        setPickedColorsContainerContent();

        initializeColorPicker();
    }

    private void setPickedColorsContainerContent(){
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: rgba(107, 201, 138,0); " +
                      "-fx-padding: 5 2 10 2; " +
                      "-fx-border-width: 0; " +
                      "-fx-background-radius: 15px;");
        pickedColors.setContent(pane);
        pickedColors.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pickedColors.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private ColorPicker colorPicker;
    private void initializeColorPicker(){
        colorPicker = new ColorPicker(mainContainer, pictureContainer, pictureView,pickedColors);
        colorPicker.setCursorColorsInfoHint();
        colorPicker.setImageViewHandler();
    }

    public ColorPicker getColorPicker(){
        return colorPicker;
    }

    private void initializePictures(){
        String currentLocation = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
//        String picName = "original-6e8dac9e1dd1a6071a27561a99dfa048.png";
        String picName = "your-image.png";
        currentImage = new Image(currentLocation + File.separator + picName);
        pictureView.setImage(currentImage);
    }

    private void setImageChanger(){
        ImagesSearch imagesSearch = new ImagesSearch(picturesFolderSource,picturesList);
        imagesSearch.addTextFieldHandler();
        imagesSearch.initializeNodesValues();
        ImageChanger changer = new ImageChanger(picturesFolderSource, picturesList, pictureContainer, pictureView);
        changer.setImageChangerHandler();
    }


    private void setPickedColorsDimensions(){
        pickedColors.setPrefHeight(projectContainer.getPrefHeight() - picturePickerContainer.getPrefHeight() - 65);
        pickedColors.setPrefWidth(projectContainer.getPrefWidth());
    }

    private void setPickedColorsLayout() {
        pickedColors.setLayoutY(picturePickerContainer.getPrefHeight() + 1);
    }

    private void setProjectContainerDimensions(){
        projectContainer.setPrefHeight(mainContainer.getPrefHeight());
        projectContainer.setPrefWidth(mainContainer.getPrefWidth() * PROJECT_CONTAINER_PERCENTAGE.value());
    }

    private void setPicturePickerContainerDimensions(){
        AnchorPane parent = (AnchorPane)picturePickerContainer.getParent();
        picturePickerContainer.setPrefHeight(parent.getPrefHeight() * PICTURE_PICKER_PERCENTAGE.value());
        picturePickerContainer.setPrefWidth(parent.getPrefWidth());
    }

    private void initializeMainContainerDimensions(){
        mainContainer.setPrefWidth(screenWidth);
        mainContainer.setPrefHeight(screenHeight);
    }

    private void setProjectContainerPosition(){
        projectContainer.setLayoutY(0);
        projectContainer.setLayoutX(mainContainer.getPrefWidth() - projectContainer.getPrefWidth());
    }

    private void setImageResizingHandler(){
        imageResizer = new ImageResizer(pictureContainer ,pictureView,initialHeightPercentage);
        if(pictureView.getImage() != null){
            imageResizer.setImageViewDimension(pictureContainer);
        }
        imageResizer.initializeHandler();
    }

    private void setImageMovingHandler(){
        ImageMover mover = new ImageMover(pictureContainer);
        mover.setPositioningHandler();
    }

    public ImageResizer getImageResizer(){
        return imageResizer;
    }

}