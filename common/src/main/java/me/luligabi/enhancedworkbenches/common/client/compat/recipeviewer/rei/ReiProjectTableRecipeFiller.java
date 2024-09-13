package me.luligabi.enhancedworkbenches.common.client.compat.recipeviewer.rei;

import me.luligabi.enhancedworkbenches.common.client.compat.recipeviewer.ProjectTableRecipeFiller;
import me.luligabi.enhancedworkbenches.common.client.screen.ProjectTableScreen;
import me.shedaniel.rei.impl.client.gui.widget.AutoCraftingEvaluator;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class ReiProjectTableRecipeFiller implements ProjectTableRecipeFiller {

    @Override
    public boolean fillRecipe(RecipeHolder<?> recipeHolder, ProjectTableScreen screen) {
        AutoCraftingEvaluator.evaluateAutoCrafting(
            true,
            Screen.hasShiftDown(),
            DefaultCraftingDisplay.of(recipeHolder),
            () -> List.of(recipeHolder.id())
        );
        return false;
    }
}