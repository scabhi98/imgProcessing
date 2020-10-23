package lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that defines the basic ImageFilter. Any Filter for Image Transformations can be implemented by
 * extending this class. Any Sub class extending this class should override either {@link #transform(ImgRaster, ImgRaster)} 
 * or {@link #transformPixel(int)} method to provide the filter effect.
 */
public abstract class ImageFilter implements RasterTransformer {

    /**
     * Tells the ImageFilter to transform the original raster.
     */
    public static final short PROCESS_FROM_ORIGIN_RASTER = 0x2;
    /**
     * Tells the ImageFilter to transform the already processed raster.
     */
    public static final short PROCESS_FROM_PROCESSED_RASTER = 0xe;

    /**
     * HashMap containing filter parameters that can be set using {@link #setParameter(String, Object)} method.
     */
    private Map<String, Object> parameters;

    /**
     * Creates an empty instance of ImageFilter.
     */
    public ImageFilter(){
        parameters = new HashMap<String, Object>();
    }

    /**
     * Call this method to apply the filter effect on the ProcessibleImage instance provided in parameter.
     * @param image ProcessibleImage instance
     * @param source Constant to decide the raster to be processed. 
     * Values must be either {@link #PROCESS_FROM_PROCESSED_RASTER} or {@link #PROCESS_FROM_ORIGIN_RASTER}.
     */
    public void apply(ProcessibleImage image, short source){
        if(source == PROCESS_FROM_ORIGIN_RASTER){
            transform(image.getOriginalRaster(),  image.getProcessedRaster());
        }
        else if(source == PROCESS_FROM_PROCESSED_RASTER) {
            transform(image.getProcessedRaster().clone(), image.getProcessedRaster());
        }
    }

    /**
     * Sets keys on parameters map. Can be used to hold filter specific parameters. For example, in GammaFilter implementation
     * gamma value can be given to the filter as {@code filter.setParameter("GAMMA", 1.0)}. This parameter value can be then used
     * in transform method definition by calling {@link #getParameter(String,Object)} method.
     * @param key name of the parameter
     * @param value of the parameter
     */
    public void setParameter(String key, Object value) {
        parameters.put(key, value);
    }

    /**
     * Returns the value of the parameter set by {@link #setParameter(String,Object)} method. If the specified parameter is not found
     * default value is returned.
     * For example, {@code filter.getParameter("GAMMA", 1.0)} returns the value of GAMMA parameter if set otherwise 1.0.
     * @param key of the parameter.
     * @param defaultValue default value to be returned if the parameter is not found.
     * @return the value of the parameter requested
     */
    public Object getParameter(String key, Object defaultValue) {
        return parameters.containsKey(key) ? parameters.get(key) : defaultValue ;
    }
}
