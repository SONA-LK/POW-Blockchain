package lk.sona;

import java.security.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class Transaction {
    private String transactionId;
    private String sender;      // Public key (simplified as String for now)
    private String receiver;
    private double amount;
    private long timestamp;
    private String signature;   // Digital signature (we'll implement basic version)

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = Instant.now().toEpochMilli();
        this.transactionId = calculateHash();
    }

    private String calculateHash() {
        String input = sender + receiver + amount + timestamp;
        return sha256(input);
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // For now, simple signature (we'll improve this in Level 3 with real keys)
    public void generateSignature(String privateKey) {
        this.signature = sha256(transactionId + privateKey); // Simplified
    }

    public String getTransactionId() { return transactionId; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public double getAmount() { return amount; }
    public String getSignature() { return signature; }

    @Override
    public String toString() {
        return sender + " → " + receiver + " : " + amount + " coins";
    }
}