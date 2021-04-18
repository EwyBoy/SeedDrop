package com.ewyboy.seeddrop.json;

import com.ewyboy.seeddrop.SeedDrop;
import com.ewyboy.seeddrop.json.objects.DropConfig;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.ewyboy.seeddrop.util.ModLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.Item;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JSONHandler {

    private static final Gson gson = new Gson();
    public static final File JSON_FILE = new File(FMLPaths.CONFIGDIR.get() + "/seeddrop/SeedDrops.json");

    private static final List<DropEntry> dropEntries = new ArrayList<>(); static {
        //dropEntries.add(new DropEntry("minecraft:wheat_seeds", 12.5D));
        //dropEntries.add(new DropEntry("minecraft:melon_seeds", 12.5D));
        //dropEntries.add(new DropEntry("minecraft:pumpkin_seeds", 12.5D));
        //dropEntries.add(new DropEntry("minecraft:beetroot_seeds", 12.5D));

        for (Item item : ForgeRegistries.ITEMS) {
            dropEntries.add(new DropEntry(Objects.requireNonNull(item.getRegistryName()).toString(), 0.0025));
        }
    }

    public static DropConfig dropConfig = new DropConfig(dropEntries);

    public static void setup() {
        createDirectory();
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
            dropConfig = gson.fromJson(reader, DropConfig.class);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory() {
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path seedDropConfigPath = Paths.get(configPath.toAbsolutePath().toString(), SeedDrop.MOD_ID);
        try {
            ModLogger.info("Creating Seed Drop config directory");
            Files.createDirectory(seedDropConfigPath);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            ModLogger.error("Failed to create seeddrop config directory", e);
        }
    }

}
