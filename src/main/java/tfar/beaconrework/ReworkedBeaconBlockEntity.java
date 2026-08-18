package tfar.beaconrework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.LockCode;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.*;

public class ReworkedBeaconBlockEntity extends BlockEntity {

    public ReworkedBeaconBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public ReworkedBeaconBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(Init.BLOCK_ENTITY, pPos, pBlockState);
    }

    Set<BlockPos> occupied = new HashSet<>();

    long remainingTime;
    public boolean removeHostiles;

    private static final int MAX_LEVELS = 7;
    /** A list of effects that beacons can apply. */


    private static final int BLOCKS_CHECK_PER_TICK = 10;
    /** A list of beam segments for this beacon. */
    List<BeaconBlockEntity.BeaconBeamSection> beamSections = Lists.newArrayList();
    private List<BeaconBlockEntity.BeaconBeamSection> checkingBeamSections = Lists.newArrayList();
    /** The number of levels of this beacon's pyramid. */
    int levels;
    private int lastCheckY;



    /** The custom name for this beacon. */
    @Nullable
    private Component name;
    private LockCode lockKey = LockCode.NO_LOCK;

    public void addTime(long time) {
        remainingTime +=time;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ReworkedBeaconBlockEntity pBlockEntity) {
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();
        BlockPos blockpos;
        if (pBlockEntity.lastCheckY < y) {
            blockpos = pPos;
            pBlockEntity.checkingBeamSections = Lists.newArrayList();
            pBlockEntity.lastCheckY = pPos.getY() - 1;
        } else {
            blockpos = new BlockPos(x, pBlockEntity.lastCheckY + 1, z);
        }

        BeaconBlockEntity.BeaconBeamSection beaconBeamSection = pBlockEntity.checkingBeamSections.isEmpty() ? null : pBlockEntity.checkingBeamSections.get(pBlockEntity.checkingBeamSections.size() - 1);
        int l = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

        for(int i1 = 0; i1 < BLOCKS_CHECK_PER_TICK && blockpos.getY() <= l; ++i1) {
            BlockState blockstate = pLevel.getBlockState(blockpos);
            float[] afloat = blockstate.getBeaconColorMultiplier(pLevel, blockpos, pPos);
            if (afloat != null) {
                if (pBlockEntity.checkingBeamSections.size() <= 1) {
                    beaconBeamSection = new BeaconBlockEntity.BeaconBeamSection(afloat);
                    pBlockEntity.checkingBeamSections.add(beaconBeamSection);
                } else if (beaconBeamSection != null) {
                    if (Arrays.equals(afloat, beaconBeamSection.color)) {
                        beaconBeamSection.increaseHeight();
                    } else {
                        beaconBeamSection = new BeaconBlockEntity.BeaconBeamSection(new float[]{(beaconBeamSection.color[0] + afloat[0]) / 2.0F, (beaconBeamSection.color[1] + afloat[1]) / 2.0F, (beaconBeamSection.color[2] + afloat[2]) / 2.0F});
                        pBlockEntity.checkingBeamSections.add(beaconBeamSection);
                    }
                }
            } else {
                if (beaconBeamSection == null || blockstate.getLightBlock(pLevel, blockpos) >= 15 && !blockstate.is(Blocks.BEDROCK)) {
                    pBlockEntity.checkingBeamSections.clear();
                    pBlockEntity.lastCheckY = l;
                    break;
                }

                beaconBeamSection.increaseHeight();
            }

            blockpos = blockpos.above();
            ++pBlockEntity.lastCheckY;
        }

        int oldLevels = pBlockEntity.levels;
        boolean wasActive = oldLevels > 0;

        if (wasActive) {
            if (pBlockEntity.remainingTime > 0) {
                pBlockEntity.remainingTime--;
                pBlockEntity.setChanged();
            }
        }

        if (pLevel.getGameTime() % 80L == 0L) {
            if (!pBlockEntity.beamSections.isEmpty()) {
                pBlockEntity.levels = pBlockEntity.updateBase(pLevel, x, y, z);

                for (Block block : pBlockEntity.currentBlocks) {
                    pBlockEntity.currentEffects.add(Datamaps.EFFECT_MAP.get(block));
                    if(block.getRegistryName().getNamespace().equals("enderitemod")) {
                        pBlockEntity.removeHostiles = true;
                    }
                }

            }

            if (pBlockEntity.levels > 0 && !pBlockEntity.beamSections.isEmpty()) {
                pBlockEntity.applyEffects(pLevel, pPos, pBlockEntity.levels);
                playSound(pLevel, pPos, SoundEvents.BEACON_AMBIENT);
            }
        }

        if (pBlockEntity.lastCheckY >= l) {
            pBlockEntity.lastCheckY = pLevel.getMinBuildHeight() - 1;
            pBlockEntity.beamSections = pBlockEntity.checkingBeamSections;
            if (!pLevel.isClientSide) {
                boolean hasPyramid = pBlockEntity.levels > 0;
                if (!wasActive && hasPyramid) {
                    playSound(pLevel, pPos, SoundEvents.BEACON_ACTIVATE);

                    for(ServerPlayer serverplayer : pLevel.getEntitiesOfClass(ServerPlayer.class, new AABB(x, y, z, x, y - 4, z).inflate(10.0D, 5.0D, 10.0D))) {
                        CriteriaTriggers.CONSTRUCT_BEACON.trigger(serverplayer, pBlockEntity.levels);
                    }
                } else if (wasActive && !hasPyramid) {
                    playSound(pLevel, pPos, SoundEvents.BEACON_DEACTIVATE);
                }
            }
        }

    }

    transient List<MobEffectInstance> currentEffects = new ArrayList<>();
    Set<Block> currentBlocks = new HashSet<>();

    private int updateBase(Level pLevel, int pX, int pY, int pZ) {
        int level = 0;


        currentEffects.clear();
        currentBlocks.clear();
        removeHostiles = false;
        occupied.clear();

        if (remainingTime<= 0) return 0;


        for(int j = 1; j <= MAX_LEVELS; level = j++) {
            int y = pY - j;
            if (y < pLevel.getMinBuildHeight()) {
                break;
            }

            boolean flag = true;
            boolean completeLayer = true;
            Block block = null;

            for(int x = pX - j; x <= pX + j && flag; ++x) {
                for(int z = pZ - j; z <= pZ + j; ++z) {
                    BlockPos pos = new BlockPos(x,y,z);
                    BlockState blockState = pLevel.getBlockState(pos);

                    if (!blockState.is(BlockTags.BEACON_BASE_BLOCKS)) {
                        flag = completeLayer = false;
                        break;
                    } else {
                        occupied.add(pos);
                        if (block == null) {
                            block = blockState.getBlock();
                        } else if (block != blockState.getBlock() && !(
                                block.defaultBlockState().is(BeaconRework.FULL_COPPER_BLOCKS) && blockState.is(BeaconRework.FULL_COPPER_BLOCKS))) {
                            completeLayer = false;
                        }
                    }
                }
            }

            if (completeLayer && block != null) {
                currentBlocks.add(block);
            }

            if (!flag) {
                break;
            }
        }

        return level;
    }



    /**
     * Marks this {@code BlockEntity} as no longer valid (removed from the level).
     */
    public void setRemoved() {
        playSound(this.level, this.worldPosition, SoundEvents.BEACON_DEACTIVATE);
        super.setRemoved();
    }

    private void applyEffects(Level pLevel, BlockPos pPos, int pLevels) {
        if (!pLevel.isClientSide) {
            double radius = pLevels * 32;
            AABB aabb = new AABB(pPos).inflate(radius).expandTowards(0.0D, pLevel.getHeight(), 0.0D);
            List<Player> list = pLevel.getEntitiesOfClass(Player.class, aabb);
            for(Player player : list) {
                for (MobEffectInstance instance : currentEffects) {
                    MobEffectInstance copy = new MobEffectInstance(instance.getEffect());
                    copy.update(instance);
                    player.addEffect(copy);
                }
            }
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public static void playSound(Level pLevel, BlockPos pPos, SoundEvent pSound) {
        pLevel.playSound(null, pPos, pSound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public List<BeaconBlockEntity.BeaconBeamSection> getBeamSections() {
        return this.levels == 0 ? ImmutableList.of() : this.beamSections;
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
     */
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(pTag.getString("CustomName"));
        }

        remainingTime = pTag.getLong("RemainingTime");

        this.lockKey = LockCode.fromTag(pTag);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Levels", this.levels);
        if (this.name != null) {
            pTag.putString("CustomName", Component.Serializer.toJson(this.name));
        }

        pTag.putLong("RemainingTime",remainingTime);

        this.lockKey.addToTag(pTag);
    }

    /**
     * Sets the custom name for this beacon.
     */
    public void setCustomName(@Nullable Component pName) {
        this.name = pName;
    }

    public Component getDisplayName() {
        return this.name != null ? this.name : new TranslatableComponent("container.beacon");
    }

    public void setLevel(Level pLevel) {
        super.setLevel(pLevel);
        this.lastCheckY = pLevel.getMinBuildHeight() - 1;
    }

}