package com.distributed;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NodeState {

    @Value("${node.id}")
    private int nodeId;

    private final AtomicInteger leaderId = new AtomicInteger(-1);
    private final AtomicLong lastHeartbeatTime = new AtomicLong(System.currentTimeMillis());

    public int getNodeId() {
        return nodeId;
    }

    public int getLeaderId() {
        return leaderId.get();
    }

    public void setLeaderId(int id) {
        leaderId.set(id);
    }

    public boolean isLeader() {
        return leaderId.get() == nodeId;
    }

    public boolean hasLeader() {
        return leaderId.get() != -1;
    }

    // --- Méthodes pour le heartbeat ---
    public long getLastHeartbeatTime() {
        return lastHeartbeatTime.get();
    }

    public void updateLastHeartbeatTime() {
        lastHeartbeatTime.set(System.currentTimeMillis());
    }

    public void resetHeartbeat() {
        lastHeartbeatTime.set(System.currentTimeMillis());
    }

}