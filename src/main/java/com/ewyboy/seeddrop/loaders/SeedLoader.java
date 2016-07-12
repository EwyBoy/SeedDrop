package com.ewyboy.seeddrop.loaders;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/** Created by EwyBoy **/
public class SeedLoader {

    static Item[] drops = {
            Items.WHEAT_SEEDS,
            Items.PUMPKIN_SEEDS,
            Items.MELON_SEEDS,
            Items.BEETROOT_SEEDS,
            Items.CARROT,
            Items.POTATO,
            Items.POISONOUS_POTATO
    };

    public static void registerSeeds() {
        for (int i = 0; i < drops.length; i++) {
            if (!ConfigLoader.disableDrops[i]) MinecraftForge.addGrassSeed(new ItemStack(drops[i]), ConfigLoader.dropChances[i]);
        }
    }
}
