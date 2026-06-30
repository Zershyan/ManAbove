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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.*;
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
            HitResult result = pick(player, player.entityInteractionRange(), 1.0f);
            if(result == null) return;
            if(result.getType() == HitResult.Type.ENTITY) {
                if(result instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof Player target) {
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

    private static HitResult pick(Entity cameraEntity, double range, float partialTicks) {
        double maxDistanceSq = Mth.square(range);
        Vec3 from = cameraEntity.getEyePosition(partialTicks);
        Vec3 direction = cameraEntity.getViewVector(partialTicks);
        Vec3 to = from.add(direction.x * range, direction.y * range, direction.z * range);
        AABB box = cameraEntity.getBoundingBox().expandTowards(direction.scale(range)).inflate(1.0F, 1.0F, 1.0F);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(cameraEntity, from, to, box, entity -> entity instanceof Player, maxDistanceSq);
        return entityHitResult != null ? filterHitResult(entityHitResult, from, range) : null;
    }

    private static HitResult filterHitResult(HitResult hitResult, Vec3 from, double maxRange) {
        Vec3 hitLocation = hitResult.getLocation();
        if (!hitLocation.closerThan(from, maxRange)) {
            Vec3 location = hitResult.getLocation();
            Direction direction = getApproximateNearest((float) (location.x - from.x), (float) (location.y - from.y), (float) (location.z - from.z));
            return BlockHitResult.miss(location, direction, BlockPos.containing(location));
        } else {
            return hitResult;
        }
    }
    private static Direction getApproximateNearest(float dx, float dy, float dz) {
        Direction result = Direction.NORTH;
        float highestDot = Float.MIN_VALUE;

        for(Direction direction : Direction.values()) {
            float dot = dx * (float) direction.getNormal().getX() + dy * (float) direction.getNormal().getY() + dz * (float) direction.getNormal().getZ();
            if (dot > highestDot) {
                highestDot = dot;
                result = direction;
            }
        }

        return result;
    }
}
