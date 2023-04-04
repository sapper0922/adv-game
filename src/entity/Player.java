package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GamePanel;
import main.KeyHandler;      
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import java.awt.AlphaComposite;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;

public class Player extends Entity{
    
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;


    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        //place player at the center of the screen
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        //Size of Collision Box in pixels and location
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // Attack area
        // attackArea.width = 36;
        // attackArea.height = 36;

        //Calls setDefaultValues function
        setDefaultValues();

        //Calls getPlayerImage function
        getPlayerImage();

        //Calls the getPlayerAttackImage that holds the attack images.
        getPlayerAttackImage();

        setItems();

    }
    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;

        //how fast the player character moves in pixels
        speed = 4;

        //direction the player is facing
        direction = "down";

        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; //More strength means he does more damage
        dexterity = 1; // More dexterity means he recieves less damage
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack = getAttack(); // The total attack value is decided by strentgh and weapon
        defence = getDefence(); // The total defence value is decided by dexterity and shield
    }
    public void setItems() {

        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        

    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefence() {
        return defence = dexterity * currentShield.defenceValue;
    }

    //Images for up1, up2, down1, down2, left1, left2, right1, right2.
    public void getPlayerImage() {

        up1 = setup("/res/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/player/boy_right_2", gp.tileSize, gp.tileSize);

    }

    public void getPlayerAttackImage() {

        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("/res/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/res/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/res/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/res/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/res/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/res/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/res/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/res/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/res/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/res/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/res/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/res/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/res/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/res/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/res/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/res/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
        }
        
    }

    public void update() {
        
        if(attacking == true) {
            attacking();
        }

        //This if statement checks if the animiation of the player only happenes when you are pushing w, a, s, d.
        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            //if W key pressed the direction will be set to up.
            if (keyH.upPressed == true) {
                direction = "up";
            }
            //if S key pressed the direction will be set to down.
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            //if A key pressed the direction will be set to left.
            else if(keyH.leftPressed == true) {
                direction = "left";
            }
            //if D key pressed the direction will be set to right.
            else if(keyH.rightPressed == true) {
                direction = "right";
            } 

            //Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //Check Object Collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check Npc Collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
    
            //Check Monster Collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //Check interactive tile collision
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            

            //CHECK EVENT
            gp.eHandler.checkEvent();

            //If Collision is false, Player can move
            if(collisionOn == false && keyH.enterPressed == false) {

                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            if(keyH.enterPressed && !attackCanceled) {
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

            spriteCounter++;
            
            //Every 12 framces the animation changes
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
        else {
            standCounter++;
            
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if(gp.keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)) {

            // Set default coordinates, direction and user
            projectile.set(worldX, worldY, direction, true, this);

            // Subtract the cost (Mana, Ammo, ETC.)
            projectile.subtractResource(this);

            // Add it to the list
            gp.projectileList.add(projectile);

            shotAvailableCounter = 0;

            gp.playSE(10);
        }

        //This needs to be outside of key if statement
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter >= 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if(life > maxLife) {
            life = maxLife;
        }
        if(mana > maxMana) {
            mana = maxMana;
        }
    }

    public void attacking() {

        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //Save the current worldX, worldY, and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for the attackArea
            switch(direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.height; break;
                case "right": worldX += attackArea.height; break;
            }

            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //Check monster collision with the updated worldX, worldY, and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);
            damageInteractiveTile(iTileIndex);

            //After checking collision, restores the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    //checks if player picked up a key and uses the key
    public void pickUpObject(int i) {
        if(i != 999) {

            // Pickup only items
            if(gp.obj[i].type == type_pickupOnly) {
                gp.obj[i].use(this);
                gp.obj[i] = null;
            }

            // Inventory items
            else {
                String text;

                if(inventory.size() != maxInventorySize) {
                    inventory.add(gp.obj[i]);
                    gp.playSE(1);
                    text = "Got a " + gp.obj[i].name + "!";
                }
                else {
                    text = "You cannot carry anymore!";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null;
            }

            
        }
    }

    public void interactNPC(int i) {
        
        if(gp.keyH.enterPressed == true) {

            if(i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }  
        }
    }

    public void contactMonster(int i) {

        if(i != 999) {
            if(!invincible && !gp.monster[i].dying) {
                gp.playSE(6);

                //Setting damage variable which controls how much life to take away depending on attack and defence
                int damage = gp.monster[i].attack - defence;
                if(damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack) {

        if(i != 999) {
            if(!gp.monster[i].invincible) {
                gp.playSE(5);

                //Setting damage variable which controls how much life to take away depending on attack and defence
                int damage = attack - gp.monster[i].defence;
                if(damage < 0) {
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage! ");

                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("EXP + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i) {

        if(i != 999 && gp.iTile[i].destructible && gp.iTile[i].isCorrectItem(this) && !gp.iTile[i].invincible) {

            gp.iTile[i].playSE();
            gp.iTile[i].life--;
            gp.iTile[i].invincible = true;

            // Generate particle
            generateParticle(gp.iTile[i],gp.iTile[i]);

            if(gp.iTile[i].life == 0) {
                gp.iTile[i] = gp.iTile[i].getDestroyedForm();

            }
        }
    }
    public void checkLevelUp() {
        if(exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defence = getDefence();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!\n" 
                + "You feel stronger!";

        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot();
        
        if(itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defence = getDefence();
            }
            if(selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void draw(Graphics2D g2) {

        //create variable image with BufferedImage
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        //if direction is up the image will change from up1 and up2
        switch(direction) {
        case "up":
        if(attacking == false) {
            if(spriteNum == 1) { image = up1; }
            if(spriteNum == 2) { image = up2; }
        }
        if(attacking == true) {
            tempScreenY = screenY - gp.tileSize;
            if(spriteNum == 1) { image = attackUp1; }
            if(spriteNum == 2) { image = attackUp2; }
        }
            break;
        //if direction is down the image will change from down2 and down2
        case "down":
            if(attacking == false) {
                if(spriteNum == 1) { image = down1; }
                if(spriteNum == 2) { image = down2; }
            }
            if(attacking == true) {
                if(spriteNum == 1) { image = attackDown1; }
                if(spriteNum == 2) { image = attackDown2; }
            }
            break;
        //if direction is left the image will change from left1 and left2
        case "left":
            if(attacking == false) {
                if(spriteNum == 1) { image = left1; }
                if(spriteNum == 2) { image = left2; }
            }
            if(attacking == true) {
                tempScreenX = screenX - gp.tileSize;
                if(spriteNum == 1) { image = attackLeft1; }
                if(spriteNum == 2) { image = attackLeft2; }
            }
            break;
        //if direction is right the image will change from right1 and right2
        case "right":
            if(attacking == false) {
                if(spriteNum == 1) { image = right1; }
                if(spriteNum == 2) { image = right2; }
            }
            if(attacking == true) {
                if(spriteNum == 1) { image = attackRight1; }
                if(spriteNum == 2) { image = attackRight2; }
            }
            break;
        }

        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        //draws image on the screen, since screenX and screenY is final variable he doesn't change location on the screen(final variable cannot be changed unless you change it in the class)
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //DEBUG
        // g2.setFont(new Font("Arial", Font.PLAIN, 26));
        // g2.setColor(Color.white);
        // g2.drawString("Invincible:"+invincibleCounter, 10, 400);
    }
}
