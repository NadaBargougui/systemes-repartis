package com.distributed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NodeApplication implements CommandLineRunner {

    @Autowired
    private ElectionService electionService;

    public static void main(String[] args) {
        SpringApplication.run(NodeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Wait 5 seconds for all nodes to start, then elect
        Thread.sleep(5000);
        electionService.runElection();
    }
}