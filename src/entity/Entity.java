package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.text.StyleConstants;

import main.GamePanel;
import main.UtilityTool;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;


public class Entity {
    
    GamePanel gp;
    public int worldX, worldY;
    public int speed;

    // list of images
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    //Variable called direction
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    boolean attacking = false;
    public int invincibleCounter = 0;
    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type; // 0 = player, 1 = npc, 2 = monster

    //CHARACTER STATUS
    public int maxLife;
    public int life;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setAction() {

    }

    public void speak() {

        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
        case "up":
            direction = "down";
            break;
        case "down":
            direction = "up";
            break;
        case "left":
            direction = "right";
            break;
        case "right":
            direction = "left";
            break;
        }

    }

    //Updates location and image for npc
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);     

        if(this.type == 2 && contactPlayer) {
            if(!gp.player.invincible) {
                //we can give damage
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }

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

        //Invincible state after Entity gets hit
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter >= 40) {
                invincible = false;
                invincibleCounter = 0;
                System.out.println("Hit!");
            }
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
                    if(spriteNum == 1) { image = up1; }
                    if(spriteNum == 2) { image = up2; }
                    break;
                //if direction is down the image will change from down2 and down2
                case "down":
                    if(spriteNum == 1) { image = down1; }
                    if(spriteNum == 2) { image = down2; }
                    break;
                //if direction is left the image will change from left1 and left2
                case "left":
                    if(spriteNum == 1) { image = left1; }
                    if(spriteNum == 2) { image = left2; }
                    break;
                //if direction is right the image will change from right1 and right2
                case "right":
                    if(spriteNum == 1) { image = right1; }
                    if(spriteNum == 2) { image = right2; }
                    break;
                }

                if(invincible) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                }

                //Draw Tiles on the screen
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }

    }
    
    //creates image path for player and npc so the images are loaded here
    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;

    }
}
