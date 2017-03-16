package com.ewyboy.seeddrop.loaders;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

/** Created by EwyBoy **/
public class SeedLoader {
    public static void registerSeeds() {
        for (int i = 0; i < ConfigLoader.customDrops.length; i += 2) {
            MinecraftForge.addGrassSeed(new ItemStack(Item.getByNameOrId(ConfigLoader.customDrops[i])), Integer.parseInt(ConfigLoader.customDrops[i+1]));
        }
    }
}
