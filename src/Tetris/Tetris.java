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
    private static Color pieceColor;
    private static Color nextPieceColor;

    public static void main(String args[]) {
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, SCREEN_WIDTH, 25);

        JMenu menuItemFile = new JMenu("File");
        menuItemFile.setBounds(0, 0, 45, 24);

        JMenuItem menuItemNewGame = new JMenuItem("New Game");
        menuItemNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Starting New Game...");
                Board.initBoard();
                createNewPiece();
            }
        });

        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Closing...");
                System.exit(0);
            }
        });

        Tetris tetris = new Tetris();
        tetris.setBounds(0, 25, SCREEN_WIDTH, SCREEN_HEIGHT - 25);

        frame.add(tetris);
        menuItemFile.add(menuItemNewGame);
        menuItemFile.add(menuItemExit);
        menuBar.add(menuItemFile);
        frame.add(menuBar);
        frame.setVisible(true);
        tetris.start();
    }

    private void start() {
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        Board.initBoard();
    }
    @Override
    public void run() {
        init();
        createNewPiece();

        while (true) {
            BufferStrategy buffer = getBufferStrategy();
            if (buffer == null) {
                createBufferStrategy(2);
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

    private void init() {
        Controller control = new Controller(this);
        this.addKeyListener(control);
        requestFocus();

        // First piece
        pieceKind = random.nextInt(6);
        pieceRotation = random.nextInt(3);
        piecePosX = (Board.BOARD_WIDTH / 2) + Pieces.getXInitialPosition(pieceKind, pieceRotation);
        piecePosY = Pieces.getYInitialPosition(pieceKind, pieceRotation);
        pieceColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

        // Next piece
        nextPieceKind = random.nextInt(6);
        nextPieceRotation = random.nextInt(3);
        nextPiecePosX = Board.BOARD_WIDTH + 1;
        nextPiecePosY = 25;
        nextPieceColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public static void createNewPiece() {
        // The new piece
        pieceKind = nextPieceKind;
        pieceRotation = nextPieceRotation;
        piecePosX = (Board.BOARD_WIDTH / 2) + Pieces.getXInitialPosition(pieceKind, pieceRotation);
        piecePosY = Pieces.getYInitialPosition(pieceKind, pieceRotation);
        pieceColor = nextPieceColor;

        // Random next piece
        nextPieceKind = random.nextInt(6);
        nextPieceRotation = random.nextInt(3);
        nextPieceColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private void drawPiece(int posX, int posY, int pieceKind, int pieceRotation, Graphics2D graphics, Color pieceColor) {

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

    private void drawScene(Graphics2D graphics) {
        drawBoard(graphics);
        drawPiece(piecePosX, piecePosY, pieceKind, pieceRotation, graphics, pieceColor);
        drawPiece(nextPiecePosX, nextPiecePosY, nextPieceKind, nextPieceRotation, graphics, nextPieceColor);
    }

    private void drawBoard(Graphics2D graphics) {
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
                graphics.setColor(new Color(Math.min(i * i * 5, 255), Math.min(j * 25, 255), Math.min(i * j, 255)));
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

    private void render(Graphics2D graphics) {

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

    public static void movePiece(int x) {
        if (Board.isPossibleMovement(piecePosX + x, piecePosY, pieceKind, pieceRotation)) {
            if (x != 0) {
                piecePosX += x;
            }
        }
    }
}
