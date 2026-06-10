package io.zershyan.manabove.client.registry;

import io.zershyan.manabove.ManAbove;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Avatar;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.renderstate.AvatarRenderStateModifier;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import org.jspecify.annotations.NonNull;

public class MARenderStateModifiers {
    public static final ContextKey<Avatar> AVATAR = new ContextKey<>(Identifier.fromNamespaceAndPath(ManAbove.MODID, "avatar"));

    public static void registerModifier(RegisterRenderStateModifiersEvent event) {
        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
            @Override
            public <T extends Avatar & ClientAvatarEntity> void accept(@NonNull T avatar, @NonNull AvatarRenderState renderState) {
                renderState.setRenderData(AVATAR, avatar);
            }
        });
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(MARenderStateModifiers::registerModifier);
    }
}
