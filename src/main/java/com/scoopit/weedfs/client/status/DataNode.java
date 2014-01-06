package com.scoopit.weedfs.client.status;

import com.scoopit.weedfs.client.Location;

public class DataNode extends AbstractNode {
    public String PublicUrl;
    public String Url;
    public int Volumes;

    public Location asLocation() {
        Location ret = new Location();
        ret.publicUrl = PublicUrl;
        ret.url = Url;
        return ret;
    }
}
