package net.enderturret.rainrot.block;

import java.util.function.BooleanSupplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.Spoilable;
import net.enderturret.rainrot.SpoilerTracker;
import net.enderturret.rainrot.block.entity.PopcornPlantBlockEntity;
import net.enderturret.rainrot.init.RBlockEntities;
import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RSoundEvents;
import net.enderturret.rainrot.init.RTags;

public final class PopcornPlantBlock extends WaterloggableDoubleBlock implements EntityBlock, Spoilable {

	public static final EnumProperty<PopcornPlantState> STATE = EnumProperty.create("state", PopcornPlantState.class);

	private static final VoxelShape SHAPE = box(4, 0, 4, 12, 16, 12);

	private final SpoilerTracker spoiler;

	public PopcornPlantBlock(Properties properties, @Nullable BooleanSupplier spoilerConfigOption) {
		super(properties);
		spoiler = spoilerConfigOption != null ? new SpoilerTracker(spoilerConfigOption) : null;
		registerDefaultState(defaultBlockState().setValue(STATE, PopcornPlantState.CLOSED).setValue(HALF, DoubleBlockHalf.UPPER));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(STATE);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return box(4, 0, 4, 12, 16, 12);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER && state.getValue(STATE) == PopcornPlantState.CLOSED && stack.is(RTags.POPS_POPCORN_PLANTS)) {
			if (stack.isDamageableItem())
				stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

			setStateFromTop(level, pos, state, PopcornPlantState.OPEN);
			level.playSound(player, pos, RSoundEvents.POPCORN_PLANT_OPEN.value(), SoundSource.BLOCKS, 1, 1);
			if (!level.isClientSide)
				((PopcornPlantBlockEntity) level.getBlockEntity(pos)).soundTicks = 40;

			return ItemInteractionResult.sidedSuccess(level.isClientSide());
		}

		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	private static final ResourceKey<LootTable> HARVEST_POPCORN_PLANT = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(RainRot.MOD_ID, "gameplay/harvest_popcorn_plant"));
	private static final ResourceKey<LootTable> HARVEST_ROTCORN_PLANT = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(RainRot.MOD_ID, "gameplay/harvest_rotcorn_plant"));

	protected ResourceKey<LootTable> getHarvestLootTable(BlockState state, Level level, BlockPos pos) {
		return this == RBlocks.ROTCORN_PLANT.value() ? HARVEST_ROTCORN_PLANT : HARVEST_POPCORN_PLANT;
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER && state.getValue(STATE) == PopcornPlantState.OPEN) {
			final BlockEntity blockEntity = level.getBlockEntity(pos);
			if (!(blockEntity instanceof PopcornPlantBlockEntity popcorn)) return InteractionResult.FAIL;

			setStateFromTop(level, pos, state, PopcornPlantState.DEPLETED);
			popcorn.cooldown = (int) (level.getRandom().nextDouble() * 24000 * 3 + 24000 * 2);

			if (level instanceof ServerLevel serverLevel) {
				final LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(getHarvestLootTable(state, level, pos));

				final LootParams params = new LootParams.Builder(serverLevel)
						.withParameter(LootContextParams.THIS_ENTITY, player)
						.withParameter(LootContextParams.ORIGIN, pos.getCenter())
						.withParameter(LootContextParams.BLOCK_STATE, state)
						.withOptionalParameter(LootContextParams.BLOCK_ENTITY, popcorn)
						.create(LootContextParamSets.BLOCK_USE);

				for (ItemStack stack : table.getRandomItems(params))
					if (!player.addItem(stack))
						player.drop(stack, false, false);
			}

			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return InteractionResult.PASS;
	}

	public static void setStateFromTop(Level level, BlockPos pos, BlockState upperState, PopcornPlantState newState) {
		level.setBlock(pos, upperState.setValue(STATE, newState), UPDATE_ALL);
		final BlockState belowState = level.getBlockState(pos.below());
		if (belowState.getBlock() instanceof PopcornPlantBlock)
			level.setBlock(pos.below(), belowState.setValue(STATE, newState), UPDATE_ALL);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PopcornPlantBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level.isClientSide || state.getValue(HALF) == DoubleBlockHalf.LOWER ? null : createTickerHelper(blockEntityType, RBlockEntities.POPCORN_PLANT.value(), PopcornPlantBlockEntity::serverTick);
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
		return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
	}

	@Override
	public @Nullable SpoilerTracker spoiler() {
		return spoiler;
	}

	@Override
	public String getDescriptionId() {
		return spoiler == null ? super.getDescriptionId() : spoiler.nameKey(super.getDescriptionId());
	}
}