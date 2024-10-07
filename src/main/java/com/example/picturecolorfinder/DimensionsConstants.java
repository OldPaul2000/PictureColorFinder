package com.example.picturecolorfinder;

public enum DimensionsConstants {

    // In ColorFinderApplication

    /**
     *Sum of the window title bar height and taskbar height
     */
    WINDOW_HEIGHT_GAP(70),

    INIT_HEIGHT_PERCENTAGE(0.4),
    PROJECT_CONTAINER_PERCENTAGE(0.17),
    PICTURE_PICKER_PERCENTAGE(0.12);


    private double value;

    DimensionsConstants(double value){
        this.value = value;
    }

    public double value(){
        return value;
    }


}
