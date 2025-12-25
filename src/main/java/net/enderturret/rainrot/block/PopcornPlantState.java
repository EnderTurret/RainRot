package net.enderturret.rainrot.block;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;

public enum PopcornPlantState implements StringRepresentable {

	CLOSED,
	OPEN,
	DEPLETED;

	public final String id = name().toLowerCase(Locale.ENGLISH);

	@Override
	public String getSerializedName() {
		return id;
	}
}