import java.io.File;
import java.io.IOException;

import lib.*;

public class DriverProgram {
    public static void main(String[] args) {

        ProcessibleImage image = null;
        try {
            image = new ProcessibleImage(new File("lena.tif"));
        } catch (IOException | InterruptedException e1) {
            e1.printStackTrace();
        }

        ImageFilter grayscale = new ImageFilter() {
            public int transformPixel(int actualPixel) {
                short colors[] = ImgRaster.splitColors(actualPixel);
                short gray = (short) ((colors[ImgRaster.RED_PIXEL] + colors[ImgRaster.GREEN_PIXEL]
                        + colors[ImgRaster.BLUE_PIXEL]) / 3);
                return ImgRaster.joinColors(gray, gray, gray, colors[ImgRaster.ALPHA_PIXEL]);
            };
        };

        ImageFilter contrast = new ImageFilter(){
            @Override
            public void transform(ImgRaster actualRaster, ImgRaster processedRaster) {
                int height = actualRaster.getHeight();
                int width = actualRaster.getWidth();

                // for (int i = 0; i < height; i++) {
                //     for (int j = 0; j < width; j++) {
                //         short[] pixels = ImgRaster.splitColors(actualRaster.getPixel(j, i));
                //         System.out.println("Red: "+pixels[ImgRaster.RED_PIXEL] + ", Green: "+pixels[ImgRaster.GREEN_PIXEL] + ", Blue: "+ pixels[ImgRaster.BLUE_PIXEL]);
                //     }                    
                // }

                short redmin=255, redmax=0, greenmin=255, greenmax=0, bluemin=255, bluemax=0;
                
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        short colors[] = ImgRaster.splitColors(actualRaster.getPixel(j, i));
                        redmax = colors[ImgRaster.RED_PIXEL] > redmax ? colors[ImgRaster.RED_PIXEL] : redmax;
                        redmin = colors[ImgRaster.RED_PIXEL] < redmin ? colors[ImgRaster.RED_PIXEL] : redmin;
                        greenmax = colors[ImgRaster.GREEN_PIXEL] > greenmax ? colors[ImgRaster.GREEN_PIXEL] : greenmax;
                        greenmin = colors[ImgRaster.GREEN_PIXEL] < greenmin ? colors[ImgRaster.GREEN_PIXEL] : greenmin;
                        bluemax = colors[ImgRaster.BLUE_PIXEL] > bluemax ? colors[ImgRaster.BLUE_PIXEL] : bluemax;
                        bluemin = colors[ImgRaster.BLUE_PIXEL] < bluemin ? colors[ImgRaster.BLUE_PIXEL] : bluemin;
                    }
                }

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        short colors[] = ImgRaster.splitColors(actualRaster.getPixel(j, i));
                        colors[ImgRaster.RED_PIXEL] = (short) (((colors[ImgRaster.RED_PIXEL] - redmin)
                                / (redmax - redmin)) * 255);
                        colors[ImgRaster.GREEN_PIXEL] = (short) (((colors[ImgRaster.GREEN_PIXEL] - greenmin) / (greenmax - greenmin)) * 255);
                        colors[ImgRaster.BLUE_PIXEL] = (short) (((colors[ImgRaster.BLUE_PIXEL] - bluemin) / (bluemax - bluemin)) * 255);
                        processedRaster.setPixel(j, i, ImgRaster.joinColors(colors));
                    }
                }                
            }
        };

        GammaFilter gamma = new GammaFilter();
        try {
            // grayscale.apply(image, ImageFilter.PROCESS_FROM_ORIGIN_RASTER);
            // image.write(new File("test.jpg"), "JPG");

            gamma.setGamma(1.05);
            gamma.apply(image, ImageFilter.PROCESS_FROM_PROCESSED_RASTER);
            image.write(new File("test2.jpg"), "JPG");

            // contrast.apply(image, ImageFilter.PROCESS_FROM_PROCESSED_RASTER);
            // image.write(new File("test3.jpg"), "JPG");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class GammaFilter extends ImageFilter {
    double gamma;

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public int transformPixel(int actual) {
        short colors[] = ImgRaster.splitColors(actual);

        int r = (int) Math.pow( colors[ImgRaster.RED_PIXEL] , gamma);
        colors[ImgRaster.RED_PIXEL] = (short) ((short) r > 255 ? 255 : r);
        r = (int) Math.pow( colors[ImgRaster.GREEN_PIXEL] , gamma);
        colors[ImgRaster.GREEN_PIXEL] = (short) ((short) r > 255 ? 255 : r);
        r = (int) Math.pow( colors[ImgRaster.BLUE_PIXEL] , gamma);
        colors[ImgRaster.BLUE_PIXEL] = (short) ((short) r > 255 ? 255 : r);

        return ImgRaster.joinColors(colors);

    }
}