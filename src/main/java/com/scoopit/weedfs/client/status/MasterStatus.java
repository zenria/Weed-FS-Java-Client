package com.scoopit.weedfs.client.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MasterStatus {
    public Topology Topology;
    public String Version;
}
