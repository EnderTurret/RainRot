package net.enderturret.rainrot.item;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

import net.enderturret.rainrot.client.ClientUtil;

public final class FivePebbsiItem extends DrinkItem {

	public FivePebbsiItem(Properties props) {
		super(props);
	}

	public static void set(ItemStack stack, Reviews reviews) {
		final CompoundTag tag = new CompoundTag();
		tag.putByte("first", reviews.first);
		tag.putByte("second", reviews.second);
		stack.getOrCreateTag().put("rainrot:reviews", tag);
	}

	public static Reviews get(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().contains("rainrot:reviews", Tag.TAG_COMPOUND)) {
			final CompoundTag tag = stack.getTag().getCompound("rainrot:reviews");
			return new Reviews(tag.getByte("first"), tag.getByte("second"));
		}

		return new Reviews((byte) 0, (byte) 2);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
		final Reviews reviews = get(stack);
		final Style style = Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY);
		if (FMLEnvironment.dist == Dist.CLIENT && ClientUtil.isControlDown()) {
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
	}
}