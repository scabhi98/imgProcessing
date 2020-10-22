package lib;

import java.awt.image.*;
import java.util.Arrays;

/**
 * ImgRaster class provides the ability to store pixel values and manipulate them in a raster.
 */
public class ImgRaster implements Cloneable {

    /**
     * Pixel array to store pixel values
     */
    int pixels[];
    /**
     * Integer value to store number of rows in raster or so called height of the image.
     */
    int height;
    /**
     * Integer value to store number of columns in raster or so called width of the image.
     */
    int width;

    /**
     * Default Constructor to create an empty instance of ImgRaster.
     */
    public ImgRaster() {
        height = 0;
        width = 0;
    }

    /**
     * Constructor to create an ImgRaster instance for a raster image with dimension specified by the parameter.
     * @param height of the image raster
     * @param width of the image raster
     */
    public ImgRaster(int height, int width){
        this.height = height;
        this.width = width;
        this.pixels = new int[height * width];
    }

    /**
     * Creates ImgRaster instance for given BufferedImage instance in parameter. Then starts grabbing pixels from the image asynchronously.
     * @param image BufferedImage instance to be converted to ImgRaster instance
     * @throws InterruptedException on interruption while grabbing the pixels from the image
     */
    public ImgRaster(BufferedImage image) throws InterruptedException {
        this.readPixels(image);
    }
    
    /**
     * Initiates pixel grabbing from the BufferedImage provided in parameter. Before any raster operations 
     * on empty ImgRaster instance one must call this method.
     * @param img BufferedImage instance to read pixels from.
     * @throws InterruptedException on interruption during grabbing pixels from the image.
     */
    public void readPixels(BufferedImage img) throws InterruptedException {
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.pixels = new int[this.height * this.width];
        
        PixelGrabber pgr = new PixelGrabber(
            img,
            0, 0,
            img.getWidth(),
            img.getHeight(),
            this.pixels,
            0,
            img.getWidth()
        );
        pgr.startGrabbing();
    }

    /**
     * Return height of the image represented by this raster.
     * @return height of the raster.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the total number of pixels in the image represented by this raster.
     * @return number of pixels
     */
    public int getSize() {
        return pixels.length;
    }

    /**
     * Returns the pixel array of this raster as single dimension Integer array.
     * @return pixel array of this raster
     */
    public int[] getPixels() {
        return pixels;
    }

    /**
     * Generates a copy of the pixels in raster
     * @return copy of the pixel array
     */
    public int[] copyOfPixels(){
        return Arrays.copyOf(pixels, pixels.length);
    }

    /**
     * Returns the width of raster image
     * @return width of raster image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets pixel value at specified position in raster
     * @param x abcissa of the pixel in raster
     * @param y ordinate of the pixel in raster
     * @param value to be assigned
     */
    public void setPixel(int x, int y, int value){
        pixels[y * width + x] = value;
    }
    
    /**
     * Returns pixel value at specified position in raster
     * @param x abcissa of the pixel in raster
     * @param y ordinate of the pixel in raster
     * @return pixel value specified by the coordinates in raster
     */
    public int getPixel(int x, int y){
        return pixels[y * width + x];
    }

    /**
     * Generates a copy of pixel array in 2D format.
     * @return copy of pixel values in 2D Array format.
     */
    public int[][] getPixels2D() {
        int pixels2d[][] = new int[this.height][];
        for (int i = 0; i < this.height; i++) {
            pixels2d[i] = Arrays.copyOfRange(this.pixels, i * width, (i + 1) * width - 1);
        }
        return pixels2d;
    }

    /**
     * Clones the current ImgRaster instance
     */
    @Override
    protected ImgRaster clone() {
        ImgRaster raster = new ImgRaster();
        raster.height = height;
        raster.width = width;
        raster.pixels = this.copyOfPixels();
        return raster;
    }

    /**
     * Checks whether the raster contains any data or not.
     * @return true if the raster contains any data, false otherwise.
     */
    public boolean isEmpty() {
        return this.pixels == null || this.pixels.length == 0;
    }

    /**
     * Represent the index of the red pixel in pixel value byte array returned by {@link #splitColors(int)} method.
     */
    public static final byte RED_PIXEL = 0;
    /**
     * Represent the index of the green pixel in pixel value byte array returned by {@link #splitColors(int)} method.
     */
    public static final byte GREEN_PIXEL = 1;
    /**
     * Represent the index of the blue pixel in pixel value byte array returned by {@link #splitColors(int)} method.
     */
    public static final byte BLUE_PIXEL = 2;
    /**
     * Represent the index of the alpha pixel in pixel value byte array returned by {@link #splitColors(int)} method.
     */
    public static final byte ALPHA_PIXEL = 3;
    
    /**
     * Splits the integer pixel representation of a color pixel in RGB or RGBA format into its components
     * pixel value byte array indexed by  {@link #RED_PIXEL}, {@link #GREEN_PIXEL}, {@link #BLUE_PIXEL} and {@link #ALPHA_PIXEL}
     * @param pixel value to be splitted into components.
     * @return the byte array consisting RGBA values.
     */
    public static byte[] splitColors(int pixel){
        byte colors[] = new byte[4];
        colors[ALPHA_PIXEL] = (byte) ((pixel >> 24) & 0xff);
        colors[RED_PIXEL] = (byte) ((pixel >> 16) & 0xff);
        colors[GREEN_PIXEL] = (byte) ((pixel >> 8) & 0xff);
        colors[BLUE_PIXEL] = (byte) (pixel & 0xff);
        return colors;
    }

    /**
     * Joins seperate pixel components into a single integer pixel representation.
     * @param red pixel value
     * @param green pixel value
     * @param blue pixel value
     * @param alpha pixel value
     * @return integer representation of joined pixel.
     */
    public static int joinColors(int red, int green, int blue, int alpha) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * Joins pixel component byte array splitted by {@link #splitColors(int)} into a single integer pixel representation.
     * @param colors byte array containing splitted color pixels.
     * @return integer representation of joined pixel.
     */
    static int joinColors(byte []colors) {
        return (colors[ALPHA_PIXEL] << 24) | (colors[RED_PIXEL] << 16) | (colors[GREEN_PIXEL] << 8) | colors[BLUE_PIXEL];
    }
}