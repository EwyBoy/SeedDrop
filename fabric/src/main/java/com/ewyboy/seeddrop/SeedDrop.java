package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.commands.CommandCenter;
import com.ewyboy.seeddrop.json.InfoHandler;
import com.ewyboy.seeddrop.json.JSONHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;

public class SeedDrop implements ModInitializer {

    @Override
    public void onInitialize() {
        JSONHandler.setup(FabricLoader.getInstance().getConfigDir());
        InfoHandler.setup(FabricLoader.getInstance().getConfigDir());
        CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> new CommandCenter(dispatcher, context));
        // Grass drops are edited by mixin.LootTableMixin
    }

}
