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
        BiConsumer<Integer, Integer> biConsumer = Tetris::movePiece;
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_LEFT):
                biConsumer.accept(-1, 0);
                break;
            case (KeyEvent.VK_RIGHT):
                biConsumer.accept(1, 0);
                break;
            case (KeyEvent.VK_DOWN):
                biConsumer.accept(0, 1);
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
