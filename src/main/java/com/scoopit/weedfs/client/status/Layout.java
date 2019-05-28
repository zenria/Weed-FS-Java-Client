package com.scoopit.weedfs.client.status;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scoopit.weedfs.client.ReplicationStrategy;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Layout {
    public String collection;
    public String replication;
    public List<Integer> writables;

    public ReplicationStrategy getReplicationStrategy() {
        return ReplicationStrategy.fromParameterValue(replication);
    }
}
