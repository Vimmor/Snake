package com.platformy_programistyczne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {

    private final int FIELD_WIDTH = 300;
    private final int FIELD_HEIGHT = 300;
    private final int POINT_SIZE = 10;
    private final int POINT_AMOUNTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 10;

    private final int x[] = new int[POINT_AMOUNTS];
    private final int y[] = new int[POINT_AMOUNTS];

    private int dots;
    private int fruitX;
    private int fruitY;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball, fruit, head;

    public GameBoard() {
        buildBoard();
    }

    public void buildBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        loadImages();
        gameInitialize();
    }

    public void loadImages() {
        ImageIcon _ball = new ImageIcon("src/images/ball.png");
        ball = _ball.getImage();

        ImageIcon _fruit = new ImageIcon("src/images/apple.png");
        fruit = _fruit.getImage();

        ImageIcon _head = new ImageIcon("src/images/head.png");
        head = _head.getImage();
    }

    public void gameInitialize() {
        dots = 3;
        for (int i = 0; i < dots; ++i) {
            x[i] = 50 - i * 10;
            y[i] = 50;
        }

        locateFruit();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(fruit, fruitX, fruitY, this);

            for (int i = 0; i < dots; ++i) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(ball, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        final String message = "Game is Over";
        Font messageFont = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(messageFont);

        g.setColor(Color.white);
        g.setFont(messageFont);
        g.drawString(message, (FIELD_WIDTH - metr.stringWidth((message)))/ 2, FIELD_HEIGHT / 2);
    }

    public void checkFruit() {
        if ((x[0] == fruitX) && (y[0] == fruitY)) {
            ++dots;
            locateFruit();
        }
    }

    public void locateFruit() {
        int r = (int) (Math.random() * RAND_POS);
        fruitX = r * POINT_SIZE;

        r = (int) (Math.random() * RAND_POS);
        fruitY = r * POINT_SIZE;
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkFruit();
            checkIfCollision();
            makeMove();
        }
    }

    private void checkIfCollision() {
        for (int i = dots; i > 0; --i) {
            if ((i > 4 ) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        if ((y[0] >= FIELD_HEIGHT) || (y[0] < 0) || (x[0] >= FIELD_WIDTH) || (x[0] < 0)) {
            inGame = false;
        }
        if (!inGame) {
            timer.stop();
        }
    }

    public void makeMove() {
        for (int i = dots; i > 0; --i) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) x[0] -= POINT_SIZE;
        if (rightDirection) x[0] += POINT_SIZE;
        if (upDirection) y[0] -= POINT_SIZE;
        if (downDirection) y[0] += POINT_SIZE;
    }


}
