package io.zershyan.manabove.api;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.common.registry.MAAttachments;
import io.zershyan.manabove.common.registry.MASounds;
import io.zershyan.manabove.network.data.SoundData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class ManAboveApi {
    public static final String MODID = ManAbove.MODID;
    @NotNull
    private final Entity entity;

    ManAboveApi(@NonNull Entity entity) {
        this.entity = entity;
    }

    public static ManAboveApi get(@NotNull Entity entity) {
        return new ManAboveApi(entity);
    }

//    public boolean isServerPlayer() {
//        return entity instanceof ServerPlayer;
//    }

    public boolean isRide(@Nullable Entity vehicle) {
        if(vehicle == null) return false;
        return vehicle.getUUID().equals(entity.getData(MAAttachments.VEHICLE));
    }

    public boolean isRidePlayer() {
        Entity vehicle = entity.getVehicle();
        return isRide(vehicle) && vehicle instanceof Player;
    }

    public int getRidePos() {
        if(!isRidePlayer()) return -1;
        return entity.getData(MAAttachments.RIDE_POS);
    }

    public void setRidePos(int ridePos) {
        entity.setData(MAAttachments.RIDE_POS, ridePos);
    }

    public void startRide(Player target) {
        if(entity.level().isClientSide()) return;
        if (entity.startRiding(target, false, true)) {
            entity.setData(MAAttachments.VEHICLE, target.getUUID());
        }
    }

    public void playWeightlessnessSound(Player target) {
        playSound(target, MASounds.WEIGHTLESSNESS.value());
    }

    public void playPretentiousSound(Player target) {
        playSound(target, MASounds.PRETENTIOUS.value());
    }

    public void flyPlayerUp(Player target) {
        Level level = target.level();
        if(level.isClientSide()) return;
        if(!ManAboveApi.get(target).isRide(entity)) return;
        target.stopRiding();
        entity.ejectPassengers();
        Vec3 viewVector = entity.getViewVector(0);
        target.setDeltaMovement(viewVector.scale(15));
        target.hurtMarked = true;
        target.setOnGround(false);
        target.fallDistance = 0.0f;
        if(entity instanceof Player player) {
            player.swing(InteractionHand.MAIN_HAND, true);
        }
        playWeightlessnessSound(target);
    }

    private void playSound(Player target, SoundEvent soundEvent) {
        if(target instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new SoundData(soundEvent));
        }
        entity.level().playSound(target, entity.blockPosition(), soundEvent, SoundSource.PLAYERS);
    }
}
