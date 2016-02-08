package Tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by Nat-nyan on 01.12.2015.
 */
public class Controller extends KeyAdapter {

    Tetris game;

    public Controller(Tetris game) {
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case (KeyEvent.VK_LEFT):
                Tetris.movePiece(-1);
                break;
            case (KeyEvent.VK_RIGHT):
                Tetris.movePiece(1);
                break;
            case (KeyEvent.VK_DOWN):
                if (Board.isPossibleMovement(Tetris.piecePosX, Tetris.piecePosY + 1, Tetris.pieceKind, Tetris.pieceRotation)) {
                    Tetris.piecePosY++;
                } else {
                    Board.storePiece(Tetris.piecePosX, Tetris.piecePosY, Tetris.pieceKind, Tetris.pieceRotation);
                    Board.deletePossibleLines();
                    if (Board.isGameOver()) {
                        System.out.println("Game over");
                    }
                    Tetris.createNewPiece();
                }
                break;
            case (KeyEvent.VK_UP):
                if (Board.isPossibleMovement(Tetris.piecePosX, Tetris.piecePosY, Tetris.pieceKind, (Tetris.pieceRotation + 1) % 4)) {
                    Tetris.pieceRotation = (Tetris.pieceRotation + 1) % 4;
                }
                break;
            case (KeyEvent.VK_P):
                if (Tetris.running) {
                    try {
                        Tetris.pause();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Tetris.resume();
                }
                break;
            default:
                break;
        }
    }
}
