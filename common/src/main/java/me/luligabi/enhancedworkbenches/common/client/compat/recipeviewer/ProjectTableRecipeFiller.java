package me.luligabi.enhancedworkbenches.common.client.compat.recipeviewer;

import me.luligabi.enhancedworkbenches.common.client.screen.ProjectTableScreen;
import net.minecraft.world.item.crafting.RecipeHolder;

public interface ProjectTableRecipeFiller {

    boolean fillRecipe(RecipeHolder<?> recipeHolder, ProjectTableScreen screen);
}