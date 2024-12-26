package net.enderturret.rainrot.init;

import net.minecraft.world.entity.decoration.PaintingVariant;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.enderturret.rainrot.RainRot;

public final class RPaintingVariants {

	public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, RainRot.MOD_ID);

	public static final RegistryObject<PaintingVariant> ITERATORS_AGAINST_BIOENGINEERING = REGISTRY.register("iterators_against_bioengineering",
			() -> new PaintingVariant(32, 32));

	public static final RegistryObject<PaintingVariant> KARMA_COMPANIONSHIP = REGISTRY.register("karma_companionship",
			() -> new PaintingVariant(64, 64));

	public static final RegistryObject<PaintingVariant> KARMA_GLUTTONY = REGISTRY.register("karma_gluttony",
			() -> new PaintingVariant(64, 64));

	public static final RegistryObject<PaintingVariant> KARMA_LUST = REGISTRY.register("karma_lust",
			() -> new PaintingVariant(64, 64));

	public static final RegistryObject<PaintingVariant> KARMA_SURVIVAL = REGISTRY.register("karma_survival",
			() -> new PaintingVariant(64, 64));

	public static final RegistryObject<PaintingVariant> KARMA_VIOLENCE = REGISTRY.register("karma_violence",
			() -> new PaintingVariant(64, 64));
}