package io.zershyan.standupandpedal.network.handler;

import io.zershyan.standupandpedal.network.data.UUIDData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void ridePlayer(UUIDData uuidData, IPayloadContext context) {
        context.enqueueWork(() -> {
            if(context.player() instanceof ServerPlayer sender) {
                Player playerByUUID = sender.level().getPlayerByUUID(uuidData.uuid());
                if(!(playerByUUID instanceof ServerPlayer target)) return;
                sender.startRiding(target, false, true);
            }
        });
    }
}
