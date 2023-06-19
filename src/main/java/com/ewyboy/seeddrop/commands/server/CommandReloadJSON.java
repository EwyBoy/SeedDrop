package com.ewyboy.seeddrop.commands.server;

import com.ewyboy.seeddrop.json.JSONHandler;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class CommandReloadJSON {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reload").requires((commandSource) -> commandSource.hasPermission(2))
            .executes((commandSource) -> reload(
                commandSource.getSource()
            )
        );
    }

    private static int reload(CommandSourceStack source) {
        try {
            JSONHandler.readJson(JSONHandler.JSON_FILE);
            source.sendSuccess(() -> Component.literal(ChatFormatting.GREEN + "SUCCESS: " + ChatFormatting.WHITE + "Config reloaded"), true);
        } catch (Exception e) {
            e.printStackTrace();
            source.sendSuccess(() -> Component.literal(ChatFormatting.RED + "ERROR: " + ChatFormatting.WHITE + "Config failed to reload"), true);
        }
        return 0;
    }
}
