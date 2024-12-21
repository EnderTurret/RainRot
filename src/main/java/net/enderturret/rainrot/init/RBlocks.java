package net.enderturret.rainrot.init;

import java.util.function.Function;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import net.neoforged.neoforge.registries.DeferredRegister;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.block.IteratorPlushieBlock;
import net.enderturret.rainrot.block.MiniIteratorBlock;
import net.enderturret.rainrot.block.SaintPlushieBlock;
import net.enderturret.rainrot.block.SlugPlushieBlock;
import net.enderturret.rainrot.block.TunnelBlock;
import net.enderturret.rainrot.block.VendingMachineBlock;
import net.enderturret.rainrot.block.ZapperBlock;

public final class RBlocks {

	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK, RainRot.MOD_ID);

	public static final Holder<Block> VENDING_MACHINE = REGISTRY.register("vending_machine", () -> new VendingMachineBlock(props().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.5F)));
	public static final Holder<Block> ZAPPER = REGISTRY.register("zapper", () -> new ZapperBlock(props().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.5F)));
	public static final Holder<Block> TUNNEL = REGISTRY.register("tunnel", () -> new TunnelBlock(props().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.5F)));

	public static final Holder<Block> ARTIFICER_SLUG_PLUSH = REGISTRY.register("artificer_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.TERRACOTTA_RED)));
	public static final Holder<Block> GOURMAND_SLUG_PLUSH = REGISTRY.register("gourmand_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.TERRACOTTA_WHITE)));
	public static final Holder<Block> HUNTER_SLUG_PLUSH = REGISTRY.register("hunter_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_RED)));
	public static final Holder<Block> MONK_SLUG_PLUSH = REGISTRY.register("monk_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_YELLOW)));
	public static final Holder<Block> RIVULET_SLUG_PLUSH = REGISTRY.register("rivulet_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_LIGHT_BLUE)));
	public static final Holder<Block> SAINT_SLUG_PLUSH = REGISTRY.register("saint_slug_plush", () -> new SaintPlushieBlock(plush(MapColor.COLOR_LIGHT_GREEN)));
	public static final Holder<Block> SPEARMASTER_SLUG_PLUSH = REGISTRY.register("spearmaster_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_PURPLE)));
	public static final Holder<Block> SURVIVOR_SLUG_PLUSH = REGISTRY.register("survivor_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.TERRACOTTA_WHITE)));
	public static final Holder<Block> WATCHER_SLUG_PLUSH = REGISTRY.register("watcher_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_BLACK)));
	public static final Holder<Block> FIVE_PEBBLES_PLUSH = REGISTRY.register("five_pebbles_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_PINK)));
	public static final Holder<Block> LOOKS_TO_THE_MOON_PLUSH = REGISTRY.register("looks_to_the_moon_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_LIGHT_BLUE)));
	public static final Holder<Block> NO_SIGNIFICANT_HARASSMENT_PLUSH = REGISTRY.register("no_significant_harassment_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_GREEN)));
	public static final Holder<Block> SEVEN_RED_SUNS_PLUSH = REGISTRY.register("seven_red_suns_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_ORANGE)));
	public static final Holder<Block> SLIVER_OF_STRAW_PLUSH = REGISTRY.register("sliver_of_straw_plush", () -> new IteratorPlushieBlock(plush(MapColor.TERRACOTTA_WHITE)));
	public static final Holder<Block> MINIATURE_ITERATOR = REGISTRY.register("miniature_iterator", () -> new MiniIteratorBlock(props().requiresCorrectToolForDrops().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(3.5F)));

	static {
		final Function<String, ResourceLocation> f = path -> ResourceLocation.fromNamespaceAndPath("contentsmp", path);
		REGISTRY.addAlias(f.apply("vending_machine"), VENDING_MACHINE.getKey().location());
		REGISTRY.addAlias(f.apply("zapper"), ZAPPER.getKey().location());
		REGISTRY.addAlias(f.apply("tunnel"), TUNNEL.getKey().location());
		REGISTRY.addAlias(f.apply("artificer_slug_plush"), ARTIFICER_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("gourmand_slug_plush"), GOURMAND_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("hunter_slug_plush"), HUNTER_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("monk_slug_plush"), MONK_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("rivulet_slug_plush"), RIVULET_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("saint_slug_plush"), SAINT_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("spearmaster_slug_plush"), SPEARMASTER_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("survivor_slug_plush"), SURVIVOR_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("watcher_slug_plush"), WATCHER_SLUG_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("five_pebbles_plush"), FIVE_PEBBLES_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("looks_to_the_moon_plush"), LOOKS_TO_THE_MOON_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("no_significant_harassment_plush"), NO_SIGNIFICANT_HARASSMENT_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("seven_red_suns_plush"), SEVEN_RED_SUNS_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("sliver_of_straw_plush"), SLIVER_OF_STRAW_PLUSH.getKey().location());
		REGISTRY.addAlias(f.apply("miniature_iterator"), MINIATURE_ITERATOR.getKey().location());
	}

	private static Block.Properties props() {
		return Block.Properties.of();
	}

	private static Block.Properties barrier() {
		return props()
				.pushReaction(PushReaction.BLOCK)
				.noLootTable().noOcclusion().sound(SoundType.GLASS).strength(-1F, 3600000.8F).isValidSpawn((_1, _2, _3, _4) -> false);
	}

	private static Block.Properties core() {
		return props().mapColor(MapColor.METAL).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5F, 1200F);
	}

	private static Block.Properties plush(MapColor color) {
		return props().mapColor(color).sound(SoundType.WOOL).strength(0.1F);
	}

	// ItemBlock is gone but not forgotten.
	private static BlockItem makeBlockItem(Block block) {
		final Item.Properties props = RItems.props();

		return new BlockItem(block, props);
	}

	static {
		for (Holder<Block> entry : REGISTRY.getEntries())
			RItems.REGISTRY.register(entry.getKey().location().getPath(), () -> makeBlockItem(entry.value()));
	}
}