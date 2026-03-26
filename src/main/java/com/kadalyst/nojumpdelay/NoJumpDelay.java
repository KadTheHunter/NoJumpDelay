package com.kadalyst.nojumpdelay;

import com.kadalyst.nojumpdelay.config.NoJumpDelayConfig;
import com.kadalyst.nojumpdelay.mixin.LivingEntityAccessor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.resources.Identifier;

import net.minecraft.network.chat.Component;

import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class NoJumpDelay implements ClientModInitializer {

	public static boolean isNoJumpDelayEnabled;

	public void onInitializeClient() {

		NoJumpDelayConfig.load();

		Category keybindCategory = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("nojumpdelay", "category"));

		KeyMapping toggleNoJumpDelay = KeyMappingHelper.registerKeyMapping(new KeyMapping("com.kadalyst.nojumpdelay.toggleMod", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keybindCategory));

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while(toggleNoJumpDelay.consumeClick()) {
				isNoJumpDelayEnabled = !isNoJumpDelayEnabled;
				NoJumpDelayConfig.save();
				if (client.player != null && NoJumpDelayConfig.confirmation) {
					Component message = isNoJumpDelayEnabled
							? Component.translatable("com.kadalyst.nojumpdelay.modEnabled")
							: Component.translatable("com.kadalyst.nojumpdelay.modDisabled");

					if (NoJumpDelayConfig.confirmationType) {
						client.player.sendOverlayMessage(message);
					} else {
						client.player.sendSystemMessage(message);
					}
				}
			}
			if(isNoJumpDelayEnabled) {
				Minecraft mc = Minecraft.getInstance();
				if(mc.player != null) {
					((LivingEntityAccessor) mc.player).setNoJumpDelay(0);
				}
			}
		});
	}
}