package lk.sona;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private List<Transaction> transactions;
    private String hash;
    private int nonce;

    public Block(int index, String previousHash) {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        this.transactions = new ArrayList<>();
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactions.add(transaction);
            hash = calculateHash(); // Recalculate hash when transaction is added
        }
    }

    public String calculateHash() {
        StringBuilder txData = new StringBuilder();
        for (Transaction tx : transactions) {
            txData.append(tx.getTransactionId());
        }

        String input = index + timestamp + previousHash + txData + nonce;
        return sha256(input);
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mineBlock(int difficulty) {
        String target = "0".repeat(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined! #" + index + " Hash: " + hash.substring(0, 12) + "...");
    }

    // Getters
    public String getHash() { return hash; }
    public String getPreviousHash() { return previousHash; }
    public List<Transaction> getTransactions() { return transactions; }
    public int getIndex() { return index; }
}