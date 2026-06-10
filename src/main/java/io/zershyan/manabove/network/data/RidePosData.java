package io.zershyan.manabove.network.data;

import io.netty.buffer.ByteBuf;
import io.zershyan.manabove.ManAbove;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record IntData(int integer) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<@NotNull IntData> RIDE_POS = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(ManAbove.MODID, "ride_pos"));

    public static final StreamCodec<ByteBuf, IntData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, IntData::integer, IntData::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends @NotNull CustomPacketPayload> type() {
        return RIDE_POS;
    }
}