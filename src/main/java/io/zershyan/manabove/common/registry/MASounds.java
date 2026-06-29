package io.zershyan.manabove.common.registry;

import io.zershyan.manabove.ManAbove;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MASounds {
    private static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, ManAbove.MODID);

    public static final List<Holder<SoundEvent>> SOUNDS = new ArrayList<>();
    public static final HashMap<Holder<SoundEvent>, Integer> SOUNDS_MAP = new HashMap<>();

    public static final Holder<SoundEvent> PRETENTIOUS = registerSoundEvent("pretentious", 2);
    public static final Holder<SoundEvent> WEIGHTLESSNESS = registerSoundEvent("weightlessness");

    private static Holder<SoundEvent> registerSoundEvent(String name) {
        Holder<SoundEvent> soundEvent = REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(ManAbove.id(name)));
        SOUNDS.add(soundEvent);
        return soundEvent;
    }

    private static Holder<SoundEvent> registerSoundEvent(String name, int num) {
        Holder<SoundEvent> soundEvent = REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(ManAbove.id(name)));
        SOUNDS_MAP.put(soundEvent, num);
        return soundEvent;
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
