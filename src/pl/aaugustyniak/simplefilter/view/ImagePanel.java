/*
 *      ImagePanel.java
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
package pl.aaugustyniak.simplefilter.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Artur Augustyniak
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private Image scaledImage;

    public ImagePanel() {
        super();

    }

    public void loadImage(BufferedImage img) {
        image = img;
        imageWidth = image.getWidth(this);
        imageHeight = image.getHeight(this);
        setScaledImage();
    }

    public void scaleImage() {
        setScaledImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        //super.paintChildren(g);
        super.paintComponent(g);
        if (scaledImage != null) {
            g.drawImage(scaledImage, 20, 20, this.scaledImage.getWidth(this)-30, this.scaledImage.getHeight(this)-30, this);
        }
    }

    private void setScaledImage() {
        if (image != null) {
            float iw = imageWidth;
            float ih = imageHeight;
            float pw = (float) (this.getWidth());
            float ph = (float) (this.getHeight());

            if (pw < iw || ph < ih) {
                if ((pw / ph) > (iw / ih)) {
                    iw = -5;
                    ih = ph;
                } else {
                    iw = pw;
                    ih = -5;
                }
                if (iw == 0) {
                    iw = -5;
                }
                if (ih == 0) {
                    ih = -5;
                }
                scaledImage = image.getScaledInstance(
                        new Float(iw).intValue(), new Float(ih).intValue(), Image.SCALE_DEFAULT);
            } else {
                scaledImage = image;
            }
        }
    }

    public BufferedImage getImage() {
        return (BufferedImage) scaledImage;
    }

    public void setImage(BufferedImage image) {
        this.image = (BufferedImage) scaledImage;
    }
}
