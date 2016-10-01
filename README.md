Weed-FS-Java-Client
===================

Low level java client library for the [Weed-FS](https://code.google.com/p/weed-fs/) REST interface.

Feel free to contribute.


### write ###

```
WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl("http://localhost:9333").build();
// Assignation( WeedFSFile(fid, version, ttl), Location(publicUrl,url))
Assignation a = client.assign(new AssignParams("java-load", 0, ReplicationStrategy.None, 1/* ttl:1 minute */));
// write
int writtenSize = client.write(a.weedFSFile, a.location, byteArray, 1/* ttl:1 minute */);
```

### read ###

```
http://127.0.0.1:9333/17,2fda220190
```

### delete (sample) ###

get location and delete

```
WeedFSClient client = WeedFSClientBuilder.createBuilder().setMasterUrl(MASTER_URL).build();
List<Location> locations;
try{
    // get Location(publicUrl,url) list
    locations = client.lookup(volumeId);
}catch(WeedFSException wfe){
    // TODO this evaluation should be changed
    if(wfe.getMessage().contains("volumeId not found.")) // Already deleted
        return;
    throw wfe;
}
```

### update ###

get location and update

```
Long volumeId = Long.parseLong(fid.split(",")[0]);
List<Location> locations = client.lookup(volumeId);
locations.forEach(location->{
  WeedFSFile weedFSFile = new WeedFSFile(fid); 
  int writtenSize = client.write(weedFSFile, location, imageByteArray, ttlMinutes);
});
```