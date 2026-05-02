package com.distributed;

import org.springframework.beans.factory.annotation.Autowired;

import com.distributed.grpc.ElectionMessage;
import com.distributed.grpc.ElectionResponse;
import com.distributed.grpc.Empty;
import com.distributed.grpc.LeaderResponse;
import com.distributed.grpc.TaskRequest;
import com.distributed.grpc.TaskResponse;
import com.distributed.grpc.TaskServiceGrpc;

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

        // I am the leader — execute the task
        System.out.println("[Node " + nodeState.getNodeId() +
                "] I am the leader, executing task: " + request.getTaskId());

        String result = "Task '" + request.getPayload() +
                "' executed by Node " + nodeState.getNodeId();

        TaskResponse response = TaskResponse.newBuilder()
                .setTaskId(request.getTaskId())
                .setResult(result)
                .setExecutedBy("Node " + nodeState.getNodeId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
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
        responseObserver.onNext(ElectionResponse.newBuilder().setOk(true).build());
    } else {
        responseObserver.onNext(ElectionResponse.newBuilder().setOk(false).build());
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
}