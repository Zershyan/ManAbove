package io.zershyan.manabove.mixin.manabove.common;

import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerEntity.class)
public class MixinServerEntity {
    @Shadow
    @Final
    private Entity entity;

    @Inject(
            method = "sendChanges",
            at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V")
    )
    public void sendPassengerChange(CallbackInfo ci) {
        if(this.entity instanceof ServerPlayer player) {
            player.connection.send(new ClientboundSetPassengersPacket(this.entity));
        }
    }
}
