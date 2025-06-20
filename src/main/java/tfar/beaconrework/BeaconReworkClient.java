package tfar.beaconrework;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class BeaconReworkClient {
    static void init(IEventBus bus) {
        bus.addListener(BeaconReworkClient::setup);
    }

    static void setup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(Init.BLOCK_ENTITY,ReworkedBeaconRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(Init.BLOCK, RenderType.cutout());
    }
}
