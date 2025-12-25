package net.enderturret.rainrot;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class RainRotCommonConfig {

	static final ModConfigSpec SPEC;
	private static final RainRotCommonConfig CONFIG;

	static {
		final var pair = new ModConfigSpec.Builder().configure(RainRotCommonConfig::new);
		SPEC = pair.getRight();
		CONFIG = pair.getLeft();
	}

	private final ModConfigSpec.BooleanValue spoilBaseGame;
	private final ModConfigSpec.BooleanValue spoilHunter;

	private final ModConfigSpec.BooleanValue spoilSpearmaster;
	private final ModConfigSpec.BooleanValue spoilSaint;
	private final ModConfigSpec.BooleanValue spoilSecretSlug;
	private final ModConfigSpec.BooleanValue spoilChallenge70;

	private final ModConfigSpec.BooleanValue spoilWatcher;
	private final ModConfigSpec.BooleanValue spoilWatcher15;

	private RainRotCommonConfig(ModConfigSpec.Builder builder) {
		builder.comment(
				"Configuration options for limiting the number of visible spoilers.",
				"On the client, these will hide items from the creative tab and make item names less specific.",
				"On the server, these will remove recipes for relevant items.").push("spoilers");

		spoilBaseGame = builder.comment(
				"Whether or not to enable spoilers for the base game.",
				"These are specifically spoilers for later parts of the game — not e.g. blue fruit.",
				"",
				"This config option also acts as a master toggle for all other spoiler settings;",
				"if you turn this off, all the others are also turned off."
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

		builder.pop().comment("Watcher-specific spoilers.").push("watcher");

		spoilWatcher = builder.comment(
				"Whether or not to enable spoilers for base Watcher.",
				"Turning this off also turns off Watcher v1.5 spoilers."
				).define("watcher", true);

		spoilWatcher15 = builder.comment(
				"Whether or not to enable spoilers for the Watcher v1.5 update."
				).define("watcher15", true);

		builder.pop().pop();
	}

	public static boolean spoilBaseGame() {
		return CONFIG.spoilBaseGame.get();
	}

	public static boolean spoilHunter() {
		return spoilBaseGame() && CONFIG.spoilHunter.get();
	}

	public static boolean spoilSpearmaster() {
		return spoilBaseGame() && CONFIG.spoilSpearmaster.get();
	}

	public static boolean spoilSaint() {
		return spoilBaseGame() && CONFIG.spoilSaint.get();
	}

	public static boolean spoilSecretSlug() {
		return spoilBaseGame() && CONFIG.spoilSecretSlug.get();
	}

	public static boolean spoilChallenge70() {
		return spoilSaint() && CONFIG.spoilChallenge70.get();
	}

	public static boolean spoilWatcher() {
		return spoilBaseGame() && CONFIG.spoilWatcher.get();
	}

	public static boolean spoilWatcher15() {
		return spoilWatcher() && CONFIG.spoilWatcher15.get();
	}
}