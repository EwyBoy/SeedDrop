package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

public class CommandViewEntries {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("view").requires((commandSource) -> commandSource.hasPermission(2))
            .executes((commandSource) -> viewEntries(
                commandSource.getSource()
            )
        );
    }

    private static int viewEntries(CommandSourceStack source) {
        source.sendSuccess(new TextComponent(ChatFormatting.BOLD + "Drops:"), true);
        for (DropEntry drop : JSONHandler.dropConfig.getDropConfig()) {
            source.sendSuccess(new TextComponent("[" + ChatFormatting.GREEN + drop.getItem() + ChatFormatting.WHITE + "]" + " " + ChatFormatting.GOLD + drop.getChance() + "%"), true);
        }

        return 0;
    }

}
