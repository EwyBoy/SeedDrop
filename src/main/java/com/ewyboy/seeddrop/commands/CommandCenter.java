package com.ewyboy.seeddrop.commands;

import com.ewyboy.seeddrop.SeedDrop;
import com.ewyboy.seeddrop.commands.server.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;

public class CommandCenter {

    public CommandCenter(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            LiteralArgumentBuilder.<CommandSourceStack> literal(SeedDrop.MOD_ID)
                .then(CommandReloadJSON.register())
                .then(CommandAddEntry.register(event.getBuildContext()))
                .then(CommandRemoveEntry.register(event.getBuildContext()))
                .then(CommandEditEntry.register(event.getBuildContext()))
                .then(CommandViewEntries.register())
                .executes(ctx -> 0)
        );
    }
}