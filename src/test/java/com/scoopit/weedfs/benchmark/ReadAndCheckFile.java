package com.scoopit.weedfs.benchmark;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.scoopit.weedfs.client.Location;
import com.scoopit.weedfs.client.WeedFSClient;
import com.scoopit.weedfs.client.WeedFSClientBuilder;
import com.scoopit.weedfs.client.WeedFSFile;

public class ReadAndCheckFile extends UntypedActor {

    final ActorRef statsActor;

    public static Props mkProps(ActorRef statsActor) {
        return Props.create(ReadAndCheckFile.class, statsActor);
    }

    public ReadAndCheckFile(ActorRef statsActor) {
        this.statsActor = statsActor;
    }

    @Override
    public void onReceive(Object arg0) throws Exception {
        statsActor.tell(StatsCollector.Event.readFile, getSelf());
        String fid = (String) arg0;

        WeedFSFile file = new WeedFSFile(fid);

        WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(LoadTest.MASTER_URL).build();

        List<Location> locations = client.lookup(file.getVolumeId());

        if (locations.size() == 0) {
            statsActor.tell(StatsCollector.Event.noLocation, getSelf());
            return;
        }

        InputStream is = client.read(file, locations.get(0));

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        byte buf[] = new byte[1024];
        int n;
        while ((n = is.read(buf)) > 0) {
            md5.update(buf, 0, n);
        }
        is.close();

        byte[] digest = md5.digest();
        byte[] dbDigest = LoadTest.fsDb.get(fid);
        for (int i = 0; i < digest.length; i++) {
            if (digest[i] != dbDigest[i]) {
                statsActor.tell(StatsCollector.Event.wrongChecksum, getSelf());
                return;
            }
        }
    }
}
