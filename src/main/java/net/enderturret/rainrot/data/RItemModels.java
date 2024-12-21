package net.enderturret.rainrot.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RItems;

public final class RItemModels extends ItemModelProvider {

	public RItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, RainRot.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(RItems.BOWL_OF_MEMORY_CONFLAKES.value());
		basicItem(RItems.BOWL_OF_UNFORTUNATE_DEVELOPMENT.value());

		basicItem(RItems.DATA_PEARL.value());

		withExistingParent(RBlocks.ZAPPER.getKey().location().toString(), ResourceLocation.fromNamespaceAndPath(RainRot.MOD_ID, "block/zapper_bottom"));
		simpleBlockItem(RBlocks.TUNNEL.value());

		simpleBlockItem(RBlocks.ARTIFICER_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.GOURMAND_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.HUNTER_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.MONK_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.RIVULET_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.SAINT_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.SPEARMASTER_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.SURVIVOR_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.WATCHER_SLUG_PLUSH.value());
		simpleBlockItem(RBlocks.FIVE_PEBBLES_PLUSH.value());
		simpleBlockItem(RBlocks.LOOKS_TO_THE_MOON_PLUSH.value());
		simpleBlockItem(RBlocks.NO_SIGNIFICANT_HARASSMENT_PLUSH.value());
		simpleBlockItem(RBlocks.SEVEN_RED_SUNS_PLUSH.value());
		simpleBlockItem(RBlocks.SLIVER_OF_STRAW_PLUSH.value());
		simpleBlockItem(RBlocks.MINIATURE_ITERATOR.value());
	}
}