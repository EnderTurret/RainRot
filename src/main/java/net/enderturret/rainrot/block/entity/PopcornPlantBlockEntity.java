package net.enderturret.rainrot.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.enderturret.rainrot.block.PopcornPlantBlock;
import net.enderturret.rainrot.block.PopcornPlantState;
import net.enderturret.rainrot.init.RBlockEntities;
import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RSoundEvents;

public final class PopcornPlantBlockEntity extends BlockEntity {

	public int cooldown = 0;
	public int soundTicks = 0;

	public PopcornPlantBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	public PopcornPlantBlockEntity(BlockPos pos, BlockState blockState) {
		this(RBlockEntities.POPCORN_PLANT.value(), pos, blockState);
	}

	// Note: only the upper half ticks.
	public static void serverTick(Level level, BlockPos pos, BlockState state, PopcornPlantBlockEntity plant) {
		if (plant.cooldown > 0) {
			if (--plant.cooldown == 0)
				PopcornPlantBlock.setStateFromTop(level, pos, state, PopcornPlantState.CLOSED);
		} else if (plant.soundTicks > 0) {
			if (plant.soundTicks % 2 == 1) {
				final double progress = (40 - plant.soundTicks) / 40d;
				final double chance = Mth.clamp(-Math.log(progress), 0, 1);
				final boolean rotted = state.getBlock() == RBlocks.ROTCORN_PLANT.value();
				if (level.random.nextFloat() <= chance)
					level.playSound(null, pos, RSoundEvents.POPCORN_PLANT_POP.value(), SoundSource.BLOCKS, 1,
							level.random.nextFloat() * (rotted ? 1F : 0.5F) + (rotted ? 0.2F : 1.35F));
			}
			plant.soundTicks--;
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag, Provider registries) {
		super.saveAdditional(tag, registries);
		if (cooldown > 0)
			tag.putShort("cooldown", (short) cooldown);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, Provider registries) {
		super.loadAdditional(tag, registries);
		cooldown = tag.getShort("cooldown");
	}
}