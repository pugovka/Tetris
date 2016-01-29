package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

/**
 * Created by Nat-nyan on 17.11.2015.
 */
public class Tetris extends Canvas implements Runnable {

    public static final int SCREEN_WIDTH = 400, SCREEN_HEIGHT = 760;
    public static final int SCREEN_TOP_BORDER = 30;
    Controller control;
    public Thread mainThread;
    public static JFrame frame = new JFrame("Tetris");
    public static volatile boolean running = true;

    // Pieces definition
    public static char[][][][] piecesArray = new char[][][][] {
        // kind, rotations, horizontal blocks, vertical blocks
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

    public static int pieceKind;
    public static int pieceRotation;
    public static int piecePosX;
    public static int piecePosY;

    private static int nextPieceKind;
    private static int nextPieceRotation;
    private static int nextPiecePosX;
    private static int nextPiecePosY;

    private static Random random = new Random();

    // Displacement of the piece to the position where it is first drawn in the board when it is created
    public static int[][][] piecesInitialPosition = new int[][][] {
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

    public static void main(String args[]) {
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        KeyGetter.loadKeys();
        try {
            Config.loadConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        JMenuBar bar = new JMenuBar();
        bar.setBounds(0, 0, SCREEN_WIDTH, 25);

        JMenu file = new JMenu("File");
        file.setBounds(0, 0, 45, 24);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Code for new game
                System.out.println("Starting New Game...");
            }
        });
        JMenuItem highScore = new JMenuItem("High Score");
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int highScore = 0; //replace with getHighScoreMethod later
                JFrame alert = new JFrame("High Score");
                alert.setSize(300, 150);
                alert.setLayout(null);
                alert.setLocationRelativeTo(null);
                //alert.setAlwaysOnTop(true);

                JLabel score = new JLabel("The highscore is: " + highScore);
                score.setBounds(10, 0, 200, 50);

                JButton okayButton = new JButton("Okay");
                okayButton.setBounds(100, 80, 100, 30);
                okayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        alert.dispose();
                    }
                });

                alert.add(score);
                alert.add(okayButton);
                alert.setResizable(false);
                alert.setVisible(true);
            }
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code for new game
                System.out.println("Closing...");
                System.exit(0);
            }
        });

        JMenuItem options = new JMenuItem("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config.openConfig(frame);
            }
        });

        Tetris tetris = new Tetris();
        tetris.setBounds(0, 25, SCREEN_WIDTH, SCREEN_HEIGHT - 25);

        frame.add(tetris);
        file.add(newGame);
        file.add(highScore);
        file.add(options);
        file.add(exit);
        bar.add(file);
        frame.add(bar);
        frame.setVisible(true);
        tetris.start();
    }

    public void start() {
        mainThread = new Thread(this);
        mainThread.setPriority(Thread.MAX_PRIORITY);
        mainThread.start();
        Board.initBoard();
    }
    @Override
    public void run() {
        init();
        createNewPiece();

        while (running) {
            BufferStrategy buffer = getBufferStrategy();
            if (buffer == null) {
                createBufferStrategy(3);
                continue;
            }

            Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();

            render(graphics);

            if (Board.isPossibleMovement(piecePosX, piecePosY + 1, pieceKind, pieceRotation))
                piecePosY++;
            else {
                Board.storePiece(piecePosX, piecePosY, pieceKind, pieceRotation);
                Board.deletePossibleLines();

                if (Board.isGameOver()) {
                    System.out.println("Game over");
                }

                createNewPiece();
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            buffer.show();
        }
    }

    public void init() {
        control = new Controller(this);
        this.addKeyListener(control);
        requestFocus();

        // First piece
        pieceKind = random.nextInt(6);
        pieceRotation = random.nextInt(3);
        piecePosX = (Board.BOARD_WIDTH / 2) + Pieces.getXInitialPosition(pieceKind, pieceRotation);
        piecePosY = Pieces.getYInitialPosition(pieceKind, pieceRotation);

        // Next piece
        nextPieceKind = random.nextInt(6);
        nextPieceRotation = random.nextInt(3);
        nextPiecePosX = Board.BOARD_WIDTH + 1;
        nextPiecePosY = 25;
    }

    public void createNewPiece() {
        // The new piece
        pieceKind = nextPieceKind;
        pieceRotation = nextPieceRotation;
        piecePosX = (Board.BOARD_WIDTH / 2) + Pieces.getXInitialPosition(pieceKind, pieceRotation);
        piecePosY = Pieces.getYInitialPosition(pieceKind, pieceRotation);

        // Random next piece
        nextPieceKind = random.nextInt(6);
        nextPieceRotation = random.nextInt(3);
    }

    public void drawPiece(int posX, int posY, int pieceKind, int pieceRotation, Graphics2D graphics) {
        Color pieceColor = new Color(0, 255, 121);

        // Obtain the position in pixel in the screen of the block we want to draw
        int posPixelsX = Board.getXPosInPixels(posX);
        int posPixelsY = Board.getYPosInPixels(posY);

        // Travel the matrix of blocks of the piece and draw the blocks that are filled
        for (int i = 0; i < Board.PIECE_BLOCKS; i++ ) {
            for (int j = 0; j < Board.PIECE_BLOCKS; j++) {
                graphics.setColor(pieceColor);
                if (Pieces.getBlockType(pieceKind, pieceRotation, j, i) != 0) {
                    graphics.fillRect(
                            posPixelsX + i * Board.BLOCK_SIZE,
                            posPixelsY + j * Board.BLOCK_SIZE,
                            Board.BLOCK_SIZE - 1,
                            Board.BLOCK_SIZE - 1
                    );
                    graphics.fillRect(
                            posPixelsX + i * Board.BLOCK_SIZE,
                            posPixelsY + j * Board.BLOCK_SIZE,
                            Board.BLOCK_SIZE - 1,
                            Board.BLOCK_SIZE - 1
                    );
                }
            }
        }
    }

    public void drawScene(Graphics2D graphics) {
        drawBoard(graphics);
        drawPiece(piecePosX, piecePosY, pieceKind, pieceRotation, graphics);
        drawPiece(nextPiecePosX, nextPiecePosY, nextPieceKind, nextPieceRotation, graphics);
    }

    public void drawBoard(Graphics2D graphics) {
        // Calculate the limits of the board in pixels
        int boardXLimit1 = Board.BOARD_POSITION - (Board.BLOCK_SIZE * (Board.BOARD_WIDTH / 2)) - 1;
        int boardXLimit2 = Board.BOARD_POSITION + (Board.BLOCK_SIZE * (Board.BOARD_WIDTH / 2));
        int boardYLimit = SCREEN_TOP_BORDER;

        // Rectangles that delimits the board
        graphics.setColor(Color.BLUE);
        graphics.fillRect(boardXLimit1 - Board.BOARD_LINE_WIDTH, boardYLimit, Board.BOARD_LINE_WIDTH, (Board.BLOCK_SIZE * Board.BOARD_HEIGHT));
        graphics.fillRect(boardXLimit2, boardYLimit, Board.BOARD_LINE_WIDTH, (Board.BLOCK_SIZE * Board.BOARD_HEIGHT));

        // Rectangles that delimits the board
        boardXLimit1++;
        graphics.setColor(Color.RED);
        for (int i = 0; i < Board.BOARD_WIDTH; i++) {
            for (int j = 0; j < Board.BOARD_HEIGHT; j++) {
                // Check if the block is filled, if so, draw it
                if (!Board.isFreeBlock(i, j)) {
                    graphics.fillRect(
                            boardXLimit1 + i * Board.BLOCK_SIZE,
                            boardYLimit + j * Board.BLOCK_SIZE,
                            Board.BLOCK_SIZE - 1,
                            Board.BLOCK_SIZE - 1
                    );
                }
            }
        }
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        drawScene(graphics);
    }

    public static void pause() throws InterruptedException {
        running = false;
    }

    public static void resume() {
        running = true;
    }

}
