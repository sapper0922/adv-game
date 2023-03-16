package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

    //try to make of door image and catch is printStackTrace
    public OBJ_Heart(GamePanel gp) {

        super(gp);
        name = "Heart";
        image = setup("/res/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/res/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/res/objects/heart_blank", gp.tileSize, gp.tileSize);

    }

}
