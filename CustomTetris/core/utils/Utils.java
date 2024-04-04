package core.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static BufferedImage loadImage(String fileName) {
        try {
            InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
                return ImageIO.read(inputStream);
            } else {
                System.err.println("Не удалось загрузить изображение. InputStream is null.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
