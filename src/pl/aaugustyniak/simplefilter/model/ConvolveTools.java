/*
 *      ConvolveTools.java
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
package pl.aaugustyniak.simplefilter.model;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * Tools for generating convolution masks and wrapper for ConvolveOp
 *
 * @author Artur Augustyniak
 */
public class ConvolveTools {

    public interface ConvolutionMask {

        public float[] getMask();

        public int getMaskWidth();

        public int getMaskHeight();
    }

    /**
     * Convolution with given mask.
     *
     * @param imgToTransform
     * @param mask
     */
    public static void convolve(BufferedImage imgToTransform, ConvolutionMask mask) {
        BufferedImage tmpSrc = ImagePair.duplicate(imgToTransform);
        Kernel kernel = new Kernel(mask.getMaskWidth(), mask.getMaskHeight(), mask.getMask());
        ConvolveOp cop;
        cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(tmpSrc, imgToTransform);
    }
}
