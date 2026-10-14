/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.google.inject.Inject;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.core.exceptions.CoreExtensionNotFoundException;
import com.incoresoft.middleware.connector.OnStatusChangeListener;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.plugins.CustomJarPluginManager;
import java.util.List;
import org.pf4j.ExtensionPoint;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorePluginsManager {
    private static final Logger log = LoggerFactory.getLogger(CorePluginsManager.class);
    private final PluginManager pluginManager = new CustomJarPluginManager("core_plugins");
    private final Storage storage;
    private final DatabaseConnector databaseConnector;
    private final MessageQueueConnector messageQueueConnector;

    @Inject
    public CorePluginsManager(OnStatusChangeListener appLoadingListener, ConfigManager configManager) {
        this.pluginManager.loadPlugins();
        this.pluginManager.startPlugins();
        this.storage = this.initExtension(Storage.class);
        this.storage.init(appLoadingListener);
        log.info("Running storage: {}", (Object)this.storage);
        this.databaseConnector = this.initExtension(DatabaseConnector.class);
        this.databaseConnector.init(appLoadingListener, configManager.getDatabase().convertToMap());
        log.info("Running database connector: {}", (Object)this.databaseConnector);
        this.messageQueueConnector = this.initExtension(MessageQueueConnector.class);
        this.messageQueueConnector.init(appLoadingListener);
        log.info("Running message queue connector: {}", (Object)this.messageQueueConnector);
    }

    public <T extends ExtensionPoint> T initExtension(Class<T> clazz) {
        List<T> extensions = this.pluginManager.getExtensions(clazz);
        if (extensions.isEmpty()) {
            throw new CoreExtensionNotFoundException(clazz.getName());
        }
        if (extensions.size() > 1) {
            log.warn("Found more than one {} implementation. Implementations: {}. {} will be used", clazz.getName(), extensions, ((ExtensionPoint)extensions.getFirst()).getClass().getName());
        }
        return (T)((ExtensionPoint)extensions.getFirst());
    }

    public Storage getStorage() {
        return this.storage;
    }

    public DatabaseConnector getDatabaseConnector() {
        return this.databaseConnector;
    }

    public MessageQueueConnector getMessageQueueConnector() {
        return this.messageQueueConnector;
    }
}

