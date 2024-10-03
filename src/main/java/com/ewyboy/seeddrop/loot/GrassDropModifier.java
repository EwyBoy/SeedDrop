package com.ewyboy.seeddrop.loot;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class GrassDropModifier extends LootModifier {

    public GrassDropModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    public static final Codec<GrassDropModifier> CODEC = RecordCodecBuilder.create(
        modifierInstance -> codecStart(modifierInstance).apply(modifierInstance, GrassDropModifier :: new)
    );

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.removeIf(itemStack -> itemStack.getItem() == Items.WHEAT_SEEDS);

        List<DropEntry> dropEntries = JSONHandler.dropConfig.getDropConfig();

        for(DropEntry dropEntry : dropEntries) {
            Item seedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(dropEntry.getItem()));
            if (seedItem == null) {
                break;
            }
            ItemStack seedItemStack = new ItemStack(seedItem);

            double dropChance = dropEntry.getChance() / 100d;
            double randomValue = Math.random();
            if(randomValue < dropChance) {
                generatedLoot.add(seedItemStack);
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

}
