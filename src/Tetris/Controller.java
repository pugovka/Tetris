package Tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Nat-nyan on 01.12.2015.
 */
public class Controller extends KeyAdapter {

    Tetris game;

    public Controller(Tetris game) {
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)) {
            if (Board.isPossibleMovement(Tetris.piecePosX - 1, Tetris.piecePosY, Tetris.pieceKind, Tetris.pieceRotation))
                Tetris.piecePosX--;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)) {
            if (Board.isPossibleMovement(Tetris.piecePosX + 1, Tetris.piecePosY, Tetris.pieceKind, Tetris.pieceRotation))
                Tetris.piecePosX++;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)) {
            if (Board.isPossibleMovement(Tetris.piecePosX, Tetris.piecePosY, Tetris.pieceKind, (Tetris.pieceRotation + 1) % 4))
                Tetris.pieceRotation = (Tetris.pieceRotation + 1) % 4;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)) {
            if (Board.isPossibleMovement(Tetris.piecePosX, Tetris.piecePosY + 1, Tetris.pieceKind, Tetris.pieceRotation))
                Tetris.piecePosY++;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)) {
            if (Tetris.running) {
                try {
                    Tetris.pause();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                System.out.println(Tetris.running);
                Tetris.resume();
                System.out.println(Tetris.running);
            }

        }
    }
}
