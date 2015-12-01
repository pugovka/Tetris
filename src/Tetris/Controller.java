package Tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Nat-nyan on 01.12.2015.
 */
public class Controller implements KeyListener {

    Tetris game;
    public boolean left, right, down, rotate, pause;

    public Controller(Tetris game) {
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)) {
            left = true;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)) {
            right = true;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)) {
            rotate = true;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)) {
            down = true;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)) {
            pause = true;
        }
    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)) {
            left = false;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)) {
            right = false;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)) {
            rotate = false;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)) {
            down = false;
        }
        if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)) {
            pause = false;
        }
    }
}
