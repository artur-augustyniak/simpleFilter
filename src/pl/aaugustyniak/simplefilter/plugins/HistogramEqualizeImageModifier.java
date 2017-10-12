/*
 *     HistogramEqualizeImageModifier.java
 *      
 *      Copyright 2013 Artur Augustyniak <artur@aaugustyniak.pl>
 *      
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *      
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *      
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */
package pl.aaugustyniak.simplefilter.plugins;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;

/**
 *
 * @author Artur Augustyniak
 */
public class HistogramEqualizeImageModifier implements ImageModifierInterface {

    @Override
    public void modifier(BufferedImage /*imgToTransform*/ original) throws Exception {

        int red;
        int green;
        int blue;
        int alpha;
        int newPixel;

        ArrayList<int[]> histLUT = histogramEqualizationLUT(original);

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();
                red = histLUT.get(0)[red];
                green = histLUT.get(1)[green];
                blue = histLUT.get(2)[blue];
                newPixel = colorToRGB(alpha, red, green, blue);
                original.setRGB(i, j, newPixel);
            }
        }
    }

    @Override
    public String getName() {
        return "Normalize RGB histogram";
    }

    @Override
    public boolean hasDialog() {
        return false;
    }

    @Override
    public HashMap<String, Object> getParams() {
        throw new UnsupportedOperationException("Not supported.");
    }

    private ArrayList<int[]> histogramEqualizationLUT(BufferedImage input) {
        ArrayList<int[]> imageHist = imageHistogram(input);
        ArrayList<int[]> imageLUT = new ArrayList<int[]>();

        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for (int i = 0; i < rhistogram.length; i++) {
            rhistogram[i] = 0;
        }
        for (int i = 0; i < ghistogram.length; i++) {
            ghistogram[i] = 0;
        }
        for (int i = 0; i < bhistogram.length; i++) {
            bhistogram[i] = 0;
        }

        long sumr = 0;
        long sumg = 0;
        long sumb = 0;

        float scale_factor = (float) (255.0 / (input.getWidth() * input.getHeight()));

        for (int i = 0; i < rhistogram.length; i++) {
            sumr += imageHist.get(0)[i];
            int valr = (int) (sumr * scale_factor);
            if (valr > 255) {
                rhistogram[i] = 255;
            } else {
                rhistogram[i] = valr;
            }

            sumg += imageHist.get(1)[i];
            int valg = (int) (sumg * scale_factor);
            if (valg > 255) {
                ghistogram[i] = 255;
            } else {
                ghistogram[i] = valg;
            }

            sumb += imageHist.get(2)[i];
            int valb = (int) (sumb * scale_factor);
            if (valb > 255) {
                bhistogram[i] = 255;
            } else {
                bhistogram[i] = valb;
            }
        }

        imageLUT.add(rhistogram);
        imageLUT.add(ghistogram);
        imageLUT.add(bhistogram);

        return imageLUT;

    }

    private ArrayList<int[]> imageHistogram(BufferedImage input) {
        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for (int i = 0; i < rhistogram.length; i++) {
            rhistogram[i] = 0;
        }
        for (int i = 0; i < ghistogram.length; i++) {
            ghistogram[i] = 0;
        }
        for (int i = 0; i < bhistogram.length; i++) {
            bhistogram[i] = 0;
        }

        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                int red = new Color(input.getRGB(i, j)).getRed();
                int green = new Color(input.getRGB(i, j)).getGreen();
                int blue = new Color(input.getRGB(i, j)).getBlue();
                rhistogram[red]++;
                ghistogram[green]++;
                bhistogram[blue]++;
            }
        }
        ArrayList<int[]> hist = new ArrayList<int[]>();
        hist.add(rhistogram);
        hist.add(ghistogram);
        hist.add(bhistogram);
        return hist;
    }

    private int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }
}
