package io.zershyan.manabove.network.handler;

import io.zershyan.manabove.api.ManAboveApi;
import io.zershyan.manabove.network.data.FlyPlayerUpData;
import io.zershyan.manabove.network.data.RidePlayerData;
import io.zershyan.manabove.network.data.RidePosData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void ridePlayer(RidePlayerData uuidData, IPayloadContext context) {
        context.enqueueWork(() -> {
            if(context.player() instanceof ServerPlayer sender) {
                Player playerByUUID = sender.level().getPlayerByUUID(uuidData.uuid());
                if(!(playerByUUID instanceof ServerPlayer target)) return;
                ManAboveApi api = ManAboveApi.get(sender);
                api.startRide(target);
                api.playPretentiousSound(target);
            }
        });
    }

    public static void flyPlayerUp(FlyPlayerUpData uuidData, IPayloadContext context) {
        context.enqueueWork(() -> {
            if(context.player() instanceof ServerPlayer sender) {
                Player playerByUUID = sender.level().getPlayerByUUID(uuidData.uuid());
                if(!(playerByUUID instanceof ServerPlayer target)) return;
                ManAboveApi.get(sender).flyPlayerUp(target);
            }
        });
    }

    public static void ridePos(RidePosData posData, IPayloadContext context) {
        context.enqueueWork(() -> {
            if(context.player() instanceof ServerPlayer sender) {
                ManAboveApi.get(sender).setRidePos(posData.integer());
            }
        });
    }
}
