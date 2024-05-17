package net.yakodan;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

import static java.lang.Thread.*;

public class MainWindow extends JFrame {

    private static Canvas canvas;
    private static final int HEIGHT = 700;
    private static final int WIDTH = 700;
    private static final int resolution = 200;
    private static boolean isRunning;
    private static boolean[][] matrix = GameEngine.generateMatrix(resolution);
    private static final double height = (double) HEIGHT / resolution;
    private static final double width = (double) WIDTH / resolution;

    public MainWindow() {
        setTitle("Game of Life");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        add(canvas = new Canvas());

        isRunning = true;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        new Thread(() -> {
            while (isRunning) {
                render();
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private static void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        graphics.setColor(new Color(0xFFFFFF));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        graphics.setColor(new Color(0x595959));
        for (int i = 0; i < resolution; i++) {
            graphics.drawLine(0, (int) (i * height), WIDTH, (int) (i * height));
            graphics.drawLine((int) (i * width), 0, (int) (i * width), HEIGHT);
        }

        for(int i =0;i<resolution;i++){
            for(int j = 0;j<resolution;j++){
                if(matrix[i][j]){
                    graphics.fillRect((int) (i*width), (int) (j*height), (int) width, (int) height);
                }
            }
        }

        matrix = GameEngine.getNextMatrix(matrix);

        graphics.dispose();
        bs.show();
    }
}
