package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class TunnelBlock extends WaterloggableHorizontalDirectionalBlock {

	private static final VoxelShape[] COMPLEX_AABB;
	private static final VoxelShape[] COLLISION_AABB;

	static {
		final VoxelShape bodyN1 = box(0, 0, 15, 2, 16, 16);
		final VoxelShape bodyN2 = box(0, 0, 15, 16, 2, 16);
		final VoxelShape bodyN3 = box(0, 14, 15, 16, 16, 16);
		final VoxelShape bodyN4 = box(14, 0, 15, 16, 16, 16);

		final VoxelShape bodyE1 = box(0, 0, 14, 1, 16, 16);
		final VoxelShape bodyE2 = box(0, 0, 0, 1, 2, 16);
		final VoxelShape bodyE3 = box(0, 14, 0, 1, 16, 16);
		final VoxelShape bodyE4 = box(0, 0, 0, 1, 16, 2);

		final VoxelShape bodyS1 = box(0, 0, 0, 2, 16, 1);
		final VoxelShape bodyS2 = box(0, 0, 0, 16, 2, 1);
		final VoxelShape bodyS3 = box(0, 14, 0, 16, 16, 1);
		final VoxelShape bodyS4 = box(14, 0, 0, 16, 16, 1);

		final VoxelShape bodyW1 = box(15, 0, 0, 16, 16, 2);
		final VoxelShape bodyW2 = box(15, 0, 0, 16, 2, 16);
		final VoxelShape bodyW3 = box(15, 14, 0, 16, 16, 16);
		final VoxelShape bodyW4 = box(15, 0, 14, 16, 16, 16);

		COMPLEX_AABB = new VoxelShape[] {
				Shapes.or(bodyS1, bodyS2, bodyS3, bodyS4),
				Shapes.or(bodyW1, bodyW2, bodyW3, bodyW4),
				Shapes.or(bodyN1, bodyN2, bodyN3, bodyN4),
				Shapes.or(bodyE1, bodyE2, bodyE3, bodyE4)
		};

		final VoxelShape bodyN = box(0, 15, 14, 16, 16, 16);
		final VoxelShape bodyE = box(0, 15, 0, 2, 16, 16);
		final VoxelShape bodyS = box(0, 15, 0, 16, 16, 2);
		final VoxelShape bodyW = box(14, 15, 0, 16, 16, 16);
		COLLISION_AABB = new VoxelShape[] {bodyS, bodyW, bodyN, bodyE};
	}

	public TunnelBlock(Properties properties) {
		super(properties.dynamicShape());
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return COMPLEX_AABB[state.getValue(FACING).get2DDataValue()];
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (context.isDescending())
			return COLLISION_AABB[state.getValue(FACING).get2DDataValue()];

		return Shapes.empty();
	}
}