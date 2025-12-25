package net.enderturret.rainrot.item;

import net.minecraft.world.item.Item;

import net.enderturret.rainrot.RainRotCommonConfig;
import net.enderturret.rainrot.Spoilable;
import net.enderturret.rainrot.SpoilerTracker;

public final class SolutionItem extends Item implements Spoilable {

	protected final SpoilerTracker spoiler = new SpoilerTracker(RainRotCommonConfig::spoilSaint);

	public SolutionItem(Properties properties) {
		super(properties);
	}

	@Override
	public SpoilerTracker spoiler() {
		return spoiler;
	}

	@Override
	public String getDescriptionId() {
		return spoiler.nameKey(super.getDescriptionId());
	}
}