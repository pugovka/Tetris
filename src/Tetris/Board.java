package Tetris;

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
    public static int boardPos[][] = new int[BOARD_WIDTH][BOARD_HEIGHT];

    /*
    ======================================
    Init the board blocks with free positions
    ======================================
    */
    public static void initBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++)
            for (int j = 0; j < BOARD_HEIGHT; j++)
                boardPos[i][j] = POS_FREE;
    }

    /*
    ======================================
    Store a piece in the board by filling the blocks

    Parameters:

    >> piecePosX:        Horizontal position in blocks
    >> piecePosY:        Vertical position in blocks
    >> pieceKind:    Piece to draw
    >> pieceRotation: 1 of the 4 possible rotations
    ======================================
    */
    public static void storePiece(int piecePosX, int piecePosY, int pieceKind, int pieceRotation) {
        // Store each block of the piece into the board
        for (int i1 = piecePosX, i2 = 0; i1 < piecePosX + PIECE_BLOCKS; i1++, i2++) {
            for (int j1 = piecePosY, j2 = 0; j1 < piecePosY + PIECE_BLOCKS; j1++, j2++) {
                // Store only the blocks of the piece that are not holes
                if (Pieces.getBlockType(pieceKind, pieceRotation, j2, i2) != 0)
                    boardPos[i1][j1] = POS_FILLED;
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
            if (boardPos[i][0] == POS_FILLED) return true;
        return false;
    }

    /*
    ======================================
    Delete a line of the board by moving all above lines down

    Parameters:

    >> piecePosY:        Vertical position in blocks of the line to delete
    ======================================
    */
    public static void deleteLine(int piecePosY) {
        //Moves all the upper lines one row down
        for (int j = piecePosY; j > 0; j--) {
            for (int i = 0; i < BOARD_WIDTH; i++) {
                boardPos[i][j] = boardPos[i][j - 1];
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
                if (boardPos[i][j] != POS_FILLED) break;
                i++;
            }

            if (i == BOARD_WIDTH) deleteLine(j);
        }
    }

    /*
    ======================================
    Returns 1 (true) if the this block of the board is empty, 0 if it is filled

    Parameters:

    >> piecePosX:        Horizontal position in blocks
    >> piecePosY:        Vertical position in blocks
    ======================================
    */
    public static boolean isFreeBlock(int piecePosX, int piecePosY) {
        return (boardPos[piecePosX][piecePosY] == POS_FREE);
    }

    /*
    ======================================
    Returns the horizontal position (in pixels) of the block given like parameter

    Parameters:

    >> piecePosX:  Horizontal position of the block in the board
    ======================================
    */
    public static int getXPosInPixels(int piecePosX) {
        return ((BOARD_POSITION - (BLOCK_SIZE * (BOARD_WIDTH / 2))) + (piecePosX * BLOCK_SIZE));
    }

    /*
    ======================================
    Returns the vertical position (in pixels) of the block given like parameter

    Parameters:

    >> piecePosY:  Vertical position of the block in the board
    ======================================
    */
    public static int getYPosInPixels(int piecePosY) {
        return (Tetris.SCREEN_TOP_BORDER + (piecePosY * BLOCK_SIZE));
    }

    /*
    ======================================
    Check if the piece can be stored at this position without any collision
    Returns true if the movement is possible, false if it not possible

    Parameters:

    >> piecePosX:        Horizontal position in blocks
    >> piecePosY:        Vertical position in blocks
    >> pieceKind:    Piece to draw
    >> pieceRotation: 1 of the 4 possible rotations
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
