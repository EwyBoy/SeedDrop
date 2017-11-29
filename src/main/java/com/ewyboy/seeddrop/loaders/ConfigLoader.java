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
        config.addCustomCategoryComment(Configuration.CATEGORY_GENERAL,
                "[Drop Weight]" +
                        "\n" + "Easiest way to explain this, is that this works like a lottery." +
                        "\n" + "You can think of the weight variable as a lottery ticket." +
                        "\n" + "When you break grass it rolls a dice to decide if it should drop something or not" +
                        "\n" + "If it decides to drop something, it draws a lottery ticket to decide what to drop" +
                        "\n" + "The more tickets an entire has, the higher chance of it dropping."
        );
        customDrops = config.getStringList("SeedDrop Drop List", Configuration.CATEGORY_GENERAL, customDrops,
                "How to configure this mod:" + "\n" +
<<<<<<< HEAD
                        "\n" + "First Line: minecraft:dirt:<meta>  [What to drop when grass is broken, meta is optional]" +
                        "\n" + "Second Line: 20  [Drop weight for item/block above (Vanilla Wheat Seeds is 10)]" +
                        "\n" + "You can edit / remove all the entries on this list as well as add new ones." + "\n" + "\n"
=======
                "\n" + "First Line: minecraft:dirt:<meta>  [What to drop when grass is broken, meta is optional]" +
                "\n" + "Second Line: 20  [Drop weight for item/block above (Vanilla Wheat Seeds is 10)]" +
                "\n" + "You can edit / remove all the entries on this list as well as add new ones." + "\n" + "\n"
>>>>>>> master
        );
        if (config.hasChanged()) config.save();
    }
}
