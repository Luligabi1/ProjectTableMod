package me.luligabi.enhancedworkbenches.common.client;

import dev.architectury.platform.Platform;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.IntSlider;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import net.minecraft.client.gui.screens.Screen;

public class ClientConfig {


    public static Screen createConfigScreen(Screen parent) {
        return HANDLER.generateGui().generateScreen(parent);
    }

    public static final ConfigClassHandler<ClientConfig> HANDLER = ConfigClassHandler.createBuilder(ClientConfig.class)
        .id(EnhancedWorkbenches.id("client_config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(Platform.getConfigFolder().resolve("enhancedworkbenches-client.json"))
            .build())
        .build();

    @AutoGen(category = "rendering", group = "rendering")
    @Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
    @SerialEntry
    public boolean renderInput = true;
    @AutoGen(category = "rendering", group = "rendering")
    @IntSlider(min = 1, max = 48, step = 1)
    @SerialEntry
    public int renderInputDistance = 3;
    @AutoGen(category = "rendering", group = "rendering")
    @Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
    @SerialEntry
    public boolean renderInputRequireFancy = true;

    @AutoGen(category = "rendering", group = "project_table")
    @Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
    @SerialEntry
    public boolean renderInputOnProjectTable = true;
    @AutoGen(category = "rendering", group = "crafting_station")
    @Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
    @SerialEntry
    public boolean renderInputOnCraftingStation = true;
}