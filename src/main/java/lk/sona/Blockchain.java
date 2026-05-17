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

        Block newBlock = new Block(chain.size(), chain.get(chain.size() - 1).getHash());

        // Add all pending transactions to this block
        for (Transaction tx : pendingTransactions) {
            newBlock.addTransaction(tx);
        }

        newBlock.mineBlock(difficulty);
        chain.add(newBlock);

        // Clear pending transactions
        pendingTransactions.clear();
        System.out.println("Block mined successfully!");
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
}