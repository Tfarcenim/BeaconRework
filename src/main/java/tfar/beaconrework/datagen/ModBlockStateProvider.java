package tfar.beaconrework.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.beaconrework.BeaconRework;
import tfar.beaconrework.Init;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BeaconRework.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile file = models().withExistingParent("reworked_beacon",mcLoc("block/beacon"));
        simpleBlock(Init.BLOCK,file);
        simpleBlockItem(Init.BLOCK,file);
    }
}
