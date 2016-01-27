package Tetris;

import java.awt.*;

/**
 * Created by Nat-nyan on 04.01.2016.
 */
public class Board {
    // Board width and height in blocks
    public static final int BOARD_WIDTH = 10, BOARD_HEIGHT = 40;
    // Delimiters of a board
    public static final int BOARD_LINE_WIDTH = 6;
    public static final int POS_FREE = 0, POS_FILLED = 1;
    public static final int PIECE_BLOCKS = 5;
    public static final int BOARD_POSITION = 200;
    // Width and height of each block of a piece
    public static final int BLOCK_SIZE = 16;
    public static int mBoard[][] = new int[BOARD_WIDTH][BOARD_HEIGHT];

    /*
    ======================================
    Init the board blocks with free positions
    ======================================
    */
    public static void initBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++)
            for (int j = 0; j < BOARD_HEIGHT; j++)
                mBoard[i][j] = POS_FREE;
    }

    /*
    ======================================
    Store a piece in the board by filling the blocks

    Parameters:

    >> pX:        Horizontal position in blocks
    >> pY:        Vertical position in blocks
    >> pPiece:    Piece to draw
    >> pRotation: 1 of the 4 possible rotations
    ======================================
    */
    public static void storePiece(int pX, int pY, int pPiece, int pRotation) {
        // Store each block of the piece into the board
        for (int i1 = pX, i2 = 0; i1 < pX + PIECE_BLOCKS; i1++, i2++) {
            for (int j1 = pY, j2 = 0; j1 < pY + PIECE_BLOCKS; j1++, j2++) {
                // Store only the blocks of the piece that are not holes
                if (Pieces.getBlockType(pPiece, pRotation, j2, i2) != 0)
                    mBoard[i1][j1] = POS_FILLED;
            }
        }
    }

    /*
    ======================================
    Check if the game is over becase a piece have achived the upper position

    Returns true or false
    ======================================
    */
    public static boolean isGameOver() {
        //If the first line has blocks, then, game over
        for (int i = 0; i < BOARD_WIDTH; i++)
            if (mBoard[i][0] == POS_FILLED) return true;
        return false;
    }

    /*
    ======================================
    Delete a line of the board by moving all above lines down

    Parameters:

    >> pY:        Vertical position in blocks of the line to delete
    ======================================
    */
    public static void deleteLine(int pY) {
        //Moves all the upper lines one row down
        for (int j = pY; j > 0; j--) {
            for (int i = 0; i < BOARD_WIDTH; i++) {
                mBoard[i][j] = mBoard[i][j - 1];
            }
        }
    }

    /*
    ======================================
    Delete all the lines that should be removed
    ======================================
    */
    public static void deletePossibleLines() {
        for (int j = 0; j < BOARD_HEIGHT; j++) {
            int i = 0;
            while (i < BOARD_WIDTH) {
                if (mBoard[i][j] != POS_FILLED) break;
                i++;
            }

            if (i == BOARD_WIDTH) deleteLine(j);
        }
    }

    /*
    ======================================
    Returns 1 (true) if the this block of the board is empty, 0 if it is filled

    Parameters:

    >> pX:        Horizontal position in blocks
    >> pY:        Vertical position in blocks
    ======================================
    */
    public static boolean isFreeBlock(int pX, int pY) {
        return (mBoard[pX][pY] == POS_FREE);
    }

    /*
    ======================================
    Returns the horizontal position (in pixels) of the block given like parameter

    Parameters:

    >> pPos:  Horizontal position of the block in the board
    ======================================
    */
    public static int getXPosInPixels(int pPos) {
        return ((BOARD_POSITION - (BLOCK_SIZE * (BOARD_WIDTH / 2))) + (pPos * BLOCK_SIZE));
    }

    /*
    ======================================
    Returns the vertical position (in pixels) of the block given like parameter

    Parameters:

    >> pPos:  Vertical position of the block in the board
    ======================================
    */
    public static int getYPosInPixels(int pPos) {
        return (Tetris.SCREEN_TOP_BORDER + (pPos * BLOCK_SIZE));
    }

    /*
    ======================================
    Check if the piece can be stored at this position without any collision
    Returns true if the movement is possible, false if it not possible

    Parameters:

    >> pX:        Horizontal position in blocks
    >> pY:        Vertical position in blocks
    >> pPiece:    Piece to draw
    >> pRotation: 1 of the 4 possible rotations
    ======================================
    */
    public static boolean isPossibleMovement(int piecePosX, int piecePosY, int pieceKind, int pieceRotation) {
        for (int i1 = piecePosX, i2 = 0; i1 < piecePosX + PIECE_BLOCKS; i1++, i2++) {
            for (int j1 = piecePosY, j2 = 0; j1 < piecePosY + PIECE_BLOCKS; j1++, j2++) {
                // Check if the piece is outside the limits of the board
                if (i1 < 0 || i1 > BOARD_WIDTH - 1 || j1 > BOARD_HEIGHT - 1) {
                    if (Pieces.getBlockType(pieceKind, pieceRotation, j2, i2) != 0)
                        return false;
                }

                // Check if the piece have collisioned with a block already stored in the map
                if (j1 >= 0) {
                    if ((Pieces.getBlockType(pieceKind, pieceRotation, j2, i2)) != 0 &&
                            (!isFreeBlock(i1, j1)))
                        return false;
                }
            }
        }

        // No collision
        return true;
    }
}
