package me.luligabi.enhancedworkbenches.common.common.compat.craftingtweaks;

import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import me.luligabi.enhancedworkbenches.common.common.menu.ProjectTableMenu;
import net.blay09.mods.craftingtweaks.api.ButtonAlignment;
import net.blay09.mods.craftingtweaks.api.CraftingGridBuilder;
import net.blay09.mods.craftingtweaks.api.CraftingGridProvider;
import net.blay09.mods.craftingtweaks.api.CraftingTweaksAPI;
import net.blay09.mods.craftingtweaks.api.impl.DefaultGridClearHandler;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ProjectTableCraftingGridProvider implements CraftingGridProvider {

    public ProjectTableCraftingGridProvider() {
        CraftingTweaksAPI.registerCraftingGridProvider(this);
    }

    @Override
    public String getModId() {
        return EnhancedWorkbenches.MOD_ID;
    }

    @Override
    public boolean handles(AbstractContainerMenu menu) {
        return menu instanceof ProjectTableMenu;
    }

    @Override
    public void buildCraftingGrids(CraftingGridBuilder builder, AbstractContainerMenu menu) {
        if(menu instanceof ProjectTableMenu) {
            builder.addGrid(1, 9)
                .setButtonAlignment(ButtonAlignment.LEFT)
                .clearHandler((craftingGrid, player, menu2, forced) -> {
                    // Put items from the crafting grid to the inventory of the table and only then to inventory.
                    for(int i = 1; i < 10; i++) {
                        menu.quickMoveStack(player, i);
                    }
                    new DefaultGridClearHandler().clearGrid(craftingGrid, player, menu2, forced);
                });
        }
    }
}