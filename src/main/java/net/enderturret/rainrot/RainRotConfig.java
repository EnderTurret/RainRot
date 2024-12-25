package net.enderturret.rainrot;

import net.minecraftforge.common.ForgeConfigSpec;

public final class RainRotConfig {

	static final ForgeConfigSpec SPEC;
	private static final RainRotConfig CONFIG;

	static {
		final var pair = new ForgeConfigSpec.Builder().configure(RainRotConfig::new);
		SPEC = pair.getRight();
		CONFIG = pair.getLeft();
	}

	private final ForgeConfigSpec.ConfigValue<String> vendingMachineCurrency;

	private RainRotConfig(ForgeConfigSpec.Builder builder) {
		vendingMachineCurrency = builder.comment(
				"The currency accepted for the Five Pebbsi Vending Machine.",
				"This config option must be in the format '<item id>,<count>'.",
				"Default: rainrot:data_pearl,1"
				).define("vendingMachineCurrency", "rainrot:data_pearl,1");
	}

	public static String vendingMachineCurrency() {
		return CONFIG.vendingMachineCurrency.get();
	}
}