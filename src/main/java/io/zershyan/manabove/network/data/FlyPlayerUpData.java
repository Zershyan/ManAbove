package io.zershyan.manabove.network.data;

import io.netty.buffer.ByteBuf;
import io.zershyan.manabove.ManAbove;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record FlyPlayerUpData(UUID uuid) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<@NotNull FlyPlayerUpData> TYPE = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(ManAbove.MODID, "fly_player_up"));

    public static final StreamCodec<ByteBuf, FlyPlayerUpData> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, FlyPlayerUpData::uuid, FlyPlayerUpData::new
    );

    @Override
    public @NotNull CustomPacketPayload.Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}