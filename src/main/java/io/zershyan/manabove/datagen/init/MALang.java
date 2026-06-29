package io.zershyan.manabove.datagen.init;

import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.common.registry.MASounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class MALang {
    public record Lang(String zhCn, String enUs) {}
    public record LangEntity<T>(T key, Lang lang) {
        public LangEntity(T key, String zhCn, String enUs) {
            this(key, new Lang(zhCn, enUs));
        }
    }
    public static final List<LangEntity<?>> langList = new ArrayList<>();
    public final static String translationString = ManAbove.MODID + ".message.";

    public static void init() {
        langList.clear();

        //sound
        langList.add(new LangEntity<>(MASounds.WEIGHTLESSNESS, "有强烈失重感", "Weightlessness"));
        langList.add(new LangEntity<>(MASounds.PRETENTIOUS, "装逼！", "Pretentious"));

        initTranslatableLang();
    }

    private static void initTranslatableLang() {
        for (MATranslatableLang value : MATranslatableLang.values()) {
            MALang.langList.add(value.langEntity);
        }
    }

    public static String getKey(Holder<?> holder){
        ResourceKey<?> resourceKey = holder.unwrapKey().orElseThrow();
        if(resourceKey.registry().equals(Registries.SOUND_EVENT.location())) {
            return getSoundKey(getPath(holder));
        } else throw new IllegalArgumentException("Unknown registry: " + resourceKey.registry());
    }

    public static String getSoundKey(String name){
        return "subtitle." + ManAbove.MODID + ".sound." + name;
    }

    public static String getPath(Holder<?> holder) {
        return holder.unwrapKey()
                .map(ResourceKey::location)
                .map(ResourceLocation::getPath)
                .orElse("");
    }
}
