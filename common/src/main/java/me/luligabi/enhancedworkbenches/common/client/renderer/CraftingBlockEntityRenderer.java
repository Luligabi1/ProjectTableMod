package me.luligabi.enhancedworkbenches.common.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import me.luligabi.enhancedworkbenches.common.client.EnhancedWorkbenchesClient;
import me.luligabi.enhancedworkbenches.common.common.block.CraftingBlock;
import me.luligabi.enhancedworkbenches.common.common.block.CraftingBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;

import java.util.HashMap;

public abstract class CraftingBlockEntityRenderer<T extends CraftingBlockEntity> implements BlockEntityRenderer<T> {

    @SuppressWarnings("unused")
    public CraftingBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(T entity, float tickDelta, PoseStack ps, MultiBufferSource mbs, int light, int overlay) {
        if(!shouldRender()) return;
        Container container = entity.getInput();
        if(container.isEmpty() || !(entity.getLevel().getBlockState(entity.getBlockPos()).getBlock() instanceof CraftingBlock)) return;

        Direction direction = entity.getLevel().getBlockState(entity.getBlockPos()).getValue(BlockStateProperties.HORIZONTAL_FACING);

        int light2 = requiresLightmapLighting() ? LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above()) : light;
        for(int i = 0; i < 9; i++) {
            renderItem(entity, container, i, direction, ps, mbs, light2);
        }
    }

    private void renderItem(T entity, Container container, int index, Direction direction, PoseStack ps, MultiBufferSource mbs, int light) {
        if(container.getItem(index).isEmpty()) return;
        ps.pushPose();
        Tuple<Double, Double> pos = getDirectionPositionMap(direction).get(index);
        ItemStack stack = container.getItem(index);
        ItemTransforms transformation = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0).getTransforms();
        boolean isBlock = transformation.fixed.equals(new ItemTransform(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0.5F, 0.5F, 0.5F))); // this is stupid but should cover almost all cases (and doesn't require any mixins, yay!)

        ps.translate(pos.getA(), isBlock ? 1.05D : 1.001D, pos.getB());
        ps.scale(0.1F, 0.1F, 0.1F);

        ps.mulPose(Axis.XP.rotationDegrees(90F));
        ps.mulPose(Axis.YP.rotationDegrees(180F));
        ps.mulPose(Axis.ZP.rotationDegrees(getItemAngle(direction)));
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, isBlock ? ItemDisplayContext.NONE : ItemDisplayContext.GUI, light, OverlayTexture.NO_OVERLAY, ps, mbs, entity.getLevel(), (int) entity.getBlockPos().asLong());
        ps.popPose();
    }

    private HashMap<Integer, Tuple<Double, Double>> getDirectionPositionMap(Direction direction) {
        return switch(direction) {
            case NORTH -> NORTH_POSITIONS;
            case SOUTH -> SOUTH_POSITIONS;
            case WEST -> WEST_POSITIONS;
            case EAST -> EAST_POSITIONS;
            default -> throw new IllegalStateException("Unexpected direction: " + direction);
        };
    }

    private float getItemAngle(Direction direction) {
        return switch(direction) {
            case NORTH -> 0;
            case SOUTH -> 2 * 90;
            case WEST, EAST -> direction.get2DDataValue() * 90;
            default -> throw new IllegalStateException("Unexpected direction: " + direction);
        };
    }

    private boolean shouldRender() {
        if(!EnhancedWorkbenchesClient.CLIENT_CONFIG.renderInput || !canRender()) return false;

        return !EnhancedWorkbenchesClient.CLIENT_CONFIG.renderInputRequireFancy || Minecraft.useFancyGraphics();
    }

    protected abstract boolean canRender();

    protected abstract boolean requiresLightmapLighting();

    @Override
    public int getViewDistance() {
        return EnhancedWorkbenchesClient.CLIENT_CONFIG.renderInputDistance * 16;
    }

    @Override
    public boolean shouldRenderOffScreen(T blockEntity) {
        return true;
    }

    private static final HashMap<Integer, Tuple<Double, Double>> NORTH_POSITIONS;
    private static final HashMap<Integer, Tuple<Double, Double>> SOUTH_POSITIONS;
    private static final HashMap<Integer, Tuple<Double, Double>> WEST_POSITIONS;
    private static final HashMap<Integer, Tuple<Double, Double>> EAST_POSITIONS;

    static {
        NORTH_POSITIONS = Maps.newHashMap(new ImmutableMap.Builder<Integer, Tuple<Double, Double>>()
            .put(0, new Tuple<>(0.0625 * 11D, 0.0625 * 11D))
            .put(1, new Tuple<>(0.0625 * 8D, 0.0625 * 11D))
            .put(2, new Tuple<>(0.0625 * 5D, 0.0625 * 11D))
            .put(3, new Tuple<>(0.0625 * 11D, 0.0625 * 8D))
            .put(4, new Tuple<>(0.0625 * 8D, 0.0625 * 8D))
            .put(5, new Tuple<>(0.0625 * 5D, 0.0625 * 8D))
            .put(6, new Tuple<>(0.0625 * 11D, 0.0625 * 5D))
            .put(7, new Tuple<>(0.0625 * 8D, 0.0625 * 5D))
            .put(8, new Tuple<>(0.0625 * 5D, 0.0625 * 5D))
            .build());
        SOUTH_POSITIONS = Maps.newHashMap(new ImmutableMap.Builder<Integer, Tuple<Double, Double>>()
            .put(0, new Tuple<>(0.0625 * 5D, 0.0625 * 5D))
            .put(1, new Tuple<>(0.0625 * 8D, 0.0625 * 5D))
            .put(2, new Tuple<>(0.0625 * 11D, 0.0625 * 5D))
            .put(3, new Tuple<>(0.0625 * 5D, 0.0625 * 8D))
            .put(4, new Tuple<>(0.0625 * 8D, 0.0625 * 8D))
            .put(5, new Tuple<>(0.0625 * 11D, 0.0625 * 8D))
            .put(6, new Tuple<>(0.0625 * 5D, 0.0625 * 11D))
            .put(7, new Tuple<>(0.0625 * 8D, 0.0625 * 11D))
            .put(8, new Tuple<>(0.0625 * 11D, 0.0625 * 11D))
            .build());
        WEST_POSITIONS = Maps.newHashMap(new ImmutableMap.Builder<Integer, Tuple<Double, Double>>()
            .put(0, new Tuple<>(0.0625 * 11D, 0.0625 * 5D))
            .put(1, new Tuple<>(0.0625 * 11D, 0.0625 * 8D))
            .put(2, new Tuple<>(0.0625 * 11D, 0.0625 * 11D))
            .put(3, new Tuple<>(0.0625 * 8D, 0.0625 * 5D))
            .put(4, new Tuple<>(0.0625 * 8D, 0.0625 * 8D))
            .put(5, new Tuple<>(0.0625 * 8D, 0.0625 * 11D))
            .put(6, new Tuple<>(0.0625 * 5D, 0.0625 * 5D))
            .put(7, new Tuple<>(0.0625 * 5D, 0.0625 * 8D))
            .put(8, new Tuple<>(0.0625 * 5D, 0.0625 * 11D))
            .build());
        EAST_POSITIONS = Maps.newHashMap(new ImmutableMap.Builder<Integer, Tuple<Double, Double>>()
            .put(0, new Tuple<>(0.0625 * 5D, 0.0625 * 11D))
            .put(1, new Tuple<>(0.0625 * 5D, 0.0625 * 8D))
            .put(2, new Tuple<>(0.0625 * 5D, 0.0625 * 5D))
            .put(3, new Tuple<>(0.0625 * 8D, 0.0625 * 11D))
            .put(4, new Tuple<>(0.0625 * 8D, 0.0625 * 8D))
            .put(5, new Tuple<>(0.0625 * 8D, 0.0625 * 5D))
            .put(6, new Tuple<>(0.0625 * 11D, 0.0625 * 11D))
            .put(7, new Tuple<>(0.0625 * 11D, 0.0625 * 8D))
            .put(8, new Tuple<>(0.0625 * 11D, 0.0625 * 5D))
            .build());
    }
}