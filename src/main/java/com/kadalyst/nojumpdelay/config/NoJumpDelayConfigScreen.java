package com.kadalyst.nojumpdelay.config;

import static com.kadalyst.nojumpdelay.NoJumpDelay.*;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class NoJumpDelayConfigScreen {
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("text.autoconfig.nojumpdelay.title"));
        builder.setSavingRunnable(NoJumpDelayConfig::save);
        ConfigCategory general = builder
                .getOrCreateCategory(Text.translatable("text.autoconfig.nojumpdelay.title"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        // No Jump Delay Enabled
        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.translatable("text.autoconfig.nojumpdelay.option.noJumpDelayEnabled"),
                        isNoJumpDelayEnabled)
                .setDefaultValue(NoJumpDelayConfig.DEFAULT_NOJUMPDELAY_ENABLED)
                .setSaveConsumer((replace) -> isNoJumpDelayEnabled = replace).build());
        // Confirmation
        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.translatable("text.autoconfig.nojumpdelay.option.confirmation"),
                        NoJumpDelayConfig.confirmation)
                .setDefaultValue(NoJumpDelayConfig.DEFAULT_CONFIRMATION)
                .setSaveConsumer((replace) -> NoJumpDelayConfig.confirmation = replace)
                .build());
        // Confirmation Type
        enum ConfirmTypeLabel {
            CHAT,
            HUD
        }
        general.addEntry(entryBuilder
                .startEnumSelector(Text.translatable("text.autoconfig.nojumpdelay.option.confirmationType"),
                        ConfirmTypeLabel.class,
                        !NoJumpDelayConfig.confirmationType ? ConfirmTypeLabel.CHAT : ConfirmTypeLabel.HUD)
                .setDefaultValue(ConfirmTypeLabel.HUD)
                .setSaveConsumer((replace) -> NoJumpDelayConfig.confirmationType = replace == ConfirmTypeLabel.HUD)
                .build());
        return builder.build();
    }
}