/*
 *      InvertImageModifier.java
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
import java.util.HashMap;
import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;

/**
 *
 * @author Artur Augustyniak
 */
public class InvertImageModifier implements ImageModifierInterface {

    @Override
    public void modifier(BufferedImage imgToTransform) throws Exception {

        if (imgToTransform == null) {
            throw new Exception();
        }

        for (int x = 0; x < imgToTransform.getWidth(); x++) {
            for (int y = 0; y < imgToTransform.getHeight(); y++) {
                int rgba = imgToTransform.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                imgToTransform.setRGB(x, y, col.getRGB());
            }
        }
    }

    @Override
    public String getName() {
        return "Invert";
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
