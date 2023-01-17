package object;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

//extends SuperObject to this class
public class OBJ_Key extends SuperObject {

    //try to make of door image and catch is printStackTrace
    public OBJ_Key() {

        name = "Key";
        try {
            image = ImageIO.read(new FileInputStream("res/objects/key.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}