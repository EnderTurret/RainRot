package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public final class InvPlushieBlock extends SlugPlushieBlock {

	public static final BooleanProperty CURSED = BooleanProperty.create("cursed");

	public InvPlushieBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(CURSED, false));
	}

	@Override
	@SuppressWarnings("deprecation")
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.isCrouching()) {
			level.setBlock(pos, state.cycle(CURSED), UPDATE_ALL);
			return InteractionResult.SUCCESS;
		}

		return super.use(state, level, pos, player, hand, hit);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(CURSED);
	}
}