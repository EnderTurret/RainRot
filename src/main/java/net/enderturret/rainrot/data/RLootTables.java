package net.enderturret.rainrot.data;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter.Collector;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import net.enderturret.rainrot.init.RBlocks;

public final class RLootTables extends LootTableProvider {

	public RLootTables(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, Set.of(), List.of(new SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK)), registries);
	}

	@Override
	protected void validate(WritableRegistry<LootTable> registry, ValidationContext context, Collector reporter) {}

	private static final class Blocks extends BlockLootSubProvider {

		public Blocks(HolderLookup.Provider registries) {
			super(Set.of(), FeatureFlags.VANILLA_SET, registries);
		}

		@Override
		protected void generate() {
			dropSelf(RBlocks.ZAPPER.value());
			dropSelf(RBlocks.TUNNEL.value());

			dropSelf(RBlocks.ARTIFICER_SLUG_PLUSH.value());
			dropSelf(RBlocks.GOURMAND_SLUG_PLUSH.value());
			dropSelf(RBlocks.HUNTER_SLUG_PLUSH.value());
			dropSelf(RBlocks.MONK_SLUG_PLUSH.value());
			dropSelf(RBlocks.RIVULET_SLUG_PLUSH.value());
			dropSelf(RBlocks.SAINT_SLUG_PLUSH.value());
			dropSelf(RBlocks.SPEARMASTER_SLUG_PLUSH.value());
			dropSelf(RBlocks.SURVIVOR_SLUG_PLUSH.value());
			dropSelf(RBlocks.WATCHER_SLUG_PLUSH.value());
			dropSelf(RBlocks.INV_SLUG_PLUSH.value());
			dropSelf(RBlocks.FIVE_PEBBLES_PLUSH.value());
			dropSelf(RBlocks.LOOKS_TO_THE_MOON_PLUSH.value());
			dropSelf(RBlocks.NO_SIGNIFICANT_HARASSMENT_PLUSH.value());
			dropSelf(RBlocks.SEVEN_RED_SUNS_PLUSH.value());
			dropSelf(RBlocks.SLIVER_OF_STRAW_PLUSH.value());
			dropSelf(RBlocks.MINIATURE_ITERATOR.value());
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return RBlocks.REGISTRY.getEntries()
					.stream()
					.filter(holder -> holder != RBlocks.FIVE_PEBBSI_VENDING_MACHINE)
					.map(Holder::value)
					.toList();
		}
	}
}