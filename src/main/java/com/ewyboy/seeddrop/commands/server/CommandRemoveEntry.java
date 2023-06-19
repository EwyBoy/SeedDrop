package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CommandRemoveEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext ctx) {
        return Commands.literal("remove").requires((commandSource) -> commandSource.hasPermission(2))
            .then(Commands.argument("item", ItemArgument.item(ctx))
            .executes((commandSource) -> removeEntry(
                    commandSource.getSource(),
                    ItemArgument.getItem(commandSource, "item")
            ))
        );
    }

    private static int removeEntry(CommandSourceStack source, ItemInput itemInput) {
        String entryName = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemInput.getItem())).toString();
        if (JSONHandler.removeEntry(entryName)) {
            source.sendSuccess(() -> Component.literal(ChatFormatting.RED + entryName + ChatFormatting.WHITE + " removed from config"), true);
        } else {
            source.sendSuccess(() -> Component.literal(ChatFormatting.RED + "ERROR: " + entryName.toUpperCase() + ChatFormatting.WHITE + " not found in config"), true);
        }

        return 0;
    }
}
