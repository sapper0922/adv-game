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

    //check if collision is happening with object
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for(int i = 0; i < gp.obj.length; i++) {

            if(gp.obj[i] != null) {

                //Get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                //get the object's solid areas position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                //checks where entity or player will be after they move
                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        //checks if rectangle collides with another rectangle
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            //checks if this specific tile has collision
                            if(gp.obj[i].collision == true) {
                                //sets collisionOn to true which means the player can't move
                                entity.collisionOn = true;
                            }
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            //checks if this specific tile has collision
                            if(gp.obj[i].collision == true) {
                                //sets collisionOn to true which means the player can't move
                                entity.collisionOn = true;
                            }
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            //checks if this specific tile has collision
                            if(gp.obj[i].collision == true) {
                                //sets collisionOn to true which means the player can't move
                                entity.collisionOn = true;
                            }
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(gp.obj[i].solidArea)){
                            //checks if this specific tile has collision
                            if(gp.obj[i].collision == true) {
                                //sets collisionOn to true which means the player can't move
                                entity.collisionOn = true;
                            }
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;   
                }
                //reseting these values because these variable will keep increasing
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }

        }

        //since this is an int function, I need to return an Integer
        return index;

    }
    
    // Npc or monster collision
    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for(int i = 0; i < target.length; i++) {

            if(target[i] != null) {

                //Get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                //get the object's solid areas position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                //checks where entity or player will be after they move
                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        //checks if rectangle collides with another rectangle
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            //checks if this specific tile has collision
                            //sets collisionOn to true which means the player can't move
                            entity.collisionOn = true;
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            //checks if this specific tile has collision
                            //sets collisionOn to true which means the player can't move
                            entity.collisionOn = true;
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            //checks if this specific tile has collision
                            //sets collisionOn to true which means the player can't move
                            entity.collisionOn = true;
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)){
                            //checks if this specific tile has collision
                            //sets collisionOn to true which means the player can't move
                            entity.collisionOn = true;
                            //checks if the player is picking up an object instead of an NPC or a Monster
                            index = i;
                        }
                        break;   
                }
                //reseting these values because these variable will keep increasing
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }

        }

        //since this is an int function, I need to return an Integer
        return index;

    }

    public void checkPlayer(Entity entity) {

        //Get the entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        
        //get the object's solid areas position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        //checks where entity or player will be after they move
        switch(entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                //checks if rectangle collides with another rectangle
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    //checks if this specific tile has collision
                    //sets collisionOn to true which means the player can't move
                    entity.collisionOn = true;
                    //checks if the player is picking up an object instead of an NPC or a Monster
                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    //checks if this specific tile has collision
                    //sets collisionOn to true which means the player can't move
                    entity.collisionOn = true;
                    //checks if the player is picking up an object instead of an NPC or a Monster
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    //checks if this specific tile has collision
                    //sets collisionOn to true which means the player can't move
                    entity.collisionOn = true;
                    //checks if the player is picking up an object instead of an NPC or a Monster
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if(entity.solidArea.intersects(gp.player.solidArea)){
                    //checks if this specific tile has collision
                    //sets collisionOn to true which means the player can't move
                    entity.collisionOn = true;
                    //checks if the player is picking up an object instead of an NPC or a Monster
                }
                break;   
        }
        //reseting these values because these variable will keep increasing
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

    }

}
