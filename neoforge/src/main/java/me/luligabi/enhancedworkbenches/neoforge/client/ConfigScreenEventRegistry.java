package me.luligabi.enhancedworkbenches.neoforge.client;

import me.luligabi.enhancedworkbenches.common.client.ClientConfig;
import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = EnhancedWorkbenches.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ConfigScreenEventRegistry {

    @SubscribeEvent
    public static void onPostInit(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(
            IConfigScreenFactory.class,
            () -> (client, parent) -> ClientConfig.createConfigScreen(parent)
        );
    }
}