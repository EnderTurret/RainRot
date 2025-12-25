package net.enderturret.rainrot.item;

import java.util.function.BooleanSupplier;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class EdibleRotItem extends AbstractSpoilableItem {

	private float nauseaChance = 0.8f;
	private float weaknessChance = 0.5f;
	private float poisonChance = 0.1f;

	public EdibleRotItem(Properties properties, BooleanSupplier spoilerConfigOption, float chanceMultiplier) {
		super(properties, spoilerConfigOption);
		nauseaChance *= chanceMultiplier;
		weaknessChance *= chanceMultiplier;
		poisonChance *= chanceMultiplier;
	}

	public EdibleRotItem(Properties properties, BooleanSupplier spoilerConfigOption) {
		super(properties, spoilerConfigOption);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		final ItemStack ret = super.finishUsingItem(stack, level, livingEntity);

		if (!level.isClientSide) {
			if (livingEntity.getRandom().nextFloat() <= nauseaChance)
				livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) ((livingEntity.getRandom().nextDouble() * 5 + 2) * 20), 1));

			if (livingEntity.getRandom().nextFloat() <= weaknessChance)
				livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) ((livingEntity.getRandom().nextDouble() * 5 + 2) * 20), 1));

			if (livingEntity.getRandom().nextFloat() <= poisonChance)
				livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, (int) ((livingEntity.getRandom().nextDouble() * 5 + 1) * 20), 0));
		}

		return ret;
	}
}