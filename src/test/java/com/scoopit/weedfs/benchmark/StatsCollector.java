package com.scoopit.weedfs.benchmark;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class StatsCollector extends UntypedActor {

    public static enum Event {
        noLocation, wrongChecksum, writeFile, readFile, printStats, wrongUploadedSize
    }

    int noLocation, wrongChecksum, writeFile, readFile, wrongUploadedSize;
    
    public static Props mkProps() {
        return Props.create(StatsCollector.class);
    }

    @Override
    public void onReceive(Object arg0) throws Exception {
        Event event = (Event) arg0;
        switch(event){
            case noLocation:
                noLocation++;
                break;
            case wrongChecksum:
                wrongChecksum++;
                break;
            case writeFile:
                writeFile++;
                break;
            case readFile:
                readFile++;
                break;
            case wrongUploadedSize:
                wrongUploadedSize++;
                break;
            case printStats:
                System.out.println("file read: " + readFile + ", file writes: " + writeFile + ", checksum errors: " + wrongChecksum
                        + ", wrong uploaded size: " + wrongUploadedSize
                        + ", no location error: " + noLocation);
                break;
                default: throw new IllegalArgumentException("Unknown event: "+event);
        }
    }

}
