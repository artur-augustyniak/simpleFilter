/*
 *     ImagePair.java
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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Artur Augustyniak
 */
public class ImagePair {

    private BufferedImage orig;
    private BufferedImage modified;

    public ImagePair(BufferedImage orig) {
        this.orig = orig;
        this.modified = ImagePair.duplicate(this.orig);
    }

    public ImagePair(String origFileName) throws IOException {
        this(ImageIO.read(new File(origFileName)));
    }

    public BufferedImage getOrig() {
        return orig;
    }

    public BufferedImage getModified() {
        return modified;
    }

    public void setOrig(BufferedImage orig) {
        this.orig = orig;
    }

    public void setModified(BufferedImage modified) {
        this.modified = modified;
    }

    public static BufferedImage duplicate(BufferedImage image) {
        BufferedImage copyOfImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        return copyOfImage;
    }
}
