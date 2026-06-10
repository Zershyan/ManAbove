package io.zershyan.manabove.common;

import com.mojang.serialization.Codec;
import io.zershyan.manabove.ManAbove;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.UUID;
import java.util.function.Supplier;

public class MAAttachments {
    private static final DeferredRegister<AttachmentType<?>> REGISTRY = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ManAbove.MODID);
    public static final UUID emptyUUID = new UUID(0, 0);
    public static final Supplier<AttachmentType<UUID>> VEHICLE;
    public static final Supplier<AttachmentType<Integer>> RIDE_POS;

    static {
        VEHICLE = REGISTRY.register("vehicle", () -> AttachmentType
                .builder(() -> emptyUUID)
                .serialize(UUIDUtil.CODEC.fieldOf("vehicle"))
                .sync(UUIDUtil.STREAM_CODEC)
                .copyOnDeath()
                .build());
        RIDE_POS = REGISTRY.register("ride_pos", () -> AttachmentType
                .builder(() -> 0)
                .serialize(Codec.INT.fieldOf("ride_pos"))
                .sync(ByteBufCodecs.INT)
                .copyOnDeath()
                .build());
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
