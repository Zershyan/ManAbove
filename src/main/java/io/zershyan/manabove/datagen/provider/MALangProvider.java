package io.zershyan.standupandpedal.datagen.provider;

import com.mojang.logging.LogUtils;
import io.zershyan.standupandpedal.ManAbove;
import io.zershyan.standupandpedal.datagen.init.SUAPLang;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class SUAPLangProvider extends LanguageProvider {
    private final String locale;
    private static final String enUs = "en_us";
    private static final String zhCn = "zh_cn";
    static {
        SUAPLang.init();
    }

    public SUAPLangProvider(PackOutput output, String locale) {
        super(output, ManAbove.MODID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        switch (locale){
            case enUs -> SUAPLang.langList.forEach(langEntity -> addTranslation(
                    langEntity.key(), langEntity.lang().enUs()));
            case zhCn -> SUAPLang.langList.forEach(langEntity -> addTranslation(
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

    public static SUAPLangProvider runZhCn(PackOutput output) {
        return new SUAPLangProvider(output, zhCn);
    }

    public static SUAPLangProvider runEnUs(PackOutput output) {
        return new SUAPLangProvider(output, enUs);
    }
}
