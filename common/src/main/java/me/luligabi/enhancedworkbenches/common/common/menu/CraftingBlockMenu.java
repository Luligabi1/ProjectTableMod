package me.luligabi.enhancedworkbenches.common.common.menu;

import me.luligabi.enhancedworkbenches.common.common.util.DelegateCraftingInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class CraftingBlockMenu<I extends CraftingInput> extends RecipeBookMenu<I, Recipe<I>> {


    protected CraftingBlockMenu(@Nullable MenuType<?> type, int syncId, Inventory playerInventory, Container input, ContainerLevelAccess access) {
        super(type, syncId);
        this.input = new DelegateCraftingInventory(this, input);
        this.access = access;
        this.player = playerInventory.player;
        this.blockPos = access.evaluate((world, pos) -> pos).orElse(BlockPos.ZERO);

        checkContainerSize(input, 9);
        input.startOpen(player);
    }

    @SuppressWarnings("ConstantConditions")
    protected Optional<RecipeHolder<CraftingRecipe>> updateResult(AbstractContainerMenu menu, Level level, Player player, DelegateCraftingInventory input, ResultContainer output) {
        if(level.isClientSide()) return Optional.empty();
        ServerPlayer serverPlayerEntity = (ServerPlayer) player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<RecipeHolder<CraftingRecipe>> recipeOptional = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input.toCraftingInput(), level);
        if(recipeOptional.isPresent()) {
            RecipeHolder<CraftingRecipe> recipe = recipeOptional.get();
            if(output.setRecipeUsed(level, serverPlayerEntity, recipe)) {
                ItemStack itemStack2 = recipe.value().assemble(input.toCraftingInput(), level.registryAccess());
                if(itemStack2.isItemEnabled(level.enabledFeatures())) {
                    itemStack = itemStack2;
                }
            }
        }
        output.setItem(0, itemStack);
        menu.setRemoteSlot(0, itemStack);
        serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));
        return recipeOptional;
    }

    @Override
    public void slotsChanged(Container container) {
        access.execute((world, pos) -> {
            updateResult(this, world, player, input, result);
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, getBlock());
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
        return slot.container != result && super.canTakeItemForPickAll(itemStack, slot);
    }

    @Override
    public boolean recipeMatches(RecipeHolder<Recipe<I>> recipeHolder) {
        return ((CraftingRecipe)recipeHolder.value()).matches(input.toPositionedCraftingInput(), player.level());
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents content) {
        input.fillStackedContents(content);
    }

    @Override
    public void clearCraftingContent() {
        input.clearContent();
        result.clearContent();
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 3;
    }

    @Override
    public int getSize() {
        return 10;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public boolean shouldMoveToInventory(int i) {
        return i != getResultSlotIndex();
    }

    protected abstract Block getBlock();

    protected final BlockPos blockPos;
    protected final Player player;
    protected final ContainerLevelAccess access;
    protected final DelegateCraftingInventory input;
    protected final ResultContainer result = new ResultContainer();

    protected class CraftingSlot extends Slot {

        public CraftingSlot(int index, int x, int y) {
            super(input, index, x, y);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            CraftingBlockMenu.this.slotsChanged(container);
        }
    }

    protected class CraftingOutputSlot extends ResultSlot {

        public CraftingOutputSlot(Player player, int index, int x, int y) {
            super(player, CraftingBlockMenu.this.input, CraftingBlockMenu.this.result, index, x, y);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            CraftingBlockMenu.this.slotsChanged(container);
        }

        @Override
        public ItemStack remove(int amount) {
            setChanged();
            return super.remove(amount);
        }

        @Override
        protected void checkTakeAchievements(ItemStack stack) {
            super.checkTakeAchievements(stack);
            setChanged();
        }

        @Override
        protected void onSwapCraft(int amount) {
            super.onSwapCraft(amount);
            setChanged();
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            super.onTake(player, stack);
            setChanged();
        }

    }
}