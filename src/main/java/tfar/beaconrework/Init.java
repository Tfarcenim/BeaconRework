package tfar.beaconrework;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class Init {

    public static final Block BLOCK = new ReworkedBeaconBlock(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.DIAMOND)
            .strength(3.0F).lightLevel((p_152688_) -> 15).noOcclusion().isRedstoneConductor((a, b, c) -> false)).setRegistryName("reworked_beacon");

    public static final BlockItem ITEM = (BlockItem) new BlockItem(BLOCK,new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS))
            .setRegistryName("reworked_beacon");
    public static final BlockEntityType<ReworkedBeaconBlockEntity> BLOCK_ENTITY = (BlockEntityType<ReworkedBeaconBlockEntity>)
            BlockEntityType.Builder.of(ReworkedBeaconBlockEntity::new,BLOCK).build(null)
            .setRegistryName("reworked_beacon");

}
