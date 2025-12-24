package net.enderturret.rainrot.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.enderturret.rainrot.init.RItems;

public final class BubblefruitItem extends Item {

	public BubblefruitItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if (entity.isInWaterRainOrBubble())
			if (entity.invulnerableTime > 0) {
				if (--entity.invulnerableTime == 0) {
					entity.playSound(SoundEvents.CHICKEN_EGG, 1, 1);
					entity.setItem(stack.transmuteCopy(RItems.POPPED_BUBBLEFRUIT.value()));
					return false;
				}
			} else entity.invulnerableTime = 20;

		return super.onEntityItemUpdate(stack, entity);
	}
}