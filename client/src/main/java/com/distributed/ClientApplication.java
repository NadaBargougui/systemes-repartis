package com.distributed;

import com.distributed.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    // All available nodes
    private static final List<String> NODES = Arrays.asList(
            "localhost:9091",
            "localhost:9092",
            "localhost:9093"
    );

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sendTask("compute", "2 + 2");
        Thread.sleep(500);

        sendTask("message", "Hello distributed world!");
        Thread.sleep(500);

        sendTask("compute", "100 * 42");
    }

    private void sendTask(String taskType, String payload) {
        // Pick a random node
        String address = NODES.get(new Random().nextInt(NODES.size()));
        System.out.println("\n[Client] Connecting to " + address);

        ManagedChannel channel = ManagedChannelBuilder
                .forTarget(address)
                .usePlaintext()
                .build();

        TaskServiceGrpc.TaskServiceBlockingStub stub =
                TaskServiceGrpc.newBlockingStub(channel);

        String taskId = UUID.randomUUID().toString().substring(0, 8);

        TaskRequest request = TaskRequest.newBuilder()
                .setTaskId(taskId)
                .setTaskType(taskType)
                .setPayload(payload)
                .build();

        try {
            TaskResponse response = stub.submitTask(request);
            System.out.println("[Client] ✅ Result received:");
            System.out.println("         Task ID    : " + response.getTaskId());
            System.out.println("         Result     : " + response.getResult());
            System.out.println("         Executed by: " + response.getExecutedBy());
        } catch (Exception e) {
            System.out.println("[Client] ❌ Error: " + e.getMessage());
        } finally {
            channel.shutdown();
        }
    }
}