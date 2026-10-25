package com.ewyboy.seeddrop.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

public class GrassDropModifier extends LootModifier {

    public GrassDropModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    public static final MapCodec<GrassDropModifier> CODEC = RecordCodecBuilder.mapCodec(
        modifierInstance -> codecStart(modifierInstance).apply(modifierInstance, GrassDropModifier :: new)
    );

    // Block and tool checks live in data/seeddrop/loot_modifiers/global_seed_modifier.json
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        GrassDrops.addDrops(generatedLoot);
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}
