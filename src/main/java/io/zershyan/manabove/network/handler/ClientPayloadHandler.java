package io.zershyan.manabove.network.handler;

import io.zershyan.manabove.network.data.SoundData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void playSound(SoundData sound, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            ClientLevel level = instance.level;
            LocalPlayer player = instance.player;
            if(level == null) return;
            if(player == null) return;
            instance.getSoundManager().play(SimpleSoundInstance.forMusic(sound.soundEvent()));
        });
    }
}