package net.enderturret.rainrot.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public final class CerealItem extends Item {

	public CerealItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
		stack = super.finishUsingItem(stack, level, livingEntity);

		if (stack.isEmpty())
			return new ItemStack(Items.BOWL);
		else if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
			final ItemStack bowl = new ItemStack(Items.BOWL);
			if (!player.getInventory().add(bowl))
				player.drop(bowl, false);
		}

		return stack;
	}
}