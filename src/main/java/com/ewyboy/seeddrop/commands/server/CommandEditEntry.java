package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.ewyboy.seeddrop.json.objects.DropEntry;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CommandEditEntry {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext ctx) {
        return Commands.literal("edit").requires((commandSource) -> commandSource.hasPermission(2))
            .then(Commands.argument("item", ItemArgument.item(ctx))
            .then(Commands.argument("new chance", DoubleArgumentType.doubleArg(0, 100))
            .executes((commandSource) -> editEntry(
                    commandSource.getSource(),
                    ItemArgument.getItem(commandSource, "item"),
                    DoubleArgumentType.getDouble(commandSource, "new chance")
            )))
        );
    }

    private static int editEntry(CommandSourceStack source, ItemInput itemInput, double newChance) {
        String entryName = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(itemInput.getItem())).toString();
        DropEntry dropEntry = new DropEntry(entryName, newChance);

        if (JSONHandler.containsEntry(dropEntry)) {
            JSONHandler.removeEntry(entryName);
            JSONHandler.addEntry(dropEntry);
            source.sendSuccess(Component.literal(ChatFormatting.GREEN + entryName + ChatFormatting.WHITE + " changed to " + ChatFormatting.GOLD + newChance + "%"), true);
        } else {
            source.sendSuccess(Component.literal(ChatFormatting.RED + "ERROR: " +  entryName.toUpperCase() + ChatFormatting.WHITE + " was not found in config"), true);
        }

        return 0;
    }
}
