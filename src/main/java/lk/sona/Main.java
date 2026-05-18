package lk.sona;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Java Blockchain - Level 5 (Persistence) ===\n");

        Blockchain blockchain;

        // Try to load existing blockchain
        List<Block> loadedChain = BlockchainStorage.load();

        if (loadedChain != null && loadedChain.size() > 0) {
            blockchain = new Blockchain(4);  // Create temporary
            blockchain.replaceChain(loadedChain);
        } else {
            blockchain = new Blockchain(4);  // Fresh blockchain
        }

        Wallet alice = new Wallet();
        Wallet bob = new Wallet();

        System.out.println("Wallets created!");

        // Add some coins to Alice (Genesis reward)
        blockchain.addTransaction(new Transaction(alice, alice.getPublicKeyAsString(), 100.0));
        blockchain.minePendingTransactions();

        // Normal transaction
        blockchain.addTransaction(new Transaction(alice, bob.getPublicKeyAsString(), 40.0));
        blockchain.minePendingTransactions();

        blockchain.addTransaction(new Transaction(alice, bob.getPublicKeyAsString(), 10.0));
        blockchain.minePendingTransactions();

        blockchain.addTransaction(new Transaction(alice, bob.getPublicKeyAsString(), 40.0));
        blockchain.minePendingTransactions();

        // Show final state
        System.out.println("\n--- Final Balances ---");
        System.out.println("Alice   : " + blockchain.getBalance(alice.getPublicKeyAsString()));
        System.out.println("Bob     : " + blockchain.getBalance(bob.getPublicKeyAsString()));

        blockchain.printChain();

        // Save the blockchain
        BlockchainStorage.save(blockchain);

        System.out.println("\n✅ Program finished. Run again to see persistence in action!");
    }
}