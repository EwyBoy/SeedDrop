package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropConfig;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.ewyboy.seeddrop.loot.GrassDrops;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.List;

// Run with ./gradlew :neoforge:runGameTestServer
@GameTestHolder(Constants.MOD_ID)
@PrefixGameTestTemplate(false)
public class SeedDropGameTests {

    private static final BlockPos GRASS = new BlockPos(1, 2, 1);

    // #13: drops other mods add to grass, like Farmer's Delight straw, must survive
    @GameTest(template = "empty")
    public static void keepsOtherModsDrops(GameTestHelper helper) {
        List<ItemStack> loot = new ArrayList<>(List.of(new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT_SEEDS)));
        withMelonSeedsOnly(() -> GrassDrops.addDrops(loot));

        helper.assertTrue(loot.stream().anyMatch(stack -> stack.is(Items.WHEAT)), "Another mod's drop was removed");
        helper.assertTrue(loot.stream().anyMatch(stack -> stack.is(Items.MELON_SEEDS)), "Configured seed was not added");
        helper.assertFalse(loot.stream().anyMatch(stack -> stack.is(Items.WHEAT_SEEDS)), "Vanilla wheat seeds were not replaced");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void skipsUnknownItems(GameTestHelper helper) {
        List<ItemStack> loot = new ArrayList<>();
        withDrops(List.of(new DropEntry("removedmod:seeds", 100), new DropEntry("Not An Item!", 100), new DropEntry("minecraft:melon_seeds", 100)), () -> GrassDrops.addDrops(loot));

        helper.assertTrue(loot.size() == 1 && loot.get(0).is(Items.MELON_SEEDS), "Entries after an unknown item were skipped");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void grassDropsConfiguredSeeds(GameTestHelper helper) {
        placeGrass(helper);
        withMelonSeedsOnly(() -> helper.getLevel().destroyBlock(helper.absolutePos(GRASS), true));

        helper.assertItemEntityPresent(Items.MELON_SEEDS, GRASS, 2);
        helper.assertItemEntityNotPresent(Items.WHEAT_SEEDS, GRASS, 2);
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void shearsDropGrassOnly(GameTestHelper helper) {
        placeGrass(helper);
        withMelonSeedsOnly(() -> Block.dropResources(helper.getBlockState(GRASS), helper.getLevel(), helper.absolutePos(GRASS), null, null, new ItemStack(Items.SHEARS)));

        helper.assertItemEntityPresent(Items.SHORT_GRASS, GRASS, 2);
        helper.assertItemEntityNotPresent(Items.MELON_SEEDS, GRASS, 2);
        helper.succeed();
    }

    private static void placeGrass(GameTestHelper helper) {
        helper.setBlock(GRASS.below(), Blocks.GRASS_BLOCK);
        helper.setBlock(GRASS, Blocks.SHORT_GRASS);
    }

    // Swaps in a config that always drops melon seeds, without touching SeedDrops.json
    private static void withMelonSeedsOnly(Runnable action) {
        withDrops(List.of(new DropEntry("minecraft:melon_seeds", 100)), action);
    }

    private static void withDrops(List<DropEntry> drops, Runnable action) {
        DropConfig config = JSONHandler.dropConfig;
        JSONHandler.dropConfig = new DropConfig(new ArrayList<>(drops));
        try {
            action.run();
        } finally {
            JSONHandler.dropConfig = config;
        }
    }

}
