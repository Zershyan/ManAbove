package io.zershyan.manabove.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.api.ManAboveApi;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import org.joml.Quaternionf;

@EventBusSubscriber(modid = ManAbove.MODID, value = Dist.CLIENT)
public class PlayerRenderHandler {
    public static final float pos1Scale = 0.5f;
    public static final float pos2and3Scale = 0.25f;
    public static final float pos2and3ShoulderOffset = 0.3f;
    public static final float pos4Offset = 0.25f;

    @SubscribeEvent
    public static void playerRenderPre(RenderPlayerEvent event) {
        Player player = event.getEntity();
        if(ManAboveApi.get(player).isRidePlayer()) {
            int pos = ManAboveApi.get(player).getRidePos();
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            if(!(player.getVehicle() instanceof AbstractClientPlayer target)) return;
            float partialTick = event.getPartialTick();
            switch (pos) {
                case 1 -> {
                    float yRot = target.yHeadRotO + (target.yHeadRot - target.yHeadRotO) * partialTick;
                    float xRot = target.getViewXRot(partialTick);
                    float angleY = (float) Math.toRadians(-yRot + 90);
                    float angleZ = (float) Math.toRadians(xRot);
                    Quaternionf headRotation = new Quaternionf().rotationXYZ(0.0f, angleY, angleZ);
                    poseStack.translate(0, 0.2f, 0);
                    poseStack.mulPose(headRotation);
                    poseStack.scale(pos1Scale, pos1Scale, pos1Scale);
                    poseStack.translate(pos2and3ShoulderOffset, 0.35f, 0);
                }
                case 2,3 -> {
                    float yRot = target.yBodyRotO + (target.yBodyRot - target.yBodyRotO) * partialTick;
                    float angleY = (float) Math.toRadians(-yRot + 90);
                    Quaternionf headRotation = new Quaternionf().rotationXYZ(0.0f, angleY, 0.0f);
                    poseStack.mulPose(headRotation);
                    float offset = 0.3f;
                    if(pos == 3) offset *= -1;
                    poseStack.translate(0.05f, 0.07f, offset);
                    poseStack.scale(pos2and3Scale, pos2and3Scale, pos2and3Scale);
                }
                case 4 -> {
                    float yRot = target.yBodyRotO + (target.yBodyRot - target.yBodyRotO) * partialTick;
                    float angleY = (float) Math.toRadians(-yRot + 90);
                    Quaternionf headRotation = new Quaternionf().rotationXYZ(0.0f, angleY, 0.0f);
                    poseStack.mulPose(headRotation);
                    poseStack.translate(pos4Offset, -0.4f, 0);
                }
            }
            player.setYBodyRot(90);
        }
    }

    @SubscribeEvent
    public static void playerRenderPost(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if(ManAboveApi.get(player).isRidePlayer()) {
            event.getPoseStack().popPose();
        }
    }
}
