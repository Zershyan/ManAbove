package io.zershyan.standupandpedal.datagen.provider;

import io.zershyan.standupandpedal.datagen.init.TranslatableLang;
import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class SUAPPackMetadataProvider extends PackMetadataGenerator {
    public SUAPPackMetadataProvider(PackOutput output) {
        super(output);
        add(PackMetadataSection.forPackType(PackType.SERVER_DATA), new PackMetadataSection(
                Component.translatable(TranslatableLang.RESOURCES.getKey()),
                DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange()
        ));
    }


}
