package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.commands.CommandCenter;
import com.ewyboy.seeddrop.json.InfoHandler;
import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.loot.GrassDrops;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class SeedDrop implements ModInitializer {

    @Override
    public void onInitialize() {
        JSONHandler.setup(FabricLoader.getInstance().getConfigDir());
        InfoHandler.setup(FabricLoader.getInstance().getConfigDir());
        CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> new CommandCenter(dispatcher, context));
        // Edits the rolled drops like a NeoForge loot modifier, so whatever other mods add to the grass loot table survives
        LootTableEvents.MODIFY_DROPS.register((lootTable, context, drops) -> {
            if (GrassDrops.appliesTo(context.getOptional(LootContextParams.BLOCK_STATE), context.getOptional(LootContextParams.TOOL))) {
                GrassDrops.addDrops(drops);
            }
        });
    }

}
