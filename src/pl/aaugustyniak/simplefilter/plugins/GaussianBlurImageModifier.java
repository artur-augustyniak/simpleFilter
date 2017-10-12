/*
 *      GaussianBlurImageModifier.java
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
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JSlider;
import pl.aaugustyniak.simplefilter.model.ConvolveTools;
import pl.aaugustyniak.simplefilter.model.ConvolveTools.ConvolutionMask;
import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;

/**
 * Adjustable Gaussian blur
 *
 * @author Artur Augustyniak
 */
public class GaussianBlurImageModifier implements ImageModifierInterface {

    private HashMap<String, Object> params;
    private JSlider radiusSlider;
    private JSlider sigmaSlider;

    private class GaussianMask implements ConvolutionMask {

        private Double sigma;
        private Integer maskRadius;
        private Integer maskSize;
        private Integer maskSizeFlat;

        public GaussianMask(Double sigma, Integer maskRadius) {
            this.sigma = sigma;
            this.maskRadius = maskRadius;
            this.maskSize = 2 * maskRadius + 1;
            this.maskSizeFlat = maskSize * maskSize;
        }

        @Override
        public float[] getMask() {
            float[] maskFlat = new float[maskSizeFlat];
            Float coefSum = 0.0f;
            int xM = 0;
            int yM;
            for (int x = -1 * maskRadius; x <= maskRadius; x++) {
                yM = 0;
                for (int y = -1 * maskRadius; y <= maskRadius; y++) {
                    coefSum += maskFlat[maskSize * xM + yM] = new Float(evalGauss(new Float(x), new Float(y)));
                    yM++;
                }
                xM++;
            }
            for (int j = 0; j < maskFlat.length; j++) {
                maskFlat[j] = maskFlat[j] / coefSum;
            }
            return maskFlat;
        }

        private Double evalGauss(Float x, Float y) {

            return (1.0 / (2 * Math.PI * Math.pow(this.sigma, 2.0))) * Math.exp((-1.0 * (x * x + y * y) / (2.0 * Math.pow(this.sigma, 2.0))));
        }

        @Override
        public int getMaskWidth() {
            return maskSize;
        }

        @Override
        public int getMaskHeight() {
            return maskSize;
        }
    }

    public GaussianBlurImageModifier() {
        radiusSlider = new JSlider();
        radiusSlider.setModel(new DefaultBoundedRangeModel(2, 0, 1, 10));
        sigmaSlider = new JSlider();
        sigmaSlider.setModel(new DefaultBoundedRangeModel(2, 0, 1, 400));
        this.params = new HashMap<String, Object>();
        this.params.put("Radius", radiusSlider);
        this.params.put("Sigma", sigmaSlider);
    }

    @Override
    public HashMap<String, Object> getParams() {
        return params;
    }

    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     *
     * @param imgToTransform
     * @throws Exception
     */
    @Override
    public void modifier(BufferedImage imgToTransform) throws Exception {
        ConvolveTools.convolve(imgToTransform, new GaussianMask(new Double(sigmaSlider.getValue()) / 100.0, radiusSlider.getValue()));
    }

    @Override
    public String getName() {
        return "Gausian Blur";
    }
}
