package lk.sona;

import java.security.*;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Base64;

public class Transaction {
    private String transactionId;
    private String sender;           // Public key string
    private String receiver;
    private double amount;
    private long timestamp;
    private String signature;

    public Transaction(Wallet senderWallet, String receiver, double amount) {
        this.sender = senderWallet.getPublicKeyAsString();
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = Instant.now().toEpochMilli();
        this.transactionId = calculateHash();

        // Auto sign the transaction
        this.signature = senderWallet.signTransaction(this);
    }

    private String calculateHash() {
        String input = sender + receiver + amount + timestamp;
        return sha256(input);
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Verify signature
    public boolean verifySignature() {
        try {
            Signature ecdsa = Signature.getInstance("SHA256withECDSA");
            PublicKey publicKey = KeyFactory.getInstance("EC")
                    .generatePublic(new java.security.spec.X509EncodedKeySpec(
                            Base64.getDecoder().decode(sender)));

            ecdsa.initVerify(publicKey);
            ecdsa.update(transactionId.getBytes());
            return ecdsa.verify(Base64.getDecoder().decode(signature));
        } catch (Exception e) {
            return false;
        }
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public double getAmount() { return amount; }
    public String getSignature() { return signature; }

    @Override
    public String toString() {
        String senderShort = sender.length() > 12
                ? sender.substring(0, 12) + "..."
                : sender;

        String receiverShort = receiver.length() > 12
                ? receiver.substring(0, 12) + "..."
                : receiver;

        return "Tx: " + senderShort + " → " + receiverShort + " | " + amount + " coins";
    }
}