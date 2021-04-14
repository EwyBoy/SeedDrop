package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CommandViewEntries {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("view").requires((commandSource) -> commandSource.hasPermission(2))
            .executes((commandSource) -> viewEntries(
                commandSource.getSource()
            )
        );
    }

    private static int viewEntries(CommandSource source) {
        source.sendSuccess(new StringTextComponent(TextFormatting.BOLD + "Drops:"), true);
        for (DropEntry drop : JSONHandler.dropConfig.getDropConfig()) {
            source.sendSuccess(new StringTextComponent("[" + TextFormatting.GREEN + drop.getItem() + TextFormatting.WHITE + "]" + " " + TextFormatting.GOLD + drop.getChance() + "%"), true);
        }

        return 0;
    }

}
