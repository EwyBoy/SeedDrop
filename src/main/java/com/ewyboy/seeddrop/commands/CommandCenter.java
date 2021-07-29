package com.ewyboy.seeddrop.commands;

import com.ewyboy.seeddrop.SeedDrop;
import com.ewyboy.seeddrop.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            LiteralArgumentBuilder.<CommandSourceStack> literal(SeedDrop.MOD_ID)
                .then(CommandReloadJSON.register())
                .then(CommandAddEntry.register())
                .then(CommandRemoveEntry.register())
                .then(CommandEditEntry.register())
                .then(CommandViewEntries.register())
                .executes(ctx -> 0)
        );
    }
}
