package com.scoopit.weedfs.client.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scoopit.weedfs.client.ReplicationStrategy;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Volume {
    public int Id;
    public long Size;
    public String RepType;
    public String Collection;
    public String Version;
    public long FileCount;
    public long DeleteCount;
    public long DeletedByteCount;
    public boolean ReadOnly;

    public ReplicationStrategy getReplicationStrategy() {
        return ReplicationStrategy.fromParameterValue(RepType);
    }
}
