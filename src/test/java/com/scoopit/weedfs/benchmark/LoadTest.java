package com.scoopit.weedfs.benchmark;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.RoundRobinRouter;

/**
 * Have fun with Akka actors...
 */
public class LoadTest {

    /** Map<fid,md5> */
    static final ConcurrentHashMap<String, byte[]> fsDb = new ConcurrentHashMap<>(100000);

    static Random random = new Random();

    static final URL MASTER_URL;
    static {
        try {
            MASTER_URL = new URL("http://homer:9333");
        } catch (MalformedURLException u) {
            throw new RuntimeException(u);
        }
    }

    public static void main(String[] args) throws Exception {
        ActorSystem s = ActorSystem.create();

        ActorRef stats = s.actorOf(StatsCollector.mkProps(), "stats");
        ActorRef upload = s.actorOf(UploadRandomFile.mkProps(stats).withRouter(new RoundRobinRouter(100)), "upload");
        ActorRef read = s.actorOf(ReadAndCheckFile.mkProps(stats).withRouter(new RoundRobinRouter(100)), "read");
        ActorRef maestro = s.actorOf(Maestro.mkProps(upload, read), "maestro");

        long t = System.currentTimeMillis();
        while (true) {
            Thread.sleep(random.nextInt(10));

            if (System.currentTimeMillis() - t > 1000) {
                stats.tell(StatsCollector.Event.printStats, ActorRef.noSender());
                t = System.currentTimeMillis();
            }

            maestro.tell(new Object(), ActorRef.noSender());

        }

    }

}
