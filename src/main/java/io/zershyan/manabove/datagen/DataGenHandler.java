package io.zershyan.manabove.datagen;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.datagen.provider.MALangProvider;
import io.zershyan.manabove.datagen.provider.MAPackMetadataProvider;
import io.zershyan.manabove.datagen.provider.MASoundProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ManAbove.MODID)
public class DataGenHandler {
    @SubscribeEvent
    public static void dataGather(GatherDataEvent event) {
        ExistingFileHelper helper = event.getExistingFileHelper();
        PackOutput packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new MASoundProvider(packOutput, helper));
        event.createProvider(MAPackMetadataProvider::new);
        event.createProvider(MALangProvider::runZhCn);
        event.createProvider(MALangProvider::runEnUs);
    }
}
