package lib;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * RasterWriter interface defines how a ImgRaster should be written to a file.
 */
public interface RasterWriter {
    /**
     * Write method is responsible to store the available raster data in specified pixel format to any Image File format specified
     * by the parameters.
     * @param file File instance to write to.
     * @param raster ImgRaster instance to get the data from.
     * @param pixelType BufferedImage.TYPE* pixel type definition tells the format of each pixel.
     * @param format String defining the format of the Image File.
     * @throws IOException on IO error during writing the data to the file.
     */
    default void write(File file, ImgRaster raster, int pixelType, String format) throws IOException {
        if(raster.isEmpty())
            throw new IllegalArgumentException("Raster is empty");
        
            BufferedImage image = new BufferedImage(raster.getWidth(), raster.getHeight(), pixelType);
        for(int i = 0; i < raster.getHeight(); i++){
            for(int j = 0; j < raster.getWidth(); j++){
                image.setRGB(j, i, raster.getPixel(j, i));
            }
        }
        ImageIO.write(image, format, file);
    }
}
