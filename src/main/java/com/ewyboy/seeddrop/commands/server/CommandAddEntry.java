package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.ewyboy.seeddrop.json.JSONHandler;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class CommandAddEntry {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("add").requires((commandSource) -> commandSource.hasPermission(2))
            .then(Commands.argument("item", ItemArgument.item())
            .then(Commands.argument("chance", DoubleArgumentType.doubleArg(0, 100))
            .executes((commandSource) -> addEntry(
                    commandSource.getSource(),
                    ItemArgument.getItem(commandSource, "item"),
                    DoubleArgumentType.getDouble(commandSource, "chance")
            )))
        );
    }

    private static int addEntry(CommandSource source, ItemInput itemInput, double chance) {
        String entryName = Objects.requireNonNull(itemInput.getItem().getRegistryName()).toString();
        DropEntry dropEntry = new DropEntry(entryName, chance);

        if (JSONHandler.addEntry(dropEntry)) {
            source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + entryName + TextFormatting.WHITE + " added to config"), true);
        } else {
            source.sendSuccess(new StringTextComponent(TextFormatting.RED + "ERROR: " + entryName.toUpperCase() + TextFormatting.WHITE + " is already found in config"), true);
        }

        return 0;
    }
}
