package io.zershyan.standupandpedal;

import com.mojang.logging.LogUtils;
import io.zershyan.standupandpedal.client.registry.SUAPKeyBindings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;


public class ManAbove {
    public static final String MODID = "standupandpedal";
    private static final Logger LOGGER = LogUtils.getLogger();

    @Mod(ManAbove.MODID)
    public static class Common {
        public Common(IEventBus modEventBus, ModContainer modContainer) {

        }
    }

    @Mod(value = ManAbove.MODID, dist = Dist.CLIENT)
    public static class Client {
        public Client(IEventBus modEventBus, ModContainer modContainer) {
            SUAPKeyBindings.register(modEventBus);
        }
    }

}
