package com.distributed;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "node")
public class PeerConfig {

    private int id;
    private int port;
    private List<Peer> peers;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public List<Peer> getPeers() { return peers; }
    public void setPeers(List<Peer> peers) { this.peers = peers; }

    public static class Peer {
        private int id;
        private String address;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }
}