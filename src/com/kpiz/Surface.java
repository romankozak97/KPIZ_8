package com.kpiz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Roman on 23.11.2015.
 */
class Surface extends JPanel implements ActionListener {
    private final int START_X = 40;
    private final int START_Y = 40;
    private BufferedImage bImage = new BufferedImage(710, 410, BufferedImage.TYPE_INT_RGB);
    private int UserX[] = new int[] {0, 1, 3, 4, 4, 7, 8, 10};
    private int UserY[] = new int[] {0, 2, 1, 3, 4, 3, 2, 5};

    /**
     * Convert X-axis coordinates from user system to canvas system
     * @param width Width of the canvas
     * @param userX Array of user X-axis coordinates
     * @return Array of canvas X-axis coordinates
     */
    private int[] convertXCoords(int width, int userX[]) {
        int formX[] = userX;
        for (int i = 0; i < userX.length; i++) {
            formX[i] = START_X + userX[i] * 50;
        }
        return formX;
    }

    /**
     * Conver Y-axis coordinates from user system to canvas system
     * @param height Height of the canvas
     * @param userY Array of user Y-axis coordinates
     * @return Array of canvas Y-axis coordinates
     */
    private int[] convertYCoords(int height, int userY[]) {
        int formY[] = userY;
        for (int i = 0; i < userY.length; i++) {
            formY[i] = height - (START_Y + userY[i] * 50);
        }
        return formY;
    }

    /**
     * Draw a graph on the canvas according to data
     * @param userX Array of X-axis coordinates
     * @param userY Array of Y-axis coordinates
     */
    public void setGraph(int userX[], int userY[])
    {
        this.UserX = userX;
        this.UserY = userY;
        repaint();
    }

    /**
     * Draw a graph, put it on the canvas and into the file
     * @param g Graphics object
     */
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        Graphics2D ig2d = bImage.createGraphics();

        // get width and height of canvas
        int w = getWidth();
        int h = getHeight();

        // set white background to canvas
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, w, h);
        ig2d.setBackground(Color.white);
        ig2d.clearRect(0, 0, w, h);

        // arrays of points' coordinates
        int x[] = convertXCoords(w, UserX);
        int y[] = convertYCoords(h, UserY);

        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        ig2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));

        // set color for grid
        g2d.setPaint(new Color(220, 220, 220));
        ig2d.setPaint(new Color(220, 220, 220));

        // draw coordinates axes grid
        for (int i = 0; i < 13; i++)
        {
            int x1 = convertXCoords(w, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13})[i];
            int x2 = convertXCoords(w, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13})[i];
            g2d.drawLine(x1, 40, x2, h-40);
            ig2d.drawLine(x1, 40, x2, h-40);
        }
        for (int i = 0; i < 7; i++)
        {
            int y1 = convertYCoords(h, new int[] {1, 2, 3, 4, 5, 6, 7})[i];
            int y2 = convertYCoords(h, new int[] {1, 2, 3, 4, 5, 6, 7})[i];
            g2d.drawLine(40, y1, w-40, y2);
            ig2d.drawLine(40, y1, w-40, y2);
        }

        // set color for graph
        g2d.setPaint(Color.red);
        ig2d.setPaint(Color.red);

        // draw graph
        for (int i = 0; i < x.length; i++)
        {
            if (i == x.length-1) break;
            g2d.drawLine(x[i], y[i], x[i+1], y[i+1]);
            ig2d.drawLine(x[i], y[i], x[i+1], y[i+1]);
        }

        // set color for coordinates axes
        g2d.setPaint(Color.gray);
        ig2d.setPaint(Color.gray);

        // draw coordinates axes
        g2d.drawLine(20, h-40, w-20, h-40);
        g2d.drawLine(40, 20, 40, h - 20);
        ig2d.drawLine(20, h-40, w-20, h-40);
        ig2d.drawLine(40, 20, 40, h - 20);
    }

    /**
     * Painting component
     * @param g Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * Repainting component on action performed
     * @param e ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    /**
     * Save the image to file "img.png"
     */
    public void saveImage()
    {
            File saveFile = new File("img.png");
            try {
                ImageIO.write(bImage, "png", saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
