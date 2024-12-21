package net.enderturret.rainrot.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import net.neoforged.neoforge.registries.DeferredRegister;

import net.enderturret.rainrot.RainRot;

public final class RSoundEvents {

	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, RainRot.MOD_ID);

	public static final Holder<SoundEvent> VENDING_MACHINE_DISPENSE = REGISTRY.register("vending_machine_dispense", SoundEvent::createVariableRangeEvent);
}