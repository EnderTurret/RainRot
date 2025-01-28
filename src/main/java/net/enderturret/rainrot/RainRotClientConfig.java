package net.enderturret.rainrot;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class RainRotClientConfig {

	static final ModConfigSpec SPEC;
	private static final RainRotClientConfig CONFIG;

	static {
		final var pair = new ModConfigSpec.Builder().configure(RainRotClientConfig::new);
		SPEC = pair.getRight();
		CONFIG = pair.getLeft();
	}

	private final ModConfigSpec.BooleanValue spoilBaseGame;
	private final ModConfigSpec.BooleanValue spoilHunter;

	private final ModConfigSpec.BooleanValue spoilSpearmaster;
	private final ModConfigSpec.BooleanValue spoilSaint;
	private final ModConfigSpec.BooleanValue spoilSecretSlug;
	private final ModConfigSpec.BooleanValue spoilChallenge70;

	private RainRotClientConfig(ModConfigSpec.Builder builder) {
		builder.comment("Configuration options for limiting the number of visible spoilers.").push("spoilers");

		spoilBaseGame = builder.comment(
				"Whether or not to enable spoilers for the base game."
				).define("base", true);

		spoilHunter = builder.comment(
				"Whether or not to enable spoilers for the Hunter campaign."
				).define("hunter", true);

		builder.comment("Downpour-specific spoilers.").push("downpour");

		spoilSpearmaster = builder.comment(
				"Whether or not to enable spoilers for the Spearmaster campaign."
				).define("spearmaster", true);

		spoilSaint = builder.comment(
				"Whether or not to enable spoilers for the Saint campaign."
				).define("saint", true);

		spoilSecretSlug = builder.comment(
				"Whether or not to enable spoilers for the secret slugcat campaign."
				).define("secretSlug", true);

		spoilChallenge70 = builder.comment(
				"Whether or not to enable spoilers for challenge 70."
				).define("challenge70", true);

		builder.pop().pop();
	}

	public static boolean spoilBaseGame() {
		return CONFIG.spoilBaseGame.get();
	}

	public static boolean spoilHunter() {
		return CONFIG.spoilHunter.get();
	}

	public static boolean spoilSpearmaster() {
		return CONFIG.spoilSpearmaster.get();
	}

	public static boolean spoilSaint() {
		return CONFIG.spoilSaint.get();
	}

	public static boolean spoilSecretSlug() {
		return CONFIG.spoilSecretSlug.get();
	}

	public static boolean spoilChallenge70() {
		return CONFIG.spoilChallenge70.get();
	}
}