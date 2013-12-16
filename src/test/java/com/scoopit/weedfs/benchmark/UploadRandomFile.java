package com.scoopit.weedfs.benchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.scoopit.weedfs.client.AssignParams;
import com.scoopit.weedfs.client.Assignation;
import com.scoopit.weedfs.client.ReplicationStrategy;
import com.scoopit.weedfs.client.WeedFSClient;
import com.scoopit.weedfs.client.WeedFSClientBuilder;

public class UploadRandomFile extends UntypedActor {

    Random random = new Random();

    final ActorRef statsActor;

    public static Props mkProps(ActorRef statsActor) {
        return Props.create(UploadRandomFile.class, statsActor);
    }

    public UploadRandomFile(ActorRef statsActor){
        this.statsActor = statsActor;
    }

    @Override
    public void onReceive(Object arg0) throws Exception {
        File f = File.createTempFile("weedfs-load-test", "tmp");
        f.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buf = new byte[random.nextInt(100) + 50];

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        int size = 0;
        for (int i = 0; i < random.nextInt(1000000) + 1; i++) {
            random.nextBytes(buf);
            fos.write(buf);
            md5.update(buf);
            size+=buf.length;
        }
        fos.close();

        WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(LoadTest.MASTER_URL).build();
        Assignation a = client.assign(new AssignParams("java-loadtest", ReplicationStrategy.None));
        // System.out.println(a.weedFSFile.fid + " assigned");
        int writtenSize = client.write(a.weedFSFile, a.location, new FileInputStream(f), "someName");

        if (writtenSize != size) {
            statsActor.tell(StatsCollector.Event.wrongUploadedSize, getSelf());
            return;
        }

        // System.out.println(a.weedFSFile.fid + " written, size=" + size);
        // update database
        LoadTest.fsDb.put(a.weedFSFile.fid, md5.digest());

        statsActor.tell(StatsCollector.Event.writeFile, getSelf());
    }

}
