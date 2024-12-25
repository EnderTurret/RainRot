package net.enderturret.rainrot.block;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.RainRotConfig;
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
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		final int facing = state.getValue(FACING).get2DDataValue();
		return state.getValue(HALF) == DoubleBlockHalf.UPPER ? TOP_AABB[facing] : BOTTOM_AABB[facing];
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
		final DoubleBlockHalf half = state.getValue(HALF);
		if (direction.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (direction == Direction.UP)) {
			return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
		} else {
			return neighborState.getBlock() instanceof VendingMachineBlock && neighborState.getValue(HALF) != half ? neighborState.setValue(HALF, half) : Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(state))) {
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

		super.playerWillDestroy(level, pos, state, player);
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
	@SuppressWarnings("deprecation")
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		final BlockPos lowerPos = pos.below();
		final BlockState lowerState = level.getBlockState(lowerPos);
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? lowerState.isFaceSturdy(level, lowerPos, Direction.UP) : lowerState.is(this);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HALF);
	}

	@Override
	@SuppressWarnings("deprecation")
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		final ItemStack stack = player.getItemInHand(hand);
		if (stack.isEmpty()) return InteractionResult.PASS;
		if (state.getValue(HALF) != DoubleBlockHalf.UPPER) return InteractionResult.PASS;
		if (hitResult.getDirection() != state.getValue(FACING)) return InteractionResult.PASS;

		final String fullCurrency = RainRotConfig.vendingMachineCurrency();
		final int commaIdx = fullCurrency.indexOf(',');
		if (commaIdx == -1) {
			RainRot.LOGGER.warn("Malformed currency: {} (missing count separator)", fullCurrency);
			return InteractionResult.PASS;
		}

		final String currencyRawId = fullCurrency.substring(0, commaIdx);
		final ResourceLocation currencyId = ResourceLocation.tryParse(currencyRawId);
		if (currencyId == null) {
			RainRot.LOGGER.warn("Malformed currency: {} (invalid item ID)", fullCurrency);
			return InteractionResult.PASS;
		}
		final Item item = ForgeRegistries.ITEMS.getValue(currencyId);

		final String currencyRawCount = fullCurrency.substring(commaIdx + 1);
		final int currencyCount;
		try {
			currencyCount = Integer.parseInt(currencyRawCount);
		} catch (NumberFormatException e) {
			RainRot.LOGGER.warn("Malformed currency: {} (invalid item count)", fullCurrency);
			return InteractionResult.PASS;
		}

		if (stack.getItem() != item || stack.getCount() < currencyCount) return InteractionResult.PASS;

		if (level.isClientSide) return InteractionResult.SUCCESS;

		final RegistryObject<Item> pick = switch (player.getRandom().nextInt(3)) {
			default -> RItems.FIVE_PEBBSI_CLASSIC;
			case 1 -> RItems.FIVE_PEBBSI_CRYSTAL;
			case 2 -> RItems.FIVE_PEBBSI_RUBICON;
		};
		final ItemStack pebbsi = new ItemStack(pick.get());
		if (player.addItem(pebbsi)) {
			stack.shrink(currencyCount);
			level.playSound(null, pos, RSoundEvents.VENDING_MACHINE_DISPENSE.get(), SoundSource.PLAYERS, 1, 0.9F + 0.15F * level.random.nextFloat());
			return InteractionResult.SUCCESS;
		}

		return super.use(state, level, pos, player, hand, hitResult);
	}

	@Override
	public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		tooltipComponents.add(Component.translatable("block.rainrot.five_pebbsi_vending_machine.instructions").setStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY)));
	}
}