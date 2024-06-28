package me.luligabi.enhancedworkbenches.common.client.screen;

import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import me.luligabi.enhancedworkbenches.common.common.menu.ProjectTableMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ProjectTableScreen extends CraftingBlockScreen<ProjectTableMenu> {


    public ProjectTableScreen(ProjectTableMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        imageHeight = 208;
        inventoryLabelY = 114;
    }

    @Override
    protected ResourceLocation getBackgroundTexture() {
        return EnhancedWorkbenches.id("textures/gui/project_table.png");
    }

}