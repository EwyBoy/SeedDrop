package com.ewyboy.seeddrop.json;

import com.ewyboy.seeddrop.util.ModLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class InfoHandler {

    public static File INFO_FILE;

    public static void setup(Path configDir) {
        INFO_FILE = configDir.resolve("seeddrop/Info.txt").toFile();
        createInfoFile();
    }

    private static void createInfoFile() {
        try {
            // Older versions never closed the writer and left an empty file behind, so fill those in too
            if (InfoHandler.INFO_FILE.createNewFile() || InfoHandler.INFO_FILE.length() == 0) {
                ModLogger.info("Creating Seed Drop information file: " + InfoHandler.INFO_FILE.getName());
                try (FileWriter writer = new FileWriter(INFO_FILE)) {
                    writeInfoFile(writer);
                }
            }
        } catch (IOException e) {
            ModLogger.error("Failed to create information file: " + InfoHandler.INFO_FILE.getName());
            e.printStackTrace();
        }
    }

    private static void writeInfoFile(FileWriter writer) throws IOException {
        writer.write("Seed Drop - Information");
        writer.write("\n");

        writer.write("Edit the SeedDrops.json file to add or remove entries from the grass loot table.");
    }

}
