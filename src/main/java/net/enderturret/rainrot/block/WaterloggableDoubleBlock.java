package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;

public abstract class WaterloggableDoubleBlock extends WaterloggableHorizontalDirectionalBlock {

	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	protected WaterloggableDoubleBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		final DoubleBlockHalf half = state.getValue(HALF);
		if (direction.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (direction == Direction.UP)) {
			return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		} else {
			return neighborState.getBlock() == this && neighborState.getValue(HALF) != half ? neighborState.setValue(HALF, half) : Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(state, level, pos))) {
			if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
				final BlockPos neighborPos = pos.below();
				final BlockState neighborState = level.getBlockState(neighborPos);
				if (neighborState.is(state.getBlock()) && neighborState.getValue(HALF) == DoubleBlockHalf.LOWER) {
					final BlockState replacement = neighborState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
					level.setBlock(neighborPos, replacement, 35);
					level.levelEvent(player, 2001, neighborPos, Block.getId(neighborState));
				}
			}
		}

		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final BlockPos pos = context.getClickedPos();
		final Level level = context.getLevel();
		if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context))
			return defaultBlockState()
					.setValue(FACING, context.getHorizontalDirection().getOpposite())
					.setValue(HALF, DoubleBlockHalf.LOWER);

		return null;
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), UPDATE_ALL);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		final BlockPos lowerPos = pos.below();
		final BlockState lowerState = level.getBlockState(lowerPos);
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? lowerState.isFaceSturdy(level, lowerPos, Direction.UP) : lowerState.is(this);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HALF);
	}
}