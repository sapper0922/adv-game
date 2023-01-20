package object;

import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
    
    //try to make of boots image and catch is printStackTrace
    public OBJ_Boots() {

        name = "Boots";
        try {
            image = ImageIO.read(new FileInputStream("res/objects/boots.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
