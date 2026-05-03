# 🚀 Système de Gestion de Ressources Distribuées

Un mini orchestrateur distribué intelligent capable de gérer des tâches avec **consensus**, **tolérance aux pannes**, **exclusion mutuelle** et **communication haute performance**.

---

## 📋 Table des Matières

- [Vue d'ensemble](#vue-densemble)
- [Architecture](#architecture)
- [Fonctionnalités](#fonctionnalités)
- [Protocoles](#protocoles)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Logs & Monitoring](#logs--monitoring)

---

## 🎯 Vue d'Ensemble

Ce projet implémente un système distribué où **3 nœuds** gèrent des **tâches** envoyées par des clients avec les garanties suivantes:

- ✅ **Cohérence globale**: Via algorithme de consensus pour élire un leader
- ✅ **Exécution unique**: Chaque tâche exécutée exactement une fois via mutex distribué
- ✅ **Tolérance aux pannes**: Détection automatique et ré-élection du leader
- ✅ **Communication rapide**: Utilise gRPC et Protocol Buffers

### Fluxe Global

```
CLIENT
   ↓
   └─→ Envoie TÂCHE à nœud aléatoire (gRPC)
         ↓
         └─→ Nœud détecte s'il est leader
               ├─ OUI → Acquiert lock → Exécute → Libère lock
               └─ NON → Redirige vers leader
         ↓
   Reçoit RÉSULTAT
```

---

## 🏗️ Architecture

### Structure du Projet

```
systemes-repartis/
├── proto/
│   └── tasks.proto              # Définition gRPC (RPC, messages)
├── node1, node2, node3/
│   ├── src/main/java/com/distributed/
│   │   ├── NodeApplication.java         # Point d'entrée Spring Boot
│   │   ├── NodeState.java               # État du nœud (locks, leader)
│   │   ├── ElectionService.java         # Algorithme d'élection
│   │   ├── TaskGrpcService.java         # Implémentation gRPC
│   │   └── PeerConfig.java              # Configuration peers
│   ├── src/main/resources/
│   │   └── application.yml              # Config nœud (ID, port)
│   └── pom.xml                          # Dépendances Maven
├── client/
│   ├── src/main/java/com/distributed/
│   │   └── ClientApplication.java       # Client gRPC
│   └── pom.xml
└── README.md
```

### Déploiement

```
Node 1  →  Port 9091
Node 2  →  Port 9092
Node 3  →  Port 9093

Client → Contacte un nœud aléatoire
```

---

## ✨ Fonctionnalités

### 1. **Consensus & Élection du Leader** 🗳️

**Algorithme**: Élection du plus petit ID

- **Démarrage**: Chaque nœud lance une élection au démarrage
- **Candidature**: "Je propose mon ID"
- **Vote**: Peer accepte si son ID < ID candidat
- **Résultat**: Le plus petit ID parmi les vivants = leader
- **Notification**: Leader notifie tous les peers

```
[Node 1] Election result: elected Node 1
[Node 1] I AM THE NEW LEADER ! (ID=1)
[Node 1] -> Notified Node 2
[Node 1] -> Notified Node 3
```

### 2. **Heartbeat & Détection de Pannes** ❤️

**Mécanisme**: Ping/Pong toutes les secondes

- **Leader**: Envoie heartbeat à tous les peers
- **Followers**: Reçoivent et mettent à jour timestamp
- **Timeout**: 3 secondes sans heartbeat = leader mort
- **Action**: Trigger automatique d'une ré-élection

```
[Node 1] [HEARTBEAT] Sending heartbeat as leader
[Node 2] [HEARTBEAT] Received from leader (Node 1)
[Node 3] [WARNING] No heartbeat from leader (Node 1) for 3000 ms → leader seems DEAD.
[Node 3] Triggering re-election...
```

### 3. **Redirection Intelligente** 🔀

Non-leader reçoit une requête:
1. Détecte qu'il n'est pas leader
2. Consulte locallement qui est le leader
3. Redirige la tâche au leader via gRPC
4. Retourne le résultat au client

```
[Node 2] Received task: aa860b87
[Node 2] Not leader, redirecting to Node 1
```

### 4. **Exclusion Mutuelle Distribuée** 🔐

**Mécanisme**: Verrou par tâche avec TTL

- **Acquisition**: Leader essaie d'acquérir lock
- **Succès**: Lock acquis → exécute tâche
- **Échec**: Lock déjà détenu → retourne erreur
- **Libération**: Automatique après exécution (même en cas d'erreur)
- **Expiration**: TTL 10 secondes (lock auto-libéré)

```
[Node 1] Received task: ec9ead9a
[Node 1] Acquired lock for task: ec9ead9a       ✅
[Node 1] Executing task: ec9ead9a
[Node 1] Released lock for task: ec9ead9a       ✅
```

**Prévention de doublons**:
```
[Node 2] CONFLICT: Task b5532728 is already locked by Node 1
[Node 2] ERROR: Task is being processed by another node
```

### 5. **Tolérance aux Pannes** 💥

Scénario: Node 1 (leader) crash

```
[Node 3] [WARNING] No heartbeat from leader (Node 1) for 3950 ms
[Node 3] Triggering re-election...
[Node 3] Proposing self (ID=3)
[Node 3] Peer Node 1 unreachable, ignoring.
[Node 3] Peer Node 2 rejected me (their ID 2 < 3).
[Node 3] Election result: elected Node 2        ← Nouveau leader
[Node 3] I am a follower. Leader is Node 2
```

Node 2 prend le relais:
```
[Node 2] I AM THE NEW LEADER ! (ID=2)
[Node 2] [HEARTBEAT] Sending heartbeat as leader
[Node 2] Received task: b5532728
[Node 2] Acquired lock for task: b5532728
[Node 2] Executing task: b5532728               ← Continue normalement
```

---

## 🔌 Protocoles

### gRPC & Protocol Buffers

**Raison**: Communication haute performance, structurée, sérialisée

#### RPC Implémentées (tasks.proto)

```protobuf
service TaskService {
  rpc SubmitTask(TaskRequest) returns (TaskResponse);     // Soumettre tâche
  rpc GetLeader(Empty) returns (LeaderResponse);           // Connaître le leader
  rpc Elect(ElectionMessage) returns (ElectionResponse);   // Election
  rpc AnnounceLeader(LeaderResponse) returns (Empty);      // Annoncer leader
  rpc Heartbeat(Empty) returns (Empty);                    // Détection panne
  rpc AcquireLock(LockRequest) returns (LockResponse);     // Acquérir verrou
  rpc ReleaseLock(LockRequest) returns (Empty);            // Libérer verrou
}
```

#### Messages Clés

```protobuf
// Client soumet une tâche
message TaskRequest {
  string task_id = 1;
  string task_type = 2;
  string payload = 3;
}

// Réponse avec résultat
message TaskResponse {
  string task_id = 1;
  string result = 2;
  string executed_by = 3;
}

// Election: Candidat propose son ID
message ElectionMessage {
  int32 candidate_id = 1;
}

// Réponse: Accept/Reject + mon ID
message ElectionResponse {
  bool ok = 1;
  int32 node_id = 2;
}

// Verrouillage distribué
message LockRequest {
  string task_id = 1;
  int32 requester_id = 2;
  int64 ttl_ms = 3;
}

message LockResponse {
  bool acquired = 1;
  int32 holder_id = 2;
}
```

### Transport

- **Netty Shaded**: Pour la couche transport gRPC
- **Version gRPC**: 1.60.0
- **Serialization**: Protocol Buffers 3.25.1

---

## 🔧 Installation & Setup

### Prérequis

- **Java**: 18+ (testé avec 18.0.2 et 23.0.2)
- **Maven**: 3.8+
- **Spring Boot**: 3.2.0

### Installation

```bash
# 1. Cloner le projet
cd c:\Users\MSI\Desktop\systemes-repartis

# 2. Build complet (génère proto → compile → package)
mvn clean install

# Ou compilation seule
mvn clean compile
```

### Configuration des Nœuds

Chaque nœud a sa configuration dans `src/main/resources/application.yml`:

**Node 1** (9091):
```yaml
node:
  id: 1
  port: 9091
  peers:
    - id: 2
      address: localhost:9092
    - id: 3
      address: localhost:9093
```

**Node 2** (9092):
```yaml
node:
  id: 2
  port: 9092
  peers:
    - id: 1
      address: localhost:9091
    - id: 3
      address: localhost:9093
```

**Node 3** (9093):
```yaml
node:
  id: 3
  port: 9093
  peers:
    - id: 1
      address: localhost:9091
    - id: 2
      address: localhost:9092
```

---

## 🚀 Usage

### Démarrer les Nœuds

**Terminal 1 - Node 1:**
```bash
cd node1
mvn spring-boot:run
```

**Terminal 2 - Node 2:**
```bash
cd node2
mvn spring-boot:run
```

**Terminal 3 - Node 3:**
```bash
cd node3
mvn spring-boot:run
```

**Output attendu:**
```
2026-05-03T12:17:21.690+01:00  INFO ... : Started NodeApplication in 0.92 seconds
[Node 1] >>> STARTING ELECTION (reason: leadership lost or initial startup)
[Node 1] Proposing self (ID=1)
[Node 1] Peer Node 2 accepted me (ID 2 > 1).
[Node 1] Peer Node 3 accepted me (ID 3 > 1).
[Node 1] Election result: elected Node 1
[Node 1] I AM THE NEW LEADER ! (ID=1)
```

### Soumettre des Tâches (Client)

**Terminal 4 - Client:**
```bash
cd client
mvn spring-boot:run
```

**Output:**
```
[Client] Connecting to localhost:9091
[Client] ✓ Result received:
         Task ID    : ec9ead9a
         Result     : Task '2 + 2' executed by Node 1
         Executed by: Node 1
```

### Simuler une Panne

Arrêter un nœud (Ctrl+C):

```
[Node 3] [WARNING] No heartbeat from leader (Node 1) for 3298 ms → leader seems DEAD.
[Node 3] Triggering re-election...
[Node 3] Peer Node 2 rejected me (their ID 2 < 3).
[Node 3] Election result: elected Node 2
[Node 2] I AM THE NEW LEADER ! (ID=2)

[Node 2] Received task: b5532728
[Node 2] Acquired lock for task: b5532728
[Node 2] Executing task: b5532728
[Node 2] Released lock for task: b5532728
```

---

## 📊 Logs & Monitoring

### Événements Clés à Observer

| Événement | Log | Signification |
|-----------|-----|---------------|
| **Élection** | `[Node X] I AM THE NEW LEADER` | Nouveau leader élu |
| **Heartbeat envoyé** | `[HEARTBEAT] Sending heartbeat as leader` | Leader vivant |
| **Heartbeat reçu** | `[HEARTBEAT] Received from leader` | Peer reçoit ping |
| **Timeout détecté** | `[WARNING] No heartbeat for 3000 ms` | Leader mort suspecté |
| **Ré-élection** | `Triggering re-election...` | Nouveau consensus en cours |
| **Tâche reçue** | `[Node X] Received task: xyz` | Nouvelle tâche |
| **Lock acquis** | `Acquired lock for task: xyz` | Mutex obtenu |
| **Tâche exécutée** | `Executing task: xyz` | Tâche en cours |
| **Lock libéré** | `Released lock for task: xyz` | Mutex libéré |
| **Redirection** | `Not leader, redirecting to Node X` | Forward au leader |

### Interprétation des Logs

**Système Sain:**
```
✅ Un seul leader à la fois
✅ Tous les followers reçoivent les heartbeats
✅ Les tâches acquièrent lock → exécutent → libèrent
✅ Pas de warnings concernant les pannes
```

**Système sous Stress (Panne):**
```
⚠️ WARNING: No heartbeat detected
⚠️ Triggering re-election...
✅ Nouveau leader élu
✅ Le reste continue normalement
```

---

## 🎓 Concepts Implantés

| Concept | Où | Implémentation |
|---------|-----|-----------------|
| **Consensus** | `ElectionService.java` | Algorithme: Plus petit ID vivant |
| **Heartbeat** | `ElectionService.java` (scheduler) | `@Scheduled(fixedRate = 1000)` |
| **Détection Pannes** | `ElectionService.java` (monitor) | Timeout 3 secondes |
| **Mutex Distribué** | `NodeState.java` | `ConcurrentHashMap` + TTL |
| **RPC** | `TaskGrpcService.java` | gRPC + Protocol Buffers |
| **Thread Safety** | `NodeState.java` | `synchronized` + `ConcurrentHashMap` |
| **Redirection** | `TaskGrpcService.submitTask()` | Forward RPC au leader |

---

## 📈 Performance

- **Consensus**: ~500ms pour élire un leader
- **Détection panne**: ~3-4 secondes (TTL 3s)
- **Communication**: gRPC HTTP/2 (< 1ms local)
- **Throughput**: Peut traiter plusieurs tâches en parallèle
- **Overhead mutex**: Négligeable (lock = O(1))

---

## 🔍 Debugging

### Problème: Nœuds ne communiquent pas

**Cause**: Version gRPC incompatible
**Solution**: 
```bash
mvn clean install
```

### Problème: Plusieurs leaders

**Cause**: Split-brain (partition réseau)
**Note**: Acceptable pour cette implémentation simplified

### Problème: Locks bloqués

**Solution**: Les locks expirent automatiquement après 10 secondes

---

## 📝 Fichiers Importants

| Fichier | Rôle |
|---------|------|
| `proto/tasks.proto` | Définition gRPC |
| `NodeApplication.java` | Spring Boot entry point |
| `ElectionService.java` | Consensus + Heartbeat + Monitoring |
| `TaskGrpcService.java` | Implémentation RPC |
| `NodeState.java` | État nœud + Locks distribuées |
| `PeerConfig.java` | Configuration des peers |

---

## 📚 Références

- **gRPC**: https://grpc.io
- **Protocol Buffers**: https://developers.google.com/protocol-buffers
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Consensus Algorithms**: Raft, Paxos (not implemented, simplified model)

---

## ✅ Checklist Projet

- [x] Gestion de tâches via clients
- [x] Consensus pour élire le leader
- [x] Détection automatique de pannes
- [x] Ré-élection après défaillance
- [x] Exclusion mutuelle distribuée
- [x] Communication gRPC
- [x] Logs structurés
- [x] Tolérance aux pannes
- [x] Cohérence globale

