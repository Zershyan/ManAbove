package io.zershyan.manabove.datagen.init;

import io.zershyan.manabove.ManAbove;

import java.util.List;

public enum MAConfigLang {
    //server

    //client
    tipOnFirstTime(
            "tipOnFirstTime",
            "弹出提示",
            "Show Tip"
    ),
    enableSound(
            "enableSound",
            "启用音效",
            "Enable Sound"
    ),

    //common

    //type
    soundConfig(
            "soundConfig",
            "音效配置",
            "Sound Config"
    )

    ;

    private final String name;
    private final MALang.Lang lang;

    MAConfigLang(String name, String zhCn, String enUs) {
        this.name = name;
        this.lang = new MALang.Lang(zhCn, enUs);
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return ManAbove.MODID + ".configuration." + name;
    }

    public static void initLang(List<MALang.LangEntity<?>> langList) {
        for (MAConfigLang value : MAConfigLang.values()) {
            langList.add(new MALang.LangEntity<>(value.getKey(), value.lang));
        }
    }
}
