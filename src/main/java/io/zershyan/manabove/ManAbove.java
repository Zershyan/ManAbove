package io.zershyan.manabove;

import com.mojang.logging.LogUtils;
import io.zershyan.manabove.client.registry.MAKeyBindings;
import io.zershyan.manabove.common.registry.MAAttachments;
import io.zershyan.manabove.common.registry.MACommands;
import io.zershyan.manabove.common.registry.MASounds;
import io.zershyan.manabove.config.MAConfigs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ManAbove.MODID)
public class ManAbove {
    public static final String MODID = "manabove";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ManAbove(IEventBus modEventBus, Dist dist, ModContainer modContainer) {
        MASounds.register(modEventBus);
        MAConfigs.register(modContainer);
        MAAttachments.register(modEventBus);
        MACommands.register(NeoForge.EVENT_BUS);
        if (dist.isClient()) {
            MAKeyBindings.register(modEventBus);
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
