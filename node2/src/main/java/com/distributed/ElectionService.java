package com.distributed;

import com.distributed.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionService {

    @Autowired
    private NodeState nodeState;

    @Autowired
    private PeerConfig peerConfig;

    public synchronized void runElection() {
        System.out.println("[Node " + nodeState.getNodeId() + "] Starting election...");

        int elected = nodeState.getNodeId();

        for (PeerConfig.Peer peer : peerConfig.getPeers()) {
            try {
                ManagedChannel channel = ManagedChannelBuilder
                        .forTarget(peer.getAddress())
                        .usePlaintext()
                        .build();

                TaskServiceGrpc.TaskServiceBlockingStub stub =
                        TaskServiceGrpc.newBlockingStub(channel);

                ElectionResponse response = stub.elect(
                        ElectionMessage.newBuilder()
                                .setCandidateId(nodeState.getNodeId())
                                .build()
                );

                if (response.getOk() && peer.getId() < elected) {
                    elected = peer.getId();
                }

                channel.shutdown();
            } catch (Exception e) {
                System.out.println("[Node " + nodeState.getNodeId() +
                        "] Peer Node " + peer.getId() + " unreachable");
            }
        }

        nodeState.setLeaderId(elected);
        System.out.println("[Node " + nodeState.getNodeId() +
                "] Election done. Leader is Node " + elected);

        // Broadcast the result to all peers
        broadcastLeader(elected);
    }

    private void broadcastLeader(int leaderId) {
        for (PeerConfig.Peer peer : peerConfig.getPeers()) {
            try {
                ManagedChannel channel = ManagedChannelBuilder
                        .forTarget(peer.getAddress())
                        .usePlaintext()
                        .build();

                TaskServiceGrpc.TaskServiceBlockingStub stub =
                        TaskServiceGrpc.newBlockingStub(channel);

                stub.announceLeader(
                        LeaderResponse.newBuilder()
                                .setLeaderId(leaderId)
                                .build()
                );

                channel.shutdown();
            } catch (Exception e) {
                System.out.println("[Node " + nodeState.getNodeId() +
                        "] Could not broadcast leader to Node " + peer.getId());
            }
        }
    }
}