package me.luligabi.enhancedworkbenches.fabric.client;

import me.luligabi.enhancedworkbenches.common.client.EnhancedWorkbenchesClient;
import me.luligabi.enhancedworkbenches.common.client.screen.CraftingStationScreen;
import me.luligabi.enhancedworkbenches.common.client.screen.ProjectTableScreen;
import me.luligabi.enhancedworkbenches.common.common.menu.MenuTypeRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public final class EnhancedWorkbenchesClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EnhancedWorkbenchesClient.init();

        // FIXME https://github.com/architectury/architectury-api/issues/517
        MenuScreens.register(MenuTypeRegistry.PROJECT_TABLE.get(), ProjectTableScreen::new);
        MenuScreens.register(MenuTypeRegistry.CRAFTING_STATION.get(), CraftingStationScreen::new);
    }
}