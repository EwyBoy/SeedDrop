package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

import java.util.Objects;

public class CommandRemoveEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("remove").requires((commandSource) -> commandSource.hasPermission(2))
            .then(Commands.argument("item", ItemArgument.item())
            .executes((commandSource) -> removeEntry(
                    commandSource.getSource(),
                    ItemArgument.getItem(commandSource, "item")
            ))
        );
    }

    private static int removeEntry(CommandSourceStack source, ItemInput itemInput) {
        String entryName = Objects.requireNonNull(itemInput.getItem().getRegistryName()).toString();
        if (JSONHandler.removeEntry(entryName)) {
            source.sendSuccess(new TextComponent(ChatFormatting.RED + entryName + ChatFormatting.WHITE + " removed from config"), true);
        } else {
            source.sendSuccess(new TextComponent(ChatFormatting.RED + "ERROR: " + entryName.toUpperCase() + ChatFormatting.WHITE + " not found in config"), true);
        }

        return 0;
    }
}
