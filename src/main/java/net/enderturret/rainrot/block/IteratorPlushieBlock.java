package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class IteratorPlushieBlock extends WaterloggableHorizontalDirectionalBlock {

	private static final VoxelShape[] AABB;

	static {
		final VoxelShape bodyN = box(2.25, 0, 1, 14.25, 16, 13.75);
		final VoxelShape bodyE = box(2.25, 0, 2.25, 15, 16, 14.25);
		final VoxelShape bodyS = box(1.75, 0, 2.25, 13.75, 16, 15);
		final VoxelShape bodyW = box(1, 0, 1.75, 13.75, 16, 13.75);
		AABB = new VoxelShape[] {bodyS, bodyW, bodyN, bodyE};
	}

	public IteratorPlushieBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AABB[state.getValue(FACING).get2DDataValue()];
	}
}