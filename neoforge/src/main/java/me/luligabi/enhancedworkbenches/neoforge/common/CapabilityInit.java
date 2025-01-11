package me.luligabi.enhancedworkbenches.neoforge.common;

import me.luligabi.enhancedworkbenches.common.common.block.BlockRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

public class CapabilityInit {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockRegistry.PROJECT_TABLE_BLOCK_ENTITY.get(), (block, side) -> new InvWrapper(block.container));
    }

}