/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Configurator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

final class ConfiguratorRegistry {
    private static ConfiguratorRegistry instance;
    @GuardedBy(value="this")
    private boolean wasConfiguratorsSet;
    @GuardedBy(value="this")
    private boolean configFrozen;
    @GuardedBy(value="this")
    private List<Configurator> configurators = Collections.emptyList();

    ConfiguratorRegistry() {
    }

    public static synchronized ConfiguratorRegistry getDefaultRegistry() {
        if (instance == null) {
            instance = new ConfiguratorRegistry();
        }
        return instance;
    }

    public synchronized void setConfigurators(List<? extends Configurator> configurators) {
        if (this.configFrozen) {
            throw new IllegalStateException("Configurators are already set");
        }
        this.configurators = Collections.unmodifiableList(new ArrayList<Configurator>(configurators));
        this.configFrozen = true;
        this.wasConfiguratorsSet = true;
    }

    public synchronized List<Configurator> getConfigurators() {
        this.configFrozen = true;
        return this.configurators;
    }

    public synchronized boolean wasSetConfiguratorsCalled() {
        return this.wasConfiguratorsSet;
    }
}

