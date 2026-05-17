package lk.sona;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Java Blockchain - Level 2 ===\n");

        Blockchain blockchain = new Blockchain(4);

        // Create transactions
        blockchain.addTransaction(new Transaction("Alice", "Bob", 10.5));
        blockchain.addTransaction(new Transaction("Bob", "Charlie", 3.0));
        blockchain.addTransaction(new Transaction("Charlie", "Alice", 2.0));

        System.out.println("\nMining pending transactions...");
        blockchain.minePendingTransactions();

        blockchain.addTransaction(new Transaction("Alice", "David", 5.0));
        blockchain.minePendingTransactions();

        System.out.println("\n=== Final Blockchain ===");
        blockchain.printChain();

        System.out.println("\nIs blockchain valid? " + blockchain.isChainValid());
    }
}