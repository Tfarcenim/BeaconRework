package tfar.beaconrework.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.beaconrework.BeaconRework;

public class BlockModels extends BlockModelProvider {

    public BlockModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BeaconRework.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
