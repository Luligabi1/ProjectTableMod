package me.luligabi.enhancedworkbenches.common.client.compat.recipeviewer.emi;

import com.google.common.collect.Lists;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import me.luligabi.enhancedworkbenches.common.common.menu.CraftingStationMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CraftingStationRecipeHandler implements StandardRecipeHandler<CraftingStationMenu> {

    @Override
    public List<Slot> getInputSources(CraftingStationMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(handler.getSlot(i));
        }
        int invStart = 10;
        for (int i = invStart; i < invStart + 36; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(CraftingStationMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public @Nullable Slot getOutputSlot(CraftingStationMenu handler) {
        return handler.slots.get(0);
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == VanillaEmiRecipeCategories.CRAFTING && recipe.supportsRecipeTree();
    }
}