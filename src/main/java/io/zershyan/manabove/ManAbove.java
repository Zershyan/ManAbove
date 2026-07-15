package io.zershyan.manabove;

import com.mojang.logging.LogUtils;
import io.zershyan.manabove.client.registry.MAKeyBindings;
import io.zershyan.manabove.client.registry.MARenderStateModifiers;
import io.zershyan.manabove.common.registry.MAAttachments;
import io.zershyan.manabove.common.registry.MACommands;
import io.zershyan.manabove.common.registry.MAConfigs;
import io.zershyan.manabove.common.registry.MASounds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;


public class ManAbove {
    public static final String MODID = "manabove";
    private static final Logger LOGGER = LogUtils.getLogger();

    @Mod(ManAbove.MODID)
    public static class Common {
        public Common(IEventBus modEventBus, ModContainer modContainer) {
            MASounds.register(modEventBus);
            MAAttachments.register(modEventBus);
            MACommands.register(NeoForge.EVENT_BUS);
        }
    }

    @Mod(value = ManAbove.MODID, dist = Dist.CLIENT)
    public static class Client {
        public Client(IEventBus modEventBus, ModContainer modContainer) {
            MAKeyBindings.register(modEventBus);
            MAConfigs.registerClient(modContainer);
            MARenderStateModifiers.register(modEventBus);
        }
    }

}
