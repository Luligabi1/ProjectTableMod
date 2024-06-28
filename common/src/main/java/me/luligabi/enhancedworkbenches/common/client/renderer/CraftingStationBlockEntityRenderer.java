package me.luligabi.enhancedworkbenches.common.client.renderer;

import me.luligabi.enhancedworkbenches.common.client.EnhancedWorkbenchesClient;
import me.luligabi.enhancedworkbenches.common.common.block.craftingstation.CraftingStationBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CraftingStationBlockEntityRenderer extends CraftingBlockEntityRenderer<CraftingStationBlockEntity> {

    public CraftingStationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected boolean canRender() {
        return EnhancedWorkbenchesClient.CLIENT_CONFIG.renderInputOnCraftingStation;
    }

    @Override
    protected boolean requiresLightmapLighting() {
        return false;
    }
}