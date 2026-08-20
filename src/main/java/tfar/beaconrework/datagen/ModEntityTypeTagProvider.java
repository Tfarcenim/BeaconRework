package tfar.beaconrework.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.beaconrework.BeaconRework;
import tfar.beaconrework.BeaconReworkEntityTags;

import javax.annotation.Nullable;

public class ModEntityTypeTagProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagProvider(final DataGenerator generatorIn, @Nullable final ExistingFileHelper existingFileHelper) {
        super(generatorIn,  BeaconRework.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BeaconReworkEntityTags.BLACKLISTED).addOptionalTag(new ResourceLocation("ps1packtweaks","midnight_lurkers"));
    }
}
