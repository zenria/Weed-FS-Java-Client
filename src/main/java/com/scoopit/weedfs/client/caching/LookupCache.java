package com.scoopit.weedfs.client.caching;

import java.util.List;

import com.scoopit.weedfs.client.Location;

public interface LookupCache {

    List<Location> lookup(long volumeId);

    void invalidate(long volumeId);

    void invalidate();

    void setLocation(long volumeId, List<Location> locations);
}
