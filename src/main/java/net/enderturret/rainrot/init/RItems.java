package net.enderturret.rainrot.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.item.CerealBoxItem;
import net.enderturret.rainrot.item.CerealItem;
import net.enderturret.rainrot.item.FivePebbsiItem;
import net.enderturret.rainrot.item.SolutionItem;

public final class RItems {

	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, RainRot.MOD_ID);

	public static final RegistryObject<Item> FIVE_PEBBSI_CLASSIC = REGISTRY.register("five_pebbsi_classic", () -> new FivePebbsiItem(food(4, 1).stacksTo(16)));
	public static final RegistryObject<Item> FIVE_PEBBSI_CRYSTAL = REGISTRY.register("five_pebbsi_crystal", () -> new FivePebbsiItem(food(4, 1).stacksTo(16)));
	public static final RegistryObject<Item> FIVE_PEBBSI_RUBICON = REGISTRY.register("five_pebbsi_rubicon", () -> new FivePebbsiItem(food(4, 1).stacksTo(16)));

	public static final RegistryObject<Item> MEMORY_CONFLAKES = REGISTRY.register("memory_conflakes", () -> new CerealBoxItem(props(1).durability(6)));
	public static final RegistryObject<Item> BOWL_OF_MEMORY_CONFLAKES = REGISTRY.register("bowl_of_memory_conflakes", () -> new CerealItem(food(6, 1).stacksTo(16)));
	public static final RegistryObject<Item> BOWL_OF_UNFORTUNATE_DEVELOPMENT = REGISTRY.register("bowl_of_unfortunate_development", () -> new CerealItem(food(6, 1).stacksTo(16)));

	public static final RegistryObject<Item> DATA_PEARL = REGISTRY.register("data_pearl", () -> new Item(props(1)));
	public static final RegistryObject<Item> SOLUTION = REGISTRY.register("solution", () -> new SolutionItem(props(1).rarity(Rarity.EPIC)));

	static Item.Properties props() {
		return new Item.Properties();
	}

	static Item.Properties props(int stackSize) {
		return props().stacksTo(stackSize);
	}

	private static Item.Properties food(int hunger, float saturation) {
		final var builder = new FoodProperties.Builder().nutrition(hunger).saturationMod(saturation);
		return props().food(builder.build());
	}
}