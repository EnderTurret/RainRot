package net.enderturret.rainrot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.Holder;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import net.enderturret.rainrot.init.RBlockEntities;
import net.enderturret.rainrot.init.RBlocks;
import net.enderturret.rainrot.init.RDataComponents;
import net.enderturret.rainrot.init.RItems;
import net.enderturret.rainrot.init.RSoundEvents;
import net.enderturret.rainrot.init.RTab;
import net.enderturret.rainrot.init.RTags;

@Mod(RainRot.MOD_ID)
public final class RainRot {

	public static final String MOD_ID = "rainrot";
	public static final Logger LOGGER = LoggerFactory.getLogger("RainRot");

	private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, MOD_ID);
	private static final Holder<MapCodec<? extends ICondition>> CONFIG_CONDITION = CONDITIONS.register("config", () -> ConfigCondition.CODEC);

	public RainRot(ModContainer mc, IEventBus modBus) {
		mc.registerConfig(ModConfig.Type.SERVER, RainRotServerConfig.SPEC);
		mc.registerConfig(ModConfig.Type.COMMON, RainRotCommonConfig.SPEC);

		CONDITIONS.register(modBus);
		RItems.REGISTRY.register(modBus);
		RBlocks.REGISTRY.register(modBus);
		RBlockEntities.REGISTRY.register(modBus);
		RTab.REGISTRY.register(modBus);
		RDataComponents.REGISTRY.register(modBus);
		RSoundEvents.REGISTRY.register(modBus);

		RTags.init();
	}
}