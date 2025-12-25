package net.enderturret.rainrot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RItems;
import net.enderturret.rainrot.item.SolutionItem;

@EventBusSubscriber(modid = RainRot.MOD_ID)
public final class CommonEvents {

	private static final ResourceKey<DamageType> ASCENSION_1 = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RainRot.MOD_ID, "ascension1"));
	private static final ResourceKey<DamageType> ASCENSION_2 = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RainRot.MOD_ID, "ascension2"));

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	static void onAttackEntity(AttackEntityEvent e) {
		final Player player = e.getEntity();
		if (player.getWeaponItem().getItem() instanceof SolutionItem && e.getTarget() instanceof LivingEntity living) {
			e.setCanceled(true);
			final boolean first = player.getRandom().nextInt(4) != 0;
			living.hurt(player.damageSources().source(first ? ASCENSION_1 : ASCENSION_2, player), living.getHealth() + 2);
		}
	}

	@SubscribeEvent
	static void onLoadLootTable(LootTableLoadEvent e) {
		if (!RainRotCommonConfig.modifySnifferLoot() || !e.getName().equals(BuiltInLootTables.SNIFFER_DIGGING.location())) return;

		RainRot.LOGGER.info("Modifying sniffer table!");

		LootPool pool = e.getTable().getPool("main");
		if (pool == null) pool = e.getTable().getPool("pool0");
		if (pool == null) {
			RainRot.LOGGER.warn("Could not find first loot pool in gameplay/sniffer_digging!");
			return;
		}

		try {
			final List<LootPoolEntryContainer> entries = new ArrayList<>(ObfuscationReflectionHelper.getPrivateValue(LootPool.class, pool, "entries"));

			entries.add(LootItem.lootTableItem(RItems.BUBBLEFRUIT.value()).build());
			entries.add(LootItem.lootTableItem(RBlocks.POPCORN_PLANT.value()).build());
			//entries.add(LootItem.lootTableItem(RBlocks.ROTCORN_PLANT.value()).build());

			ObfuscationReflectionHelper.setPrivateValue(LootPool.class, pool, entries, "entries");
		} catch (Exception e1) {
			RainRot.LOGGER.error("Exception modifying sniffer loot:", e1);
		}

		RainRot.LOGGER.info("Successfully modified sniffer loot!");
	}
}