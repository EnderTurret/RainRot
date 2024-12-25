package net.enderturret.rainrot.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class CerealBoxItem extends Item {

	public CerealBoxItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage();
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack stack) {
		final ItemStack ret = stack.copy();
		ret.setDamageValue(ret.getDamageValue() + 1);
		return ret;
	}
}