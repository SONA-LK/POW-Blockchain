package lk.sona;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class BlockchainStorage {
    private static final String FILE_NAME = "blockchain.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // Save blockchain to file
    public static void save(Blockchain blockchain) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(blockchain.getChain(), writer);
            System.out.println("✅ Blockchain saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("❌ Error saving blockchain: " + e.getMessage());
        }
    }

    // Load blockchain from file
    public static List<Block> load() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type type = new TypeToken<List<Block>>() {}.getType();
            List<Block> loadedChain = gson.fromJson(reader, type);
            System.out.println("✅ Blockchain loaded from file (" + loadedChain.size() + " blocks)");
            return loadedChain;
        } catch (FileNotFoundException e) {
            System.out.println("No saved blockchain found. Starting fresh.");
            return null;
        } catch (Exception e) {
            System.out.println("❌ Error loading blockchain: " + e.getMessage());
            return null;
        }
    }
}