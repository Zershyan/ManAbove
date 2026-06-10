package io.zershyan.manabove.network.data;

import io.netty.buffer.ByteBuf;
import io.zershyan.manabove.ManAbove;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record UUIDData(UUID uuid) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<@NotNull UUIDData> RIDE_PLAYER = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(ManAbove.MODID, "ride_player"));

    public static final StreamCodec<ByteBuf, UUIDData> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, UUIDData::uuid, UUIDData::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends @NotNull CustomPacketPayload> type() {
        return RIDE_PLAYER;
    }
}
