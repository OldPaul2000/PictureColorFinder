package com.example.picturecolorfinder;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import picturesResource.PicturesSource;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ImagesSearch {

    public static String FOLDER_SRC_NAME = "folderSrcLocation.txt";

    private final Path currentPath = FileSystems.getDefault().getPath("").toAbsolutePath();
//    private final String FOLDER_SRC_LOCATION = "C:\\Users\\paulb\\IntellijProjects2\\PictureColorFinder\\src\\folderSrcLocation.txt";
    private final String FOLDER_SRC_LOCATION = currentPath + File.separator + FOLDER_SRC_NAME;

    private TextField sourceFolder;
    private ChoiceBox<String> picturesList;
    private PicturesSource picturesSource = PicturesSource.getInstance();

    public ImagesSearch(){}

    public ImagesSearch(TextField sourceFolder, ChoiceBox<String> picturesList) {
        this.sourceFolder = sourceFolder;
        this.picturesList = picturesList;
    }

    public void addTextFieldHandler(){
        sourceFolder.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)){
                setImagesSourcesNodes();
                writeFolderLocationOnFile();
            }
        });
    }

    private void setImagesSourcesNodes(){
        picturesSource.setSourceFolderUrl(sourceFolder.getText());
        picturesSource.setImagesList();
        picturesList.getItems().clear();
        picturesSource.getImagesList().forEach(value -> picturesList.getItems().add(value));
    }

    public void initializeNodesValues(){
        sourceFolder.setText(getFolderLocationFromFile());
        picturesSource.setSourceFolderUrl(getFolderLocationFromFile());
        picturesSource.setImagesList();
        picturesList.getItems().clear();
        picturesSource.getImagesList().forEach(value -> picturesList.getItems().add(value));
    }

    public String getFolderLocationFromFile(){
        String line = "";
        try(BufferedReader br = new BufferedReader(new FileReader(FOLDER_SRC_LOCATION))){
            line = br.readLine();
        }
        catch (IOException e){
            System.out.println("Error getting location from file");
        }
        return line;
    }

    private void writeFolderLocationOnFile(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FOLDER_SRC_LOCATION))){
            bw.write(sourceFolder.getText());
        }
        catch (IOException e){
            System.out.println("Error writing location to file");
        }
    }




}
