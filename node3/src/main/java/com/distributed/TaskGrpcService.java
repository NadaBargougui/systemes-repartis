package com.distributed;

import org.springframework.beans.factory.annotation.Autowired;

import com.distributed.grpc.ElectionMessage;
import com.distributed.grpc.ElectionResponse;
import com.distributed.grpc.Empty;
import com.distributed.grpc.LeaderResponse;
import com.distributed.grpc.TaskRequest;
import com.distributed.grpc.TaskResponse;
import com.distributed.grpc.TaskServiceGrpc;
import com.distributed.grpc.LockRequest;
import com.distributed.grpc.LockResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TaskGrpcService extends TaskServiceGrpc.TaskServiceImplBase {

    @Autowired
    private NodeState nodeState;

    @Autowired
    private ElectionService electionService;

    @Autowired
    private PeerConfig peerConfig;

    @Override
    public void submitTask(TaskRequest request, StreamObserver<TaskResponse> responseObserver) {
        System.out.println("[Node " + nodeState.getNodeId() +
                "] Received task: " + request.getTaskId());

        // If no leader yet, trigger election
        if (!nodeState.hasLeader()) {
            electionService.runElection();
        }

        // If I am not the leader, redirect
        if (!nodeState.isLeader()) {
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] Not leader, redirecting to Node " + nodeState.getLeaderId());

            String leaderAddress = getLeaderAddress();
            ManagedChannel channel = ManagedChannelBuilder
                    .forTarget(leaderAddress)
                    .usePlaintext()
                    .build();

            TaskServiceGrpc.TaskServiceBlockingStub stub =
                    TaskServiceGrpc.newBlockingStub(channel);

            TaskResponse response = stub.submitTask(request);
            channel.shutdown();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        // I am the leader — try to acquire lock before executing
        String taskId = request.getTaskId();
        long lockTTL = 10000; // 10 seconds TTL for the lock
        
        if (!nodeState.acquireLock(taskId, nodeState.getNodeId(), lockTTL)) {
            // Lock could not be acquired (another node has it)
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] CONFLICT: Task " + taskId + " is already locked by Node " + 
                    nodeState.getLockHolder(taskId));
            
            // Return error response
            TaskResponse response = TaskResponse.newBuilder()
                    .setTaskId(taskId)
                    .setResult("ERROR: Task is being processed by another node")
                    .setExecutedBy("NONE")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        try {
            // Execute the task with lock held
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] Acquired lock for task: " + taskId);
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] Executing task: " + taskId);

            String result = "Task '" + request.getPayload() +
                    "' executed by Node " + nodeState.getNodeId();

            TaskResponse response = TaskResponse.newBuilder()
                    .setTaskId(taskId)
                    .setResult(result)
                    .setExecutedBy("Node " + nodeState.getNodeId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } finally {
            // Always release the lock
            nodeState.releaseLock(taskId, nodeState.getNodeId());
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] Released lock for task: " + taskId);
        }
    }

    @Override
    public void getLeader(Empty request, StreamObserver<LeaderResponse> responseObserver) {
        LeaderResponse response = LeaderResponse.newBuilder()
                .setLeaderId(nodeState.getLeaderId())
                .setLeaderAddress(getLeaderAddress())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void elect(ElectionMessage request, StreamObserver<ElectionResponse> responseObserver) {
        int candidateId = request.getCandidateId();
        System.out.println("[Node " + nodeState.getNodeId() +
                "] Received election from candidate " + candidateId);

        if (candidateId < nodeState.getNodeId()) {
            nodeState.setLeaderId(candidateId);
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] Accepted Node " + candidateId + " as leader");
            responseObserver.onNext(ElectionResponse.newBuilder()
                    .setOk(true)
                    .setNodeId(nodeState.getNodeId())
                    .build());
        } else {
            responseObserver.onNext(ElectionResponse.newBuilder()
                    .setOk(false)
                    .setNodeId(nodeState.getNodeId())
                    .build());
        }
        responseObserver.onCompleted();
    }

    private String getLeaderAddress() {
        int leaderId = nodeState.getLeaderId();
        for (PeerConfig.Peer peer : peerConfig.getPeers()) {
            if (peer.getId() == leaderId) {
                return peer.getAddress();
            }
        }
        // Leader is this node itself
        return "localhost:" + peerConfig.getPort();
    }
    
    public void announceLeader(LeaderResponse request,
        StreamObserver<Empty> responseObserver) {
    System.out.println("[Node " + nodeState.getNodeId() +
            "] Leader announced: Node " + request.getLeaderId());
    nodeState.setLeaderId(request.getLeaderId());
    responseObserver.onNext(Empty.newBuilder().build());
    responseObserver.onCompleted();
    }

    @Override
    public void heartbeat(Empty request, StreamObserver<Empty> responseObserver) {
        // Délègue à ElectionService pour mettre à jour le timestamp
        electionService.onHeartbeatReceived();
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void acquireLock(LockRequest request, StreamObserver<LockResponse> responseObserver) {
        String taskId = request.getTaskId();
        int requesterId = request.getRequesterId();
        long ttlMs = request.getTtlMs();

        boolean acquired = nodeState.acquireLock(taskId, requesterId, ttlMs);
        
        if (acquired) {
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] Granted lock for task " + taskId + " to Node " + requesterId);
        } else {
            System.out.println("[Node " + nodeState.getNodeId() +
                    "] DENIED lock for task " + taskId + " (already held)");
        }

        LockResponse response = LockResponse.newBuilder()
                .setAcquired(acquired)
                .setHolderId(nodeState.getLockHolder(taskId))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void releaseLock(LockRequest request, StreamObserver<Empty> responseObserver) {
        String taskId = request.getTaskId();
        int requesterId = request.getRequesterId();

        nodeState.releaseLock(taskId, requesterId);
        System.out.println("[Node " + nodeState.getNodeId() +
                "] Released lock for task " + taskId + " from Node " + requesterId);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}