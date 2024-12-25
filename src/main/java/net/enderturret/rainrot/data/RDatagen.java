package net.enderturret.rainrot.data;

import net.minecraft.data.DataProvider.Factory;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import net.enderturret.rainrot.RainRot;

@EventBusSubscriber(modid = RainRot.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class RDatagen {

	@SubscribeEvent
	static void gatherData(GatherDataEvent e) {
		final ExistingFileHelper files = e.getExistingFileHelper();
		e.getGenerator().addProvider(e.includeClient(), (Factory<RItemModels>) output -> new RItemModels(output, files));
		e.getGenerator().addProvider(e.includeServer(), (Factory<RLootTables>) output -> new RLootTables(output));
	}
}