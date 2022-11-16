package entity;

import Main.GamePanel;
import Main.KeyHandler;
import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();

    }
    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";

    }
    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(new FileInputStream("res/player/Adventure Player-7.png"));
            up2 = ImageIO.read(new FileInputStream("res/player/Adventure Player-8.png"));
            down1 = ImageIO.read(new FileInputStream("res/player/Adventure Player-1.png"));
            down2 = ImageIO.read(new FileInputStream("res/player/Adventure Player-2.png"));
            left1 = ImageIO.read(new FileInputStream("res/player/Adventure Player-3.png"));
            left2 = ImageIO.read(new FileInputStream("res/player/Adventure Player-4.png"));
            right1 = ImageIO.read(new FileInputStream("res/player/Adventure Player-5.png"));
            right2 = ImageIO.read(new FileInputStream("res/player/Adventure Player-6.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
                y -= speed;
            }
            else if(keyH.downPressed == true) {
                direction = "down";
                y += speed;
            }
            else if(keyH.leftPressed == true) {
                direction = "left";
                x -= speed;
            }
            else if(keyH.rightPressed == true) {
                direction = "right";
                x += speed;
            }
    
            spriteCounter++;
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
    public void draw(Graphics2D g2) {

//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction) {
        case "up":
            if(spriteNum == 1) {
                image = up1;
            }
            if(spriteNum ==2) {
                image = up2;
            }
            break;
        case "down":
            if(spriteNum == 1) {
                image = down1;
            }
            if(spriteNum ==2) {
                image = down2;
            }
            break;
        case "left":
            if(spriteNum == 1) {
                image = left1;
            }
            if(spriteNum ==2) {
                image = left2;
            }
            break;
        case "right":
            if(spriteNum == 1) {
                image = right1;
            }
            if(spriteNum ==2) {
                image = right2;
            }
            break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);


    }
}
