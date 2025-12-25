package net.enderturret.rainrot.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.enderturret.rainrot.init.RItems;
import net.enderturret.rainrot.init.RSoundEvents;

public final class BubblefruitItem extends Item {

	public BubblefruitItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		if (entity.isInWaterRainOrBubble())
			if (entity.invulnerableTime > 0) {
				if (--entity.invulnerableTime == 0) {
					entity.playSound(RSoundEvents.BUBBLEFRUIT_POP.value(), 1, entity.getRandom().nextFloat() * 0.2f + 0.9f);
					entity.setItem(stack.transmuteCopy(RItems.POPPED_BUBBLEFRUIT.value()));
					return false;
				}
			} else entity.invulnerableTime = 20;

		return super.onEntityItemUpdate(stack, entity);
	}
}