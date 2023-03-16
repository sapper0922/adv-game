package object;

import entity.Entity;
import main.GamePanel;

//A subclass of SuperObject used to make Chest
//extends SuperObject to this class
public class OBJ_Chest extends Entity{

    //try to make of chest image and catch is printStackTrace
    public OBJ_Chest(GamePanel gp) {

        super(gp);
        name = "Chest";
        setup("/res/objects/chest (OLD)", gp.tileSize, gp.tileSize);

    }

}
