package io.zershyan.manabove.client.registry.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.zershyan.manabove.config.ClientConfig;
import io.zershyan.manabove.datagen.init.MATranslatableLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static net.minecraft.commands.Commands.literal;

public class SoundCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> command) {
        command.then(literal("sound")
                .then(literal("enable").executes(ctx -> switchConfig(ctx, true)))
                .then(literal("disable").executes(ctx -> switchConfig(ctx, false))));
    }

    private static int switchConfig(CommandContext<CommandSourceStack> context, boolean enabled) {
        ClientConfig.enableSound.set(enabled);
        context.getSource().sendSuccess(() -> Component.translatable(
                enabled ? MATranslatableLang.ENABLED.getKey() : MATranslatableLang.DISABLED.getKey()
        ), false);
        return Command.SINGLE_SUCCESS;
    }
}
