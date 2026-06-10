package io.zershyan.manabove.datagen.provider;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.common.registry.MASounds;
import io.zershyan.manabove.datagen.init.MALang;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class MASoundProvider extends SoundDefinitionsProvider {
    public MASoundProvider(PackOutput output) {
        super(output, ManAbove.MODID);
    }

    @Override
    public void registerSounds() {
        MASounds.SOUNDS.forEach(sound -> sound.unwrapKey()
                .ifPresent(key -> {
                    Identifier name = key.identifier();
                    String translatableKey = MALang.getKey(sound);
                    add(name, definition().with(sound(name)).subtitle(translatableKey));
                })
        );

        MASounds.SOUNDS_MAP.forEach((sound, number) -> sound.unwrapKey()
                .ifPresent(key -> {
                    Identifier name = key.identifier();
                    String translatableKey = MALang.getKey(sound);
                    SoundDefinition.Sound[] sounds = new SoundDefinition.Sound[number];
                    for (int i = 0; i < number; i++) {
                        sounds[i] = sound(name.withSuffix("_" + i));
                    }
                    add(name, definition().with(sounds).subtitle(translatableKey));
                })
        );
    }
}
