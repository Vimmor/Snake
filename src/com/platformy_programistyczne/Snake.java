package com.platformy_programistyczne;

import javax.swing.*;
import java.awt.*;

public class Snake extends JFrame {

    public Snake() {
        initWindow();
    }

    public void initWindow() {
        add(new GameBoard());
        setResizable(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame snake = new Snake();
            snake.setVisible(true);
        });
    }
}as
