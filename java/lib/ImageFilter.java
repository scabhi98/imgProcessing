package lib;

public abstract class ImageFilter implements RasterTransformer {

    public static final byte PROCESS_FROM_ORIGIN_RASTER = 0x2;
    static final byte PROCESS_FROM_PROCESSED_RASTER = 0xe;

    public ImageFilter(){

    }

    public void apply(ProcessibleImage image, byte source){
        if(source == PROCESS_FROM_ORIGIN_RASTER){
            transform(image.getOriginalRaster(),  image.getProcessedRaster());
        }
        else if(source == PROCESS_FROM_PROCESSED_RASTER) {
            transform(image.getProcessedRaster().clone(), image.getProcessedRaster());
        }
    }
}
