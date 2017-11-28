package com.ewyboy.seeddrop.loaders;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/** Created by EwyBoy **/
public class ConfigLoader {

    public static String[] customDrops = new String[] {
            "minecraft:wheat_seeds",
                "10",
            "minecraft:pumpkin_seeds",
                "10",
            "minecraft:melon_seeds",
                "10",
            "minecraft:beetroot_seeds",
                "10",
            "minecraft:carrot",
                "10",
            "minecraft:potato",
                "10",
            "minecraft:poisonous_potato",
                "10"
    };

    public static void init(File configurationFile) {
        Configuration config = new Configuration(configurationFile);
        customDrops = config.getStringList("SeedDrop Drop List", Configuration.CATEGORY_GENERAL, customDrops,
                "How to configure this mod:" + "\n" +
                "\n" + "First Line: minecraft:dirt:<meta>  [What to drop when grass is broken, meta is optional]" +
                "\n" + "Second Line: 20  [Drop weight for item/block above (Vanilla Wheat Seeds is 10)]" +
                "\n" + "You can edit / remove all the entries on this list as well as add new ones." + "\n" + "\n"
        );
        if (config.hasChanged()) config.save();
    }
}
