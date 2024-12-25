package net.enderturret.rainrot.item;

import java.util.function.Consumer;

import net.minecraft.world.item.Item;

import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import net.enderturret.rainrot.client.ClientEvents;

public final class SolutionItem extends Item {

	public SolutionItem(Properties properties) {
		super(properties);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(ClientEvents.getSolutionExtensions());
	}
}