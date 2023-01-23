package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends SuperObject{
    
    GamePanel gp;

    //try to make of boots image and catch is printStackTrace
    public OBJ_Boots(GamePanel gp) {

        this.gp = gp;

        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
