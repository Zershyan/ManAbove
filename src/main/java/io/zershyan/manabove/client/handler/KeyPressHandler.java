package io.zershyan.manabove.client.handler;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.api.ManAboveApi;
import io.zershyan.manabove.client.registry.MAKeyBindings;
import io.zershyan.manabove.network.data.FlyPlayerUpData;
import io.zershyan.manabove.network.data.RidePlayerData;
import io.zershyan.manabove.network.data.RidePosData;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ManAbove.MODID, value = Dist.CLIENT)
public class KeyPressHandler {
    @SubscribeEvent
    public static void onKeyPress(InputEvent.Key event) {
        Minecraft instance = Minecraft.getInstance();
        LocalPlayer player = instance.player;
        if(player == null) return;
        if(MAKeyBindings.KEY_RIDE.get().isDown()) {
            HitResult pick = player.pick(0.0f, 0.0f, false);
            if(pick.getType() == HitResult.Type.ENTITY) {
                EntityHitResult hitResult = (EntityHitResult) pick;
                if(hitResult.getEntity() instanceof Player target) {
                    if(target.getVehicle() != player) {
                        PacketDistributor.sendToServer(new RidePlayerData(target.getUUID()));
                        instance.options.setCameraType(CameraType.THIRD_PERSON_BACK);
                    } else {
                        PacketDistributor.sendToServer(new FlyPlayerUpData(target.getUUID()));
                    }
                }
            }
        }
        if(ManAboveApi.get(player).isRidePlayer()) {
            int pos = -1;
            if (MAKeyBindings.KEY_POS_1.get().isDown()) pos = 1;
            else if (MAKeyBindings.KEY_POS_2.get().isDown()) pos = 2;
            else if (MAKeyBindings.KEY_POS_3.get().isDown()) pos = 3;
            else if (MAKeyBindings.KEY_POS_4.get().isDown()) pos = 4;
            if(pos != -1) PacketDistributor.sendToServer(new RidePosData(pos));
        }
    }
}
