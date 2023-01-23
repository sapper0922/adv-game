package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

//A subclass of SuperObject used to make Door
//extends SuperObject to this class
public class OBJ_Door extends SuperObject{
    
    GamePanel gp;

    //try to make of door image and catch is printStackTrace
    public OBJ_Door(GamePanel gp) {

        this.gp = gp;

        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
