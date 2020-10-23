package lib;

import java.io.File;
import java.io.IOException;

/**
 * Creates a ProcessibleImage Instance for being processed with an {@code ImageFilter} Instance. It contains two ImgRaster 
 * one is original raster and one is processed raster.
 */
public class ProcessibleImage implements RasterReader, RasterWriter {
    /**
     * ImgRaster instance holding the original image raster data.
     */
    private ImgRaster originalRaster;
    /**
     * ImgRaster instance holding the processed image raster data.
     */
    private ImgRaster processedRaster;

    /**
     * Sets during reading of the image file and denotes the type of image pixel format defined in BufferedImage types.
     */
    private int pixelType;

    /**
     * Creates a new instance of ProcessibleImage from a Image file on disk, provided in parameter.
     * @param file pointer to the image file.
     * @throws IOException on error while reading the image file.
     * @throws InterruptedException on interruption during grabbing pixels from the image.
     */
    public ProcessibleImage(File file) throws IOException, InterruptedException {
        read(file);
    }

    /**
     * Creates a new instance of ProcessibleImage from an existing ImgRaster instance.
     * @param raster ImgRaster instance holding image data.
     */
    public ProcessibleImage(ImgRaster raster){
        originalRaster = raster;
        processedRaster = raster.clone();
    }
    /**
     * Reads a local disk file image and prepares the instance on which it is called.
     * @param file pointer to the image file.
     * @throws IOException on error reading the file.
     * @throws InterruptedException on interruption during grabbing pixels from the image.
     */
    public void read(File file) throws IOException, InterruptedException {
        originalRaster = new ImgRaster();
        pixelType = read(originalRaster, file);
        processedRaster = originalRaster.clone();
    }

    /**
     * Writes the current processed raster instance to the file pointer provided in parameter as image file 
     * format specified.
     * @param file pointer to the image file.
     * @param format of the image
     * @throws IOException on error writing to the file.
     */
    public void write(File file, String format) throws IOException {
        write(file, processedRaster, pixelType, format);
    }
    /**
     * Returns the original raster instance.
     * @return ImgRaster instance holding the original raster.
     */
    public ImgRaster getOriginalRaster() {
        return originalRaster;
    }
    /**
     * Returns the processed raster instance.
     * @return ImgRaster instance holding the processed raster.
     */
    public ImgRaster getProcessedRaster() {
        return processedRaster;
    }

}
