package net.enderturret.rainrot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RItems;
import net.enderturret.rainrot.init.RSoundEvents;
import net.enderturret.rainrot.init.RTab;

@Mod(RainRot.MOD_ID)
public final class RainRot {

	public static final String MOD_ID = "rainrot";
	public static final Logger LOGGER = LoggerFactory.getLogger("RainRot");

	@SuppressWarnings("removal")
	public RainRot() {
		final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, RainRotConfig.SPEC);
		RItems.REGISTRY.register(modBus);
		RBlocks.REGISTRY.register(modBus);
		RTab.REGISTRY.register(modBus);
		RSoundEvents.REGISTRY.register(modBus);
	}
}