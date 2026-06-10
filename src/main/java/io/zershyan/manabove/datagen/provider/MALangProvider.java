package io.zershyan.manabove.datagen.provider;

import com.mojang.logging.LogUtils;
import io.zershyan.manabove.ManAbove;
import io.zershyan.manabove.datagen.init.MALang;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MALangProvider extends LanguageProvider {
    private final String locale;
    private static final String enUs = "en_us";
    private static final String zhCn = "zh_cn";
    static {
        MALang.init();
    }

    public MALangProvider(PackOutput output, String locale) {
        super(output, ManAbove.MODID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        switch (locale){
            case enUs -> MALang.langList.forEach(langEntity -> addTranslation(
                    langEntity.key(), langEntity.lang().enUs()));
            case zhCn -> MALang.langList.forEach(langEntity -> addTranslation(
                    langEntity.key(), langEntity.lang().zhCn()));
        }
    }

    private <T> void addTranslation(T o, String string) {
        switch (o) {
            case Item object -> add(object, string);
            case Block object -> add(object, string);
            case String object -> add(object, string);
            case ItemStack object -> add(object.getItem(), string);
            case MobEffect object -> add(object, string);
            case EntityType<?> object -> add(object, string);
            case TagKey<?> object -> add(object, string);
//            case Holder<?> object -> add(SUAPLang.getKey(object), string);
            default -> {
                LogUtils.getLogger().error("Unknown object type: {}", o.getClass());
                add(o.toString(), string);
            }
        }
    }

    public static MALangProvider runZhCn(PackOutput output) {
        return new MALangProvider(output, zhCn);
    }

    public static MALangProvider runEnUs(PackOutput output) {
        return new MALangProvider(output, enUs);
    }
}
