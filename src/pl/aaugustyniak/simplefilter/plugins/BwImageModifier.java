/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.aaugustyniak.simplefilter.plugins;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import pl.aaugustyniak.simplefilter.model.ImageModifierInterface;
import pl.aaugustyniak.simplefilter.model.ImagePair;

/**
 * @author artur
 */
public class BwImageModifier implements ImageModifierInterface {

    @Override
    public void modifier(BufferedImage imgToTransform) throws Exception {

        BufferedImage tmpSrc = ImagePair.duplicate(imgToTransform);
        imgToTransform = new BufferedImage(tmpSrc.getWidth(), tmpSrc.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = imgToTransform.createGraphics();
        g2d.drawImage(tmpSrc, 0, 0, null);


    }

    @Override
    public String getName() {
        return "Binary Image";
    }

    @Override
    public boolean hasDialog() {
        return false;
    }

    @Override
    public HashMap<String, Object> getParams() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
