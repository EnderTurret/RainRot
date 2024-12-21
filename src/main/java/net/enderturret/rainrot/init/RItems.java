package net.enderturret.rainrot.init;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;

import net.neoforged.neoforge.registries.DeferredRegister;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.item.CerealBoxItem;
import net.enderturret.rainrot.item.FivePebbsiItem;
import net.enderturret.rainrot.item.SolutionItem;

public final class RItems {

	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, RainRot.MOD_ID);

	public static final Holder<Item> FIVE_PEBBSI_CLASSIC = REGISTRY.register("five_pebbsi_classic", () -> new FivePebbsiItem(food(4, 1).stacksTo(16)));
	public static final Holder<Item> FIVE_PEBBSI_CRYSTAL = REGISTRY.register("five_pebbsi_crystal", () -> new FivePebbsiItem(food(4, 1).stacksTo(16)));
	public static final Holder<Item> FIVE_PEBBSI_RUBICON = REGISTRY.register("five_pebbsi_rubicon", () -> new FivePebbsiItem(food(4, 1).stacksTo(16)));

	public static final Holder<Item> MEMORY_CONFLAKES = REGISTRY.register("memory_conflakes", () -> new CerealBoxItem(props(1).durability(6)));
	public static final Holder<Item> BOWL_OF_MEMORY_CONFLAKES = REGISTRY.register("bowl_of_memory_conflakes", () -> new Item(food(6, 1, Items.BOWL).stacksTo(16)));
	public static final Holder<Item> BOWL_OF_UNFORTUNATE_DEVELOPMENT = REGISTRY.register("bowl_of_unfortunate_development", () -> new Item(food(6, 1, Items.BOWL).stacksTo(16)));

	public static final Holder<Item> DATA_PEARL = REGISTRY.register("data_pearl", () -> new Item(props(1)));
	public static final Holder<Item> SOLUTION = REGISTRY.register("solution", () -> new SolutionItem(props(1).rarity(Rarity.EPIC)));

	static {
		final Function<String, ResourceLocation> f = path -> ResourceLocation.fromNamespaceAndPath("contentsmp", path);
		REGISTRY.addAlias(f.apply("five_pebbsi_classic"), FIVE_PEBBSI_CLASSIC.getKey().location());
		REGISTRY.addAlias(f.apply("five_pebbsi_crystal"), FIVE_PEBBSI_CRYSTAL.getKey().location());
		REGISTRY.addAlias(f.apply("five_pebbsi_rubicon"), FIVE_PEBBSI_RUBICON.getKey().location());
		REGISTRY.addAlias(f.apply("memory_conflakes"), MEMORY_CONFLAKES.getKey().location());
		REGISTRY.addAlias(f.apply("bowl_of_memory_conflakes"), BOWL_OF_MEMORY_CONFLAKES.getKey().location());
		REGISTRY.addAlias(f.apply("bowl_of_unfortunate_development"), BOWL_OF_UNFORTUNATE_DEVELOPMENT.getKey().location());
		REGISTRY.addAlias(f.apply("data_pearl"), DATA_PEARL.getKey().location());
		REGISTRY.addAlias(f.apply("solution"), SOLUTION.getKey().location());
	}

	static Item.Properties props() {
		return new Item.Properties();
	}

	static Item.Properties props(int stackSize) {
		return props().stacksTo(stackSize);
	}

	private static Item.Properties food(int hunger, float saturation, @Nullable ItemLike conversion) {
		final var builder = new FoodProperties.Builder().nutrition(hunger).saturationModifier(saturation);
		if (conversion != null) builder.usingConvertsTo(conversion);
		return props().food(builder.build());
	}

	private static Item.Properties food(int hunger, float saturation) {
		return food(hunger, saturation, null);
	}
}