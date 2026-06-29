package io.zershyan.manabove.mixin.manabove.common;

import io.zershyan.manabove.api.ManAboveApi;
import io.zershyan.manabove.common.registry.MAAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {
    @Shadow
    private AABB bb;

//    @WrapOperation(
//            method = "startRiding(Lnet/minecraft/world/entity/Entity;)Z",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;canSerialize()Z")
//    )
//    public boolean startRiding(EntityType<?> instance, Operation<Boolean> original, Entity entityToRide) {
//        boolean call = original.call(instance);
//        if(Entity.class.cast(this) instanceof Player)
//            call |= entityToRide instanceof Player;
//        return call;
//    }

    @Inject(
            method = "getBoundingBox",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void redefinedBoundingBox(CallbackInfoReturnable<AABB> cir){
        if(Entity.class.cast(this) instanceof Player player){
            ManAboveApi api = ManAboveApi.get(player);
            if(api.isRidePlayer()) {
                int pos = api.getRidePos();
                AABB returnValue = this.bb;
                returnValue = returnValue.setMinY(0.6f + returnValue.minY);
                float yOffset = switch (pos) {
                    case 1 -> -0.4f;
                    case 2,3 -> -0.9f;
                    case 4 -> -0.2f;
                    default -> 0;
                };
                cir.setReturnValue(returnValue.setMaxY(yOffset + returnValue.maxY));
            }
        }
    }

    @Inject(
            method = "stopRiding",
            at = @At("HEAD")
    )
    public void stopRiding(CallbackInfo ci) {
        if (Entity.class.cast(this) instanceof ServerPlayer player) {
            if(ManAboveApi.get(player).isRidePlayer()) {
                player.setData(MAAttachments.VEHICLE, MAAttachments.emptyUUID);
            }
        }
    }
}
