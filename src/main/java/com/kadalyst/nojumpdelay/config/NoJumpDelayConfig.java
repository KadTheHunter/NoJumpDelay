package com.kadalyst.nojumpdelay.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import static com.kadalyst.nojumpdelay.NoJumpDelay.*;

import net.fabricmc.loader.api.FabricLoader;

public class NoJumpDelayConfig {

    private static final String NOJUMPDELAY_ENABLED = "nojumpdelay-enabled";
    final public static boolean DEFAULT_NOJUMPDELAY_ENABLED = true;

    private static final String CONFIRMATION_KEY = "confirmation";
    final public static boolean DEFAULT_CONFIRMATION = true;
    public static boolean confirmation;

    private static final String CONFIRMATION_TYPE = "confirmationType";
    final public static boolean DEFAULT_CONFIRMATION_TYPE = true;
    public static boolean confirmationType;

    public static void save() {
        File configFile = FabricLoader.getInstance().getConfigDir().resolve("nojumpdelay.properties").toFile();

        try (Writer writer = new FileWriter(configFile)) {
            Properties properties = new Properties();
            properties.setProperty(NOJUMPDELAY_ENABLED, Boolean.toString(isNoJumpDelayEnabled));
            properties.setProperty(CONFIRMATION_KEY, String.valueOf(confirmation));
            properties.setProperty(CONFIRMATION_TYPE, String.valueOf(confirmationType));
            properties.store(writer, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        File configFile = FabricLoader.getInstance().getConfigDir().resolve("nojumpdelay.properties").toFile();
        if (!configFile.exists()) {
            try (Writer writer = new FileWriter(configFile)) {
                Properties properties = new Properties();
                properties.setProperty(NOJUMPDELAY_ENABLED, Boolean.toString(DEFAULT_NOJUMPDELAY_ENABLED));
                properties.setProperty(CONFIRMATION_KEY, String.valueOf(DEFAULT_CONFIRMATION));
                properties.setProperty(CONFIRMATION_TYPE, Boolean.toString(DEFAULT_CONFIRMATION_TYPE));
                properties.store(writer, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (Reader reader = new FileReader(configFile)) {
            Properties properties = new Properties();
            properties.load(reader);
            isNoJumpDelayEnabled = Boolean.parseBoolean(properties.getProperty(NOJUMPDELAY_ENABLED, String.valueOf(DEFAULT_NOJUMPDELAY_ENABLED)));
            confirmation = Boolean.parseBoolean(properties.getProperty(CONFIRMATION_KEY, String.valueOf(DEFAULT_CONFIRMATION)));
            confirmationType = Boolean.parseBoolean(properties.getProperty(CONFIRMATION_TYPE, String.valueOf(DEFAULT_CONFIRMATION_TYPE)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalStateException e) {
            save();
        }
    }
}
