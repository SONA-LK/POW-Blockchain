package lk.sona;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Java Blockchain - Level 3 (Wallets + Signatures) ===\n");

        Blockchain blockchain = new Blockchain(4);

        // Create Wallets
        Wallet alice = new Wallet();
        Wallet bob = new Wallet();
        Wallet charlie = new Wallet();

        System.out.println("Wallets created successfully!");

        // Create signed transactions
        System.out.println("\nCreating transactions...");
        Transaction tx1 = new Transaction(alice, bob.getPublicKeyAsString(), 25.0);
        Transaction tx2 = new Transaction(bob, charlie.getPublicKeyAsString(), 8.5);
        Transaction tx3 = new Transaction(alice, charlie.getPublicKeyAsString(), 12.0);

        blockchain.addTransaction(tx1);
        blockchain.addTransaction(tx2);
        blockchain.addTransaction(tx3);

        System.out.println("\nMining pending transactions...");
        blockchain.minePendingTransactions();

        System.out.println("\n=== Final Blockchain ===");
        blockchain.printChain();

        System.out.println("\nIs blockchain valid? " + blockchain.isChainValid());

        // Test signature verification
        System.out.println("\nVerifying first transaction signature: " +
                tx1.verifySignature());
    }
}