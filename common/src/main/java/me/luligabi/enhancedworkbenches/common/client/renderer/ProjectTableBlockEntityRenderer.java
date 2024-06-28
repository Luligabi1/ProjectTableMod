package me.luligabi.enhancedworkbenches.common.client.renderer;

import me.luligabi.enhancedworkbenches.common.client.EnhancedWorkbenchesClient;
import me.luligabi.enhancedworkbenches.common.common.block.projecttable.ProjectTableBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ProjectTableBlockEntityRenderer extends CraftingBlockEntityRenderer<ProjectTableBlockEntity> {

    public ProjectTableBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected boolean canRender() {
        return EnhancedWorkbenchesClient.CLIENT_CONFIG.renderInputOnProjectTable;
    }

    @Override
    protected boolean requiresLightmapLighting() {
        return true;
    }
}