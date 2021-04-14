package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class CommandEditEntry {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("edit").requires((commandSource) -> commandSource.hasPermission(2))
            .then(Commands.argument("item", ItemArgument.item())
            .then(Commands.argument("new chance", DoubleArgumentType.doubleArg(0, 100))
            .executes((commandSource) -> editEntry(
                    commandSource.getSource(),
                    ItemArgument.getItem(commandSource, "item"),
                    DoubleArgumentType.getDouble(commandSource, "new chance")
            )))
        );
    }

    private static int editEntry(CommandSource source, ItemInput itemInput, double newChance) {
        String entryName = Objects.requireNonNull(itemInput.getItem().getRegistryName()).toString();
        DropEntry dropEntry = new DropEntry(entryName, newChance);

        if (JSONHandler.containsEntry(dropEntry)) {
            JSONHandler.removeEntry(entryName);
            JSONHandler.addEntry(dropEntry);
            source.sendSuccess(new StringTextComponent(TextFormatting.GREEN + entryName + TextFormatting.WHITE + " changed to " + TextFormatting.GOLD + newChance + "%"), true);
        } else {
            source.sendSuccess(new StringTextComponent(TextFormatting.RED + "ERROR: " +  entryName.toUpperCase() + TextFormatting.WHITE + " was not found in config"), true);
        }

        return 0;
    }
}
