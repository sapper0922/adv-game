package tile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

import java.awt.Graphics2D;

public class TileManager {
    
    //variable with GamePanel stuff
    GamePanel gp;

    //array called tile
    public Tile[] tile;

    //array called mapTileNum
    public int mapTileNum[][];

    public TileManager (GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10]; 

        //instantiate mapTileNum
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("src/res/maps/world01.txt");
    }

    public void getTileImage() {

        System.out.println("Image loading started");

        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);

        System.out.println("Image loading finished");

    }


    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    //loadMap will read a text in filePath and populate the mapTileNum matrix with what is in the file
    public void loadMap(String filePath) {

        try {
            //read a text file
            InputStream is = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                //read a line of text in filePath
                String Line = br.readLine();

                while(col < gp.maxWorldCol) {

                    //splits a string with the space delimiter
                    String numbers[] = Line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            //close the br because you don't need them anymore
            br.close();

        }catch(Exception e) {

        }

    }

    //draw the mapTileNum on the screen
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
 
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
            //rendering the map in the screen only.
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                    //Draw Tiles on the screen
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);

                }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

    }

}