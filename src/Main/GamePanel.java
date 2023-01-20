package main;

import javax.swing.JPanel;
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
    int FPS = 1000000000;

    //Instantiate tileM class
    TileManager tileM = new TileManager(this);

    //Make variable keyH with KeyHandler
    KeyHandler keyH = new KeyHandler();

    //Instantiate Sound class
    Sound sound = new Sound();

    //Instantiate CollisionChecker class
    public CollisionChecker cChecker = new CollisionChecker(this);

    //Instantiate AssetSetter class
    public AssetSetter aSetter = new AssetSetter(this);

    //Creates a variable called gameThread that has all the functions the Thread has
    Thread gameThread;

    //Instantiate player class
    public Player player = new Player(this,keyH);

    //Instantiate SuperObject class
    public SuperObject obj[] = new SuperObject[10];

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

        playMusic(0);

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

        player.update();

    }
    
    //redraws the information so you can see the change in the window. Graphics is a class that has many functions to draw things on the window
    public void paintComponent(Graphics g) {

        //When you use paintComponent, you need to write this
        super.paintComponent(g);

        //Convert Graphics class to Graphics2D because Graphics2D has more functions like Geometry, Coordinates, Color, and Text
        Graphics2D g2 = (Graphics2D)g;

        //draw everything from TileManager class
        tileM.draw(g2);

        //draw everything from Object class
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        //draw everything from Player class
        player.draw(g2);

        //Dispose of this graphics context
        g2.dispose();

    }
    public void playMusic(int i) {

        sound.setFile(i);
        sound.play();
        sound.loop();

    }
    public void stopMusic() {

        sound.stop();

    }
    public void playSE(int i) {

        sound.setFile(i);
        sound.play();

    }
}
