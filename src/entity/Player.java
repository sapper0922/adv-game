package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    // public int hasKey = 0;


    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        //place player at the center of the screen
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //Size of Collision Box in pixels and location
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        //Calls setDefaultValues function
        setDefaultValues();

        //Calls getPlayerImage function
        getPlayerImage();

    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;

        //how fast the player character moves in pixels
        speed = 4;

        //direction the player is facing
        direction = "down";

    }

    //Images for up1, up2, down1, down2, left1, left2, right1, right2.
    public void getPlayerImage() {

        up1 = setup("Adventure Player-7");
        up2 = setup("Adventure Player-8");
        down1 = setup("Adventure Player-1");
        down2 = setup("Adventure Player-2");
        left1 = setup("Adventure Player-3");
        left2 = setup("Adventure Player-4");
        right1 = setup("Adventure Player-5");
        right2 = setup("Adventure Player-6");

    }

    public BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    public void update() {
        
        //This if statement checks if the animiation of the player only happenes when you are pushing w, a, s, d.
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            //if W key pressed the direction will be set to up.
            if (keyH.upPressed == true) {
                direction = "up";
            }
            //if S key pressed the direction will be set to down.
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            //if A key pressed the direction will be set to left.
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            //if D key pressed the direction will be set to right.
            else if(keyH.rightPressed == true) {
                direction = "right";
            }

            //Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //Check Object Collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
    
            //If Collision is false, Player can move
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
    }

    //checks if player picked up a key and uses the key
    public void pickUpObject(int i) {
        if(i != 999) {

        }
    }

    public void draw(Graphics2D g2) {

//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        //create variable image with BufferedImage
        BufferedImage image = null;

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
        //draws image on the screen, since screenX and screenY is final variable he doesn't change location on the screen(final variable cannot be changed unless you change it in the class)
        g2.drawImage(image, screenX, screenY, null);


    }
}
