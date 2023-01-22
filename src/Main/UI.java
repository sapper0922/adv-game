package main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
    
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        
        this.gp = gp;

        //variable arial_40 is set outside draw method because if this is called in the draw method which will be called 60 times a second can decrease performance
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        
        //makes a variable called key which has all the components of OBJ_Key class
        OBJ_Key key = new OBJ_Key();

        //BufferedImage keyImage takes the image out of OBJ_Key
        keyImage = key.image;

    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;

    }

    /**
     * @param g2
     * Draws the UI on the screen
     * Draws the key on the top left of the screen with how many you have
     */
    public void draw(Graphics2D g2) {

        if(gameFinished == true) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);

            text = "You Time is : " + dFormat.format(playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*4);
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulations";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        }
        else {
            //sets the stats of the UI
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            //Draws key image on the screen to represent how many keys you have
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);

            //writes a string, this is how many keys I have
            g2.drawString("x  "+ gp.player.hasKey, 74, 65);

            //Time
            playTime +=(double)1/60;
            g2.drawString("Time:" + dFormat.format(playTime), gp.tileSize*11, 65);

            //If messageOn = true it will display 
            if(messageOn == true) {

                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                //messageCounter increases by 1 every 60 times a second so if messageCounter = 120 than messageCounter will = 0 and messageOn will be false
                if(messageCounter >= 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
