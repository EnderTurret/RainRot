package net.enderturret.rainrot.init;

import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public final class RDataComponents {

	private static String trim(String str) {
		if (str.startsWith("#"))
			return str.substring(1);
		else if (str.startsWith("0x"))
			return str.substring(2);

		return str;
	}

	private static int parseColor(String input) {
		final String trimmed = trim(input);
		if (trimmed.length() > 6)
			return 0xFFFFFF;

		try {
			return Integer.parseUnsignedInt(trimmed, 16);
		} catch (NumberFormatException e) {
			return 0xFFFFFF;
		}
	}

	public static int getColor(ItemStack stack) {
		if (!stack.hasTag()) return 0xFFFFFF;

		if (stack.getTag().contains("rainrot:color", Tag.TAG_ANY_NUMERIC))
			return stack.getTag().getInt("rainrot:color");

		if (stack.getTag().contains("rainrot:color", Tag.TAG_STRING))
			return parseColor(stack.getTag().getString("rainrot:color"));

		return 0xFFFFFF;
	}
}