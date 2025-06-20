package tfar.beaconrework.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.beaconrework.BeaconRework;

import javax.annotation.Nullable;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(final DataGenerator generatorIn, ModBlockTagProvider blockTagProvider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, BeaconRework.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }
}
