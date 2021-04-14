package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class CommandRemoveEntry {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("remove").requires((commandSource) -> commandSource.hasPermission(2))
            .then(Commands.argument("item", ItemArgument.item())
            .executes((commandSource) -> removeEntry(
                    commandSource.getSource(),
                    ItemArgument.getItem(commandSource, "item")
            ))
        );
    }

    private static int removeEntry(CommandSource source, ItemInput itemInput) {
        String entryName = Objects.requireNonNull(itemInput.getItem().getRegistryName()).toString();
        if (JSONHandler.removeEntry(entryName)) {
            source.sendSuccess(new StringTextComponent(TextFormatting.RED + entryName + TextFormatting.WHITE + " removed from config"), true);
        } else {
            source.sendSuccess(new StringTextComponent(TextFormatting.RED + "ERROR: " + entryName.toUpperCase() + TextFormatting.WHITE + " not found in config"), true);
        }

        return 0;
    }
}
