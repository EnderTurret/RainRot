package net.enderturret.rainrot.item;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import net.enderturret.rainrot.RainRotCommonConfig;
import net.enderturret.rainrot.Spoilable;
import net.enderturret.rainrot.SpoilerTracker;
import net.enderturret.rainrot.init.RDataComponents;

public final class FivePebbsiItem extends DrinkItem implements Spoilable {

	protected final SpoilerTracker spoiler = new SpoilerTracker(RainRotCommonConfig::spoilSecretSlug);

	public FivePebbsiItem(Properties props) {
		super(props);
	}

	private Reviews get(ItemStack stack) {
		return stack.getOrDefault(RDataComponents.PEBBSI_REVIEWS, new Reviews((byte) 0, (byte) 2));
	}

	@Override
	public SpoilerTracker spoiler() {
		return spoiler;
	}

	@Override
	public String getDescriptionId() {
		return spoiler.nameKey(super.getDescriptionId());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
		if (!spoiler.enabled()) return;

		final Reviews reviews = get(stack);
		final Style style = Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY);
		if (tooltipFlag.hasControlDown()) {
			tooltip.add(Component.translatable("item.rainrot.five_pebbsi.desc.1").setStyle(style));
			tooltip.add(Component.empty());
			tooltip.add(Component.translatable("item.rainrot.five_pebbsi.desc.2").setStyle(style));
			tooltip.add(Component.empty());
			tooltip.add(Component.translatable("item.rainrot.five_pebbsi.desc.review." + (reviews.first + 1)).setStyle(style));
			tooltip.add(Component.empty());
			tooltip.add(Component.translatable("item.rainrot.five_pebbsi.desc.review." + (reviews.second + 1)).setStyle(style));
		} else
			tooltip.add(Component.translatable("item.rainrot.five_pebbsi.desc.hidden").setStyle(style));
	}

	public static record Reviews(byte first, byte second) {

		public static final byte MAX = 10;

		public static final Codec<Reviews> CODEC = RecordCodecBuilder.create(builder -> builder.group(
				Codec.BYTE.fieldOf("first").forGetter(Reviews::first),
				Codec.BYTE.fieldOf("second").forGetter(Reviews::second)
				).apply(builder, Reviews::new));

		public static final StreamCodec<ByteBuf, Reviews> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.BYTE,
				Reviews::first,
				ByteBufCodecs.BYTE,
				Reviews::second,
				Reviews::new
				);
	}
}