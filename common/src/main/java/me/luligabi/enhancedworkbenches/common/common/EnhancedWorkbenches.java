package me.luligabi.enhancedworkbenches.common.common;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import me.luligabi.enhancedworkbenches.common.common.block.BlockRegistry;
import me.luligabi.enhancedworkbenches.common.common.menu.MenuTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EnhancedWorkbenches {

    public static void init() {
        BlockRegistry.init();
        MenuTypeRegistry.init();


        if(Platform.isModLoaded("craftingtweaks")) {
            try {
                Class.forName("me.luligabi.enhancedworkbenches.common.common.compat.craftingtweaks.CraftingTweaksCompat").getConstructor().newInstance();
            } catch(Throwable e) {
                e.printStackTrace();
            }
        }
    }


    // FIXME public static final boolean QUICKBENCH = Platform.isModLoaded("quickbench");
    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public static final String MOD_ID = "enhancedworkbenches";
    public static final RegistrySupplier<CreativeModeTab> ITEM_GROUP;

    public static final Registrar<Block> BLOCKS;
    public static final Registrar<Item> ITEMS;
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITIES;
    public static final Registrar<MenuType<?>> MENU_TYPES;
    public static final Registrar<CreativeModeTab> CREATIVE_TABS;
    private static final Supplier<RegistrarManager> MANAGER;

    static {
        MANAGER = Suppliers.memoize(() -> RegistrarManager.get(EnhancedWorkbenches.MOD_ID));
        BLOCKS = EnhancedWorkbenches.MANAGER.get().get(Registries.BLOCK);
        ITEMS = EnhancedWorkbenches.MANAGER.get().get(Registries.ITEM);
        BLOCK_ENTITIES = EnhancedWorkbenches.MANAGER.get().get(Registries.BLOCK_ENTITY_TYPE);
        MENU_TYPES = EnhancedWorkbenches.MANAGER.get().get(Registries.MENU);
        CREATIVE_TABS = EnhancedWorkbenches.MANAGER.get().get(Registries.CREATIVE_MODE_TAB);

        ITEM_GROUP = CREATIVE_TABS.register(
            id("item_group"),
            () -> CreativeTabRegistry.create(
                Component.literal("Enhanced Workbenches"),
                () -> new ItemStack(BlockRegistry.PROJECT_TABLE.get())
            )
        );
    }
}