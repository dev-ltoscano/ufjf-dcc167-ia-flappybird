package com.ltoscano.ia.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author ltosc
 */
public class ImageHelper
{
    private static final HashMap<String, Image> IMG_CACHE = new HashMap<>();
    
    public static Image loadImage(String path) throws IOException 
    {
        Image img;

        if (IMG_CACHE.containsKey(path)) 
        {
            return IMG_CACHE.get(path);
        }

        img = ImageIO.read(new File(path));
        IMG_CACHE.put(path, img);
        
        return img;
    }
}
