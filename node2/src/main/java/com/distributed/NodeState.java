package com.distributed;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NodeState {

    @Value("${node.id}")
    private int nodeId;

    private final AtomicInteger leaderId = new AtomicInteger(-1);
    private final AtomicLong lastHeartbeatTime = new AtomicLong(System.currentTimeMillis());
    
    // Distributed lock management: taskId -> (nodeId, expirationTime)
    private final ConcurrentHashMap<String, Long> lockHolders = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> lockExpirations = new ConcurrentHashMap<>();

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

    // --- Méthodes pour les locks distribués ---
    
    /**
     * Try to acquire a lock for a task.
     * Returns true if lock acquired, false otherwise.
     */
    public synchronized boolean acquireLock(String taskId, int requesterId, long ttlMs) {
        // Clean up expired locks
        cleanExpiredLocks();
        
        Long expiration = lockExpirations.get(taskId);
        long currentTime = System.currentTimeMillis();
        
        // If task is locked and not expired, reject
        if (expiration != null && expiration > currentTime) {
            return false;
        }
        
        // Acquire lock
        lockHolders.put(taskId, (long) requesterId);
        lockExpirations.put(taskId, currentTime + ttlMs);
        return true;
    }
    
    /**
     * Release a lock for a task (only the holder can release)
     */
    public synchronized void releaseLock(String taskId, int requesterId) {
        Long holder = lockHolders.get(taskId);
        if (holder != null && holder == requesterId) {
            lockHolders.remove(taskId);
            lockExpirations.remove(taskId);
        }
    }
    
    /**
     * Get the current holder of a lock (or -1 if not held)
     */
    public int getLockHolder(String taskId) {
        cleanExpiredLocks();
        Long holder = lockHolders.get(taskId);
        return holder != null ? holder.intValue() : -1;
    }
    
    /**
     * Remove expired locks
     */
    private void cleanExpiredLocks() {
        long currentTime = System.currentTimeMillis();
        lockExpirations.entrySet().removeIf(entry -> entry.getValue() <= currentTime);
        lockHolders.entrySet().removeIf(entry -> !lockExpirations.containsKey(entry.getKey()));
    }

}