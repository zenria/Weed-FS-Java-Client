/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scoopit.weedfs.benchmark;

import com.scoopit.weedfs.client.AssignParams;
import com.scoopit.weedfs.client.Assignation;
import com.scoopit.weedfs.client.WeedFSClient;
import com.scoopit.weedfs.client.WeedFSClientBuilder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author sangdn
 */
public class SimpleUnitTest {
    public static void main(String[] args) {
        SimpleUnitTest test = new SimpleUnitTest();
        try {
            test.testReadWritePNG();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
    public static void testReadWritePNG() throws IOException{
         WeedFSClient client = WeedFSClientBuilder.createBuilder().build();
        
        Assignation assign = client.assign(AssignParams.DEFAULT);
        File file = new File("src/test/resources/img1024x760.png");
        
        
        int write = client.write(assign.weedFSFile, assign.location, file);
        System.out.println("Write Result:" + write);
        
        
        InputStream is2 =  client.read(assign.weedFSFile, assign.location);
        File t = new File("src/test/resources/img1024x760_temp.png");
        
        BufferedImage bi = ImageIO.read(is2);
        ImageIO.write(bi, "png", t);
        client.delete(assign.weedFSFile, assign.location);
    }
    
}
