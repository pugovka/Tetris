package Tetris;

/**
 * Created by Nat-nyan on 03.01.2016.
 */
public class Pieces {
    /*
    ======================================
    Return the type of a block (0 = no-block, 1 = normal block, 2 = pivot block)

    Parameters:

    >> piecePosX:            Horizontal position in blocks
    >> piecePosY:            Vertical position in blocks
    ======================================
    */
    public static int getBlockType(int pieceKind, int pieceRotation, int piecePosX, int piecePosY) {
        return Tetris.piecesArray [pieceKind][pieceRotation][piecePosX][piecePosY];
    }

    /*
    ======================================
    Returns the horizontal displacement of the piece that has to be applied in order to create it in the
    correct position.
    ======================================
    */
    public static int getXInitialPosition(int pieceKind, int pieceRotation) {
        return Tetris.piecesInitialPosition [pieceKind][pieceRotation][0];
    }

    /*
    ======================================
    Returns the vertical displacement of the piece that has to be applied in order to create it in the
    correct position.
    ======================================
    */
    public static int getYInitialPosition(int pieceKind, int pieceRotation) {
        return Tetris.piecesInitialPosition [pieceKind][pieceRotation][1];
    }
}
