package io.zershyan.manabove.config;

import io.zershyan.manabove.datagen.init.MAConfigLang;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue TipOnFirstTime;
    public static final ModConfigSpec.BooleanValue EnableSound;
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        BUILDER.push(MAConfigLang.soundConfig.getName()).translation(MAConfigLang.soundConfig.getKey());
        TipOnFirstTime = BUILDER.translation(MAConfigLang.TipOnFirstTime.getKey())
                .define(MAConfigLang.TipOnFirstTime.getName(), true);
        EnableSound = BUILDER.translation(MAConfigLang.EnableSound.getKey())
                .define(MAConfigLang.EnableSound.getName(), false);
        BUILDER.pop();
        
        SPEC = BUILDER.build();
    }
}
