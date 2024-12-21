package net.enderturret.rainrot.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider.Factory;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.enderturret.rainrot.RainRot;

@EventBusSubscriber(modid = RainRot.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class RDatagen {

	@SubscribeEvent
	static void gatherData(GatherDataEvent e) {
		final ExistingFileHelper files = e.getExistingFileHelper();
		final CompletableFuture<HolderLookup.Provider> lookup = e.getLookupProvider();
		e.getGenerator().addProvider(e.includeClient(), (Factory<RItemModels>) output -> new RItemModels(output, files));
		e.getGenerator().addProvider(e.includeServer(), (Factory<RLootTables>) output -> new RLootTables(output, lookup));
	}
}