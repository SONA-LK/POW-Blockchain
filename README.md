# Proo-of-work (Java Blockchain)

A small single-node proof-of-work blockchain written in Java 17. The current implementation focuses on core data structures, transaction signing, mining, and local persistence.

## Project state (current)
- Single-node chain with proof-of-work mining (leading zero hash).
- Transactions signed with ECDSA and validated before mining.
- Pending transaction pool and balance calculation by scanning the chain.
- Local persistence to blockchain.json using Gson.
- Console demo in Main that creates wallets, sends transactions, mines blocks, and prints balances.

### Not yet implemented
- Networking or peer-to-peer consensus.
- Difficulty adjustment or dynamic reward rules.
- Transaction fees, coinbase rewards, or a UTXO model.
- Chain validation on load beyond basic hash linkage.
- Automated tests.

## Requirements
- Java 17
- Maven 3.x

## Build and run
```bash
mvn -q -DskipTests package
mvn -q -DskipTests exec:java -Dexec.mainClass=lk.sona.Main
```

Notes:
- On first run, Maven downloads the exec plugin.
- A blockchain.json file is written in the project root. Delete it to reset the chain.

## How the demo works
- Creates two wallets (Alice and Bob).
- Adds a genesis transaction to give Alice an initial balance.
- Sends multiple transactions from Alice to Bob and mines after each batch.
- Prints final balances and the full chain.

## Project structure
- src/main/java/lk/sona/Main.java: Demo entry point.
- src/main/java/lk/sona/Block.java: Block structure and PoW mining.
- src/main/java/lk/sona/Blockchain.java: Chain management, mining, validation, balances.
- src/main/java/lk/sona/Transaction.java: Signed transaction data.
- src/main/java/lk/sona/Wallet.java: ECDSA keypair and signing.
- src/main/java/lk/sona/BlockchainStorage.java: JSON persistence to blockchain.json.

## Data persistence
- blockchain.json stores the chain as a list of blocks.
- Each block includes index, timestamp, previousHash, nonce, hash, and transactions.

## License
- Not specified.
