package io.zershyan.manabove.common.registry;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.client.registry.command.SoundCommand;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.commands.Commands.literal;

public class MACommands {
    static final Set<String> manaboveCommand = new HashSet<>(Set.of(ManAbove.MODID, "ma"));

    public static void register(IEventBus neoBus) {
        neoBus.addListener(MACommands::clientRegister);
    }

    public static void clientRegister(RegisterClientCommandsEvent event) {
        manaboveCommand.forEach(string -> {
            LiteralArgumentBuilder<CommandSourceStack> builder = literal(string);
            SoundCommand.register(builder);
            event.getDispatcher().register(builder);
        });
    }
}
