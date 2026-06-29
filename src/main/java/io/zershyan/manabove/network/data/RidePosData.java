package io.zershyan.manabove.network.data;

import io.netty.buffer.ByteBuf;
import io.zershyan.manabove.ManAbove;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record RidePosData(int integer) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<@NotNull RidePosData> TYPE = new CustomPacketPayload.Type<>(ManAbove.id("ride_pos"));

    public static final StreamCodec<ByteBuf, RidePosData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, RidePosData::integer, RidePosData::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}