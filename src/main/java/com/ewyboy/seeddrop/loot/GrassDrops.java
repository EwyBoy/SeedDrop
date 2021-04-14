package com.ewyboy.seeddrop.loot;

import com.ewyboy.seeddrop.SeedDrop;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.util.ModLogger;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SeedDrop.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GrassDrops {

    @SubscribeEvent
    public static void registerModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> registryEvent) {
        registryEvent.getRegistry().register(new GrassDropSerializer().setRegistryName(SeedDrop.MOD_ID, "seed_drops"));
    }

    public static class GrassDropSerializer extends GlobalLootModifierSerializer<GrassDropModifier> {

        @Override
        public GrassDropModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new GrassDropModifier(conditions);
        }

        @Override
        public JsonObject write(GrassDropModifier instance) {
            return new JsonObject();
        }

    }

    private static class GrassDropModifier extends LootModifier {

        protected GrassDropModifier(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            generatedLoot.removeIf(itemStack -> itemStack.getItem() == Items.WHEAT_SEEDS);

            List<ItemStack> finalLootList = new ArrayList<>();
            List<DropEntry> dropEntries = JSONHandler.dropConfig.getDropConfig();

            for(DropEntry dropEntry : dropEntries) {
                Item seedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(dropEntry.getItem()));
                ItemStack seedItemStack = new ItemStack(seedItem);
                generatedLoot.add(new ItemStack(seedItem));
                double drop_percentage = dropEntry.getChance();

                if(!generatedLoot.isEmpty() && drop_percentage < 100) {
                    double randomValue = Math.random();

                    if(randomValue < drop_percentage / 100) {
                        finalLootList.add(seedItemStack);
                    }
                }
            }
            return finalLootList;
        }

    }

}
