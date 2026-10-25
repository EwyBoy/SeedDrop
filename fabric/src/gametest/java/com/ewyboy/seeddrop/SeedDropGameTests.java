package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropConfig;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

// Checks the MODIFY_DROPS hook end to end, the shared drop logic is covered by the NeoForge game tests.
// Run with ./gradlew :fabric:runGameTest
public class SeedDropGameTests {

    private static final BlockPos GRASS = new BlockPos(1, 2, 1);

    @GameTest
    public void grassDropsConfiguredSeeds(GameTestHelper helper) {
        placeGrass(helper);
        withMelonSeedsOnly(() -> helper.getLevel().destroyBlock(helper.absolutePos(GRASS), true));

        helper.assertItemEntityPresent(Items.MELON_SEEDS, GRASS, 2);
        helper.assertItemEntityNotPresent(Items.WHEAT_SEEDS, GRASS, 2);
        helper.succeed();
    }

    @GameTest
    public void shearsDropGrassOnly(GameTestHelper helper) {
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
        DropConfig config = JSONHandler.dropConfig;
        JSONHandler.dropConfig = new DropConfig(new ArrayList<>(List.of(new DropEntry("minecraft:melon_seeds", 100))));
        try {
            action.run();
        } finally {
            JSONHandler.dropConfig = config;
        }
    }

}
