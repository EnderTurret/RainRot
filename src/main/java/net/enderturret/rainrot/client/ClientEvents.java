package net.enderturret.rainrot.client;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import net.enderturret.rainrot.RainRot;
import net.enderturret.rainrot.init.RDataComponents;
import net.enderturret.rainrot.init.RItems;

@EventBusSubscriber(modid = RainRot.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

	@SubscribeEvent
	static void registerItemColors(RegisterColorHandlersEvent.Item e) {
		e.register((stack, index) -> 0xFF000000 | RDataComponents.getColor(stack), RItems.DATA_PEARL.get());
	}

	public static IClientItemExtensions getSolutionExtensions() {
		return new IClientItemExtensions() {
			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
				poseStack.translate(-0.038, -0.038, -0.7);
				poseStack.mulPose(new Quaternionf().rotateY(Mth.PI / 2).rotateX(25 * Mth.DEG_TO_RAD));
				poseStack.scale(0.25f, 0.25f, 0.25f);
				return true;
			}
		};
	}
}