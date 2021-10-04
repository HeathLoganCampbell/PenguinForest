package dev.cobblesword.penguinforest;

import dev.cobblesword.penguinforest.utils.AppletAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int SCALE = 2;
    public static final String TITLE = "Penguin Forest";

    public static void main(String[] av)
    {
        JFrame frame = new JFrame(TITLE);
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                frame.setVisible(false);
                frame.dispose();
                System.exit(0);
            }
        });
        Container contentPanel = frame.getContentPane();
        AppletAdapter appletAdapter = new AppletAdapter();

        PenguinForestClient castle = new PenguinForestClient(WIDTH, HEIGHT, SCALE);

        castle.setStub(appletAdapter);

        contentPanel.add("Center", castle);

        frame.setSize(castle.getSize());
        frame.setVisible(true);

        contentPanel.remove(appletAdapter);

        castle.init();
        castle.start();
        frame.requestFocus();
    }
}
