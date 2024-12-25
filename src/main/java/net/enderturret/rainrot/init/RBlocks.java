package net.enderturret.rainrot.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.block.InvPlushieBlock;
import net.enderturret.rainrot.block.IteratorPlushieBlock;
import net.enderturret.rainrot.block.MiniIteratorBlock;
import net.enderturret.rainrot.block.SaintPlushieBlock;
import net.enderturret.rainrot.block.SlugPlushieBlock;
import net.enderturret.rainrot.block.TunnelBlock;
import net.enderturret.rainrot.block.VendingMachineBlock;
import net.enderturret.rainrot.block.ZapperBlock;

public final class RBlocks {

	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, RainRot.MOD_ID);

	public static final RegistryObject<Block> FIVE_PEBBSI_VENDING_MACHINE = REGISTRY.register("five_pebbsi_vending_machine", () -> new VendingMachineBlock(props().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.5F).lightLevel(state -> 5)));
	public static final RegistryObject<Block> ZAPPER = REGISTRY.register("zapper", () -> new ZapperBlock(props().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.5F)));
	public static final RegistryObject<Block> TUNNEL = REGISTRY.register("tunnel", () -> new TunnelBlock(props().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(0.5F)));

	public static final RegistryObject<Block> ARTIFICER_SLUG_PLUSH = REGISTRY.register("artificer_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.TERRACOTTA_RED)));
	public static final RegistryObject<Block> GOURMAND_SLUG_PLUSH = REGISTRY.register("gourmand_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block> HUNTER_SLUG_PLUSH = REGISTRY.register("hunter_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_RED)));
	public static final RegistryObject<Block> MONK_SLUG_PLUSH = REGISTRY.register("monk_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_YELLOW)));
	public static final RegistryObject<Block> RIVULET_SLUG_PLUSH = REGISTRY.register("rivulet_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block> SAINT_SLUG_PLUSH = REGISTRY.register("saint_slug_plush", () -> new SaintPlushieBlock(plush(MapColor.COLOR_LIGHT_GREEN)));
	public static final RegistryObject<Block> SPEARMASTER_SLUG_PLUSH = REGISTRY.register("spearmaster_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_PURPLE)));
	public static final RegistryObject<Block> SURVIVOR_SLUG_PLUSH = REGISTRY.register("survivor_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block> WATCHER_SLUG_PLUSH = REGISTRY.register("watcher_slug_plush", () -> new SlugPlushieBlock(plush(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block> INV_SLUG_PLUSH = REGISTRY.register("inv_slug_plush", () -> new InvPlushieBlock(plush(MapColor.COLOR_BLACK)));
	public static final RegistryObject<Block> FIVE_PEBBLES_PLUSH = REGISTRY.register("five_pebbles_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_PINK)));
	public static final RegistryObject<Block> LOOKS_TO_THE_MOON_PLUSH = REGISTRY.register("looks_to_the_moon_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_LIGHT_BLUE)));
	public static final RegistryObject<Block> NO_SIGNIFICANT_HARASSMENT_PLUSH = REGISTRY.register("no_significant_harassment_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_GREEN)));
	public static final RegistryObject<Block> SEVEN_RED_SUNS_PLUSH = REGISTRY.register("seven_red_suns_plush", () -> new IteratorPlushieBlock(plush(MapColor.COLOR_ORANGE)));
	public static final RegistryObject<Block> SLIVER_OF_STRAW_PLUSH = REGISTRY.register("sliver_of_straw_plush", () -> new IteratorPlushieBlock(plush(MapColor.TERRACOTTA_WHITE)));
	public static final RegistryObject<Block> MINIATURE_ITERATOR = REGISTRY.register("miniature_iterator", () -> new MiniIteratorBlock(props().requiresCorrectToolForDrops().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(3.5F)));

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
		for (RegistryObject<Block> entry : REGISTRY.getEntries())
			RItems.REGISTRY.register(entry.getKey().location().getPath(), () -> makeBlockItem(entry.get()));
	}
}