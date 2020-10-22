package lib;

import java.io.File;
import java.io.IOException;

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

    public ProcessibleImage(File file) throws IOException, InterruptedException {
        read(file);
    }
    public ProcessibleImage(ImgRaster raster){
        originalRaster = raster;
        processedRaster = raster.clone();
    }
    public void read(File file) throws IOException, InterruptedException {
        originalRaster = new ImgRaster();
        pixelType = read(originalRaster, file);
        processedRaster = originalRaster.clone();
    }
    public void write(File file, String format) throws IOException {
        write(file, processedRaster, pixelType, format);
    }
    public ImgRaster getOriginalRaster() {
        return originalRaster;
    }
    public ImgRaster getProcessedRaster() {
        return processedRaster;
    }

}
