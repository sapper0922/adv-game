package object;

import java.io.IOException;
import javax.imageio.ImageIO;

//A subclass of SuperObject used to make Chest
//extends SuperObject to this class
public class OBJ_Chest extends SuperObject{
    
    //try to make of chest image and catch is printStackTrace
    public OBJ_Chest() {

        name = "Chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chest (OLD).png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
