package tests;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

;

public class Main {
    public static void main(String[] args) {

        File image =  new File("C:\\Users\\paulb\\Desktop\\UI Ideas\\original-6e8dac9e1dd1a6071a27561a99dfa048.png");

        try{
            BufferedImage bufferedImage = ImageIO.read(image);
            int[] rgb = {0,0,0,0};
            bufferedImage.getRGB(0,0,1200,900,rgb,0,0);
            System.out.println(Arrays.toString(rgb));
        }
        catch (IOException e){
            System.out.println("Error reading pixels");
        }


    }
}
