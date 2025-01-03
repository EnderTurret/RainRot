package net.enderturret.rainrot.client;

import java.util.List;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class ClientUtil {

	public static void appendSplitComponent(List<Component> list, String key, Style style) {
		final String text = I18n.get(key);
		final String[] lines = text.split("\n");

		for (String line : lines)
			list.add(Component.literal(line).setStyle(style));
	}
}