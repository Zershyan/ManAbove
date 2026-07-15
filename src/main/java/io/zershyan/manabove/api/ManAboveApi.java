package io.zershyan.manabove.api;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.common.registry.MAAttachments;
import io.zershyan.manabove.common.registry.MASounds;
import io.zershyan.manabove.config.ClientConfig;
import io.zershyan.manabove.datagen.init.MATranslatableLang;
import io.zershyan.manabove.network.data.SoundData;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManAboveApi {
    public static final String MODID = ManAbove.MODID;
    @NotNull
    private final Entity entity;

    ManAboveApi(@NotNull Entity entity) {
        this.entity = entity;
    }

    public static ManAboveApi get(@NotNull Entity entity) {
        return new ManAboveApi(entity);
    }

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

    public void playSound(Player target, SoundEvent soundEvent) {
        if(target instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new SoundData(soundEvent));
        }
    }

    public void playSound(SoundEvent soundEvent) {
        if(!entity.level().isClientSide()) return;
        if(ClientConfig.tipOnFirstTime.getAsBoolean() && entity instanceof Player player) {
            ClientConfig.tipOnFirstTime.set(false);
            player.sendSystemMessage(Component.translatable(
                    MATranslatableLang.SOUND_ENABLED_TIPS.getKey(),
                    Component.translatable(MATranslatableLang.ENABLE.getKey()).withStyle(style -> style.withClickEvent(
                            new ClickEvent.RunCommand("/manabove sound enable")
                    )),
                    Component.translatable(MATranslatableLang.DISABLE.getKey()).withStyle(style -> style.withClickEvent(
                            new ClickEvent.RunCommand("/manabove sound disable")
                    ))
            ));
        }
        if(!ClientConfig.enableSound.getAsBoolean()) soundEvent = SoundEvents.EXPERIENCE_ORB_PICKUP;
        entity.level().playPlayerSound(soundEvent, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
}
