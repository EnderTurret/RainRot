package net.enderturret.rainrot.item;

import java.util.function.BooleanSupplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class CerealBoxItem extends AbstractSpoilableItem {

	public CerealBoxItem(Properties properties, @Nullable BooleanSupplier spoilerConfigOption) {
		super(properties, spoilerConfigOption);
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage();
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack stack) {
		final ItemStack ret = stack.copy();
		ret.set(DataComponents.DAMAGE, ret.get(DataComponents.DAMAGE) + 1);
		return ret;
	}
}