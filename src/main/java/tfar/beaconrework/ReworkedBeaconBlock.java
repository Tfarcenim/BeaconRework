package tfar.beaconrework;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ReworkedBeaconBlock extends BaseEntityBlock implements BeaconBeamBlock {
    public ReworkedBeaconBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.WHITE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ReworkedBeaconBlockEntity(pPos,pState);
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, Init.BLOCK_ENTITY, ReworkedBeaconBlockEntity::tick);
    }
}
