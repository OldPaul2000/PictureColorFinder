package com.example.picturecolorfinder;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ColorPicker {

    private final String WIDTH = "WIDTH";
    private final String HEIGHT = "HEIGHT";

    private AnchorPane mainContainer;
    private AnchorPane pictureContainer;
    private ImageView imageView;
    private ScrollPane pickedColors;

    private AnchorPane cursorColorsHint;
    private Label hintColorsInfo;
    private PixelReader pixelReader;

    public ColorPicker(AnchorPane mainContainer, AnchorPane pictureContainer, ImageView imageView, ScrollPane pickedColors) {
        this.mainContainer = mainContainer;
        this.pictureContainer = pictureContainer;
        this.imageView = imageView;
        this.pickedColors = pickedColors;
        pixelReader = getImageViewImage().getPixelReader();
    }


    private int pictureX;
    private int pictureY;
    int[] rgb;
    String hex;

    public void setImageViewHandler(){
        pictureContainer.setOnMouseMoved(mouseEvent -> {
            pictureX = calculatePictureX(mouseEvent.getX());
            pictureY = calculatePictureY(mouseEvent.getY());

            rgb = getRGB(pictureX, pictureY);
            hintColorsInfo.setText("RGB(" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")");

            moveHint(mouseEvent.getX(), mouseEvent.getY());
        });

        pictureContainer.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                hex = getHexColor(pictureX, pictureY);
                addNewColorInfoCassette(rgb, hex);
            }
        });

        pictureContainer.setOnMouseEntered(mouseEvent -> {
            showHint();
        });

        pictureContainer.setOnMouseExited(mouseEvent -> {
            hideHint();
        });
    }

    private String getHexColor(int x, int y){
        int pixelX = Math.min((int)getImageViewImage().getWidth() - 1,Math.max(0, x));
        int pixelY = Math.min((int)getImageViewImage().getHeight() - 1,Math.max(0, y));

        int argb = pixelReader.getArgb(pixelX,pixelY);
        String hexValue = Integer.toHexString(argb);

        return hexValue;
    }

    private int[] getRGB(int x, int y){
        int pixelX = Math.min((int)getImageViewImage().getWidth() - 1,Math.max(0, x));
        int pixelY = Math.min((int)getImageViewImage().getHeight() - 1,Math.max(0, y));

        int argb = pixelReader.getArgb(pixelX,pixelY);

        String bits = Integer.toBinaryString(argb);

        int red = Integer.parseInt(bits.substring(8,16), 2);
        int green = Integer.parseInt(bits.substring(16,24), 2);
        int blue = Integer.parseInt(bits.substring(24,32), 2);

        return new int[]{red,green,blue};
    }

    private final String DEFAULT_HINT_STYLE = "-fx-background-color: white;" +
                                              "-fx-background-radius: 8 8 8 0;";
    private final String X_OUTBOUNDS_HINT_STYLE = "-fx-background-color: white;" +
                                                  "-fx-background-radius: 8 8 0 8;";

    private void moveHint(double x, double y){
        double hintWidth = cursorColorsHint.getPrefWidth();
        double hintHeight = cursorColorsHint.getPrefHeight();

        if(y - hintHeight < 0){
            cursorColorsHint.setLayoutY(y + hintHeight);
        }
        else{
            cursorColorsHint.setLayoutY(y - cursorColorsHint.getPrefHeight());
            cursorColorsHint.setStyle(DEFAULT_HINT_STYLE);
        }
        if(x + hintWidth > pictureContainer.getPrefWidth()){
            cursorColorsHint.setLayoutX(x - hintWidth);
            cursorColorsHint.setStyle(X_OUTBOUNDS_HINT_STYLE);
        }
        else{
            cursorColorsHint.setLayoutX(x);
            cursorColorsHint.setStyle(DEFAULT_HINT_STYLE);
        }

    }

    private void showHint(){
        cursorColorsHint.setOpacity(1);
    }

    private void hideHint(){
        cursorColorsHint.setOpacity(0);
    }

    private void setColorCassetteRemoveHandler(VBox parent, AnchorPane cassette){
        cassette.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                parent.getChildren().remove(cassette);
            }
        });
    }

    private AnchorPane colorCassette;
    private void addNewColorInfoCassette(int[] rgb, String hex){
        colorCassette = new AnchorPane();
        colorCassette.setPrefHeight(40);
        colorCassette.setPrefWidth(pickedColors.getPrefWidth() - 6);
        colorCassette.setStyle("-fx-background-color: rgb(90, 139, 107);" +
                               "-fx-background-radius: 15px;");
        
        AnchorPane colorSquare = new AnchorPane();
        colorSquare.setPrefWidth(30);
        colorSquare.setPrefHeight(30);
        colorSquare.setLayoutX(7);
        colorSquare.setLayoutY(5);

        String rgbColor = rgb[0] + "," + rgb[1] + "," + rgb[2];
        colorSquare.setStyle("-fx-background-color: rgb(" + rgbColor + ");" +
                             "-fx-background-radius: 5px");

        Label rgbLabel = new Label();
        rgbLabel.setPrefWidth(110);
        rgbLabel.setPrefHeight(15);
        rgbLabel.setFont(Font.font(12));
        rgbLabel.setAlignment(Pos.CENTER_LEFT);
        rgbLabel.setLayoutX(45);
        rgbLabel.setLayoutY(5);
        rgbLabel.setTextFill(Color.rgb(1,10,8));
        rgbLabel.setText("RGB(" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")");
        
        Label hexLabel = new Label();
        hexLabel.setPrefWidth(110);
        hexLabel.setPrefHeight(15);
        hexLabel.setFont(Font.font(12));
        hexLabel.setAlignment(Pos.CENTER_LEFT);
        hexLabel.setLayoutX(45);
        hexLabel.setLayoutY(20);
        hexLabel.setTextFill(Color.rgb(1,10,8));
        hexLabel.setText("HEX: " + hex);

        colorCassette.getChildren().add(colorSquare);
        colorCassette.getChildren().add(rgbLabel);
        colorCassette.getChildren().add(hexLabel);

        VBox scrollPaneContent = (VBox) pickedColors.getContent();
        scrollPaneContent.getChildren().add(colorCassette);
        setColorCassetteRemoveHandler(scrollPaneContent, colorCassette);
    }

    public void setCursorColorsInfoHint(){
        cursorColorsHint = new AnchorPane();
        cursorColorsHint.setPrefWidth(120);
        cursorColorsHint.setPrefHeight(20);
        cursorColorsHint.setStyle(DEFAULT_HINT_STYLE);

        hintColorsInfo = new Label();
        hintColorsInfo.setPrefWidth(110);
        hintColorsInfo.setPrefHeight(15);
        hintColorsInfo.setFont(Font.font(12));
        hintColorsInfo.setAlignment(Pos.CENTER);
        hintColorsInfo.setLayoutX(cursorColorsHint.getPrefWidth() / 2 - hintColorsInfo.getPrefWidth() / 2);
        hintColorsInfo.setLayoutY(cursorColorsHint.getPrefHeight() / 2 - hintColorsInfo.getPrefHeight() / 2);

        cursorColorsHint.setOpacity(0);
        cursorColorsHint.getChildren().add(hintColorsInfo);
        pictureContainer.getChildren().add(cursorColorsHint);
    }

    // Returns picture X coordinate relative to ImageView and picture ratio
    private int calculatePictureX(double x){
        double imageViewWidth = imageView.getFitWidth();

        if(viewDimensionBiggerThanPicDimension(WIDTH)){
            return (int)Math.round(x / getDimensionRatio(WIDTH));
        }
        return (int)Math.round(x * getDimensionRatio(WIDTH));
    }

    // Returns picture Y coordinate relative to ImageView and picture ratio
    private int calculatePictureY(double y){
        double imageViewHeight = imageView.getFitHeight();

        if(viewDimensionBiggerThanPicDimension(HEIGHT)){
            return (int)Math.round(y / getDimensionRatio(HEIGHT));
        }
        return (int)Math.round(y * getDimensionRatio(HEIGHT));
    }

    // Ratio between ImageView width and Image width itself
    private double getDimensionRatio(String dimension){
        double ratio = 0;
        if(dimension.equals(WIDTH)){
            if(viewDimensionBiggerThanPicDimension(dimension)){
                ratio = imageView.getFitWidth() / getImageViewImage().getWidth();
            }
            else{
                ratio = getImageViewImage().getWidth() / imageView.getFitWidth();
            }
        }
        else{
            if(viewDimensionBiggerThanPicDimension(dimension)){
                ratio = imageView.getFitHeight() / getImageViewImage().getHeight();
            }
            else{
                ratio = getImageViewImage().getHeight() / imageView.getFitHeight();
            }
        }
        return ratio;
    }

    // Returns true if ImageView width or height is bigger than Image width or height(dimension specified by 'dimension' input String)
    private boolean viewDimensionBiggerThanPicDimension(String dimension){
        if(dimension.equals(WIDTH)){
            return imageView.getFitWidth() > getImageViewImage().getWidth();
        }
        return imageView.getFitHeight() > getImageViewImage().getHeight();
    }

    private Image getImageViewImage(){
        return imageView.getImage();
    }

    public void setPixelReader(PixelReader pixelReader){
        this.pixelReader = pixelReader;
    }

    public AnchorPane getCursorColorsHint(){
        return cursorColorsHint;
    }

}
