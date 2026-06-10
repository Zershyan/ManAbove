package io.zershyan.standupandpedal.datagen;

import io.zershyan.standupandpedal.ManAbove;
import io.zershyan.standupandpedal.datagen.provider.SUAPLangProvider;
import io.zershyan.standupandpedal.datagen.provider.SUAPPackMetadataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ManAbove.MODID)
public class DataGenHandler {
    @SubscribeEvent
    public static void dataGather(GatherDataEvent.Client event) {
        event.createProvider(SUAPPackMetadataProvider::new);
        event.createProvider(SUAPLangProvider::runZhCn);
        event.createProvider(SUAPLangProvider::runEnUs);
    }
}
