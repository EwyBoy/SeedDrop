package com.ewyboy.seeddrop.loot;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Set;

public class GrassDrops {

    // Mirrors the conditions in the NeoForge global_seed_modifier.json, for loaders without loot modifiers
    private static final Set<Block> GRASS_BLOCKS = Set.of(Blocks.SHORT_GRASS, Blocks.TALL_GRASS, Blocks.FERN, Blocks.LARGE_FERN);
    private static final TagKey<Item> SHEARS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "tools/shear"));

    public static boolean appliesTo(BlockState state, ItemStack tool) {
        return state != null && GRASS_BLOCKS.contains(state.getBlock()) && (tool == null || !tool.is(SHEARS));
    }

    // Only touches vanilla wheat seeds and appends, other mods' drops must survive (#13)
    public static void addDrops(List<ItemStack> generatedLoot) {
        generatedLoot.removeIf(itemStack -> itemStack.getItem() == Items.WHEAT_SEEDS);

        List<DropEntry> dropEntries = JSONHandler.dropConfig.getDropConfig();

        for(DropEntry dropEntry : dropEntries) {
            // Skip entries for removed mods or typos instead of crashing or ending the loop
            ResourceLocation itemId = dropEntry.getItem() == null ? null : ResourceLocation.tryParse(dropEntry.getItem());
            Item seedItem = itemId == null ? null : BuiltInRegistries.ITEM.getOptional(itemId).orElse(null);
            if (seedItem == null) {
                continue;
            }
            ItemStack seedItemStack = new ItemStack(seedItem);

            double dropChance = dropEntry.getChance() / 100d;
            double randomValue = Math.random();
            if(randomValue < dropChance) {
                generatedLoot.add(seedItemStack);
            }
        }
    }

}
