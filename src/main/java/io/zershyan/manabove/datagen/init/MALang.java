package io.zershyan.standupandpedal.datagen.init;

import io.zershyan.standupandpedal.ManAbove;

import java.util.ArrayList;
import java.util.List;

public class SUAPLang {
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

        initTranslatableLang();
        SUAPConfigLang.initLang(langList);
    }

    private static void initTranslatableLang() {
        for (TranslatableLang value : TranslatableLang.values()) {
            SUAPLang.langList.add(value.langEntity);
        }
    }

//    public static String getAttributeKey(String name){
//        return "attributes." + StandUpAndPedal.MODID + "." + name;
//    }
//
//    public static String getKey(Holder<?> holder){
//        ResourceKey<?> resourceKey = holder.unwrapKey().orElseThrow();
//        if (resourceKey.registry().equals(Registries.ATTRIBUTE.identifier())) {
//            return getAttributeKey(getPath(holder));
//        } else if(resourceKey.registry().equals(Registries.SOUND_EVENT.identifier())) {
//            return getSoundKey(getPath(holder));
//        } else throw new IllegalArgumentException("Unknown registry: " + resourceKey.registry());
//    }
//
//    public static String getDamageTypeKey(ResourceKey<DamageType> resourceKey) {
//        return "death.attack." + StandUpAndPedal.MODID + "." + resourceKey.identifier().getPath();
//    }
//
//    public static String getDamageTypeKeyPlayer(ResourceKey<DamageType> resourceKey) {
//        return getDamageTypeKey(resourceKey) + ".player";
//    }
//
//    public static String getSoundKey(String name){
//        return "subtitle." + DreamtCanvas.MODID + ".sound." + name;
//    }
//
//    public static String getPath(Holder<?> holder) {
//        return holder.unwrapKey()
//                .map(ResourceKey::identifier)
//                .map(Identifier::getPath)
//                .orElse("");
//    }
}
