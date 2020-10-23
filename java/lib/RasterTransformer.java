package lib;

/**
 * RasterTransformer interface defines how to transform a Raster image into a
 * new filtered Raster.
 */
public interface RasterTransformer {
    /**
     * Defines how to transform actualRaster pixels into a processedRaster specified by the parameters. Override this method in any
     * implementation to define the transformation. The Default implementation is set to transform each pixel by a transformation method
     * defined by {@link #transformPixel(int)} method. You can override {@link #transformPixel(int)} method instead overriding this if you
     * are going to transform the pixels independent of other pixels in raster.
     * @param actualRaster ImgRaster instance containing the original data.
     * @param processedRaster ImgRaster instance containing the processed data.
     */
    default void transform(ImgRaster actualRaster, ImgRaster processedRaster){
        for (int i = 0; i < actualRaster.getWidth(); i++) {
            for (int j = 0; j < actualRaster.getHeight(); j++) {
                processedRaster.setPixel(i, j, transformPixel(actualRaster.getPixel(i,j)));
            }
        }
    }

    /**
     * Defines how a pixel value will be transformed into another pixel value independently. This pixel value is combined integer representation
     * of RGBA values. One must split the pixel components before transforming them and join them before returning the value.
     * Splitting can be done using {@code short colors[] = ImgRaster.splitColors(pixel);}.
     * Access Blue pixel as {@code colors[ImgRaster.BLUE_PIXEL]}.
     * Joining pixels can be done using {@code ImgRaster.joinColors(colors)} 
     * @param actualPixel actual pixel value.
     * @return transformed pixel value.
     */
    default int transformPixel(int actualPixel){
        return actualPixel;
    }
}