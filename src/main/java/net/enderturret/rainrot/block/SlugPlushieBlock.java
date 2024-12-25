package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlugPlushieBlock extends WaterloggableHorizontalDirectionalBlock {

	private static final VoxelShape[] AABB;

	static {
		final VoxelShape bodyN = box(4, 0, 1, 12, 16, 9);
		final VoxelShape tailN = box(5, 0, 9, 11, 6, 15);
		final VoxelShape bodyE = box(7, 0, 4, 15, 16, 12);
		final VoxelShape tailE = box(1, 0, 5, 7, 6, 11);
		final VoxelShape bodyS = box(4, 0, 7, 12, 16, 15);
		final VoxelShape tailS = box(5, 0, 1, 11, 6, 7);
		final VoxelShape bodyW = box(1, 0, 4, 9, 16, 12);
		final VoxelShape tailW = box(9, 0, 5, 15, 6, 11);
		AABB = new VoxelShape[] {Shapes.or(bodyS, tailS), Shapes.or(bodyW, tailW), Shapes.or(bodyN, tailN), Shapes.or(bodyE, tailE)};
	}

	public SlugPlushieBlock(Properties properties) {
		super(properties);
	}

	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AABB[state.getValue(FACING).get2DDataValue()];
	}
}