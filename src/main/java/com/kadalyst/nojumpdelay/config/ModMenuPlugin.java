package com.kadalyst.nojumpdelay.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import com.kadalyst.nojumpdelay.config.NoJumpDelayConfigScreen;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuPlugin implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return NoJumpDelayConfigScreen::createConfigScreen;
        } else {
            return null;
        }
    }
}