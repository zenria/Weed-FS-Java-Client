package com.scoopit.weedfs.client.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scoopit.weedfs.client.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataNode extends AbstractNode {
    public Integer Free;
    public Integer Max;
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
