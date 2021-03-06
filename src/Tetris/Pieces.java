package Tetris;

/**
 * Created by Nat-nyan on 03.01.2016.
 */
public class Pieces {
    /**
    * Returns the type of a block (0 = no-block, 1 = normal block, 2 = pivot block)

    * Parameters:

    * piecePosX:            Horizontal position in blocks
    * piecePosY:            Vertical position in blocks
    */
    public static int getBlockType(int pieceKind, int pieceRotation, int piecePosX, int piecePosY) {
        return pieces [pieceKind][pieceRotation][piecePosX][piecePosY];
    }

    /**
    * Returns the horizontal displacement of the piece that has to be applied in order to create it in the
    * correct position.
    */
    public static int getXInitialPosition(int pieceKind, int pieceRotation) {
        return piecesInitialPosition [pieceKind][pieceRotation][0];
    }

    /**
    * Returns the vertical displacement of the piece that has to be applied in order to create it in the
    * correct position.
    */
    public static int getYInitialPosition(int pieceKind, int pieceRotation) {
        return piecesInitialPosition [pieceKind][pieceRotation][1];
    }

    /** Pieces definition
    * kind, rotations, horizontal blocks, vertical blocks
    */
    private static char[][][][] pieces = new char[][][][] {
            // square
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0}
                    }
            },
            // I
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 1, 2, 1, 1},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 2, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 1, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {1, 1, 2, 1, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 1, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 2, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
            },
            // L
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 2, 0, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 1, 2, 1, 0},
                            {0, 1, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 1, 1, 0, 0},
                            {0, 0, 2, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 1, 0},
                            {0, 1, 2, 1, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
            },
            // L mirrored
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 2, 0, 0},
                            {0, 1, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 1, 0, 0, 0},
                            {0, 1, 2, 1, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 2, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 1, 2, 1, 0},
                            {0, 0, 0, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
            },
            // N
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 1, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 1, 2, 0, 0},
                            {0, 0, 1, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 1, 2, 0, 0},
                            {0, 1, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 1, 1, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
            },
            // N mirrored
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 0, 1, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 1, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 1, 0, 0, 0},
                            {0, 1, 2, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 1, 0},
                            {0, 1, 2, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
            },
            // T
            {
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 2, 1, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0},
                            {0, 1, 2, 1, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 1, 2, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
                    {
                            {0, 0, 0, 0, 0},
                            {0, 0, 1, 0, 0},
                            {0, 1, 2, 1, 0},
                            {0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0}
                    },
            },

    };
    // Displacement of the piece to the position where it is first drawn in the board when it is created
    private static int[][][] piecesInitialPosition = new int[][][] {
            // kind, rotation, position
                /* Square */
            {
                    {-2, -3},
                    {-2, -3},
                    {-2, -3},
                    {-2, -3}
            },
                /* I */
            {
                    {-2, -2},
                    {-2, -3},
                    {-2, -2},
                    {-2, -3}
            },
                /* L */
            {
                    {-2, -3},
                    {-2, -3},
                    {-2, -3},
                    {-2, -2}
            },
                /* L mirrored */
            {
                    {-2, -3},
                    {-2, -2},
                    {-2, -3},
                    {-2, -3}
            },
                /* N */
            {
                    {-2, -3},
                    {-2, -3},
                    {-2, -3},
                    {-2, -2}
            },
                /* N mirrored */
            {
                    {-2, -3},
                    {-2, -3},
                    {-2, -3},
                    {-2, -2}
            },
                /* T */
            {
                    {-2, -3},
                    {-2, -3},
                    {-2, -3},
                    {-2, -2}
            }
    };
}
