/*
 *     LowPassFilterImageModifier.java
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

import edu.emory.mathcs.jtransforms.fft.FloatFFT_2D;
import edu.emory.mathcs.jtransforms.fft.RealFFTUtils_2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;

/**
 *
 * @author Artur Augustyniak
 */
public class LowPassFilterImageModifier implements ImageModifierInterface {

    @Override
    public void modifier(BufferedImage imgToTransform) throws Exception {
        int width = java.lang.Integer.highestOneBit(imgToTransform.getWidth()) >> 1;
        int height = java.lang.Integer.highestOneBit(imgToTransform.getHeight()) >> 1;
        //byte[] pixels = (byte[])imgToTransform.getRaster().getDataElements(0, 0, imgToTransform.getWidth(), imgToTransform.getHeight(), null);
        float[][] pixels = new float[width][height];
        int[][] intPixels = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = new Float(imgToTransform.getRGB(i, j));
            }
        }

        FloatFFT_2D fft = new FloatFFT_2D(width, height);
        RealFFTUtils_2D unpacker = new RealFFTUtils_2D(width, height);


        fft.realForward(pixels);
        
        
        
        

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                intPixels[i][j] = (int) Math.abs(pixels[i][j]);
            }
        }


//        unpacker.pack(val1, r1, c1, data);
//        val2 = unpacker.unpack(r2, c2, data, 0);
//
//        FloatFFT_2D fFFT = new FloatFFT_2D(imgToTransform.getWidth(), imgToTransform.getHeight());
//        fFFT.realForward(pixels);

        BufferedImage bufferImage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //int Pixel = intPixel[x][y] << 16 | PixelArray[x][y] << 8 | PixelArray[x][y];
                imgToTransform.setRGB(x, y, intPixels[x][y]);
            }


        }
        
        //imgToTransform = bufferImage2;
    }

    @Override
    public String getName() {
        return "LowPassFilter";
    }

    @Override
    public boolean hasDialog() {
        return false;
    }

    @Override
    public HashMap<String, Object> getParams() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
