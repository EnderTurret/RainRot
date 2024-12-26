package net.enderturret.rainrot;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import net.enderturret.rainrot.item.SolutionItem;

@EventBusSubscriber(modid = RainRot.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public final class CommonEvents {

	private static final ResourceKey<DamageType> ASCENSION_1 = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(RainRot.MOD_ID, "ascension1"));
	private static final ResourceKey<DamageType> ASCENSION_2 = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(RainRot.MOD_ID, "ascension2"));

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	static void onAttackEntity(AttackEntityEvent e) {
		final Player player = e.getEntity();
		if (player.getMainHandItem().getItem() instanceof SolutionItem && e.getTarget() instanceof LivingEntity living) {
			e.setCanceled(true);
			final boolean first = player.getRandom().nextInt(4) != 0;
			living.hurt(new DamageSource(
					player.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(first ? ASCENSION_1 : ASCENSION_2),
					player), living.getHealth() + 2);
		}
	}
}