package io.zershyan.manabove.datagen.provider;

import io.zershyan.manabove.datagen.init.MATranslatableLang;
import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;

public class MAPackMetadataProvider extends PackMetadataGenerator {
    public MAPackMetadataProvider(PackOutput output) {
        super(output);
        add(PackMetadataSection.forPackType(PackType.SERVER_DATA), new PackMetadataSection(
                Component.translatable(MATranslatableLang.RESOURCES.getKey()),
                DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange()
        ));
    }


}
