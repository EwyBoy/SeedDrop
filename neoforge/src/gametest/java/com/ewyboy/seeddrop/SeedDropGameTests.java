package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropConfig;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.ewyboy.seeddrop.loot.GrassDrops;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

// Run with ./gradlew :neoforge:runGameTestServer
@EventBusSubscriber(modid = Constants.MOD_ID)
public class SeedDropGameTests {

    private static final BlockPos GRASS = new BlockPos(1, 2, 1);

    private static final Map<String, Consumer<GameTestHelper>> TESTS = Map.of(
        "keeps_other_mods_drops", SeedDropGameTests :: keepsOtherModsDrops,
        "skips_unknown_items", SeedDropGameTests :: skipsUnknownItems,
        "grass_drops_configured_seeds", SeedDropGameTests :: grassDropsConfiguredSeeds,
        "shears_drop_grass_only", SeedDropGameTests :: shearsDropGrassOnly
    );

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> TESTS.forEach((name, test) -> helper.register(id(name), test)));
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> environment = event.registerEnvironment(id("default"));
        TESTS.keySet().forEach(name -> event.registerTest(id(name), new FunctionGameTestInstance(
            ResourceKey.create(Registries.TEST_FUNCTION, id(name)),
            new TestData<>(environment, id("empty"), 20, 0, true)
        )));
    }

    // #13: drops other mods add to grass, like Farmer's Delight straw, must survive
    public static void keepsOtherModsDrops(GameTestHelper helper) {
        List<ItemStack> loot = new ArrayList<>(List.of(new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT_SEEDS)));
        withMelonSeedsOnly(() -> GrassDrops.addDrops(loot));

        helper.assertTrue(loot.stream().anyMatch(stack -> stack.is(Items.WHEAT)), "Another mod's drop was removed");
        helper.assertTrue(loot.stream().anyMatch(stack -> stack.is(Items.MELON_SEEDS)), "Configured seed was not added");
        helper.assertFalse(loot.stream().anyMatch(stack -> stack.is(Items.WHEAT_SEEDS)), "Vanilla wheat seeds were not replaced");
        helper.succeed();
    }

    public static void skipsUnknownItems(GameTestHelper helper) {
        List<ItemStack> loot = new ArrayList<>();
        withDrops(List.of(new DropEntry("removedmod:seeds", 100), new DropEntry("Not An Item!", 100), new DropEntry("minecraft:melon_seeds", 100)), () -> GrassDrops.addDrops(loot));

        helper.assertTrue(loot.size() == 1 && loot.get(0).is(Items.MELON_SEEDS), "Entries after an unknown item were skipped");
        helper.succeed();
    }

    public static void grassDropsConfiguredSeeds(GameTestHelper helper) {
        placeGrass(helper);
        withMelonSeedsOnly(() -> helper.getLevel().destroyBlock(helper.absolutePos(GRASS), true));

        helper.assertItemEntityPresent(Items.MELON_SEEDS, GRASS, 2);
        helper.assertItemEntityNotPresent(Items.WHEAT_SEEDS, GRASS, 2);
        helper.succeed();
    }

    public static void shearsDropGrassOnly(GameTestHelper helper) {
        placeGrass(helper);
        withMelonSeedsOnly(() -> Block.dropResources(helper.getBlockState(GRASS), helper.getLevel(), helper.absolutePos(GRASS), null, null, new ItemStack(Items.SHEARS)));

        helper.assertItemEntityPresent(Items.SHORT_GRASS, GRASS, 2);
        helper.assertItemEntityNotPresent(Items.MELON_SEEDS, GRASS, 2);
        helper.succeed();
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
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
