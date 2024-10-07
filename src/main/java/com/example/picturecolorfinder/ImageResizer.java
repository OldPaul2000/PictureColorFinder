package com.example.picturecolorfinder;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ImageResizer {

    private AnchorPane pictureContainer;
    private ImageView imageView;
    private Image image;
    private double heightPercentage;
    private final double RESIZING_FACTOR = 0.05;
    private MainController controller = ColorFinderApplication.fxmlLoader.getController();

    public ImageResizer(AnchorPane pictureContainer, ImageView imageView, double heightPercentage) {
        this.pictureContainer = pictureContainer;
        this.imageView = imageView;
        this.heightPercentage = heightPercentage;
        image = imageView.getImage();
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void initializeHandler() {
        pictureContainer.setOnScroll(scrollEvent -> {
            if(scrollEvent.getDeltaY() > 0){
                heightPercentage += RESIZING_FACTOR;
                setImageViewDimension(pictureContainer);
            }
            else{
                heightPercentage -= RESIZING_FACTOR;
                setImageViewDimension(pictureContainer);
            }
        });
    }

    public void setImageViewDimension(AnchorPane pictureContainer){
        pictureContainer.setPrefHeight(controller.screenHeight * heightPercentage);

        if(imageWidthBiggerThanHeight()){
            pictureContainer.setPrefWidth(pictureContainer.getPrefHeight() * getImageRatio());
        }
        else{
            pictureContainer.setPrefWidth(pictureContainer.getPrefHeight() / getImageRatio());
        }

        resizeImage();
    }

    private double getImageRatio(){
        double imageRatio;
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        imageRatio = imageWidthBiggerThanHeight() ? (imageWidth / imageHeight) : (imageHeight / imageWidth);

        return imageRatio;
    }

    private boolean imageWidthBiggerThanHeight(){
        return image.getWidth() > image.getHeight();
    }

    private void resizeImage(){
        imageView.setFitWidth(pictureContainer.getPrefWidth());
        imageView.setFitHeight(pictureContainer.getPrefHeight());
    }

}
