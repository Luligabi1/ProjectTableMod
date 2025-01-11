package me.luligabi.enhancedworkbenches.common.common.compat.craftingtweaks;

import net.blay09.mods.craftingtweaks.api.CraftingTweaksAPI;

public class CraftingTweaksCompat {

    public CraftingTweaksCompat() {
        CraftingTweaksAPI.registerCraftingGridProvider(new CraftingStationCraftingGridProvider());
        CraftingTweaksAPI.registerCraftingGridProvider(new ProjectTableCraftingGridProvider());
    }

}