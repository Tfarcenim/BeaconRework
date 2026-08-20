package tfar.beaconrework;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import tfar.beaconrework.datagen.ModDataGenerator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BeaconRework.MOD_ID)
public class BeaconRework {
    public static final String MOD_ID = "beaconrework";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Block> FULL_COPPER_BLOCKS = BlockTags.create(new ResourceLocation("forge","full_copper_blocks"));

    public BeaconRework() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        bus.addListener(this::setup);
        bus.addListener(ModDataGenerator::gatherData);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,BeaconReworkConfig.SERVER_SPEC);

        // Register ourselves for server and other game events we are interested in
        bus.addGenericListener(Block.class,this::registerBlock);
        bus.addGenericListener(Item.class,this::registerItem);
        bus.addGenericListener(PoiType.class,this::registerPOIType);
        bus.addGenericListener(BlockEntityType.class,this::registerBlockEntity);
        bus.addGenericListener(MobEffect.class,this::registerMobEffect);
        MinecraftForge.EVENT_BUS.addListener(this::spawn);
        MinecraftForge.EVENT_BUS.addListener(this::livingTick);
        if (FMLEnvironment.dist.isClient()) {
            BeaconReworkClient.init(bus);
        }
    }

    public static ResourceLocation id(String pName) {
        return new ResourceLocation(MOD_ID, pName);
    }

    void livingTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.isClientSide || entity.tickCount % 20 != 0 || !(entity instanceof Enemy) ||
                entity.getType().is(BeaconReworkEntityTags.BLACKLISTED)) {
            return;
        }

        boolean beacon = findBeacon((ServerLevel) entity.level,entity.getX(), entity.getY(), entity.getZ());
        if (beacon) {
            entity.hurt(DamageSource.MAGIC, 4.0F);
        }
    }

    void spawn(LivingSpawnEvent.CheckSpawn event) {
        LivingEntity livingEntity = event.getEntityLiving();
        MobSpawnType spawnType = event.getSpawnReason();

        if (spawnType != MobSpawnType.SPAWNER && spawnType != MobSpawnType.NATURAL) {
            return;
        }

        if (!(livingEntity instanceof Enemy)) {
            return;
        }


        boolean beacon = findBeacon((ServerLevel) livingEntity.level,event.getX(),event.getY(),event.getZ());
        if (beacon) {event.setResult(Event.Result.DENY);}

    }

    void registerBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(Init.BLOCK);
    }

    void registerMobEffect(RegistryEvent.Register<MobEffect> event) {
        event.getRegistry().register(Init.HOSTILITY_PURIFICATION);
    }

    void registerBlockEntity(RegistryEvent.Register<BlockEntityType<?>> event) {
        event.getRegistry().register(Init.BLOCK_ENTITY);
    }

    void registerPOIType(RegistryEvent.Register<PoiType> event) {
        event.getRegistry().register(Init.POI_TYPE);
    }

    public static boolean findBeacon(ServerLevel level, double x,double y,double z) {
        PoiManager poimanager = level.getPoiManager();
        Stream<PoiRecord> stream = poimanager.getInRange(type -> type == Init.POI_TYPE, new BlockPos(x,y,z), 224, PoiManager.Occupancy.ANY);
        List<ReworkedBeaconBlockEntity> list = stream.map(PoiRecord::getPos).map(level::getBlockEntity).filter(Objects::nonNull).filter(blockEntity ->
                        blockEntity instanceof ReworkedBeaconBlockEntity).map(ReworkedBeaconBlockEntity.class::cast)
                .filter(blockEntity -> blockEntity.removeHostiles).toList();
        for (ReworkedBeaconBlockEntity entity : list) {
            int range = 32 * entity.levels;
            if (entity.getBlockPos().distToCenterSqr(x,y,z) <= range * range) {
             return true;
            }
        }
        return false;
    }

    void registerItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(Init.ITEM);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }
}