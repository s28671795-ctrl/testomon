/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.launch.FirstLaunch;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.launch.DefaultLaunch;
import com.incoresoft.vezha.modules.JSONBaseSerializerDeserializerInit;
import com.incoresoft.vezha.modules.VezhaConfigModule;
import com.incoresoft.vezha.modules.VezhaFirstLaunchModule;
import java.time.Duration;
import java.time.Instant;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args2) {
        Instant launchTime = Instant.now();
        CoreServiceVersionSupplier versionSupplier = new CoreServiceVersionSupplier();
        log.info("Vezha version: {}", (Object)versionSupplier.getCoreVersion());
        System.setProperty("org.jooq.no-logo", "true");
        System.setProperty("liquibase.secureParsing", "false");
        JSONBaseSerializerDeserializerInit.init();
        Injector injector = Guice.createInjector(new VezhaConfigModule());
        VezhaConfigManager configManager = injector.getInstance(VezhaConfigManager.class);
        Runnable logAppLoaded = () -> {
            long startDuration = Duration.between(launchTime, Instant.now()).toMillis();
            log.info("Application started in {}", (Object)DurationFormatUtils.formatDuration(startDuration, "mm:ss.SSS"));
            if (configManager.isConfigured()) {
                if (configManager.isVmsIntegrationEnabled()) {
                    log.info("Application is working in integration mode");
                } else {
                    log.info("Application is working in standalone mode");
                }
            }
        };
        if (!configManager.isConfigured()) {
            FirstLaunch.launch(injector, 2001, 0, 4222, new VezhaFirstLaunchModule());
            logAppLoaded.run();
        } else {
            DefaultLaunch.launch(injector, args2, logAppLoaded);
        }
    }
}

