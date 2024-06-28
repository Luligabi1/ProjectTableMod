package me.luligabi.enhancedworkbenches.common.common.block;

import com.google.common.base.Preconditions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class CraftingBlockEntity extends BlockEntity implements MenuProvider {

    protected CraftingBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        input = new SimpleContainer(3*3);
        input.addListener(i -> {
            setChanged();
            sync();
        });
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return createScreenHandler(syncId, playerInventory);
    }

    @Override
    public Component getDisplayName() {
        return getContainerName();
    }


    @Override
    protected final void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        toTag(tag, provider);
    }

    @Override
    public final void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        if(tag.contains("#c")) {
            fromClientTag(tag, provider);
            if(tag.getBoolean("#c")) {
                remesh();
            }
        } else {
            fromTag(tag, provider);
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public final CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag nbt = super.getUpdateTag(provider);
        toClientTag(nbt, provider);
        nbt.putBoolean("#c", shouldClientRemesh); // mark client tag
        shouldClientRemesh = false;
        return nbt;
    }


    public void fromTag(CompoundTag tag, HolderLookup.Provider provider) {
        loadAllItems(tag.getCompound("Input"), input.getItems(), provider);
    }

    public void toTag(CompoundTag tag, HolderLookup.Provider provider) {
        CompoundTag inputCompound = saveAllItems(new CompoundTag(), input.getItems(), provider);
        tag.put("Input", inputCompound);
    }

    public void toClientTag(CompoundTag tag, HolderLookup.Provider provider) {
        toTag(tag, provider);
    }

    public void fromClientTag(CompoundTag tag, HolderLookup.Provider provider) {
        fromTag(tag, provider);
    }

    public void sync(boolean shouldRemesh) {
        Preconditions.checkNotNull(level);
        if(!(level instanceof ServerLevel)) {
            throw new IllegalStateException("Cannot call sync() on the logical client! Did you check level.isClientSide first?");
        }
        shouldClientRemesh = shouldRemesh | shouldClientRemesh;
        ((ServerLevel) level).getChunkSource().blockChanged(worldPosition);
    }


    public void sync() {
        sync(true);
    }

    public final void remesh() {
        Preconditions.checkNotNull(level);
        if(!(level instanceof ClientLevel)) {
            throw new IllegalStateException("Cannot call remesh() on the server!");
        }
        level.sendBlockUpdated(worldPosition, null, null, 0);
    }


    protected abstract AbstractContainerMenu createScreenHandler(int syncId, Inventory playerInventory);

    protected abstract Component getContainerName();

    public SimpleContainer getInput() {
        return input;
    }


    protected void loadAllItems(CompoundTag compoundTag, NonNullList<ItemStack> nonNullList, HolderLookup.Provider provider) {
        ListTag listTag = compoundTag.getList("Items", 10);

        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag2 = listTag.getCompound(i);
            int j = compoundTag2.getByte("Slot") & 255;
            if (j < nonNullList.size()) {
                if(compoundTag2.getString("id").equalsIgnoreCase("minecraft:air")) {
                    nonNullList.set(j, ItemStack.EMPTY);
                } else {
                    nonNullList.set(j, ItemStack.parse(provider, compoundTag2).orElse(ItemStack.EMPTY));
                }
            }
        }

    }

    protected CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> stacks, HolderLookup.Provider provider) {
        ListTag stackList = new ListTag();

        for(int i = 0; i < stacks.size(); ++i) {
            ItemStack stack = stacks.get(i);
            CompoundTag stackTag = new CompoundTag();
            stackTag.putByte("Slot", (byte) i);
            if(!stack.isEmpty()) {
                stackList.add(stack.save(provider, stackTag));
            } else {
                stackTag.putString("id", "minecraft:air");
                stackTag.putInt("count", 1);
                stackList.add(stackTag);
            }
        }

        tag.put("Items", stackList);

        return tag;
    }


    protected SimpleContainer input;
    private boolean shouldClientRemesh = true;
}