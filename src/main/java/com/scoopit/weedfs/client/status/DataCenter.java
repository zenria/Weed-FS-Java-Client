package com.scoopit.weedfs.client.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCenter extends AbstractNode {
    public Integer Free;
    public String Id;
    public Integer Max;
    public List<Rack> Racks;
}
