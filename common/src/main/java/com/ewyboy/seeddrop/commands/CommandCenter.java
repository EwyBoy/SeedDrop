package com.ewyboy.seeddrop.commands;

import com.ewyboy.seeddrop.Constants;
import com.ewyboy.seeddrop.commands.server.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(
            LiteralArgumentBuilder.<CommandSourceStack> literal(Constants.MOD_ID)
                .then(CommandReloadJSON.register())
                .then(CommandAddEntry.register(context))
                .then(CommandRemoveEntry.register(context))
                .then(CommandEditEntry.register(context))
                .then(CommandViewEntries.register())
                .executes(ctx -> 0)
        );
    }
}