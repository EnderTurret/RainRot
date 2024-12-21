package net.enderturret.rainrot.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.item.FivePebbsiItem;

public final class RDataComponents {

	public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, RainRot.MOD_ID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<FivePebbsiItem.Reviews>> PEBBSI_REVIEWS = REGISTRY.register("reviews", () ->
			new DataComponentType.Builder<FivePebbsiItem.Reviews>()
			.persistent(FivePebbsiItem.Reviews.CODEC)
			.networkSynchronized(FivePebbsiItem.Reviews.STREAM_CODEC)
			.build());

	private static String trim(String str) {
		if (str.startsWith("#"))
			return str.substring(1);
		else if (str.startsWith("0x"))
			return str.substring(2);

		return str;
	}

	private static final Codec<Integer> COLOR_FROM_STRING_CODEC = Codec.STRING
			.validate(str -> {
				final String trimmed = trim(str);
				if (trimmed.length() > 6)
					return DataResult.error(() -> "Input string too long");

				try {
					Integer.parseUnsignedInt(trimmed, 16);
				} catch (NumberFormatException e) {
					return DataResult.error(() -> "Not a hexadecimal number: " + trimmed);
				}

				return DataResult.success(str);
			})
			.xmap(str -> Integer.parseUnsignedInt(trim(str), 16), i -> Integer.toString(i));

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COLOR = REGISTRY.register("color", () ->
			new DataComponentType.Builder<Integer>()
			.persistent(Codec.withAlternative(Codec.INT, COLOR_FROM_STRING_CODEC))
			.networkSynchronized(ByteBufCodecs.VAR_INT)
			.build());
}