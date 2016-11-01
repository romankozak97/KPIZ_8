package com.kpiz;

import com.javadocx.CreateDocx;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * Created by Roman on 23.11.2015.
 */
public class Application extends JFrame implements TableModelListener {
    /**
     * Class constructor
     */
    public Application() {
        initUI();
    }

    // window width and height constraints
    private final int WIDTH = 950;
    private final int HEIGHT = 450;

    // create canvas for graph and table
    private final Surface surface = new Surface();

    /**
     * Init user interface elements
     */
    private void initUI() {
        // table content description
        String[] columnNames = new String[] {"x", "y"};
        String[][] data = new String[][] {
                {"0", "0"},
                {"2", "2"},
                {"4", "5"},
                {"6", "3"},
                {"7", "5"},
                {"10", "3"},
                {"12", "6"},
                {"13", "1"}
        };
        // adjust alignment to columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // create table
        JTable table = new JTable(data, columnNames);
        // set alignment to columns
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        // add event listener to table
        table.getModel().addTableModelListener(this);
        // set NULL layout
        setLayout(null);

        // create scrollPane
        JScrollPane scrollPane = new JScrollPane();
        // set scrollBar settings
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // add table to scrollPane
        scrollPane.getViewport().add(table);

        // create button
        JButton saveBtn = new JButton("Save");

        // positioning elements
        scrollPane.setBounds(0, 0, 200, HEIGHT-50);
        saveBtn.setBounds(0, HEIGHT - 50, 200, 20);
        surface.setBounds(200, 0, WIDTH - 200, HEIGHT - 20);
        // add elements to frame
        add(saveBtn);
        add(scrollPane);
        add(surface);

        // on "Save" button clicked
        /*saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a temporary image from canvas
                surface.saveImage();
                // create .docx document
                CreateDocx docx = new CreateDocx("file");
                HashMap paramsImage = new HashMap();
                paramsImage.put("name", "img.png");
                paramsImage.put("scaling", "50");
                paramsImage.put("spacingTop", "100");
                paramsImage.put("spacingBottom", "0");
                paramsImage.put("spacingLeft", "100");
                paramsImage.put("spacingRight", "0");
                paramsImage.put("textWrap", "1");
                paramsImage.put("border", "0");
                paramsImage.put("borderDiscontinuous", "1");
                paramsImage.put("tamX", "20");
                paramsImage.put("tamY", "30");
                // add image to the document
                docx.addImage(paramsImage);
                docx.createDocx("file");

                // delete used image
                Path path = FileSystems.getDefault().getPath("img.png");
                try {
                    Files.deleteIfExists(path);
                }
                catch (IOException x)
                {
                    x.printStackTrace();
                }
            }
        });*/

        // adjust frame properties
        setResizable(false);
        setTitle("Graph");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Change graph on table field changed
     * @param e TableModelEvent object
     */
    public void tableChanged(TableModelEvent e)
    {
        TableModel model = (TableModel)e.getSource();
        int arrX[] = new int[model.getRowCount()];
        int arrY[] = new int[model.getRowCount()];
        for (int i = 0; i < model.getRowCount(); i++)
        {
            arrX[i] = Integer.valueOf(model.getValueAt(i, 0).toString());
            arrY[i] = Integer.valueOf(model.getValueAt(i, 1).toString());
        }
        surface.setGraph(arrX, arrY);
    }

    /**
     * Run the application
     * @param args Standard argument
     */
    public static void main(String[] args) {
        Application app = new Application();
        app.setVisible(true);
    }
}

