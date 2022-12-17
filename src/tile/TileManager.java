package tile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import Main.GamePanel;
import java.awt.Graphics2D;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager (GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("res/maps/world01.txt");
    }

    public void getTileImage() {

        System.out.println("Image loading started");

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new FileInputStream("res/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new FileInputStream("res/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new FileInputStream("res/tiles/water.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new FileInputStream("res/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new FileInputStream("res/tiles/tree.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new FileInputStream("res/tiles/sand.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Image loading finished");

    }
    public void loadMap(String filePath) {

        try {
            //
            InputStream is = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String Line = br.readLine();

                while(col < gp.maxWorldCol) {

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
            br.close();

        }catch(Exception e) {

        }

    }
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;


            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }

    }

}
