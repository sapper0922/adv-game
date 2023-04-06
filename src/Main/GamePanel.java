package main;

import javax.swing.JPanel;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    
    // Screen Settings

    //Standard size of Character in pixels
    final int originalTileSize = 16; // 16x16 tile

    //Variable for scaling the charater
    final int scale = 3;

    //Sets the size with the scale
    public final int tileSize = originalTileSize * scale; // 48x48 tile

    //Window is 16 TileSize across
    public final int maxScreenCol = 20;

    //Window is 12 TileSize Tall
    public final int maxScreenRow = 12;

    //Window size across is 768 pixels
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels

    //Window size is 576 pixels Tall
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    //For full screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //FPS
    int FPS = 60;

    //Instantiate tileM class
    TileManager tileM = new TileManager(this);

    //Make variable keyH with KeyHandler
    public KeyHandler keyH = new KeyHandler(this);

    //Instantiate Sound class
    Sound music = new Sound();
    Sound se = new Sound();

    //Instantiate CollisionChecker class
    public CollisionChecker cChecker = new CollisionChecker(this);

    //Instantiate AssetSetter class
    public AssetSetter aSetter = new AssetSetter(this);

    //Instantiate UI class
    public UI ui = new UI(this);

    //Omstamtiate the EventHandler class
    public EventHandler eHandler = new EventHandler(this);

    Config config = new Config(this);

    //Creates a variable called gameThread that has all the functions the Thread has
    Thread gameThread;

    //Instantiate player class
    public Player player = new Player(this,keyH);

    //Instantiate SuperObject class
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[] []= new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;

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
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(fullScreenOn) {
            setFullScreen();
        }
    }
    public void retry() {

        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    public void restart() {

        player.setDefaultValues();
        player.setDefaultPositions();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }
    public void setFullScreen() {

        //Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Game.window);

        //Get full screen width and height
        screenWidth2 = Game.window.getWidth();
        screenHeight2 = Game.window.getHeight();
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

                drawToTempScreen();
                drawToScreen();
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

        //check if gameState is 1 than updates the player
        if(gameState == playState) {
            player.update();

            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }

                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }

                }
            }
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
        for(int i = 0; i < particleList.size(); i++) {
            if(particleList.get(i) != null) {
                if(particleList.get(i).alive) {
                    particleList.get(i).update();
                }
                if(!particleList.get(i).alive) {
                    particleList.remove(i);
                }

            }
        }
        if(gameState == pauseState) {
            //nothing
        }


    }
    public void drawToTempScreen() {
        //Debug
        long drawStart = 0;
        if(keyH.showDebugText == true) {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState) {

            ui.draw(g2);
            

        }
        //OTHERS
        else {

            //draw everything from TileManager class
            tileM.draw(g2);

            // Interactive tile
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            //Add entities to the list
            entityList.add(player);
            
            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for(int i = 0; i < obj[1].length; i++) {
                if(obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }
            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

            });

            //Draw Entityies
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //Empty Entity List
            for(int i = 0; i < entityList.size(); i++) {
                entityList.remove(i);
            }

            //Draws the ui on the screen
            ui.draw(g2);

        }



        //Debug
        if(keyH.showDebugText == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX" + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY" + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;

            g2.drawString("Draw Time: " + passed, x , y);

        }
    }
    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
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
