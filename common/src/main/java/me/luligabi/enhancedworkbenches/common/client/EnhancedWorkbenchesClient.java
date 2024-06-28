package me.luligabi.enhancedworkbenches.common.client;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import me.luligabi.enhancedworkbenches.common.client.renderer.CraftingStationBlockEntityRenderer;
import me.luligabi.enhancedworkbenches.common.client.renderer.ProjectTableBlockEntityRenderer;
import me.luligabi.enhancedworkbenches.common.client.screen.CraftingStationScreen;
import me.luligabi.enhancedworkbenches.common.client.screen.ProjectTableScreen;
import me.luligabi.enhancedworkbenches.common.common.block.BlockRegistry;
import me.luligabi.enhancedworkbenches.common.common.menu.MenuTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnhancedWorkbenchesClient {

    public static void init() {
        ClientLifecycleEvent.CLIENT_SETUP.register(instance -> {
            BlockEntityRendererRegistry.register(
                BlockRegistry.PROJECT_TABLE_BLOCK_ENTITY.get(),
                ProjectTableBlockEntityRenderer::new
            );
            BlockEntityRendererRegistry.register(
                BlockRegistry.CRAFTING_STATION_BLOCK_ENTITY.get(),
                CraftingStationBlockEntityRenderer::new
            );
        });
    }

    public static final Logger LOGGER;
    public static final ClientConfig CLIENT_CONFIG;

    static {
        LOGGER = LoggerFactory.getLogger("Enhanced Workbenches");

        ClientConfig.HANDLER.load();
        CLIENT_CONFIG = ClientConfig.HANDLER.instance();
    }
}