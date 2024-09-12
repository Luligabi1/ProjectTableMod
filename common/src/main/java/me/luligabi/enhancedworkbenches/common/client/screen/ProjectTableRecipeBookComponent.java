package me.luligabi.enhancedworkbenches.common.client.screen;

import me.luligabi.enhancedworkbenches.common.mixin.RecipeBookComponentInvoker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public class ProjectTableRecipeBookComponent extends RecipeBookComponent {


    @Override
    public void slotClicked(@Nullable Slot slot) {
        if(slot != null && slot.index < this.menu.getSize()) {
            ghostRecipe.clear();
        }
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        return false;
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        return false;
    }

    @Override
    public boolean keyReleased(int i, int j, int k) {
        return false;
    }

    @Override
    public boolean charTyped(char c, int i) {
        return false;
    }


    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        ghostRecipe.render(guiGraphics, this.minecraft, i, j, true, f);
    }

    @Override
    public void renderTooltip(GuiGraphics guiGraphics, int i, int j, int k, int l) {
        ((RecipeBookComponentInvoker) this).invokeRenderGhostRecipeTooltip(guiGraphics, i, j, k, l);
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}