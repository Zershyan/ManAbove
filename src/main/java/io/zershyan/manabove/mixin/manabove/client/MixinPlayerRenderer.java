package io.zershyan.manabove.mixin.manabove.client;

import io.zershyan.manabove.api.ManAboveApi;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer {

    @ModifyVariable(
            method = "setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V",
            at = @At("HEAD"),
            index = 4,
            argsOnly = true
    )
    public float setupRotations(float yBodyRot, AbstractClientPlayer player) {
        if(ManAboveApi.get(player).isRidePlayer()) {
            return 90;
        } else return yBodyRot;
    }
}
