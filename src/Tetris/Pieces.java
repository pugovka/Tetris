package Tetris;

/**
 * Created by Nat-nyan on 03.01.2016.
 */
public class Pieces {
    /*
    ======================================
    Return the type of a block (0 = no-block, 1 = normal block, 2 = pivot block)

    Parameters:

    >> pPiece:        Piece to draw
    >> pRotation: 1 of the 4 possible rotations
    >> pX:            Horizontal position in blocks
    >> pY:            Vertical position in blocks
    ======================================
    */
    public int GetBlockType(int pPiece, int pRotation, int pX, int pY) {
        return Tetris.mPieces [pPiece][pRotation][pX][pY];
    }

    /*
    ======================================
    Returns the horizontal displacement of the piece that has to be applied in order to create it in the
    correct position.

    Parameters:

    >> pPiece:    Piece to draw
    >> pRotation: 1 of the 4 possible rotations
    ======================================
    */
    public int GetXInitialPosition(int pPiece, int pRotation) {
        return Tetris.mPiecesInitialPosition [pPiece][pRotation][0];
    }

    /*
    ======================================
    Returns the vertical displacement of the piece that has to be applied in order to create it in the
    correct position.

    Parameters:

    >> pPiece:    Piece to draw
    >> pRotation: 1 of the 4 possible rotations
    ======================================
    */
    public int GetYInitialPosition(int pPiece, int pRotation) {
        return Tetris.mPiecesInitialPosition [pPiece][pRotation][1];
    }
}
