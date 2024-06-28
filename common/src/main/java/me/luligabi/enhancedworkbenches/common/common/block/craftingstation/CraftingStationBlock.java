package me.luligabi.enhancedworkbenches.common.common.block.craftingstation;

import com.mojang.serialization.MapCodec;
import me.luligabi.enhancedworkbenches.common.common.block.CraftingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CraftingStationBlock extends CraftingBlock {

    public CraftingStationBlock(Properties properties) {
        super(properties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CraftingStationBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if(!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof CraftingStationBlockEntity) {
                Containers.dropContents(level, pos, ((CraftingStationBlockEntity) blockEntity).getInput());
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, moved);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext ctx) {
        return VOXEL_SHAPE;
    }

    private static final VoxelShape COUNTER = box(0, 12, 0, 16, 16, 16);
    private static final VoxelShape LEG_1 = box(0, 0, 0, 4, 12, 4);
    private static final VoxelShape LEG_2 = box(0, 0, 12, 4, 12, 16);
    private static final VoxelShape LEG_3 = box(12, 0, 12, 16, 12, 16);
    private static final VoxelShape LEG_4 = box(12, 0, 0, 16, 12, 4);
    private static final VoxelShape VOXEL_SHAPE = Shapes.or(
        CraftingStationBlock.COUNTER,
        CraftingStationBlock.LEG_1,
        CraftingStationBlock.LEG_2,
        CraftingStationBlock.LEG_3,
        CraftingStationBlock.LEG_4
    );

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CraftingStationBlock::new);
    }
}