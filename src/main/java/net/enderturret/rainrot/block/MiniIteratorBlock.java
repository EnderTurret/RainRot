package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class MiniIteratorBlock extends WaterloggableHorizontalDirectionalBlock {

	private static final VoxelShape[] AABB;

	static {
		final VoxelShape bodyN = box(0, 0, 4.5, 16, 12, 11.5);
		final VoxelShape bodyE = box(4.5, 0, 0, 11.5, 12, 16);
		final VoxelShape bodyS = box(0, 0, 4.5, 16, 12, 11.5);
		final VoxelShape bodyW = box(4.5, 0, 0, 11.5, 12, 16);
		AABB = new VoxelShape[] {bodyS, bodyW, bodyN, bodyE};
	}

	public MiniIteratorBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AABB[state.getValue(FACING).get2DDataValue()];
	}
}