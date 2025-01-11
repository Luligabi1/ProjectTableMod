package me.luligabi.enhancedworkbenches.common.common.block.projecttable;

import me.luligabi.enhancedworkbenches.common.common.block.BlockRegistry;
import me.luligabi.enhancedworkbenches.common.common.block.CraftingBlockEntity;
import me.luligabi.enhancedworkbenches.common.common.menu.ProjectTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

public class ProjectTableBlockEntity extends CraftingBlockEntity {

    private final SimpleContainer container = new SimpleContainer(2*9) {
        @Override
        public void setChanged() {
            super.setChanged();
            ProjectTableBlockEntity.this.setChanged();
            sync();
        }
    };

    // FIXME public final InventoryStorage inventoryWrapper = InventoryStorage.of(container, null);

    public ProjectTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.PROJECT_TABLE_BLOCK_ENTITY.get(), pos, state);
    }


    @Override
    protected AbstractContainerMenu createScreenHandler(int syncId, Inventory playerInventory) {
        return new ProjectTableMenu(
            syncId,
            playerInventory,
            input,
            container,
            ContainerLevelAccess.create(level, worldPosition)
        );
    }

    @Override
    protected Component getContainerName() {
        return Component.translatable("block.enhancedworkbenches.project_table");
    }

    @Override
    public void fromTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.fromTag(tag, provider);
        loadAllItems(tag.getCompound("Items"), container.getItems(), provider);
    }

    @Override
    public void toTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.toTag(tag, provider);

        CompoundTag itemsCompound = saveAllItems(new CompoundTag(), container.getItems(), provider);
        tag.put("Items", itemsCompound);
    }

    public SimpleContainer getContainer() {
        return container;
    }
}