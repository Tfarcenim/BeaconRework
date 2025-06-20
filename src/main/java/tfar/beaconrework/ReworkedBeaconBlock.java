package tfar.beaconrework;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeaconBeamBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ReworkedBeaconBlock extends BaseEntityBlock implements BeaconBeamBlock {
    public ReworkedBeaconBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.WHITE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof ReworkedBeaconBlockEntity beaconBlockEntity) {
            ItemStack stack = pPlayer.getItemInHand(pHand);

            long timeAdded = Datamaps.PAYMENTS.getOrDefault(stack.getItem(), 0L);
            if (timeAdded > 0) {
                if (!pLevel.isClientSide) {
                    pPlayer.awardStat(Stats.INTERACT_WITH_BEACON);
                    beaconBlockEntity.addTime(timeAdded);
                    stack.shrink(1);
                }
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ReworkedBeaconBlockEntity(pPos, pState);
    }

    @Override
    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, Init.BLOCK_ENTITY, ReworkedBeaconBlockEntity::tick);
    }
}
