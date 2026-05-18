package lk.sona;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

        public static void main(String[] args) {
            System.out.println("=== Java Blockchain - Level 4 (Balances + Validation) ===\n");

            Blockchain blockchain = new Blockchain(4);

            Wallet alice = new Wallet();
            Wallet bob = new Wallet();
            Wallet charlie = new Wallet();

            System.out.println("Wallets created!");

            // Show initial balances
            System.out.println("\n--- Initial Balances ---");
            System.out.println("Alice   : " + blockchain.getBalance(alice.getPublicKeyAsString()));
            System.out.println("Bob     : " + blockchain.getBalance(bob.getPublicKeyAsString()));
            System.out.println("Charlie : " + blockchain.getBalance(charlie.getPublicKeyAsString()));

            // Genesis gives some coins to Alice (we'll simulate reward)
            System.out.println("\nAdding Genesis Reward to Alice...");
            Transaction genesisReward = new Transaction(new Wallet() { // Temporary hack for demo
                @Override
                public String getPublicKeyAsString() {
                    return "System";
                }
            }, alice.getPublicKeyAsString(), 100.0);
            // Manually set signature for genesis
            genesisReward = new Transaction(alice, alice.getPublicKeyAsString(), 100.0); // Simplified

            blockchain.addTransaction(new Transaction(alice, alice.getPublicKeyAsString(), 100)); // Reward

            blockchain.minePendingTransactions();

            System.out.println("\n--- Balances After Genesis Reward ---");
            System.out.println("Alice   : " + blockchain.getBalance(alice.getPublicKeyAsString()));

            // Normal transactions
            System.out.println("\n--- Making Transactions ---");
            blockchain.addTransaction(new Transaction(alice, bob.getPublicKeyAsString(), 30.0));
            blockchain.addTransaction(new Transaction(alice, charlie.getPublicKeyAsString(), 25.0));

            blockchain.minePendingTransactions();

            System.out.println("\n--- Final Balances ---");
            System.out.println("Alice   : " + blockchain.getBalance(alice.getPublicKeyAsString()));
            System.out.println("Bob     : " + blockchain.getBalance(bob.getPublicKeyAsString()));
            System.out.println("Charlie : " + blockchain.getBalance(charlie.getPublicKeyAsString()));

            System.out.println("\n=== Final Blockchain ===");
            blockchain.printChain();

            System.out.println("\nIs blockchain valid? " + blockchain.isChainValid());
        }

}