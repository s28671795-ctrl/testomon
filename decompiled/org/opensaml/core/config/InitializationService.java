/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config;

import java.util.ServiceLoader;
import javax.annotation.Nonnull;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializationService {
    protected InitializationService() {
    }

    public static synchronized void initialize() throws InitializationException {
        Logger log = InitializationService.getLogger();
        log.info("Initializing OpenSAML using the Java Services API");
        ServiceLoader<Initializer> serviceLoader = InitializationService.getServiceLoader();
        for (Initializer initializer : serviceLoader) {
            log.debug("Initializing module initializer implementation: {}", (Object)initializer.getClass().getName());
            try {
                initializer.init();
            }
            catch (InitializationException e) {
                log.error("Error initializing module", e);
                throw e;
            }
        }
    }

    private static ServiceLoader<Initializer> getServiceLoader() {
        return ServiceLoader.load(Initializer.class);
    }

    @Nonnull
    private static Logger getLogger() {
        return LoggerFactory.getLogger(InitializationService.class);
    }
}

