package net.enderturret.rainrot.block;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.enderturret.rainrot.RainRot;

public final class ZapperBlock extends RotatedPillarBlock {

	public static final EnumProperty<TripleBlockPart> PART = EnumProperty.create("part", TripleBlockPart.class);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	private static final VoxelShape[] X_SHAPES;
	private static final VoxelShape[] Y_SHAPES;
	private static final VoxelShape[] Z_SHAPES;

	static {
		VoxelShape top = box(0, 0, 0, 7, 16, 16);
		VoxelShape middle = box(0, 1, 1, 16, 15, 15);
		VoxelShape bottom = box(9, 0, 0, 16, 16, 16);
		X_SHAPES = new VoxelShape[] { Shapes.or(top, middle), middle, Shapes.or(bottom, middle) };

		top = box(0, 9, 0, 16, 16, 16);
		middle = box(1, 0, 1, 15, 16, 15);
		bottom = box(0, 0, 0, 16, 7, 16);
		Y_SHAPES = new VoxelShape[] { Shapes.or(top, middle), middle, Shapes.or(bottom, middle) };

		top = box(0, 0, 0, 16, 16, 7);
		middle = box(1, 1, 0, 15, 15, 16);
		bottom = box(0, 0, 9, 16, 16, 16);
		Z_SHAPES = new VoxelShape[] { Shapes.or(top, middle), middle, Shapes.or(bottom, middle) };
	}

	public ZapperBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(PART, TripleBlockPart.BOTTOM).setValue(POWERED, false));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		final int index = state.getValue(PART).ordinal();
		return switch (state.getValue(AXIS)) {
			case X -> X_SHAPES[index];
			case Y -> Y_SHAPES[index];
			case Z -> Z_SHAPES[index];
		};
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(PART, POWERED);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		if (state.getValue(PART) != TripleBlockPart.BOTTOM) return;

		final Direction.Axis axis = state.getValue(AXIS);
		final boolean powered = level.hasNeighborSignal(pos);
		if (powered == state.getValue(POWERED)) return;

		level.setBlock(pos, state.setValue(POWERED, powered), UPDATE_CLIENTS);

		BlockPos current = pos.mutable();
		BlockState nextState = null;

		while (true) {
			switch (axis) {
				case X -> current = current.west();
				case Y -> current = current.above();
				case Z -> current = current.north();
			}
			if (!level.isInWorldBounds(current)) break;
			if (!level.isLoaded(current)) break;

			nextState = level.getBlockState(current);

			if (!(nextState.getBlock() instanceof ZapperBlock) || nextState.getValue(AXIS) != axis)
				break;

			level.setBlock(current, nextState.setValue(POWERED, powered), UPDATE_CLIENTS);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState base = super.getStateForPlacement(context);
		final Direction.Axis axis = base.getValue(AXIS);

		final BlockPos prev = switch (axis) {
			case X -> context.getClickedPos().east();
			case Y -> context.getClickedPos().below();
			case Z -> context.getClickedPos().south();
		};
		final BlockState prevState = context.getLevel().getBlockState(prev);

		if (prevState.getBlock() instanceof ZapperBlock && prevState.getValue(AXIS) == axis)
			base = base.setValue(PART, TripleBlockPart.MIDDLE);

		final BlockPos next = switch (axis) {
			case X -> context.getClickedPos().west();
			case Y -> context.getClickedPos().above();
			case Z -> context.getClickedPos().north();
		};
		final BlockState nextState = context.getLevel().getBlockState(next);

		if (!nextState.isAir() && !(nextState.getBlock() instanceof ZapperBlock))
			base = base.setValue(PART, TripleBlockPart.TOP);

		return base;
	}

	private static final ResourceKey<DamageType> TRANSFORM_ARRAY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RainRot.MOD_ID, "transform_array"));

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity living && state.getValue(POWERED)) {
			living.hurt(living.damageSources().source(TRANSFORM_ARRAY), 2);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.translatable("block.rainrot.zapper.instructions").setStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY)));
	}
}