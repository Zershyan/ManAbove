package io.zershyan.standupandpedal.client.registry;

import com.mojang.blaze3d.platform.InputConstants;
import io.zershyan.standupandpedal.ManAbove;
import io.zershyan.standupandpedal.datagen.init.TranslatableLang;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class SUAPKeyBindings {
    public static final List<Lazy<KeyMapping>> keys = new ArrayList<>();
    public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(ManAbove.MODID, "category"));

    public static final Lazy<KeyMapping> KEY_RIDE = registerKeyMapping(TranslatableLang.KEY_RIDE.getKey());
    public static final Lazy<KeyMapping> KEY_POS_1 = registerCtrlKeyMapping(TranslatableLang.KEY_POS_1.getKey(), GLFW.GLFW_KEY_1);
    public static final Lazy<KeyMapping> KEY_POS_2 = registerCtrlKeyMapping(TranslatableLang.KEY_POS_2.getKey(), GLFW.GLFW_KEY_2);
    public static final Lazy<KeyMapping> KEY_POS_3 = registerCtrlKeyMapping(TranslatableLang.KEY_POS_3.getKey(), GLFW.GLFW_KEY_3);
    public static final Lazy<KeyMapping> KEY_POS_4 = registerCtrlKeyMapping(TranslatableLang.KEY_POS_4.getKey(), GLFW.GLFW_KEY_4);

    private static Lazy<KeyMapping> registerKeyMapping(String name) {
        Lazy<KeyMapping> mappingLazy = Lazy.of(() -> new KeyMapping(name, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY));
        keys.add(mappingLazy);
        return mappingLazy;
    }

    private static Lazy<KeyMapping> registerCtrlKeyMapping(String name, int keyValue) {
        Lazy<KeyMapping> mappingLazy = Lazy.of(() -> new KeyMapping(name, KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputConstants.Type.KEYSYM, keyValue, CATEGORY));
        keys.add(mappingLazy);
        return mappingLazy;
    }

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        SUAPKeyBindings.keys.stream().map(Lazy::get).forEach(event::register);
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(SUAPKeyBindings::registerKeyBinding);
    }
}
