package net.enderturret.rainrot.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import net.minecraftforge.registries.RegistryObject;

import net.enderturret.rainrot.init.RBlocks;

public final class RLootTables extends LootTableProvider {

	public RLootTables(PackOutput output) {
		super(output, Set.of(), List.of(new SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK)));
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {}

	private static final class Blocks extends BlockLootSubProvider {

		public Blocks() {
			super(Set.of(), FeatureFlags.VANILLA_SET);
		}

		@Override
		protected void generate() {
			dropSelf(RBlocks.ZAPPER.get());
			dropSelf(RBlocks.TUNNEL.get());

			dropSelf(RBlocks.ARTIFICER_SLUG_PLUSH.get());
			dropSelf(RBlocks.GOURMAND_SLUG_PLUSH.get());
			dropSelf(RBlocks.HUNTER_SLUG_PLUSH.get());
			dropSelf(RBlocks.MONK_SLUG_PLUSH.get());
			dropSelf(RBlocks.RIVULET_SLUG_PLUSH.get());
			dropSelf(RBlocks.SAINT_SLUG_PLUSH.get());
			dropSelf(RBlocks.SPEARMASTER_SLUG_PLUSH.get());
			dropSelf(RBlocks.SURVIVOR_SLUG_PLUSH.get());
			dropSelf(RBlocks.WATCHER_SLUG_PLUSH.get());
			dropSelf(RBlocks.INV_SLUG_PLUSH.get());
			dropSelf(RBlocks.FIVE_PEBBLES_PLUSH.get());
			dropSelf(RBlocks.LOOKS_TO_THE_MOON_PLUSH.get());
			dropSelf(RBlocks.NO_SIGNIFICANT_HARASSMENT_PLUSH.get());
			dropSelf(RBlocks.SEVEN_RED_SUNS_PLUSH.get());
			dropSelf(RBlocks.SLIVER_OF_STRAW_PLUSH.get());
			dropSelf(RBlocks.MINIATURE_ITERATOR.get());
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return RBlocks.REGISTRY.getEntries()
					.stream()
					.filter(holder -> holder != RBlocks.FIVE_PEBBSI_VENDING_MACHINE)
					.map(RegistryObject::get)
					.toList();
		}
	}
}