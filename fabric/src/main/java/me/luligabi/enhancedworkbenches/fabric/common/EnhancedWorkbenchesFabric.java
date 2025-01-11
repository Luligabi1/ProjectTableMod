package me.luligabi.enhancedworkbenches.fabric.common;

import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import net.fabricmc.api.ModInitializer;

public final class EnhancedWorkbenchesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        EnhancedWorkbenches.init();
        ItemStorageInit.init();
    }

}