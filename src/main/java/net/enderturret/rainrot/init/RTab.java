package net.enderturret.rainrot.init;

import static net.enderturret.rainrot.init.RItems.*;

import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import net.neoforged.neoforge.registries.DeferredRegister;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.item.FivePebbsiItem;

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteList;

public final class RTab {

	private static final int CURRENT_SESSION_MISSINGSHARD_MESSAGE;
	private static final FivePebbsiItem.Reviews CURRENT_SESSION_FIVEPEBBSI_REVIEWS_1;
	private static final FivePebbsiItem.Reviews CURRENT_SESSION_FIVEPEBBSI_REVIEWS_2;
	private static final FivePebbsiItem.Reviews CURRENT_SESSION_FIVEPEBBSI_REVIEWS_3;

	static {
		final Random rand = new Random();
		CURRENT_SESSION_MISSINGSHARD_MESSAGE = rand.nextInt(24) + 1; // [0, 23] + 1 => [1, 24]

		final ByteList options = IntStream.range(0, FivePebbsiItem.Reviews.MAX)
				.mapToObj(i -> (byte) i)
				.collect(Collectors.toCollection(ByteArrayList::new));
		CURRENT_SESSION_FIVEPEBBSI_REVIEWS_1 = new FivePebbsiItem.Reviews(
				options.removeByte(rand.nextInt(options.size() - 1)),
				options.removeByte(rand.nextInt(options.size() - 1)));
		CURRENT_SESSION_FIVEPEBBSI_REVIEWS_2 = new FivePebbsiItem.Reviews(
				options.removeByte(rand.nextInt(options.size() - 1)),
				options.removeByte(rand.nextInt(options.size() - 1)));
		CURRENT_SESSION_FIVEPEBBSI_REVIEWS_3 = new FivePebbsiItem.Reviews(
				options.removeByte(rand.nextInt(options.size() - 1)),
				options.removeByte(rand.nextInt(options.size() - 1)));
	}

	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, RainRot.MOD_ID);

	public static final Holder<CreativeModeTab> INSTANCE = REGISTRY.register("tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.rainrot"))
			.icon(() -> get(SOLUTION))
			.displayItems((params, output) -> {
				addAll(output, MEMORY_CONFLAKES, BOWL_OF_MEMORY_CONFLAKES, BOWL_OF_UNFORTUNATE_DEVELOPMENT);

				output.accept(get(FIVE_PEBBSI_CLASSIC, stack -> stack.set(RDataComponents.PEBBSI_REVIEWS, CURRENT_SESSION_FIVEPEBBSI_REVIEWS_1)));
				output.accept(get(FIVE_PEBBSI_CRYSTAL, stack -> stack.set(RDataComponents.PEBBSI_REVIEWS, CURRENT_SESSION_FIVEPEBBSI_REVIEWS_2)));
				output.accept(get(FIVE_PEBBSI_RUBICON, stack -> stack.set(RDataComponents.PEBBSI_REVIEWS, CURRENT_SESSION_FIVEPEBBSI_REVIEWS_3)));

				addAll(output, DATA_PEARL);

				addAll(output, RBlocks.FIVE_PEBBSI_VENDING_MACHINE, RBlocks.ZAPPER, RBlocks.TUNNEL);

				addAll(output, RBlocks.MONK_SLUG_PLUSH, RBlocks.SURVIVOR_SLUG_PLUSH, RBlocks.HUNTER_SLUG_PLUSH,
						RBlocks.GOURMAND_SLUG_PLUSH, RBlocks.ARTIFICER_SLUG_PLUSH, RBlocks.RIVULET_SLUG_PLUSH,
						RBlocks.SPEARMASTER_SLUG_PLUSH, RBlocks.SAINT_SLUG_PLUSH, RBlocks.WATCHER_SLUG_PLUSH);

				addAll(output, RBlocks.FIVE_PEBBLES_PLUSH, RBlocks.LOOKS_TO_THE_MOON_PLUSH,
						RBlocks.NO_SIGNIFICANT_HARASSMENT_PLUSH, RBlocks.SEVEN_RED_SUNS_PLUSH,
						RBlocks.SLIVER_OF_STRAW_PLUSH, RBlocks.MINIATURE_ITERATOR);
			})
			.build());

	private static void addAll(CreativeModeTab.Output output, Holder<? extends ItemLike>... items) {
		for (var item : items)
			output.accept(get(item));
	}

	private static ItemStack get(Holder<? extends ItemLike> item) {
		return get(item, stack -> {});
	}

	private static ItemStack get(Holder<? extends ItemLike> item, Consumer<ItemStack> configurer) {
		final ItemStack ret = new ItemStack(item.value());
		configurer.accept(ret);
		return ret;
	}
}