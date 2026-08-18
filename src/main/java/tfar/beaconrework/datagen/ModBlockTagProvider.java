package tfar.beaconrework.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.beaconrework.BeaconRework;

import javax.annotation.Nullable;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(final DataGenerator generatorIn, @Nullable final ExistingFileHelper existingFileHelper) {
        super(generatorIn, BeaconRework.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        tag(BeaconRework.FULL_COPPER_BLOCKS).add(Blocks.COPPER_BLOCK,Blocks.EXPOSED_COPPER,Blocks.WEATHERED_COPPER,Blocks.OXIDIZED_COPPER
        ,Blocks.WAXED_COPPER_BLOCK,Blocks.WAXED_EXPOSED_COPPER,Blocks.WAXED_WEATHERED_COPPER,Blocks.WAXED_OXIDIZED_COPPER);

        tag(BlockTags.BEACON_BASE_BLOCKS).addTag(BeaconRework.FULL_COPPER_BLOCKS);
    }
}
