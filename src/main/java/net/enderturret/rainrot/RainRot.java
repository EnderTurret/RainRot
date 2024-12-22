package net.enderturret.rainrot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RDataComponents;
import net.enderturret.rainrot.init.RItems;
import net.enderturret.rainrot.init.RSoundEvents;
import net.enderturret.rainrot.init.RTab;

@Mod(RainRot.MOD_ID)
public final class RainRot {

	public static final String MOD_ID = "rainrot";
	public static final Logger LOGGER = LoggerFactory.getLogger("RainRot");

	public RainRot(ModContainer mc, IEventBus modBus) {
		mc.registerConfig(ModConfig.Type.SERVER, RainRotConfig.SPEC);
		RItems.REGISTRY.register(modBus);
		RBlocks.REGISTRY.register(modBus);
		RTab.REGISTRY.register(modBus);
		RDataComponents.REGISTRY.register(modBus);
		RSoundEvents.REGISTRY.register(modBus);
	}
}