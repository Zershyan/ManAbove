package io.zershyan.manabove.network;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.network.data.FlyPlayerUpData;
import io.zershyan.manabove.network.data.RidePlayerData;
import io.zershyan.manabove.network.data.RidePosData;
import io.zershyan.manabove.network.data.SoundData;
import io.zershyan.manabove.network.handler.ClientPayloadHandler;
import io.zershyan.manabove.network.handler.ServerPayloadHandler;
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
        //server
        registrar.playToServer(RidePlayerData.TYPE, RidePlayerData.STREAM_CODEC, ServerPayloadHandler::ridePlayer);
        registrar.playToServer(FlyPlayerUpData.TYPE, FlyPlayerUpData.STREAM_CODEC, ServerPayloadHandler::flyPlayerUp);
        registrar.playToServer(RidePosData.TYPE, RidePosData.STREAM_CODEC, ServerPayloadHandler::ridePos);
        //client
        registrar.playToClient(SoundData.TYPE, SoundData.STREAM_CODEC, ClientPayloadHandler::playSound);
    }
}
