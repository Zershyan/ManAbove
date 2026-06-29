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
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
        if(instance.level == null) return;
        if(player == null) return;
        if(MAKeyBindings.KEY_RIDE.get().isDown()) {
            Vec3 eyePos = player.getEyePosition();
            Vec3 viewVec = player.getViewVector(1.0f);
            Vec3 endPos = eyePos.add(viewVec.scale(5.0));

            EntityHitResult result = ProjectileUtil.getEntityHitResult(
                    instance.level,
                    player,
                    eyePos,
                    endPos,
                    player.getBoundingBox().expandTowards(viewVec).inflate(1.0),
                    entity -> !entity.isSpectator() && entity.isPickable()
            );
            if(result == null) return;
            if(result.getType() == HitResult.Type.ENTITY) {
                if(result.getEntity() instanceof Player target) {
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
