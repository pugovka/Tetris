package Tetris;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Nat-nyan on 01.12.2015.
 */
public class ImageLoader {

    public static Image[] loadImage(String path, int width) throws IOException {
        BufferedImage load = ImageIO.read(ImageLoader.class.getResource(path));
        Image[] images = new Image[load.getWidth() / width];
        for (int i = 0; i < images.length; i++) {
            images[i] = load.getSubimage(i * width, 0, width, width);
        }
        return images;
    }

}
