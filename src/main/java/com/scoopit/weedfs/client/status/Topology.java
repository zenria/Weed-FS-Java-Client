package com.scoopit.weedfs.client.status;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Topology extends AbstractNode {
    public List<DataCenter> DataCenters;
    public List<Layout> layouts;

    private class Stats {
        int dcCount;
        int rackCount;
        int nodeCount;
        List<DataNode> nodeList = new LinkedList<>();
    }

    private Stats stats;

    private void computeStats() {
        if (DataCenters == null) {
            return;
        }
        stats = new Stats();
        for (DataCenter dc : DataCenters) {
            stats.dcCount++;
            if (dc.Racks == null) {
                continue;
            }
            for (Rack rack : dc.Racks) {
                stats.rackCount++;
                if (rack.DataNodes == null) {
                    continue;
                }
                stats.nodeCount += rack.DataNodes.size();
                stats.nodeList.addAll(rack.DataNodes);
            }
        }
    }

    public int getDataCenterCount() {
        if (stats == null) {
            computeStats();
        }
        if (stats == null) {
            return 0;
        }
        return stats.dcCount;
    }

    public int getRackCount() {
        if (stats == null) {
            computeStats();
        }
        if (stats == null) {
            return 0;
        }
        return stats.rackCount;
    }

    public int getDataNodeCount() {
        if (stats == null) {
            computeStats();
        }
        if (stats == null) {
            return 0;
        }
        return stats.nodeCount;
    }

    public List<DataNode> getDataNodes() {
        if (stats == null) {
            computeStats();
        }
        if (stats == null) {
            return Collections.emptyList();
        }
        return stats.nodeList;
    }
}
