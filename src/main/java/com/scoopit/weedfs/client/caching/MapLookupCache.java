package com.scoopit.weedfs.client.caching;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.scoopit.weedfs.client.Location;

public class MapLookupCache implements LookupCache {
    ConcurrentHashMap<Long, List<Location>> cache = new ConcurrentHashMap<>();

    @Override
    public void invalidate() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void invalidate(long volumeId) {
        cache.remove(volumeId);
    }

    @Override
    public List<Location> lookup(long volumeId) {
        return cache.get(volumeId);
    }

    @Override
    public void setLocation(long volumeId, List<Location> locations) {
        cache.put(volumeId, locations);
    }

}
