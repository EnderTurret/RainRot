package net.enderturret.rainrot;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class RainRotServerConfig {

	static final ModConfigSpec SPEC;
	private static final RainRotServerConfig CONFIG;

	static {
		final var pair = new ModConfigSpec.Builder().configure(RainRotServerConfig::new);
		SPEC = pair.getRight();
		CONFIG = pair.getLeft();
	}

	private final ModConfigSpec.ConfigValue<String> vendingMachineCurrency;

	private RainRotServerConfig(ModConfigSpec.Builder builder) {
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