/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.bus.config;

import net.engio.mbassy.bus.config.Feature;

public class ConfigurationError
extends RuntimeException {
    private String message;

    private ConfigurationError(String message) {
        this.message = message;
    }

    public static ConfigurationError MissingFeature(Class<? extends Feature> featureType) {
        return new ConfigurationError("The expected feature " + featureType + " was missing. Use addFeature() in IBusConfiguration to add features.");
    }

    @Override
    public String toString() {
        return this.message;
    }
}

