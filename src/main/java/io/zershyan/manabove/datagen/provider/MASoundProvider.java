package io.zershyan.manabove.datagen.provider;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.common.registry.MASounds;
import io.zershyan.manabove.datagen.init.MALang;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class MASoundProvider extends SoundDefinitionsProvider {
    public MASoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ManAbove.MODID, helper);
    }

    @Override
    public void registerSounds() {
        MASounds.SOUNDS.forEach(sound -> sound.unwrapKey()
                .ifPresent(key -> {
                    ResourceLocation name = key.location();
                    String translatableKey = MALang.getKey(sound);
                    add(name, definition().with(sound(name)).subtitle(translatableKey));
                })
        );

        MASounds.SOUNDS_MAP.forEach((sound, number) -> sound.unwrapKey()
                .ifPresent(key -> {
                    ResourceLocation name = key.location();
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
