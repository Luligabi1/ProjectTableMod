package me.luligabi.enhancedworkbenches.common.common.block.craftingstation;

import me.luligabi.enhancedworkbenches.common.common.block.BlockRegistry;
import me.luligabi.enhancedworkbenches.common.common.block.CraftingBlockEntity;
import me.luligabi.enhancedworkbenches.common.common.menu.CraftingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

public class CraftingStationBlockEntity extends CraftingBlockEntity {

    public CraftingStationBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.CRAFTING_STATION_BLOCK_ENTITY.get(), pos, state);
    }


    @Override
    protected AbstractContainerMenu createScreenHandler(int syncId, Inventory playerInventory) {
        return new CraftingStationMenu(
                syncId,
                playerInventory,
                input,
                ContainerLevelAccess.create(level, worldPosition)
        );
    }


    @Override
    protected Component getContainerName() {
        return Component.translatable("container.enhancedworkbenches.crafting_station");
    }

}