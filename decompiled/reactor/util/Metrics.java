/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Metrics
 */
package reactor.util;

import io.micrometer.core.instrument.MeterRegistry;

@Deprecated
public class Metrics {
    static final boolean isMicrometerAvailable;

    @Deprecated
    public static final boolean isInstrumentationAvailable() {
        return isMicrometerAvailable;
    }

    static {
        boolean micrometer;
        try {
            io.micrometer.core.instrument.Metrics.globalRegistry.getRegistries();
            micrometer = true;
        }
        catch (Throwable t2) {
            micrometer = false;
        }
        isMicrometerAvailable = micrometer;
    }

    @Deprecated
    public static class MicrometerConfiguration {
        private static MeterRegistry registry = io.micrometer.core.instrument.Metrics.globalRegistry;

        @Deprecated
        public static MeterRegistry useRegistry(MeterRegistry registry) {
            MeterRegistry previous = MicrometerConfiguration.registry;
            MicrometerConfiguration.registry = registry;
            return previous;
        }

        @Deprecated
        public static MeterRegistry getRegistry() {
            return registry;
        }
    }
}

