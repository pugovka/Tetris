package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * Created by Nat-nyan on 17.11.2015.
 */
public class Tetris extends Canvas implements Runnable, KeyListener {

    public static final int WIDTH = 400, HEIGHT = 540;

    public static void main(String args[]) {
        JFrame frame = new JFrame("Tetris");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        Tetris tetris = new Tetris();
        frame.add(tetris);
        frame.setVisible(true);
        tetris.start();
    }

    public void start() {
        Thread t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
    public void run() {
        boolean running = true;
        while (running) {
            update();
            BufferStrategy buffer = getBufferStrategy();
            if(buffer == null) {
                createBufferStrategy(3);
                continue;
            }
            Graphics2D graphics = (Graphics2D) buffer.getDrawGraphics();
            render(graphics);
            buffer.show();
        }
    }
    public void update() {

    }
    public void render(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.setFont((new Font("Calibri", Font.BOLD, 20)));
        graphics.drawString("TETRIS", 170, 50);

    }
    public void keyPressed(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }
}