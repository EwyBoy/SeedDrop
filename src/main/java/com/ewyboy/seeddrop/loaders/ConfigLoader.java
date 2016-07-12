package com.ewyboy.seeddrop.loaders;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/** Created by EwyBoy **/
public class ConfigLoader {

    static boolean disableWheat, disablePumpkin, disableMelon, disableBeet, disableCarrot, disablePotato, disablePoisonPotato;
    static boolean disableDrops[] = {
        disableWheat,
        disablePumpkin,
        disableMelon,
        disableBeet,
        disableCarrot,
        disablePotato,
        disablePoisonPotato
    };

    static int dropChanceWheat, dropChancePumpkin, dropChanceMelon, dropChanceBeet, dropChanceCarrot, dropChancePotato, dropChancePoisonPotato;
    static int dropChances[] = {
            dropChanceWheat,
            dropChancePumpkin,
            dropChanceMelon,
            dropChanceBeet,
            dropChanceCarrot,
            dropChancePotato,
            dropChancePoisonPotato
    };

    public static void init(File configurationFile) {
        Configuration config = new Configuration(configurationFile);

        config.load();
            for (int i = 0; i < SeedLoader.drops.length; i++) {
                String dropName = new ItemStack(SeedLoader.drops[i]).getDisplayName().toUpperCase();
                disableDrops[i] = config.getBoolean("Disable " + dropName + " Drops", "DISABLE", false, "Set to true to disable the drop of " + dropName);
                dropChances[i] = config.getInt(dropName + " Drop Chance", "DROP CHANCES", 10, 0, 100, "Sets the drop chance(%) of " + dropName);
            }
        config.save();
    }
}
