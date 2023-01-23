package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//KeyListener is able to receive inputs from keyboards
public class KeyHandler implements KeyListener{

    //Create Boolean Variable upPressed, downPressed, leftPressed, and rightPressed
    public Boolean
     upPressed = false,
     downPressed = false,
     leftPressed = false,
     rightPressed = false;

     //Debug
     boolean checkDrawTime = false;

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        //getKeyCode returns a number of the key that was pressed EX. if you type A, getKeyCode wil return 65, if you type C, getKeyCode will return 67
        int code = e.getKeyCode();

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