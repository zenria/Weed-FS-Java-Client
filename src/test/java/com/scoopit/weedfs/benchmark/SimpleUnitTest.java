/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scoopit.weedfs.benchmark;

import com.scoopit.weedfs.client.*;
import com.scoopit.weedfs.client.status.MasterStatus;
import com.scoopit.weedfs.client.status.VolumeStatus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import static sun.misc.Version.println;

/**
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

    public static void testReadWritePNG() throws IOException {
        String masterUrl = "http://3.0.226.54:9333";
        String publicUrl = "3.0.226.54:8080";
        String url = "3.0.226.54:8080" ;
//         WeedFSClient client = WeedFSClientBuilder.createBuilder().build();
        WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(new URL(masterUrl))
                .build();


        MasterStatus masterStatus=client.getMasterStatus();
        System.out.println(masterStatus.toString());


        Assignation assign = client.assign(AssignParams.DEFAULT);
        File file = new File("src/test/resources/img1024x760.png");

        VolumeStatus volumeStatus=client.getVolumeStatus(assign.location);
        System.out.println(volumeStatus.toString());

        int write = client.write(assign.weedFSFile, assign.location, file);
        System.out.println("Write Result:" + write);


        InputStream is2 = client.read(assign.weedFSFile, assign.location);
        File t = new File("src/test/resources/img1024x760_temp.png");

        BufferedImage bi = ImageIO.read(is2);
        ImageIO.write(bi, "png", t);
        client.delete(assign.weedFSFile, assign.location);
    }

}
