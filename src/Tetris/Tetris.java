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
    private Controller control;
    public Thread mainThread;
    public static JFrame frame = new JFrame("Tetris");
    public static boolean running = true;
    public static int pieceKind;
    public static int pieceRotation;
    public static int piecePosX;
    public static int piecePosY;
    private static int nextPieceKind;
    private static int nextPieceRotation;
    private static int nextPiecePosX;
    private static int nextPiecePosY;
    private static Random random = new Random();

    public static void main(String args[]) {
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

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

        Tetris tetris = new Tetris();
        tetris.setBounds(0, 25, SCREEN_WIDTH, SCREEN_HEIGHT - 25);

        frame.add(tetris);
        file.add(newGame);
        file.add(highScore);
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

        while (true) {
            BufferStrategy buffer = getBufferStrategy();
            if (buffer == null) {
                createBufferStrategy(3);
                continue;
            }

            Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();

            render(graphics);
            if (elapsed() && running) {
                if (Board.isPossibleMovement(piecePosX, piecePosY + 1, pieceKind, pieceRotation)) {
                    piecePosY++;
                } else {
                    Board.storePiece(piecePosX, piecePosY, pieceKind, pieceRotation);
                    Board.deletePossibleLines();
                    if (Board.isGameOver()) {
                        System.out.println("Game over");
                        System.exit(0);
                    }
                    createNewPiece();
                }
                time = System.nanoTime();
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

    public static void createNewPiece() {
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
                switch (Pieces.getBlockType(pieceKind, pieceRotation, j, i)) {
                    case 1: pieceColor = new Color(0, 255, 121); break;
                    // Pivot color
                    case 2: pieceColor = new Color(255, 250, 250); break;
                }

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
        for (int i = 0; i < Board.BOARD_WIDTH; i++) {
            for (int j = 0; j < Board.BOARD_HEIGHT; j++) {
                graphics.setColor(new Color(Math.min(i * i * 10, 255), Math.min(j * 25, 255), Math.min(i * j, 255)));
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

    private long time = System.nanoTime();

    private boolean elapsed() {
        return System.nanoTime() - time > 250000000;
    }

    public static void movePiece(int x, int y) {
        if (Board.isPossibleMovement(piecePosX + x, piecePosY + y, pieceKind, pieceRotation)) {
            if (x != 0) {
                piecePosX += x;
            }
            if (y != 0) {
                piecePosY += y;
            }
        }
    }
}
