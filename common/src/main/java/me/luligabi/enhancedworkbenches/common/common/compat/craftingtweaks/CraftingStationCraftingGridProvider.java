package me.luligabi.enhancedworkbenches.common.common.compat.craftingtweaks;

import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import me.luligabi.enhancedworkbenches.common.common.menu.CraftingStationMenu;
import net.blay09.mods.craftingtweaks.api.ButtonAlignment;
import net.blay09.mods.craftingtweaks.api.CraftingGridBuilder;
import net.blay09.mods.craftingtweaks.api.CraftingGridProvider;
import net.blay09.mods.craftingtweaks.api.CraftingTweaksAPI;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CraftingStationCraftingGridProvider implements CraftingGridProvider {


    @Override
    public String getModId() {
        return EnhancedWorkbenches.MOD_ID;
    }

    @Override
    public boolean handles(AbstractContainerMenu menu) {
        return menu instanceof CraftingStationMenu;
    }

    @Override
    public void buildCraftingGrids(CraftingGridBuilder builder, AbstractContainerMenu menu) {
        if(menu instanceof CraftingStationMenu) {
            builder.addGrid(1, 9)
                .setButtonAlignment(ButtonAlignment.LEFT);
        }
    }
}