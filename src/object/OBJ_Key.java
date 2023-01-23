package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

//A subclass of SuperObject used to make Key
//extends SuperObject to this class
public class OBJ_Key extends SuperObject {

    GamePanel gp;

    //try to make of door image and catch is printStackTrace
    public OBJ_Key(GamePanel gp) {

        this.gp = gp;

        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}