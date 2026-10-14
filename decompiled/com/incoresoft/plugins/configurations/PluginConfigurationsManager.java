/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins.configurations;

import com.google.inject.Inject;
import com.incoresoft.database.PluginConfigurationsRepository;
import com.incoresoft.extensions.ExtensionListener;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.middleware.plugin.SystemExtension;
import com.incoresoft.plugins.configurations.PluginConfiguration;
import java.util.List;
import java.util.Optional;
import org.jooq.Condition;

public class PluginConfigurationsManager
implements ExtensionListener {
    private final PluginConfigurationsRepository configurationsRepository;

    public List<PluginConfiguration> getConfigurations() {
        return this.configurationsRepository.getAll(new Condition[0]);
    }

    @Override
    public void onExtensionInit(InitializableExtension<?> extension, String pluginId) {
        if (extension instanceof SystemExtension) {
            SystemExtension systemExtension = (SystemExtension)extension;
            PluginConfiguration newConfiguration = new PluginConfiguration(systemExtension, pluginId);
            Optional<PluginConfiguration> existingConfiguration = this.configurationsRepository.getByPluginName(systemExtension.getPluginName());
            existingConfiguration.ifPresentOrElse(existing -> this.configurationsRepository.update(newConfiguration.withId(existing.getId())), () -> this.configurationsRepository.add(newConfiguration));
        }
    }

    @Override
    public void onExtensionTerminate(InitializableExtension<?> extension, String pluginId) {
    }

    @Inject
    public PluginConfigurationsManager(PluginConfigurationsRepository configurationsRepository) {
        this.configurationsRepository = configurationsRepository;
    }
}

