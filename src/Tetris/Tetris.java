package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nat-nyan on 17.11.2015.
 */
public class Tetris extends Canvas implements Runnable {

    public static final int WIDTH = 400, HEIGHT = 565;
    private Image[] tetrisBlocks;
    Controller control;
    public static JFrame frame = new JFrame("Tetris");

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
        String test = "";
        int x = 0;
        int y = 0;
        while (running) {
            BufferStrategy buffer = getBufferStrategy();
            if(buffer == null) {
                createBufferStrategy(3);
                continue;
            }
            Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();
            System.out.println(x);
            test = update(graphics);
            render(graphics, x, y);
            if (test == "left") {
                x -= 10;
                render(graphics, x, y);
            }
            if (test == "right") {
                x += 10;
                render(graphics, x, y);
            }
            if (test == "down") {
                y += 10;
                render(graphics, x, y);
            }
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
    }

    public String update(Graphics2D graphics) {
        String test = "";
        //System.out.println(control.left + " : " + control.right + " : " + control.rotate + " : " + control.down + " : " + control.pause);
        if (control.left) {
            test = "left";
        }
        if (control.right) {
            test = "right";
        }
        if (control.down) {
            test = "down";
        }
        return test;
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
