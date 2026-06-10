package io.zershyan.standupandpedal.datagen.init;

import io.zershyan.standupandpedal.ManAbove;

public enum TranslatableLang {
    RESOURCES(new SUAPLang.LangEntity<>(
            ManAbove.MODID + ".resources",
            "Resources for " + ManAbove.class.getSimpleName(),
            "Resources for " + ManAbove.class.getSimpleName()
    )),
    KEY_CATEGORY(new SUAPLang.LangEntity<>(
            SUAPLang.translationString + ".key.category",
            "站起来蹬",
            "Stand Up And Pedal"
    )),
    KEY_RIDE(new SUAPLang.LangEntity<>(
            SUAPLang.translationString + ".key.ride",
            "骑乘目标",
            "Ride target"
    )),
    KEY_POS_1(new SUAPLang.LangEntity<>(
            SUAPLang.translationString + ".key.pos_1",
            "默认姿势",
            "Default Pose"
    )),
    KEY_POS_2(new SUAPLang.LangEntity<>(
            SUAPLang.translationString + ".key.pos_2",
            "坐到左肩",
            "Sit to Left Shoulder"
    )),
    KEY_POS_3(new SUAPLang.LangEntity<>(
            SUAPLang.translationString + ".key.pos_3",
            "坐到右肩",
            "Sit to Right Shoulder"
    )),
    KEY_POS_4(new SUAPLang.LangEntity<>(
            SUAPLang.translationString + ".key.pos_4",
            "站在头上",
            "Stand on Top of Head"
    )),
    ;

    public final SUAPLang.LangEntity<String> langEntity;

    TranslatableLang(SUAPLang.LangEntity<String> lang) {
        this.langEntity = lang;
    }

    public String getKey() {
        return langEntity.key();
    }
}
