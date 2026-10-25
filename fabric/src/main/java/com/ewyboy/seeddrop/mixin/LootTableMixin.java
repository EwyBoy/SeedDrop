package com.ewyboy.seeddrop.mixin;

import com.ewyboy.seeddrop.loot.GrassDrops;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// Fabric API for 1.21.1 has no LootTableEvents.MODIFY_DROPS yet, so this does the same thing: edits the
// rolled drops like a NeoForge loot modifier, so whatever other mods add to the grass loot table survives.
@Mixin(LootTable.class)
public class LootTableMixin {

    @WrapOperation(
        method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V")
    )
    private void seeddrop$modifyDrops(LootTable lootTable, LootContext context, Consumer<ItemStack> output, Operation<Void> original) {
        if (!GrassDrops.appliesTo(context.getParamOrNull(LootContextParams.BLOCK_STATE), context.getParamOrNull(LootContextParams.TOOL))) {
            original.call(lootTable, context, output);
            return;
        }
        List<ItemStack> drops = new ArrayList<>();
        original.call(lootTable, context, (Consumer<ItemStack>) drops :: add);
        GrassDrops.addDrops(drops);
        drops.forEach(output);
    }

}
