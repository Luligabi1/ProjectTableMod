package me.luligabi.enhancedworkbenches.neoforge.common;

import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(EnhancedWorkbenches.MOD_ID)
public final class EnhancedWorkbenchesNeoForge {

    public EnhancedWorkbenchesNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        EnhancedWorkbenches.init();
        modEventBus.addListener(CapabilityInit::registerCapabilities);
    }

}