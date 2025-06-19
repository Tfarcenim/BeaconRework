package tfar.beaconrework;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BeaconRework.MOD_ID)
public class BeaconRework {
    public static final String MOD_ID = "beaconrework";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public BeaconRework() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        bus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        bus.addGenericListener(Block.class,this::registerBlock);
        bus.addGenericListener(Item.class,this::registerItem);
        bus.addGenericListener(BlockEntityType.class,this::registerBlockEntity);
    }

    void registerBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(Init.BLOCK);
    }


    void registerBlockEntity(RegistryEvent.Register<BlockEntityType<?>> event) {
        event.getRegistry().register(Init.BLOCK_ENTITY);
    }

    void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(Init.ITEM);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }
}
//Construction:
//     The blocks you use to build the beacon base will determine the effects you have access to. All effects can be available at once, provided that the minimum requirements for each effect are met.
//
//Building Blocks:
//Copper - Jump Boost 1, Speed 1
//Iron - Resistance 1, Haste 1
//Emerald - Jump Boost 2, Strength 1
//Gold - Haste 2, Strength 2
//Diamond - Regeneration 2, Speed 2
//Netherite - Strength 3, Haste 3
//Enderite - Regeneration 3, Resistance 2
//
//     To receive the desired effect, at least one layer must be made entirely out of that block. The number of layers used will determine the beacon range. A layer made out of a mixture of blocks will provide no effect, but will still add to the range. Having 2 or more layers of the same block will provide no additional benefit, but will still add to the range.
//
//Layer Range:
//1 - 32 blocks
//2 - 64 blocks
//3 - 96 blocks
//4 - 128 blocks
//5 - 160 blocks
//6 - 192 blocks
//7 - 224 blocks
//---------------------------------------------------------------------------
//Payment:
//     Payment will determine how long the beacon is on for. After the time runs out, the beacon shuts off. There is no slot in the gui for payment, the player will instead right click on the beacon block with their choice of payment to activate it.
//
//Payment Types:
//Copper Ingot - 16 minutes
//Iron Ingot -  32 minutes
//Emerald - 48 minutes
//Gold Ingot - 64 minutes
//Diamond - 80 minutes
//Netherite Ingot - 96 minutes
//Enderite Ingot - 112 minutes
//---------------------------------------------------------------------------
//Max Beacon:
//     A max beacon will provide all effects simultaniously at max range, and is constructed as follows.
//
//Layers (top to bottom):
//Beacon x1
//Enderite block x9
//Netherite block x25
//Diamond block x49
//Gold block x81
//Emerald block x121
//Iron block x169
//Copper block x225
//---------------------------------------------------------------------------
//Other Changes:
//Six Beacon Pyramid - This should no longer be possible.