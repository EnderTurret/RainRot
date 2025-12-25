package net.enderturret.rainrot.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import net.enderturret.rainrot.RainRot;

public final class RTags {

	public static final TagKey<Item> POPS_POPCORN_PLANTS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(RainRot.MOD_ID, "pops_popcorn_plants"));

	public static void init() {}
}