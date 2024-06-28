package me.luligabi.enhancedworkbenches.neoforge.client;

import me.luligabi.enhancedworkbenches.common.client.screen.CraftingStationScreen;
import me.luligabi.enhancedworkbenches.common.client.screen.ProjectTableScreen;
import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import me.luligabi.enhancedworkbenches.common.common.menu.MenuTypeRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

// FIXME https://github.com/architectury/architectury-api/issues/517
@EventBusSubscriber(modid = EnhancedWorkbenches.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MenuScreenEventRegistry {

    @SubscribeEvent
    public static void onPostInit(RegisterMenuScreensEvent event) {
        event.register(MenuTypeRegistry.PROJECT_TABLE.get(), ProjectTableScreen::new);
        event.register(MenuTypeRegistry.CRAFTING_STATION.get(), CraftingStationScreen::new);
    }
}