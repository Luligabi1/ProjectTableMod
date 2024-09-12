package me.luligabi.enhancedworkbenches.common.client.compat.emi;

import com.google.common.collect.Lists;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import me.luligabi.enhancedworkbenches.common.common.menu.ProjectTableMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("SequencedCollectionMethodCanBeUsed")
public class ProjectTableRecipeHandler  implements StandardRecipeHandler<ProjectTableMenu> {

    @Override
    public List<Slot> getCraftingSlots(ProjectTableMenu menu) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(menu.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getInputSources(ProjectTableMenu menu) {
        List<Slot> list = getCraftingSlots(menu);

        for (int i = 10; i < 64; i++) {
            list.add(menu.getSlot(i));
        }
        return list;
    }


    @Override
    public @Nullable Slot getOutputSlot(ProjectTableMenu handler) {
        return handler.slots.get(0);
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe.getCategory() == VanillaEmiRecipeCategories.CRAFTING && recipe.supportsRecipeTree();
    }
}