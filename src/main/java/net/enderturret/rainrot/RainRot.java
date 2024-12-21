package net.enderturret.rainrot;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RDataComponents;
import net.enderturret.rainrot.init.RItems;
import net.enderturret.rainrot.init.RSoundEvents;
import net.enderturret.rainrot.init.RTab;

@Mod(RainRot.MOD_ID)
public final class RainRot {

	public static final String MOD_ID = "rainrot";

	public RainRot(IEventBus modBus) {
		RItems.REGISTRY.register(modBus);
		RBlocks.REGISTRY.register(modBus);
		RTab.REGISTRY.register(modBus);
		RDataComponents.REGISTRY.register(modBus);
		RSoundEvents.REGISTRY.register(modBus);
	}
}