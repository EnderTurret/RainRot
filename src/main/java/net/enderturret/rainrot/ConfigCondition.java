package net.enderturret.rainrot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigCondition(String option) implements ICondition {

	public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			Codec.STRING.optionalFieldOf("option", "").forGetter(ConfigCondition::option)
			).apply(builder, ConfigCondition::new));

	public ConfigCondition {}

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}

	@Override
	public boolean test(IContext context) {
		return switch (option) {
			case "spoilBaseGame" -> RainRotCommonConfig.spoilBaseGame();
			case "spoilHunter" -> RainRotCommonConfig.spoilHunter();
			case "spoilSpearmaster" -> RainRotCommonConfig.spoilSpearmaster();
			case "spoilSaint" -> RainRotCommonConfig.spoilSaint();
			case "spoilSecretSlug" -> RainRotCommonConfig.spoilSecretSlug();
			case "spoilChallenge70" -> RainRotCommonConfig.spoilChallenge70();
			case "spoilWatcher" -> RainRotCommonConfig.spoilWatcher();
			case "spoilWatcher15" -> RainRotCommonConfig.spoilWatcher15();
			default -> {
				RainRot.LOGGER.warn("Unknown config option: {}", option);
				yield false;
			}
		};
	}

	@Override
	public String toString() {
		return "config(\"" + option + "\")";
	}
}