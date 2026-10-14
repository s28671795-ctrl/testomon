/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pf4j.DefaultExtensionFactory;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;

public class SingletonExtensionFactory
extends DefaultExtensionFactory {
    private final List<String> extensionClassNames;
    private final Map<ClassLoader, Map<String, Object>> cache;

    public SingletonExtensionFactory(PluginManager pluginManager, String ... extensionClassNames) {
        this.extensionClassNames = Arrays.asList(extensionClassNames);
        this.cache = new HashMap<ClassLoader, Map<String, Object>>();
        pluginManager.addPluginStateListener(event -> {
            if (event.getPluginState() != PluginState.STARTED) {
                this.cache.remove(event.getPlugin().getPluginClassLoader());
            }
        });
    }

    @Override
    public <T> T create(Class<T> extensionClass) {
        Map<String, Object> classLoaderBucket;
        String extensionClassName = extensionClass.getName();
        ClassLoader extensionClassLoader = extensionClass.getClassLoader();
        if (!this.cache.containsKey(extensionClassLoader)) {
            this.cache.put(extensionClassLoader, new HashMap());
        }
        if ((classLoaderBucket = this.cache.get(extensionClassLoader)).containsKey(extensionClassName)) {
            return (T)classLoaderBucket.get(extensionClassName);
        }
        T extension = super.create(extensionClass);
        if (this.extensionClassNames.isEmpty() || this.extensionClassNames.contains(extensionClassName)) {
            classLoaderBucket.put(extensionClassName, extension);
        }
        return extension;
    }
}

