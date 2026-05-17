package lk.sona;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Simple Blockchain...\n");

        Blockchain blockchain = new Blockchain(4); // Difficulty = 4 zeros

        System.out.println("Mining block 1...");
        blockchain.addBlock("Transaction: Alice sent 10 BTC to Bob");

        System.out.println("Mining block 2...");
        blockchain.addBlock("Transaction: Bob sent 5 BTC to Charlie");

        System.out.println("Mining block 3...");
        blockchain.addBlock("Transaction: Charlie sent 2 BTC to Alice");

        System.out.println("\n=== Blockchain ===");
        blockchain.printChain();

        System.out.println("\nIs blockchain valid? " + blockchain.isChainValid());

        // Test tampering
        System.out.println("\nTampering with block 1 data...");
        blockchain.getChain().get(1).data = "Hacked data!";  // Note: this is not proper way, just for demo

        System.out.println("Is blockchain still valid? " + blockchain.isChainValid());
    }
}