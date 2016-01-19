package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nat-nyan on 17.11.2015.
 */
public class Tetris extends Canvas implements Runnable {

    public static final int WIDTH = 400, HEIGHT = 565;
    //Number of milliseconds that the piece remains before going 1 block down
    public static final int WAIT_TIME = 700;
    Controller control;
    public static JFrame frame = new JFrame("Tetris");

    //Pieces definition
    public static char[][][][] mPieces = new char[][][][] {
        //kind, rotations, horizontal blocks, vertical blocks
            //square
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
            //I
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
            //L
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
            //L mirrored
            {
                    {
                            {0, 0, 1, 0, 0},
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
            //N
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
            //N mirrored
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
            //T
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

    public static int piece;
    public static int pieceRotation;
    public static int piecePosX;
    public static int piecePosY;

    private static int nextPiece;
    private static int nextPieceRotation;
    private static int nextPiecePosX;
    private static int nextPiecePosY;

    private static Random random = new Random();
    private Image[] tetrisBlocks;
    private static Pieces pieces;
    private static Board board;

    //Displacement of the piece to the position where it is first drawn in the board when it is created
    public static int[][][] mPiecesInitialPosition = new int[][][] {
        //kind, rotation, position
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

        frame.setSize(WIDTH, HEIGHT);
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
        bar.setBounds(0, 0, WIDTH, 25);

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
                //Code for new game
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
        tetris.setBounds(0, 25, WIDTH, HEIGHT - 25);

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
        Thread t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    public void run() {
        init();
        boolean running = true;
        int x = 0;
        int y = 0;
        while (running) {
            BufferStrategy buffer = getBufferStrategy();
            if(buffer == null) {
                createBufferStrategy(2);
                continue;
            }
            Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();
            render(graphics, x, y);

            try {
                Thread.sleep(100);
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

        try {
            tetrisBlocks = ImageLoader.loadImage("/tetris.png", 25);
        }
        catch (IOException e) {
            System.out.println("Error loading in tetris.png");
            System.exit(1);
        }

        //First piece
        piece = random.nextInt(6);
        pieceRotation = random.nextInt(3);
        piecePosX = (Board.BOARD_WIDTH / 2) + Pieces.getXInitialPosition(piece, pieceRotation);
        piecePosY = Pieces.getYInitialPosition(piece, pieceRotation);

        //Next piece
        nextPiece = random.nextInt(6);
        nextPieceRotation = random.nextInt(3);
        nextPiecePosX = Board.BOARD_WIDTH + 5;
        nextPiecePosY = 5;

    }

    public void createNewPiece() {
        //The new piece
        piece = nextPiece;
        pieceRotation = nextPieceRotation;
        piecePosX = (Board.BOARD_WIDTH / 2) + Pieces.getXInitialPosition(piece, pieceRotation);
        piecePosY = Pieces.getYInitialPosition(piece, pieceRotation);

        //Random next piece
        nextPiece = random.nextInt(6);
        nextPieceRotation = random.nextInt(3);
    }

    public void update(Graphics2D graphics) {
    }

    public void render(Graphics2D graphics, int x, int y) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.setFont((new Font("Calibri", Font.BOLD, 20)));
        graphics.drawString("TETRIS", 170, 50);
        graphics.drawImage(tetrisBlocks[6], 100 + x, 100 + y, 25, 25, null);
    }

}
