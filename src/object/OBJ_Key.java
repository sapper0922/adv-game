package object;

import entity.Entity;
import main.GamePanel;

//A subclass of SuperObject used to make Key
//extends SuperObject to this class
public class OBJ_Key extends Entity {

    //try to make of door image and catch is printStackTrace
    public OBJ_Key(GamePanel gp) {

        super(gp);

        name = "Key";
        down1 = setup("/res/objects/key", gp.tileSize, gp.tileSize);

    }

}