package me.luligabi.enhancedworkbenches.common.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.ScreenHandler;

import java.util.ArrayList;
import java.util.List;

public class DelegateCraftingInventory extends CraftingInventory {

    protected ScreenHandler handler;
    protected Inventory input;

    public DelegateCraftingInventory(ScreenHandler handler, Inventory input) {
        super(handler, 3, 3);
        this.handler = handler;
        this.input = input;
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < 9; i++) {
            if(!getStack(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= 9 ? ItemStack.EMPTY : input.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if(slot >= 9) throw new IndexOutOfBoundsException();
        return input.removeStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if(slot >= 9) throw new IndexOutOfBoundsException();
        ItemStack itemStack = input.removeStack(slot, amount);
        if(!itemStack.isEmpty()) {
            handler.onContentChanged(this);
        }
        return itemStack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(slot >= 9) throw new IndexOutOfBoundsException();
        input.setStack(slot, stack);
        handler.onContentChanged(this);
    }

    @Override
    public void markDirty() {
        input.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return input.canPlayerUse(player);
    }

    @Override
    public void clear() {
        for(int i = 0; i < 9; i++) {
            removeStack(i);
        }
    }

    @Override
    public List<ItemStack> getInputStacks() {
        List<ItemStack> stacks = new ArrayList<>(9);
        for(int i = 0; i < 9; i++) {
            stacks.add(getStack(i));
        }
        return stacks;
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for(int i = 0; i < 9; i++) {
            finder.addUnenchantedInput(getStack(i));
        }
    }
}
