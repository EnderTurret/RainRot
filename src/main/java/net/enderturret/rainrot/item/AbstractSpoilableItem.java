package net.enderturret.rainrot.item;

import java.util.function.BooleanSupplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.Item;

import net.enderturret.rainrot.Spoilable;
import net.enderturret.rainrot.SpoilerTracker;

public class AbstractSpoilableItem extends Item implements Spoilable {

	protected final @Nullable SpoilerTracker spoiler;

	public AbstractSpoilableItem(Properties properties, @Nullable BooleanSupplier spoilerConfigOption) {
		super(properties);
		spoiler = spoilerConfigOption != null ? new SpoilerTracker(spoilerConfigOption) : null;
	}

	@Override
	public @Nullable SpoilerTracker spoiler() {
		return spoiler;
	}

	@Override
	public String getDescriptionId() {
		return spoiler == null ? super.getDescriptionId() : spoiler.nameKey(super.getDescriptionId());
	}
}