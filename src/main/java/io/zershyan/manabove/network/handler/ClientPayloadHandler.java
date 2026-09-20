package io.zershyan.manabove.network.handler;

import io.zershyan.manabove.config.ClientConfig;
import io.zershyan.manabove.datagen.init.MATranslatableLang;
import io.zershyan.manabove.network.data.SoundData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void playSound(SoundData sound, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            ClientLevel level = instance.level;
            LocalPlayer player = instance.player;
            if(level == null) return;
            if(player == null) return;
            if(ClientConfig.TipOnFirstTime.getAsBoolean()) {
                ClientConfig.TipOnFirstTime.set(false);
                player.sendSystemMessage(Component.translatable(
                        MATranslatableLang.SOUND_ENABLED_TIPS.getKey(),
                        Component.translatable(MATranslatableLang.ENABLE.getKey()).withStyle(style -> style.withClickEvent(
                                new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/manabove sound enable")
                        )),
                        Component.translatable(MATranslatableLang.DISABLE.getKey()).withStyle(style -> style.withClickEvent(
                                new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/manabove sound disable")
                        ))
                ));
            }
            SoundEvent soundEvent = sound.soundEvent();
            if(!ClientConfig.EnableSound.getAsBoolean()) soundEvent = SoundEvents.EXPERIENCE_ORB_PICKUP;
            player.playSound(soundEvent, 1.0f, 1.0f);
        });
    }
}