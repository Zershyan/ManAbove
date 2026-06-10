package io.zershyan.standupandpedal.network;

import io.zershyan.standupandpedal.ManAbove;
import io.zershyan.standupandpedal.network.data.UUIDData;
import io.zershyan.standupandpedal.network.handler.ServerPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforgespi.language.IModInfo;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = ManAbove.MODID)
public class PayloadRegister {
    @NotNull
    private static final String PROTOCOL_VERSION = ModList.get()
            .getModContainerById(ManAbove.MODID)
            .map(ModContainer::getModInfo)
            .map(IModInfo::getVersion)
            .map(Object::toString)
            .orElse("unknown");

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToServer(UUIDData.TYPE, UUIDData.STREAM_CODEC, ServerPayloadHandler::ridePlayer);
    }
}
