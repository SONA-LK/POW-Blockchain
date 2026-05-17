package lk.sona;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    String data;
    private String hash;
    private int nonce;

    public Block(int index, String previousHash, String data) {
        this.index = index;
        this.timestamp = Instant.now().toEpochMilli();
        this.previousHash = previousHash;
        this.data = data;
        this.nonce = 0;
        this.hash = calculateHash();
    }

    // Calculate SHA-256 hash
    public String calculateHash() {
        String input = index + timestamp + previousHash + data + nonce;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Mining - Proof of Work
    public void mineBlock(int difficulty) {
        String target = "0".repeat(difficulty);  // e.g., "0000"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined! Hash: " + hash);
    }

    // Getters
    public String getHash() { return hash; }
    public String getPreviousHash() { return previousHash; }
    public String getData() { return data; }
    public int getIndex() { return index; }
}