package com.distributed;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NodeState {

    @Value("${node.id}")
    private int nodeId;

    private final AtomicInteger leaderId = new AtomicInteger(-1);

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
}