package tfar.beaconrework;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.Map;

public class Datamaps {
    public static final Map<Block, MobEffectInstance> EFFECT_MAP = new HashMap<>();

    static {
        Datamaps.EFFECT_MAP.put(Blocks.COPPER_BLOCK,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.EXPOSED_COPPER,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.WEATHERED_COPPER,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.OXIDIZED_COPPER,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));

        Datamaps.EFFECT_MAP.put(Blocks.WAXED_COPPER_BLOCK,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.WAXED_EXPOSED_COPPER,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.WAXED_WEATHERED_COPPER,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.WAXED_OXIDIZED_COPPER,new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,1,true,true));

        Datamaps.EFFECT_MAP.put(Blocks.IRON_BLOCK,new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,200,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.EMERALD_BLOCK,new MobEffectInstance(MobEffects.NIGHT_VISION,400,0,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.GOLD_BLOCK,new MobEffectInstance(MobEffects.REGENERATION,400,2,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.DIAMOND_BLOCK,new MobEffectInstance(MobEffects.DIG_SPEED,400,1,true,true));
        Datamaps.EFFECT_MAP.put(Blocks.NETHERITE_BLOCK,new MobEffectInstance(MobEffects.DAMAGE_BOOST,400,2,true,true));
        if (ModList.get().isLoaded("enderitemod")) {
            Block b = Registry.BLOCK.get(new ResourceLocation("enderitemod","enderite_block"));
            if (b != Blocks.AIR) {
                Datamaps.EFFECT_MAP.put(b,new MobEffectInstance(Init.HOSTILITY_PURIFICATION,200,0,true,true));
            }
        }
    }

    //    //Payment Types:
    //    //Copper Ingot - 16 minutes
    //    //Iron Ingot -  32 minutes
    //    //Emerald - 48 minutes
    //    //Gold Ingot - 64 minutes
    //    //Diamond - 80 minutes
    //    //Netherite Ingot - 96 minutes
    //    //Enderite Ingot - 112 minutes
    public static final Map<Item,Long> PAYMENTS = new HashMap<>();

    static {
        PAYMENTS.put(Items.COPPER_INGOT,16 * 1200L);
        PAYMENTS.put(Items.IRON_INGOT,32 * 1200L);
        PAYMENTS.put(Items.EMERALD,48 * 1200L);
        PAYMENTS.put(Items.GOLD_INGOT,64 * 1200L);
        PAYMENTS.put(Items.DIAMOND,80 * 1200L);
        PAYMENTS.put(Items.NETHERITE_INGOT,96 * 1200L);
        if (ModList.get().isLoaded("enderitemod")) {
            Item b = Registry.ITEM.get(new ResourceLocation("enderitemod","enderite_ingot"));
            if (b != Items.AIR) {
                PAYMENTS.put(b,112 * 1200L);
            }
        }
    }
}
