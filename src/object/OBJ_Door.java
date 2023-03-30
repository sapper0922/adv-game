package object;

import entity.Entity;
import main.GamePanel;

//extends SuperObject to this class
public class OBJ_Door extends Entity{

    //try to make of door image and catch is printStackTrace
    public OBJ_Door(GamePanel gp) {

        super(gp);
        name = "Door";
        down1 = setup("/res/objects/door", gp.tileSize, gp.tileSize);
        down2 = setup("/res/objects/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

}
