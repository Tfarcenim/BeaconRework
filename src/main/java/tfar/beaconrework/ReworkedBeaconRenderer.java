package tfar.beaconrework;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ReworkedBeaconRenderer implements BlockEntityRenderer<ReworkedBeaconBlockEntity> {

    public ReworkedBeaconRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    @Override
    public void render(ReworkedBeaconBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        long i = pBlockEntity.getLevel().getGameTime();
        List<BeaconBlockEntity.BeaconBeamSection> list = pBlockEntity.getBeamSections();
        int j = 0;

        for (int k = 0; k < list.size(); ++k) {
            BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
            renderBeaconBeam(pPoseStack, pBufferSource, pPartialTick, i, j, k == list.size() - 1 ? BeaconRenderer.MAX_RENDER_Y : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor());
            j += beaconblockentity$beaconbeamsection.getHeight();
        }

    }

    private static void renderBeaconBeam(PoseStack pPoseStack, MultiBufferSource pBufferSource, float pPartialTick, long pGameTime, int pYOffset, int pHeight, float[] pColors) {
        BeaconRenderer.renderBeaconBeam(pPoseStack, pBufferSource, BeaconRenderer.BEAM_LOCATION, pPartialTick, 1.0F, pGameTime, pYOffset, pHeight, pColors, 0.2F, 0.25F);
    }

    @Override
    public boolean shouldRenderOffScreen(ReworkedBeaconBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(ReworkedBeaconBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan(pCameraPos.multiply(1.0D, 0.0D, 1.0D), (double) this.getViewDistance());
    }
}
