/*
 *     SimpleSharpenImageModifier.java
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

import java.awt.image.BufferedImage;
import java.util.HashMap;
import pl.aaugustyniak.simplefilter.model.ConvolveTools;
import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;

/**
 *
 * @author Artur Augustyniak
 */
public class SimpleSharpenImageModifier implements ImageModifierInterface {

    @Override
    public void modifier(BufferedImage imgToTransform) throws Exception {
        ConvolveTools.convolve(imgToTransform, new SimpleSharpenImageModifier.SharpenMask());
    }

    @Override
    public String getName() {
        return "Sharpen";
    }

    @Override
    public boolean hasDialog() {
        return false;
    }

    @Override
    public HashMap<String, Object> getParams() {
        throw new UnsupportedOperationException("Not supported.");
    }

    private static class SharpenMask implements ConvolveTools.ConvolutionMask {

        public SharpenMask() {
        }

        @Override
        public float[] getMask() {
            float[] SHARPEN3x3 = {
                0.f, -1.f, 0.f,
                -1.f, 5.0f, -1.f,
                0.f, -1.f, 0.f
            };
            return SHARPEN3x3;
        }

        @Override
        public int getMaskWidth() {
            return 3;
        }

        @Override
        public int getMaskHeight() {
            return 3;
        }
    }
}
