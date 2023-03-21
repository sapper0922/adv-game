package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//KeyListener is able to receive inputs from keyboards
public class KeyHandler implements KeyListener{

    GamePanel gp;
    //Create Boolean Variable upPressed, downPressed, leftPressed, and rightPressed
    public Boolean
     upPressed = false,
     downPressed = false,
     leftPressed = false,
     rightPressed = false,
     enterPressed = false;

     //Debug
     boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        //getKeyCode returns a number of the key that was pressed EX. if you type A, getKeyCode wil return 65, if you type C, getKeyCode will return 67
        int code = e.getKeyCode();
        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            titleState(code);
        }
        //Play State
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        //Pause State
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        //Dialogue State
        else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        //Character State
        else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
    }

    public void titleState(int code) {
        //checks if W is pressed
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        //checks if S is pressed
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if(gp.ui.commandNum == 1) {
                //ADD LATER
            }
            if(gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }
    public void playState(int code) {
        //checks if W is pressed
        if(code == KeyEvent.VK_W) {
            //if W is pressed, upPressed will be true
            upPressed = true; 
        }
        //checks if S is pressed
        if(code == KeyEvent.VK_S) {
            //if S is pressed, downPressed will be true
            downPressed = true;
        }
        //checks if A is pressed
        if(code == KeyEvent.VK_A) {
            //if A is pressed, leftPressed will be true
            leftPressed = true;
        }
        //chekcs if D is pressed
        if(code == KeyEvent.VK_D) {
            //if D is pressed, rightPressed will be true
            rightPressed = true;
        }
        //checks if P is pressed
        if(code == KeyEvent.VK_P) {                
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        //checks if enter is pressed
        if(code == KeyEvent.VK_ENTER) {                
            enterPressed = true;
        }
        //Debug
        if(code == KeyEvent.VK_T) {
            if(checkDrawTime == false) {
                checkDrawTime = true;
            }
            else if(checkDrawTime == true) {
                checkDrawTime = false;
            }
        }
    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_P) {                
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            //if W is pressed, upPressed will be false
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            //if S is pressed, downPressed will be false
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            //if A is pressed, leftPressed will be false
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            //if D is pressed, rightPressed will be false
            rightPressed = false;
        }
        
    }
    
}