package lk.sona;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;
    private int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = difficulty;
        // Create Genesis Block
        Block genesis = new Block(0, "0", "Genesis Block");
        genesis.mineBlock(difficulty);
        chain.add(genesis);
    }

    public void addBlock(String data) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(chain.size(), previousBlock.getHash(), data);
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    // Validate the entire chain
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            // Check current hash
            if (!current.getHash().equals(current.calculateHash())) {
                return false;
            }

            // Check link with previous block
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }
        return true;
    }

    public void printChain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Data     : " + block.getData());
            System.out.println("Hash     : " + block.getHash());
            System.out.println("Prev Hash: " + block.getPreviousHash());
            System.out.println("--------------------------------");
        }
    }

    public List<Block> getChain() {
        return chain;
    }
}
