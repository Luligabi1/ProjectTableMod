package me.luligabi.enhancedworkbenches.fabric.client.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.luligabi.enhancedworkbenches.common.client.ClientConfig;
import net.minecraft.client.gui.screens.Screen;

public class ConfigScreenEntrypoint implements ModMenuApi {

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return ClientConfig::createConfigScreen;
    }
}