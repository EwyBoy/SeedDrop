package com.ewyboy.seeddrop.json;

import com.ewyboy.seeddrop.util.ModLogger;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InfoHandler {

    public static final File INFO_FILE = new File(FMLPaths.CONFIGDIR.get() + "/seeddrop/Info.txt");

    public static void setup() {
        createInfoFile();
    }

    private static void createInfoFile() {
        try {
            if (InfoHandler.INFO_FILE.createNewFile()) {
                ModLogger.info("Creating Seed Drop information file: " + InfoHandler.INFO_FILE.getName());
                FileWriter writer = new FileWriter(INFO_FILE);
                writeInfoFile(writer);
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
