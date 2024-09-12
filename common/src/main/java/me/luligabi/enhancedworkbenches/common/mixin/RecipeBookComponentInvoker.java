package me.luligabi.enhancedworkbenches.common.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeBookComponent.class)
public interface RecipeBookComponentInvoker {

    @Invoker("renderGhostRecipeTooltip")
    void invokeRenderGhostRecipeTooltip(GuiGraphics guiGraphics, int i, int j, int k, int l);
}