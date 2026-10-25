package com.ewyboy.seeddrop.json;

import com.ewyboy.seeddrop.Constants;
import com.ewyboy.seeddrop.json.objects.DropConfig;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.ewyboy.seeddrop.util.ModLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {

    private static final Gson gson = new Gson();
    public static File JSON_FILE;

    private static final List<DropEntry> dropEntries = new ArrayList<>();

    static {
        dropEntries.add(new DropEntry("minecraft:wheat_seeds", 3.125D));
        dropEntries.add(new DropEntry("minecraft:melon_seeds", 3.125D));
        dropEntries.add(new DropEntry("minecraft:pumpkin_seeds", 3.125D));
        dropEntries.add(new DropEntry("minecraft:beetroot_seeds", 3.125D));
    }

    public static DropConfig dropConfig = new DropConfig(dropEntries);

    public static DropConfig getDropConfig() {
        return dropConfig;
    }

    public static void setup(Path configDir) {
        JSON_FILE = configDir.resolve("seeddrop/SeedDrops.json").toFile();
        createDirectory(configDir);
        if(!JSON_FILE.exists()) {
            ModLogger.info("Creating Seed Drop config JSON file");
            writeJson(JSON_FILE);
        }
        ModLogger.info("Reading Seed Drop config JSON file");
        readJson(JSON_FILE);
    }

    public static void reload() {
        writeJson(JSON_FILE);
        readJson(JSON_FILE);
    }

    public static boolean containsEntry(DropEntry entry) {
        for (DropEntry drop : dropConfig.getDropConfig()) {
            if (drop.getItem().equals(entry.getItem())) {
                return true;
            }
        }
        return false;
    }

    public static boolean addEntry(DropEntry entry) {
        if (!containsEntry(entry)) {
            dropConfig.getDropConfig().add(entry);
            reload();
            return true;
        }
        return false;
    }

    public static boolean removeEntry(String entryName) {
        if (containsEntry(new DropEntry(entryName, 0.0D))) {
            dropConfig.getDropConfig().removeIf(drop -> drop.getItem().equals(entryName));
            reload();
            return true;
        }
        return false;
    }

    public static void writeJson(File jsonFile) {
        try(Writer writer = new FileWriter(jsonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(dropConfig, writer);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void readJson(File jsonFile) {
        try(Reader reader = new FileReader(jsonFile)) {
            DropConfig config = gson.fromJson(reader, DropConfig.class);
            // An empty file parses to null and would crash every grass drop, keep the current entries instead
            if (config == null || config.getDropConfig() == null) {
                throw new JsonParseException(jsonFile.getName() + " has no dropConfig list");
            }
            dropConfig = config;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory(Path configPath) {
        Path seedDropConfigPath = Paths.get(configPath.toAbsolutePath().toString(), Constants.MOD_ID);
        try {
            ModLogger.info("Creating Seed Drop config directory");
            Files.createDirectory(seedDropConfigPath);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            ModLogger.error("Failed to create seeddrop config directory", e);
        }
    }

}
