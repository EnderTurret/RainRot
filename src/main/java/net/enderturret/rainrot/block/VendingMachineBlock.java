package net.enderturret.rainrot.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.enderturret.rainrot.init.RItems;
import net.enderturret.rainrot.init.RSoundEvents;

public final class VendingMachineBlock extends WaterloggableHorizontalDirectionalBlock {

	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	private static final VoxelShape[] TOP_AABB;
	private static final VoxelShape[] BOTTOM_AABB;

	static {
		VoxelShape northMainAABB = box(4, 0, 0, 16, 16, 16);
		VoxelShape northSideAABB = box(0, 0, 1, 4, 16, 16);
		VoxelShape northAABB = Shapes.or(northMainAABB, northSideAABB);

		VoxelShape westMainAABB = box(0, 0, 0, 16, 16, 12);
		VoxelShape westSideAABB = box(1, 0, 12, 16, 16, 16);
		VoxelShape westAABB = Shapes.or(westMainAABB, westSideAABB);

		VoxelShape southMainAABB = box(0, 0, 0, 12, 16, 16);
		VoxelShape southSideAABB = box(12, 0, 0, 16, 16, 15);
		VoxelShape southAABB = Shapes.or(southMainAABB, southSideAABB);

		VoxelShape eastMainAABB = box(0, 0, 4, 16, 16, 16);
		VoxelShape eastSideAABB = box(0, 0, 0, 15, 16, 4);
		VoxelShape eastAABB = Shapes.or(eastMainAABB, eastSideAABB);

		TOP_AABB = new VoxelShape[] {southAABB, westAABB, northAABB, eastAABB};

		northMainAABB = box(4, 1, 0, 16, 16, 16);
		northSideAABB = box(0, 1, 1, 4, 16, 16);
		final VoxelShape baseAABB = box(1, 0, 1, 15, 1, 15);
		northAABB = Shapes.or(northMainAABB, northSideAABB, baseAABB);

		westMainAABB = box(0, 1, 0, 16, 16, 12);
		westSideAABB = box(1, 1, 12, 16, 16, 16);
		westAABB = Shapes.or(westMainAABB, westSideAABB, baseAABB);

		southMainAABB = box(0, 1, 0, 12, 16, 16);
		southSideAABB = box(12, 1, 0, 16, 16, 15);
		southAABB = Shapes.or(southMainAABB, southSideAABB, baseAABB);

		eastMainAABB = box(0, 1, 4, 16, 16, 16);
		eastSideAABB = box(0, 1, 0, 15, 16, 4);
		eastAABB = Shapes.or(eastMainAABB, eastSideAABB, baseAABB);

		BOTTOM_AABB = new VoxelShape[] {southAABB, westAABB, northAABB, eastAABB};
	}

	public VendingMachineBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		final int facing = state.getValue(FACING).get2DDataValue();
		return state.getValue(HALF) == DoubleBlockHalf.UPPER ? TOP_AABB[facing] : BOTTOM_AABB[facing];
	}

	@Override
	protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		final DoubleBlockHalf half = state.getValue(HALF);
		if (direction.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (direction == Direction.UP)) {
			return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		} else {
			return neighborState.getBlock() instanceof VendingMachineBlock && neighborState.getValue(HALF) != half ? neighborState.setValue(HALF, half) : Blocks.AIR.defaultBlockState();
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

	private static final Holder<Item> CURRENCY = RItems.DATA_PEARL;
	private static final int CURRENCY_COUNT = 1;

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (state.getValue(HALF) != DoubleBlockHalf.UPPER) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		if (stack.getItem() != CURRENCY.value() || stack.getCount() < CURRENCY_COUNT) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		if (hitResult.getDirection() != state.getValue(FACING)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

		if (level.isClientSide) return ItemInteractionResult.SUCCESS;

		final Holder<Item> pick = switch (player.getRandom().nextInt(3)) {
			default -> RItems.FIVE_PEBBSI_CLASSIC;
			case 1 -> RItems.FIVE_PEBBSI_CRYSTAL;
			case 2 -> RItems.FIVE_PEBBSI_RUBICON;
		};
		final ItemStack pebbsi = new ItemStack(pick);
		if (player.addItem(pebbsi)) {
			stack.shrink(CURRENCY_COUNT);
			level.playSound(null, pos, RSoundEvents.VENDING_MACHINE_DISPENSE.value(), SoundSource.PLAYERS, 1, 0.9F + 0.15F * level.random.nextFloat());
			return ItemInteractionResult.SUCCESS;
		}

		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
}