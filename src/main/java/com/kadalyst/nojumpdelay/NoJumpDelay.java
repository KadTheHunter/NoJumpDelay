package com.kadalyst.nojumpdelay;

import com.kadalyst.nojumpdelay.config.NoJumpDelayConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class NoJumpDelay implements ClientModInitializer {

	int ticks = 0;

	public static boolean isNoJumpDelayEnabled;

	public void onInitializeClient() {

		NoJumpDelayConfig.load();

		KeyBinding toggleNoJumpDelay = KeyBindingHelper.registerKeyBinding(new KeyBinding("com.kadalyst.nojumpdelay.toggleMod", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category.nojumpdelay.main"));

		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			while(toggleNoJumpDelay.wasPressed()) {
				isNoJumpDelayEnabled = !isNoJumpDelayEnabled;
				NoJumpDelayConfig.save();
				if (client.player != null && NoJumpDelayConfig.confirmation) {
					client.player.sendMessage(isNoJumpDelayEnabled ? Text.translatable("com.kadalyst.nojumpdelay.modEnabled") : Text.translatable("com.kadalyst.nojumpdelay.modDisabled"), NoJumpDelayConfig.confirmationType);
				}
			}
			if(isNoJumpDelayEnabled) {
				Random random = new Random();
				++this.ticks;
				MinecraftClient mc = MinecraftClient.getInstance();
				if(mc.player != null) {
					int randomNumber = random.nextInt(3) + 1;
					if(this.ticks > randomNumber) {
						if(mc.player != null && client.player != null && mc.options.jumpKey.isPressed() && mc.player.isOnGround()) {
							mc.player.jump();
						}

						this.ticks = 0;
					}
				}
			}
		});
	}
}