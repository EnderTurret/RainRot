package net.enderturret.rainrot;

import java.util.function.BooleanSupplier;

import org.jetbrains.annotations.Nullable;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

public final class SpoilerTracker {

	private final BooleanSupplier configOption;
	@Nullable
	private String nameKey;

	public SpoilerTracker(BooleanSupplier configOption) {
		this.configOption = configOption;
	}

	public boolean enabled() {
		return FMLEnvironment.dist != Dist.CLIENT || configOption.getAsBoolean();
	}

	public String nameKey(String def) {
		if (enabled()) return def;
		if (nameKey == null) nameKey = def + ".unspoiler";
		return nameKey;
	}
}