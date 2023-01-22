package object;

import java.io.IOException;

import javax.imageio.ImageIO;

//A subclass of SuperObject used to make Door
//extends SuperObject to this class
public class OBJ_Door extends SuperObject{
    
    //try to make of door image and catch is printStackTrace
    public OBJ_Door() {

        name = "Door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
