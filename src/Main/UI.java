package main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    // BufferedImage keyImage;
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
        // OBJ_Key key = new OBJ_Key(gp);

        //BufferedImage keyImage takes the image out of OBJ_Key
        // keyImage = key.image;

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

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            // Do playState stuff later
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);

    }
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
