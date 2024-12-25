package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public abstract class WaterloggableHorizontalDirectionalBlock extends HorizontalDirectionalBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected WaterloggableHorizontalDirectionalBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return super.getStateForPlacement(ctx)
				.setValue(FACING, ctx.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED))
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED, FACING);
	}
}