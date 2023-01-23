package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

//A subclass of SuperObject used to make Chest
//extends SuperObject to this class
public class OBJ_Chest extends SuperObject{
    
    GamePanel gp;

    //try to make of chest image and catch is printStackTrace
    public OBJ_Chest(GamePanel gp) {

        this.gp = gp;

        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest (OLD).png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
