package io.zershyan.manabove.network.data;

import io.netty.buffer.ByteBuf;
import io.zershyan.manabove.ManAbove;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

public record SoundData(SoundEvent soundEvent) implements CustomPacketPayload {
    public static final Type<@NotNull SoundData> TYPE = new Type<>(ManAbove.id("sound_data"));

    public static final StreamCodec<ByteBuf, SoundData> STREAM_CODEC = StreamCodec.composite(
            SoundEvent.DIRECT_STREAM_CODEC, SoundData::soundEvent, SoundData::new);

    @Override
    public @NotNull CustomPacketPayload.Type<? extends @NotNull CustomPacketPayload> type() {
        return TYPE;
    }
}
