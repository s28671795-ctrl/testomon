/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.config;

import com.google.inject.Inject;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.storage.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.pf4j.ExtensionPoint;
import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorePluginsProvider {
    private static final Logger log = LoggerFactory.getLogger(CorePluginsProvider.class);
    private final ConcurrentInitializer<PluginManager> pluginManager = new LazyInitializer<PluginManager>(this){

        @Override
        protected PluginManager initialize() {
            Path pluginsRoot = Path.of("core_plugins", new String[0]);
            if (!Files.exists(pluginsRoot, new LinkOption[0])) {
                try {
                    Files.createDirectories(pluginsRoot, new FileAttribute[0]);
                }
                catch (IOException e) {
                    log.error("Couldn't create core plugins directory", e);
                }
            }
            JarPluginManager pluginManager = new JarPluginManager(pluginsRoot);
            pluginManager.loadPlugins();
            pluginManager.startPlugins();
            return pluginManager;
        }
    };

    public Storage createStorageConnector() {
        return this.initExtension(Storage.class);
    }

    public MessageQueueConnector createMessageQueueConnector() {
        return this.initExtension(MessageQueueConnector.class);
    }

    public DatabaseConnector createDatabaseConnector() {
        return this.initExtension(DatabaseConnector.class);
    }

    private <T extends ExtensionPoint> T initExtension(Class<T> clazz) {
        List<T> extensions;
        try {
            extensions = this.pluginManager.get().getExtensions(clazz);
        }
        catch (ConcurrentException e) {
            throw new RuntimeException(e);
        }
        if (extensions.isEmpty()) {
            throw new RuntimeException(String.format("Extension implementing %s not found", clazz.getName()));
        }
        if (extensions.size() > 1) {
            log.warn("Found more than one {} implementation. Implementations: {}", (Object)clazz.getName(), (Object)extensions);
        }
        return (T)((ExtensionPoint)extensions.getFirst());
    }

    @Inject
    public CorePluginsProvider() {
    }
}

