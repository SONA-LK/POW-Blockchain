package lk.sona;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;
    private int difficulty;
    private List<Transaction> pendingTransactions;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        this.difficulty = difficulty;

        // Genesis Block
        Block genesis = new Block(0, "0");
        genesis.addTransaction(new Transaction(new Wallet(), "Genesis", 0));
        genesis.mineBlock(difficulty);
        chain.add(genesis);
    }

    public void addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
        System.out.println("Transaction added to pending: " + transaction);
    }

    public void minePendingTransactions() {
        if (pendingTransactions.isEmpty()) {
            System.out.println("No transactions to mine.");
            return;
        }

        // Validate all pending transactions
        List<Transaction> validTransactions = new ArrayList<>();
        for (Transaction tx : pendingTransactions) {
            if (isValidTransaction(tx)) {
                validTransactions.add(tx);
            }
        }

        if (validTransactions.isEmpty()) {
            System.out.println("No valid transactions to mine.");
            pendingTransactions.clear();
            return;
        }

        Block newBlock = new Block(chain.size(), chain.get(chain.size() - 1).getHash());

        for (Transaction tx : validTransactions) {
            newBlock.addTransaction(tx);
        }

        newBlock.mineBlock(difficulty);
        chain.add(newBlock);

        pendingTransactions.clear();
        System.out.println("✅ Block mined successfully with " + validTransactions.size() + " transaction(s)!");
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.getHash().equals(current.calculateHash())) {
                return false;
            }
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }
        return true;
    }

    public void printChain() {
        for (Block block : chain) {
            System.out.println("\nBlock #" + block.getIndex());
            System.out.println("Previous Hash : " + block.getPreviousHash());
            System.out.println("Hash          : " + block.getHash());
            System.out.println("Transactions  :");
            for (Transaction tx : block.getTransactions()) {
                System.out.println("   " + tx);
            }
        }
    }

    // Calculate balance for a wallet (public key)
    public double getBalance(String publicKey) {
        double balance = 0.0;

        for (Block block : chain) {
            for (Transaction tx : block.getTransactions()) {
                // Money coming in
                if (tx.getReceiver().equals(publicKey)) {
                    balance += tx.getAmount();
                }
                // Money going out
                if (tx.getSender().equals(publicKey)) {
                    balance -= tx.getAmount();
                }
            }
        }
        return balance;
    }

    // Validate transaction before adding
    public boolean isValidTransaction(Transaction tx) {
        if (tx.getSender().equals("System")) {
            return true; // Genesis transaction
        }

        double senderBalance = getBalance(tx.getSender());

        if (senderBalance < tx.getAmount()) {
            System.out.println("❌ Transaction rejected: Insufficient balance! "
                    + "Has: " + senderBalance + ", Trying to send: " + tx.getAmount());
            return false;
        }

        if (!tx.verifySignature()) {
            System.out.println("❌ Transaction rejected: Invalid signature!");
            return false;
        }

        return true;
    }

    // Get the chain (needed for saving)
    public List<Block> getChain() {
        return chain;
    }

    // Replace current chain (used when loading)
    public void replaceChain(List<Block> newChain) {
        if (newChain != null && !newChain.isEmpty()) {
            this.chain = newChain;
            System.out.println("Blockchain replaced with loaded data.");
        }
    }
}