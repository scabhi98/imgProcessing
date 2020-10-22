package lib;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * RasterReader interface defines how a ImgRaster should be read from a file.
 */
public interface RasterReader {
    /**
     * read method defines how a ImgRaster should be read from a file. Implementation should override this method however it has a
     * default definition to read the Image file to raster using BufferedImage reading process using {@code ImageIO.read()}
     * @param raster ImgRaster instance to hold the data.
     * @param file File pointer to the image file.
     * @throws IOException on IO error while reading data from the file.
     * @throws InterruptedException on interruption during while grabbing the image pixels.
     */
    default int read(ImgRaster raster, File file) throws IOException, InterruptedException {
        if(raster == null)
            raster = new ImgRaster();
        BufferedImage image = ImageIO.read(file);
        raster.readPixels(image);
        return image.getType();
    }
}
