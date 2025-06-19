package tfar.beaconrework;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ReworkedBeaconBlockEntity extends BlockEntity {
    public ReworkedBeaconBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public ReworkedBeaconBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(Init.BLOCK_ENTITY, pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ReworkedBeaconBlockEntity pBlockEntity) {

    }
}