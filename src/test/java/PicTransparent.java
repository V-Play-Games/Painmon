import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicTransparent {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\Downloads\\CaitlinRed.png"));
        for (int x = 0; x < image.getHeight(); x++)
            for (int y = 0; y < image.getWidth(); y++)
                if (((image.getRGB(x, y) >> 16) & 0xFF) > ((image.getRGB(x, y) >> 8) & 0xFF))
                    image.setRGB(x, y, 0);
        ImageIO.write(image, "png", new File("D:\\Downloads\\CaitlinFixed.png"));
    }
}
