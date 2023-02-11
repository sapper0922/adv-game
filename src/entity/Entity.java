package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import entity.NPC_OldMan;

public class Entity {
    
    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    // list of images
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    //Variable called direction
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setAction() {

    }

    public void speak() {

    }

    //Updates location and image for npc
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);

        if(collisionOn == false) {

            switch(direction) {
                case "up":
                worldY -= speed;
                    break;
                case "down":
                worldY += speed;
                    break;
                case "left":
                worldX -= speed;
                    break;
                case "right":
                worldX += speed;
                    break;
            }

        }

        spriteCounter++;
        
        //Every 12 framces the animation changes
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        //if the object is in the screen it will draw
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            //if direction is up the image will change from up1 and up2
            switch(direction) {
                case "up":
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum == 2) {
                        image = up2;
                    }
                    break;
                //if direction is down the image will change from down2 and down2
                case "down":
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                    break;
                //if direction is left the image will change from left1 and left2
                case "left":
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                    break;
                //if direction is right the image will change from right1 and right2
                case "right":
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                    break;
                }

                //Draw Tiles on the screen
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }

    }
    
    //creates image path for player and npc so the images are loaded here
    public BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;

    }
}
