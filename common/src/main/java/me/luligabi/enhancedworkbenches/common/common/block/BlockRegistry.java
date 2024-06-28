package me.luligabi.enhancedworkbenches.common.common.block;

import dev.architectury.registry.registries.RegistrySupplier;
import me.luligabi.enhancedworkbenches.common.common.EnhancedWorkbenches;
import me.luligabi.enhancedworkbenches.common.common.block.craftingstation.CraftingStationBlock;
import me.luligabi.enhancedworkbenches.common.common.block.craftingstation.CraftingStationBlockEntity;
import me.luligabi.enhancedworkbenches.common.common.block.projecttable.ProjectTableBlock;
import me.luligabi.enhancedworkbenches.common.common.block.projecttable.ProjectTableBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockRegistry {

    public static final RegistrySupplier<Block> PROJECT_TABLE = EnhancedWorkbenches.BLOCKS.register(
        EnhancedWorkbenches.id("project_table"),
        () -> new ProjectTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE))
    );
    public static final RegistrySupplier<BlockEntityType<ProjectTableBlockEntity>> PROJECT_TABLE_BLOCK_ENTITY = EnhancedWorkbenches.BLOCK_ENTITIES.register(
        EnhancedWorkbenches.id("project_table"),
        () -> BlockEntityType.Builder.of(ProjectTableBlockEntity::new, PROJECT_TABLE.get()).build(null)
    );

    public static final RegistrySupplier<Block> CRAFTING_STATION = EnhancedWorkbenches.BLOCKS.register(
        EnhancedWorkbenches.id("crafting_station"),
        () -> new CraftingStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE))
    );
    public static final RegistrySupplier<BlockEntityType<CraftingStationBlockEntity>> CRAFTING_STATION_BLOCK_ENTITY = EnhancedWorkbenches.BLOCK_ENTITIES.register(
        EnhancedWorkbenches.id("crafting_station"),
        () -> BlockEntityType.Builder.of(CraftingStationBlockEntity::new, CRAFTING_STATION.get()).build(null)
    );


    public static void init() {
        ArrayList<RegistrySupplier<Block>> blocks = new ArrayList<>(Arrays.asList(
            PROJECT_TABLE,
            CRAFTING_STATION
        ));
        for(RegistrySupplier<Block> block : blocks) {
            EnhancedWorkbenches.ITEMS.register(
                block.getId(),
                () -> new BlockItem(block.get(), new Item.Properties().arch$tab(EnhancedWorkbenches.ITEM_GROUP))
            );
        }
        // FIXME ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.inventoryWrapper, PROJECT_TABLE_ENTITY_TYPE);
    }

    private BlockRegistry() {
        // NO-OP
    }

}