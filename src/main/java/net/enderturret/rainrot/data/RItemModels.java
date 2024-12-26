package net.enderturret.rainrot.data;

import java.util.Objects;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RItems;

public final class RItemModels extends ItemModelProvider {

	public RItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, RainRot.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(RItems.BOWL_OF_MEMORY_CONFLAKES.get());
		basicItem(RItems.BOWL_OF_UNFORTUNATE_DEVELOPMENT.get());

		basicItem(RItems.DATA_PEARL.get());

		withExistingParent(RBlocks.ZAPPER.getKey().location().toString(), new ResourceLocation(RainRot.MOD_ID, "block/zapper_bottom"));
		simpleBlockItem(RBlocks.TUNNEL.get());

		simpleBlockItem(RBlocks.ARTIFICER_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.GOURMAND_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.HUNTER_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.MONK_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.RIVULET_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.SAINT_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.SPEARMASTER_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.SURVIVOR_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.WATCHER_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.INV_SLUG_PLUSH.get());
		simpleBlockItem(RBlocks.FIVE_PEBBLES_PLUSH.get());
		simpleBlockItem(RBlocks.LOOKS_TO_THE_MOON_PLUSH.get());
		simpleBlockItem(RBlocks.NO_SIGNIFICANT_HARASSMENT_PLUSH.get());
		simpleBlockItem(RBlocks.SEVEN_RED_SUNS_PLUSH.get());
		simpleBlockItem(RBlocks.SLIVER_OF_STRAW_PLUSH.get());
		simpleBlockItem(RBlocks.MINIATURE_ITERATOR.get());
	}

	private ItemModelBuilder simpleBlockItem(Block block) {
		final ResourceLocation id = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
		return getBuilder(id.toString())
				.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(id.getNamespace(), "block/" + id.getPath())));
	}
}