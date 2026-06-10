package io.zershyan.standupandpedal.client.handler;

import io.zershyan.standupandpedal.ManAbove;
import io.zershyan.standupandpedal.client.registry.SUAPKeyBindings;
import io.zershyan.standupandpedal.network.data.UUIDData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@EventBusSubscriber(modid = ManAbove.MODID, value = Dist.CLIENT)
public class KeyPressHandler {
    @SubscribeEvent
    public static void onClientTick(InputEvent.Key event) {
        if(SUAPKeyBindings.KEY_RIDE.get().isDown()) {
            Minecraft instance = Minecraft.getInstance();
            LocalPlayer player = instance.player;
            if(player == null) return;
            Entity cameraEntity = instance.getCameraEntity();
            if(cameraEntity == null) return;
            HitResult pick = player.raycastHitResult(0.0f, cameraEntity);
            if(pick.getType() == HitResult.Type.ENTITY) {
                EntityHitResult hitResult = (EntityHitResult) pick;
                if(hitResult.getEntity() instanceof Player target) {
                    ClientPacketDistributor.sendToServer(new UUIDData(target.getUUID()));
                }
            }
        }
    }
}
