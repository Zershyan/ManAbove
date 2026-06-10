package io.zershyan.manabove.datagen.init;

import io.zershyan.manabove.ManAbove;

public enum MATranslatableLang {
    RESOURCES(new MALang.LangEntity<>(
            ManAbove.MODID + ".resources",
            "Resources for " + ManAbove.class.getSimpleName(),
            "Resources for " + ManAbove.class.getSimpleName()
    )),
    KEY_CATEGORY(new MALang.LangEntity<>(
            MALang.translationString + ".key.category",
            "高人一等",
            "Man Above"
    )),
    KEY_RIDE(new MALang.LangEntity<>(
            MALang.translationString + ".key.ride",
            "高人一等/飞起来",
            "Ride Player/Fly Up"
    )),
    KEY_POS_1(new MALang.LangEntity<>(
            MALang.translationString + ".key.pos_1",
            "默认姿势",
            "Default Pose"
    )),
    KEY_POS_2(new MALang.LangEntity<>(
            MALang.translationString + ".key.pos_2",
            "坐到左肩",
            "Sit to Left Shoulder"
    )),
    KEY_POS_3(new MALang.LangEntity<>(
            MALang.translationString + ".key.pos_3",
            "坐到右肩",
            "Sit to Right Shoulder"
    )),
    KEY_POS_4(new MALang.LangEntity<>(
            MALang.translationString + ".key.pos_4",
            "骑大马",
            "Sit to Shoulder"
    )),
    ;

    public final MALang.LangEntity<String> langEntity;

    MATranslatableLang(MALang.LangEntity<String> lang) {
        this.langEntity = lang;
    }

    public String getKey() {
        return langEntity.key();
    }
}
