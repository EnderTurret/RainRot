package net.enderturret.rainrot.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.enderturret.rainrot.RainRot;

public final class RSoundEvents {

	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RainRot.MOD_ID);

	public static final RegistryObject<SoundEvent> VENDING_MACHINE_DISPENSE = REGISTRY.register("vending_machine_dispense",
			() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RainRot.MOD_ID, "vending_machine_dispense")));
}