package io.zershyan.manabove.mixin.manabove.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.zershyan.manabove.api.ManAboveApi;
import io.zershyan.manabove.client.handler.PlayerRenderHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Shadow
    private @Nullable Entity entity;

    @Shadow
    public abstract float getCameraEntityPartialTicks(DeltaTracker deltaTracker);

    @ModifyReturnValue(
            method = "position",
            at = @At("RETURN")
    )
    public Vec3 position(Vec3 original) {
        if(!(entity instanceof Player player)) return original;
        ManAboveApi api = ManAboveApi.get(player);
        if(!api.isRidePlayer()) return original;
        int pos = api.getRidePos();
        if(!(player.getVehicle() instanceof AbstractClientPlayer target)) return original;
        Minecraft instance = Minecraft.getInstance();
        if(!instance.options.getCameraType().isFirstPerson()) return original;
        DeltaTracker deltaTracker = instance.getDeltaTracker();
        float partialTicks = getCameraEntityPartialTicks(deltaTracker);
        float bodyRot = target.yBodyRotO + (target.yBodyRot - target.yBodyRotO) * partialTicks;
        return switch (pos) {
            case 1 -> original.add(0, -1.2f * PlayerRenderHandler.pos1Scale, 0);
            case 2,3 -> {
                float offset = PlayerRenderHandler.pos2and3ShoulderOffset;
                if(pos == 3) offset *= -1;
                double zOffset = Math.cos(Math.toRadians(bodyRot)) * offset;
                double xOffset = Math.sin(Math.toRadians(bodyRot)) * offset;
                float yOffset = 0.15f + player.getEyeHeight() * PlayerRenderHandler.pos2and3Scale;
                yield player.getPosition(partialTicks).add(zOffset, yOffset, xOffset);
            }
            case 4 -> {
                bodyRot -= 90;
                double zOffset = Math.cos(Math.toRadians(bodyRot)) * PlayerRenderHandler.pos4Offset;
                double xOffset = Math.sin(Math.toRadians(bodyRot)) * PlayerRenderHandler.pos4Offset;
                yield original.add(zOffset, -0.3, xOffset);
            }
            default -> original;
        };
    }
}
