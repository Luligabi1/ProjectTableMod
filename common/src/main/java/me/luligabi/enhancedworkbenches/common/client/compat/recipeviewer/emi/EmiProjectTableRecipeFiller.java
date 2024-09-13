package me.luligabi.enhancedworkbenches.common.client.compat.recipeviewer.emi;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.platform.EmiClient;
import dev.emi.emi.recipe.EmiShapedRecipe;
import dev.emi.emi.recipe.EmiShapelessRecipe;
import dev.emi.emi.registry.EmiRecipeFiller;
import me.luligabi.enhancedworkbenches.common.client.compat.recipeviewer.ProjectTableRecipeFiller;
import me.luligabi.enhancedworkbenches.common.client.screen.ProjectTableScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.List;

public class EmiProjectTableRecipeFiller implements ProjectTableRecipeFiller {

    @Override
    public boolean fillRecipe(RecipeHolder<?> recipeHolder, ProjectTableScreen screen) {
        EmiRecipe recipe;
        if(recipeHolder.value() instanceof ShapedRecipe shapedRecipe) {
            recipe = new EmiShapedRecipe(shapedRecipe);
        } else if(recipeHolder.value() instanceof ShapelessRecipe shapelessRecipe) {
            recipe = new EmiShapelessRecipe(shapelessRecipe);
        } else {
            recipe = new EmiCraftingRecipe(
                recipeHolder.value().getIngredients().stream().map(EmiIngredient::of).toList(),
                EmiStack.of(EmiPort.getOutput(recipeHolder.value())),
                EmiPort.getId(recipeHolder.value()),
                recipeHolder.value() instanceof ShapedRecipe
            );
        }

        List<ItemStack> stacks = EmiRecipeFiller.getStacks(
            EnhancedWorkbenchesEmiPlugin.PROJECT_TABLE_HANDLER,
            recipe,
            screen,
            Screen.hasShiftDown() ? Integer.MAX_VALUE : 1
        );
        if(stacks == null) return false;

        if(!EmiClient.onServer) {
            return EmiRecipeFiller.clientFill(
                EnhancedWorkbenchesEmiPlugin.PROJECT_TABLE_HANDLER,
                recipe,
                screen, stacks, EmiCraftContext.Destination.NONE
            );
        } else {
            EmiClient.sendFillRecipe(
                EnhancedWorkbenchesEmiPlugin.PROJECT_TABLE_HANDLER,
                screen,
                screen.getMenu().containerId,
                0,
                stacks,
                recipe
            );
        }
        return true;
    }
}