package main;

import entity.Entity;

//For collision detection the collision solid are is smaller than tileSize.
/*
 * The player's solid area is used for collision detection.
 * We check for colision on all four corners of the player's solid area x and y coordinates + world location, to find the right tile in the world
 * The solid area is smaller then the tilesize, so we check the top left and top right coordinates are overlapping with any tiles, if they are set collision to true
 * 
 */
public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    //sets collisionOn to true or false
    public void checkTile(Entity entity) {
        
        //entityLeftWorldX is the left side of the collision rectangle used for detecting collisions.  Collision rectangle is entity.solidArea
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        //convert entity collision variables into the right tilesize
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        //Adds the collision in the direction up, down, left, and right
        switch(entity.direction) {    
        case "up":
            //use the speed to predict where the player is going to be next frame
            entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
            //the top left tile
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            //the top right tile
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            //if either one collides, set collision to true
            if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
            break;
        case "down":
        //use the speed to predict where the player is going to be next frame
        entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
        //the bottom left tile
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
        //the bottom right tile
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
        if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
        }
            break;
        case "left":
        //use the speed to predict where the player is going to be next frame
        entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
        //the top left tile
        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
        //the bottom left tile
        tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
        if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
        }
            break;
        case "right":
        //use the speed to predict where the player is going to be next frame
        entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
        //the top right tile
        tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
        //the bottom right tile
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
        if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
        }
            break;
        }
    }
    
}
