package net.enderturret.rainrot.block;

import net.minecraft.util.StringRepresentable;

public enum TripleBlockPart implements StringRepresentable {

	TOP("top"),
	MIDDLE("middle"),
	BOTTOM("bottom");

	public final String id;

	private TripleBlockPart(String id) {
		this.id = id;
	}

	@Override
	public String getSerializedName() {
		return id;
	}
}