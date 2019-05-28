package com.scoopit.weedfs.client.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rack extends AbstractNode {
    public Integer Free;
    public String Id;
    public Integer Max;
    public List<DataNode> DataNodes;
}
