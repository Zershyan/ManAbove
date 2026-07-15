package io.zershyan.manabove.config;

import io.zershyan.manabove.datagen.init.MAConfigLang;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue tipOnFirstTime;
    public static final ModConfigSpec.BooleanValue enableSound;

    static {
        BUILDER.push(MAConfigLang.soundConfig.getName()).translation(MAConfigLang.soundConfig.getKey());
        tipOnFirstTime = BUILDER.translation(MAConfigLang.tipOnFirstTime.getKey())
                .define(MAConfigLang.tipOnFirstTime.getName(), true);
        enableSound = BUILDER.translation(MAConfigLang.enableSound.getKey())
                .define(MAConfigLang.enableSound.getName(), false);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
