package net.enderturret.rainrot.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.block.entity.PopcornPlantBlockEntity;

public final class RBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, RainRot.MOD_ID);

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PopcornPlantBlockEntity>> POPCORN_PLANT = REGISTRY.register("popcorn_plant",
			() -> BlockEntityType.Builder.of(PopcornPlantBlockEntity::new, RBlocks.POPCORN_PLANT.value(), RBlocks.ROTCORN_PLANT.value()).build(null));
}