package io.zershyan.manabove.network.data;

import io.netty.buffer.ByteBuf;
import io.zershyan.manabove.ManAbove;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record RidePlayerData(UUID uuid) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<@NotNull RidePlayerData> TYPE = new CustomPacketPayload.Type<>(ManAbove.id("ride_player"));

    public static final StreamCodec<ByteBuf, RidePlayerData> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, RidePlayerData::uuid, RidePlayerData::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}
