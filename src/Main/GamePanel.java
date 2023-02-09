package main;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    
    // Screen Settings

    //Standard size of Character in pixels
    final int originalTileSize = 16; // 16x16 tile

    //Variable for scaling the charater
    final int scale = 3;

    //Sets the size with the scale
    public final int tileSize = originalTileSize * scale; // 48x48 tile

    //Window is 16 TileSize across
    public final int maxScreenCol = 16;

    //Window is 12 TileSize Tall
    public final int maxScreenRow = 12;

    //Window size across is 768 pixels
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels

    //Window size is 576 pixels Tall
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;

    //Instantiate tileM class
    TileManager tileM = new TileManager(this);

    //Make variable keyH with KeyHandler
    KeyHandler keyH = new KeyHandler(this);

    //Instantiate Sound class
    Sound music = new Sound();
    Sound se = new Sound();

    //Instantiate CollisionChecker class
    public CollisionChecker cChecker = new CollisionChecker(this);

    //Instantiate AssetSetter class
    public AssetSetter aSetter = new AssetSetter(this);

    public UI ui = new UI(this);

    //Creates a variable called gameThread that has all the functions the Thread has
    Thread gameThread;

    //Instantiate player class
    public Player player = new Player(this,keyH);

    //Instantiate SuperObject class
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel() {

        //Perfered Size of the Window is 768 pixels across and 576 pixels tall
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        //Sets the Background Color
        this.setBackground(Color.black);

        //Increases Performance by drawing anything from this component will be drawn by an offscreen painting buffer
        this.setDoubleBuffered(true);

        //make sure GamePanel can recognize key inputs
        this.addKeyListener(keyH);

        //GamePanel can be focused to receive key inputs
        this.setFocusable(true);
 
    }

    //calls setObject function in aSetter class
    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();

        playMusic(0);

        gameState = playState;

    }

    public void startGameThread() {

        //Passing GamePanel to the Thread constructure
        gameThread = new Thread(this);

        //Starts the Thread
        gameThread.start();

    }
    
    //When Thread is called this function will automatically be called
    public void run() {

        double drawInterval = 1000000000/FPS; //0.0166666666 Seconds

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        
        while(gameThread != null) {

            //Gets current time in Nanoseconds(1,000,000,000 Nanoseconds = 1 Second)
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;

            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1) {

                //Calls the update function
                update();

                //Calls the paintComponent function
                repaint();
                delta--;
                drawCount++;

            }
            
            //Display FPS in terminal
            if(timer >= 1000000000) {
                System.out.println(drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }
    
    //Update the information
    public void update() {

        if(gameState == playState) {
            player.update();

            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState) {
            // nothing
        }


    }
    
    //redraws the information so you can see the change in the window. Graphics is a class that has many functions to draw things on the window
    public void paintComponent(Graphics g) {

        //This is calling the superclass component to draw the background.
        super.paintComponent(g);

        //Convert Graphics class to Graphics2D because Graphics2D has more functions like Geometry, Coordinates, Color, and Text
        Graphics2D g2 = (Graphics2D)g;

        //Debug
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        //draw everything from TileManager class
        tileM.draw(g2);

        //draw everything from Object class
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        //NPC
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        //draw everything from Player class
        player.draw(g2);

        //Draws the ui on the screen
        ui.draw(g2);

        //Debug
        if(keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10 , 400);
            System.out.println("Draw Tile: "+passed);
        }


        //Dispose of this graphics context
        g2.dispose();

    }
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic() {

        music.stop();

    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();

    }
}
