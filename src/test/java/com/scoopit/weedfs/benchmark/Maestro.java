package com.scoopit.weedfs.benchmark;

import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Maestro extends UntypedActor {

    Random random = new Random();

    final ActorRef uploadActor;
    final ActorRef readActor;

    public static Props mkProps(ActorRef uploadActor, ActorRef readActor) {
        return Props.create(Maestro.class, uploadActor, readActor);
    }

    public Maestro(ActorRef uploadActor, ActorRef readActor) {
        this.uploadActor = uploadActor;
        this.readActor = readActor;
    }

    @Override
    public void onReceive(Object arg0) throws Exception {

        // write or read ?

        boolean write = LoadTest.fsDb.size() == 0 || random.nextInt(100) > 85;

        if (write) {
            uploadActor.tell(new Object(), getSelf());
        } else {
            // find a file on the db...
            int n = random.nextInt(LoadTest.fsDb.size()), i = 0;
            for (String fid : LoadTest.fsDb.keySet()) {
                if (++i == n) {
                    readActor.tell(fid, getSelf());
                }
            }
        }
    }
}
