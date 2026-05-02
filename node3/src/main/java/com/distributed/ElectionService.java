package com.distributed;

import com.distributed.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ElectionService {

    @Autowired
    private NodeState nodeState;

    @Autowired
    private PeerConfig peerConfig;

    // Évite les élections parallèles
    private final AtomicBoolean electionInProgress = new AtomicBoolean(false);

    // Map pour éviter d'envoyer des heartbeats à soi-même
    private final ConcurrentHashMap<Integer, ManagedChannel> peerChannels = new ConcurrentHashMap<>();

    // Timeout avant de considérer le leader mort (3 secondes sans heartbeat)
    private static final long LEADER_TIMEOUT_MS = 3000;

    // Intervalle d'envoi des heartbeats (1 seconde)
    private static final long HEARTBEAT_INTERVAL_MS = 1000;

    @PostConstruct
    public void init() {
        // Pré-créer les canaux vers les pairs pour les heartbeats
        for (PeerConfig.Peer peer : peerConfig.getPeers()) {
            ManagedChannel channel = ManagedChannelBuilder
                    .forTarget(peer.getAddress())
                    .usePlaintext()
                    .build();
            peerChannels.put(peer.getId(), channel);
        }
        // Démarrer la surveillance du leader (si follower)
        startLeaderMonitor();
    }

    // ------------------------------------------------------------------
    // 1. ALGORITHME D'ÉLECTION (plus petit ID parmi les vivants)
    // ------------------------------------------------------------------
    public synchronized void runElection() {
        if (!electionInProgress.compareAndSet(false, true)) {
            System.out.println("[Node " + nodeState.getNodeId() + "] Election already in progress, skipping.");
            return;
        }

        try {
            System.out.println("[Node " + nodeState.getNodeId() + "] >>> STARTING ELECTION (reason: leadership lost or initial startup)");

            // 1. On se propose d'abord soi-même
            int elected = nodeState.getNodeId();
            System.out.println("[Node " + nodeState.getNodeId() + "] Proposing self (ID=" + elected + ")");

            // 2. Interroger tous les pairs
            for (PeerConfig.Peer peer : peerConfig.getPeers()) {
                try {
                    ManagedChannel channel = peerChannels.get(peer.getId());
                    if (channel == null) {
                        // fallback : créer un canal temporaire
                        channel = ManagedChannelBuilder.forTarget(peer.getAddress()).usePlaintext().build();
                    }

                    TaskServiceGrpc.TaskServiceBlockingStub stub =
                            TaskServiceGrpc.newBlockingStub(channel);

                    ElectionResponse response = stub.elect(
                            ElectionMessage.newBuilder()
                                    .setCandidateId(nodeState.getNodeId())
                                    .build()
                    );

                    if (response.getOk()) {
                        System.out.println("[Node " + nodeState.getNodeId() + "] Peer Node " + peer.getId() +
                                " accepted me. Its ID (" + peer.getId() + ") is smaller than current elected (" + elected + ") ?");
                        if (peer.getId() < elected) {
                            elected = peer.getId();
                            System.out.println("[Node " + nodeState.getNodeId() + "] New elected candidate = Node " + elected);
                        }
                    } else {
                        System.out.println("[Node " + nodeState.getNodeId() + "] Peer Node " + peer.getId() + " rejected me.");
                    }

                } catch (Exception e) {
                    System.out.println("[Node " + nodeState.getNodeId() + "] Peer Node " + peer.getId() + " unreachable, ignoring.");
                }
            }

            // 3. Résultat final : le plus petit ID parmi ceux qui ont répondu OK + nous-même
            //    (déjà calculé dans elected)
            System.out.println("[Node " + nodeState.getNodeId() + "] Election result: elected Node " + elected);

            // 4. Mettre à jour le leader local
            nodeState.setLeaderId(elected);
            nodeState.updateLastHeartbeatTime(); // On vient d'élire, on reset le timeout

            // 5. Si c'est nous le leader, on démarre l'envoi de heartbeats
            if (nodeState.isLeader()) {
                System.out.println("[Node " + nodeState.getNodeId() + "] I AM THE NEW LEADER ! (ID=" + elected + ")");
            } else {
                System.out.println("[Node " + nodeState.getNodeId() + "] I am a follower. Leader is Node " + elected);
            }

            // 6. Diffuser la nouvelle à tous les pairs (y compris ceux qui étaient injoignables)
            broadcastLeader(elected);

        } finally {
            electionInProgress.set(false);
        }
    }

    // Diffuser l'identité du leader à tous les pairs
    private void broadcastLeader(int leaderId) {
        System.out.println("[Node " + nodeState.getNodeId() + "] Broadcasting leader (Node " + leaderId + ") to all peers");
        for (PeerConfig.Peer peer : peerConfig.getPeers()) {
            try {
                ManagedChannel channel = peerChannels.get(peer.getId());
                if (channel == null) continue;
                TaskServiceGrpc.TaskServiceBlockingStub stub =
                        TaskServiceGrpc.newBlockingStub(channel);
                stub.announceLeader(
                        LeaderResponse.newBuilder()
                                .setLeaderId(leaderId)
                                .build()
                );
                System.out.println("[Node " + nodeState.getNodeId() + "] -> Notified Node " + peer.getId());
            } catch (Exception e) {
                System.out.println("[Node " + nodeState.getNodeId() + "] Could not broadcast leader to Node " + peer.getId());
            }
        }
    }

    // ------------------------------------------------------------------
    // 2. HEARTBEAT : envoi (si leader) et réception (implémentée dans TaskGrpcService)
    // ------------------------------------------------------------------
    // Cette méthode est appelée périodiquement par Spring Scheduling
    @Scheduled(fixedDelay = HEARTBEAT_INTERVAL_MS)
    public void sendHeartbeatIfLeader() {
        if (!nodeState.isLeader()) return;
        if (!nodeState.hasLeader()) return; // pas encore de leader, on attend

        System.out.println("[Node " + nodeState.getNodeId() + "] [HEARTBEAT] Sending heartbeat as leader");
        for (PeerConfig.Peer peer : peerConfig.getPeers()) {
            try {
                ManagedChannel channel = peerChannels.get(peer.getId());
                if (channel == null) continue;
                TaskServiceGrpc.TaskServiceBlockingStub stub =
                        TaskServiceGrpc.newBlockingStub(channel);
                stub.heartbeat(Empty.newBuilder().build());
                // Pas de log pour chaque succès, sinon trop bavard
            } catch (Exception e) {
                System.out.println("[Node " + nodeState.getNodeId() + "] [HEARTBEAT] Failed to send to Node " + peer.getId());
            }
        }
    }

    // ------------------------------------------------------------------
    // 3. SURVEILLANCE DU LEADER (follower uniquement)
    // ------------------------------------------------------------------
    private void startLeaderMonitor() {
        Thread monitor = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(LEADER_TIMEOUT_MS / 2); // vérifier toutes les 1.5s
                    if (!nodeState.hasLeader()) continue;
                    if (nodeState.isLeader()) continue;

                    long last = nodeState.getLastHeartbeatTime();
                    long now = System.currentTimeMillis();
                    if (now - last > LEADER_TIMEOUT_MS) {
                        System.out.println("[Node " + nodeState.getNodeId() +
                                "] [WARNING] No heartbeat from leader (Node " + nodeState.getLeaderId() +
                                ") for " + (now - last) + " ms -> leader seems DEAD.");
                        // Lancer une réélection si aucune n'est déjà en cours
                        if (!electionInProgress.get()) {
                            System.out.println("[Node " + nodeState.getNodeId() + "] Triggering re-election...");
                            runElection();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        monitor.setDaemon(true);
        monitor.start();
    }

    // Cette méthode est appelée par le gRPC quand on reçoit un heartbeat
    // (L'implémentation réelle est dans TaskGrpcService, mais on peut faire ici une méthode utilitaire)
    public void onHeartbeatReceived() {
        nodeState.updateLastHeartbeatTime();
        System.out.println("[Node " + nodeState.getNodeId() + "] [HEARTBEAT] Received from leader (Node " + nodeState.getLeaderId() + ")");
    }
}