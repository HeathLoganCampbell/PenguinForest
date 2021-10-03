package dev.cobblesword.penguinforest.assets;

import dev.cobblesword.penguinforest.fx.Bitmap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Asset
{
    public static Bitmap DESERT = loadBitmap("levels/desert/layer1.png");
//    public static Bitmap DESERT_2_COFFEE = loadBitmap("levels/desert/layer2.png", true);
    public static Bitmap DESERT_COLLISION = loadBitmap("levels/desert/collision.png");

    public static Bitmap PENGUIN = loadBitmap("entities/penguin/spritesheet.png", true);
    public static Bitmap SNOWBALL = loadBitmap("entities/projectile/snowball/spritesheet.png");

    public static Bitmap loadBitmap(String file) {
        return loadBitmap(file, false);
    }

    public static Bitmap loadBitmap(String file, boolean transparent) {
        InputStream stream = Asset.class.getClassLoader().getResourceAsStream(file);
        BufferedImage image = null;
        try {
            image = ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        Bitmap bitmap = new Bitmap(width, height);

        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
        for (int i = 0; i < pixels.length; i++)
        {
            int pixel = pixels[i];

            int alpha = (pixel >> 24) & 0xFF;
            if(transparent && alpha == 0x00)
            {
                bitmap.pixels[i] = 0xFC00FC;
            }
            else
            {
                bitmap.pixels[i] = (pixels[i]);
            }
        }

        return bitmap;
    }
}
