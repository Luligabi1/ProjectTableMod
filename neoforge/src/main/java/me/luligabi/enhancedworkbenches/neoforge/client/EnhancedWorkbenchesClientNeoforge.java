package me.luligabi.enhancedworkbenches.neoforge.client;

import me.luligabi.enhancedworkbenches.common.client.EnhancedWorkbenchesClient;
import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = EnhancedWorkbenches.MOD_ID, dist = Dist.CLIENT)
public final class EnhancedWorkbenchesClientNeoforge {

    public EnhancedWorkbenchesClientNeoforge() {
        EnhancedWorkbenchesClient.init();
    }
}