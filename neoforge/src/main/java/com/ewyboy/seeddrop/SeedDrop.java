package com.ewyboy.seeddrop;

import com.ewyboy.seeddrop.commands.CommandCenter;
import com.ewyboy.seeddrop.json.InfoHandler;
import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.loot.GrassDropModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static com.ewyboy.seeddrop.Constants.MOD_ID;

@Mod(MOD_ID)
public class SeedDrop {

    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    private static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<GrassDropModifier>> GRASS_DROP = GLM.register("seed_drops", () -> GrassDropModifier.CODEC);

    // Server side only, see the note in neoforge.mods.toml
    public SeedDrop(IEventBus modEventBus) {
        JSONHandler.setup(FMLPaths.CONFIGDIR.get());
        InfoHandler.setup(FMLPaths.CONFIGDIR.get());
        NeoForge.EVENT_BUS.addListener(this :: registerCommands);
        GLM.register(modEventBus);
    }

    public void registerCommands(RegisterCommandsEvent event) {
        new CommandCenter(event.getDispatcher(), event.getBuildContext());
    }

}
