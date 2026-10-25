package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class CommandViewEntries {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("view").requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
            .executes((commandSource) -> viewEntries(
                commandSource.getSource()
            )
        );
    }

    private static int viewEntries(CommandSourceStack source) {
        source.sendSuccess(() -> Component.literal(ChatFormatting.BOLD + "Drops:"), true);
        for (DropEntry drop : JSONHandler.dropConfig.getDropConfig()) {
            source.sendSuccess(() -> Component.literal("[" + ChatFormatting.GREEN + drop.getItem() + ChatFormatting.WHITE + "]" + " " + ChatFormatting.GOLD + drop.getChance() + "%"), true);
        }

        return 0;
    }

}
