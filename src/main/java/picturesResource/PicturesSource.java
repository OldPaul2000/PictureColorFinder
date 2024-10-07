package picturesResource;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PicturesSource {

    private String sourceFolder;
    private static List<String> imagesList;

    private static PicturesSource picturesSource;
    public static PicturesSource getInstance(){
        if(picturesSource == null){
            picturesSource = new PicturesSource();
            imagesList = new ArrayList<>();
        }
        return picturesSource;
    }

    private PicturesSource() {
        imagesList = new ArrayList<>();
    }

    public void setSourceFolderUrl(String source){
        sourceFolder = source;
    }

    public void setImagesList(){
        Path sourcePath = Path.of(sourceFolder);
        if(Files.exists(sourcePath)){
            imagesList.clear();
            try{
                Files.walk(sourcePath, 1, FileVisitOption.FOLLOW_LINKS)
                        .forEach(value -> {
                            String fileLoc = sourcePath.relativize(value).toString();
                            if(!fileLoc.isBlank() && !fileLoc.isEmpty()){
                                imagesList.add(fileLoc);
                            }
                        });
            }
            catch (IOException e){
                System.out.println("Error walking directory");
            }
        }
    }

    public List<String> getImagesList(){
        return new ArrayList<>(imagesList);
    }




}
