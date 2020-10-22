import java.io.File;
import java.io.IOException;

import lib.*;

public class DriverProgram {
    public static void main(String[] args) {

        ProcessibleImage image = null;
        try {
            image = new ProcessibleImage(new File("IMG_20200130_144253.jpg"));
        } catch (IOException | InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        ImageFilter grayscale = new ImageFilter() {
            public int transformPixel(int actualPixel) {
                byte colors[] = ImgRaster.splitColors(actualPixel);
                byte gray = (byte) ((colors[ImgRaster.RED_PIXEL] + colors[ImgRaster.GREEN_PIXEL]
                        + colors[ImgRaster.BLUE_PIXEL]) / 3);
                return ImgRaster.joinColors(gray, gray, gray, colors[ImgRaster.ALPHA_PIXEL]);
            };
        };

        grayscale.apply(image, ImageFilter.PROCESS_FROM_ORIGIN_RASTER);

        try {
            image.write(new File("test.jpg"), "JPG");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}