package io.zershyan.manabove.mixin.manabove.client;

import io.zershyan.manabove.api.ManAboveApi;
import io.zershyan.manabove.client.handler.PlayerRenderHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Shadow
    protected abstract void setPosition(Vec3 pos);

    @Shadow
    public abstract float getPartialTickTime();

    @Inject(
            method = "setup",
            at = @At("TAIL")
    )
    public void position(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
        if(!(entity instanceof Player player)) return;
        ManAboveApi api = ManAboveApi.get(player);
        if(!api.isRidePlayer()) return;
        int pos = api.getRidePos();
        if(!(player.getVehicle() instanceof AbstractClientPlayer target)) return;
        Minecraft instance = Minecraft.getInstance();
        if(!instance.options.getCameraType().isFirstPerson()) return;
        float partialTicks = getPartialTickTime();
        float bodyRot = target.yBodyRotO + (target.yBodyRot - target.yBodyRotO) * partialTicks;
        Vec3 position = switch (pos) {
            case 1 -> player.position().add(0, -1.2f * PlayerRenderHandler.pos1Scale, 0);
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
                yield player.position().add(zOffset, -0.3, xOffset);
            }
            default -> player.position();
        };
        setPosition(position);
    }
}
