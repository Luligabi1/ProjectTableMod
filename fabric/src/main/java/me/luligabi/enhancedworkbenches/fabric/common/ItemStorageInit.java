package me.luligabi.enhancedworkbenches.fabric.common;

import me.luligabi.enhancedworkbenches.common.common.block.BlockRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;

public class ItemStorageInit {


    public static void init() {
        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
            return InventoryStorage.of(blockEntity.container, null);
        }, BlockRegistry.PROJECT_TABLE_BLOCK_ENTITY.get());
    }

    private ItemStorageInit() {
        // NO-OP
    }

}