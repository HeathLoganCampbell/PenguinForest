package dev.cobblesword.penguinforest;

import dev.cobblesword.penguinforest.assets.Asset;
import dev.cobblesword.penguinforest.entity.EntityManager;
import dev.cobblesword.penguinforest.entity.living.PenguinEntity;
import dev.cobblesword.penguinforest.fx.Bitmap;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

public class PenguinForestClient extends Applet implements Runnable
{
    private int width, height, scale;
    private int mouseX, mouseY;
    private int lastMouseX, lastMouseY;

    private PenguinEntity player;

    public static EntityManager entityManager;

    public PenguinForestClient(int width, int height, int scale)
    {
        this.width = width;
        this.height = height;
        this.scale = scale;

        setSize(width * scale, height * scale);

        entityManager = new EntityManager();
    }

    @Override
    public void start()
    {
        super.start();

        enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        long lastTime = System.nanoTime();
        Graphics sg = getGraphics();
        Random random = new Random();
        Bitmap screen = new Bitmap(width, height);
        screen.pixels = pixels;
        int tick = 0;

        player = new PenguinEntity();

        double spawnX = Main.WIDTH / 2d;
        double spawnY = (Main.HEIGHT * 3) / 4d;

        spawnX += (random.nextDouble() * 100) - 50;
        spawnY += (random.nextDouble() * 100) - 50;

        player.spawn(spawnX, spawnY);

        entityManager.registerEntity(this.player);

        while(true)
        {
            entityManager.update(tick);

            screen.draw(Asset.DESERT, 0, 0);

            entityManager.render(screen);

            sg.drawImage(image, 0, 0, width * scale, height * scale, 0, 0, width, height, null);
            do
            {
                Thread.yield();
            }
            while (System.nanoTime() - lastTime < 0);
            if (!isActive()) return;

            lastTime += (1000000000 / 30);
            tick++;
        }
    }

    private void updateMousePosition(MouseEvent e)
    {
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        mouseX = e.getX() / scale;
        mouseY = e.getY() / scale;
    }

    public void processEvent(AWTEvent e)
    {
        switch (e.getID())
        {
            case KeyEvent.KEY_PRESSED:
            case KeyEvent.KEY_RELEASED:
//                k[((KeyEvent) e).getKeyCode()] = down;
                break;
//            case MouseEvent.MOUSE_PRESSED:
            case MouseEvent.MOUSE_MOVED:
                updateMousePosition((MouseEvent) e);

                entityManager.handleHover(mouseX, mouseY);
                break;
            case MouseEvent.MOUSE_RELEASED:
            case MouseEvent.MOUSE_DRAGGED:
                updateMousePosition((MouseEvent) e);

                int button = ((MouseEvent) e).getButton();

                boolean rightClick = button == MouseEvent.BUTTON3;
                boolean leftClick = button == MouseEvent.BUTTON1;

                boolean success = entityManager.handleClicks(mouseX, mouseY, leftClick, rightClick);
                if(!success)
                {
                    player.onClicked(mouseX, mouseY, leftClick, rightClick);
                }
        }
    }
}
