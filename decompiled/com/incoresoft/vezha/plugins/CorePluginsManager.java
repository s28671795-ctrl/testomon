/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.google.inject.Inject;
import com.incoresoft.middleware.connector.OnStatusChangeListener;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.plugins.CustomJarPluginManager;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import java.util.List;
import java.util.Map;
import org.pf4j.ExtensionPoint;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorePluginsManager {
    private static final Logger log = LoggerFactory.getLogger(CorePluginsManager.class);
    private final PluginManager pluginManager;
    private final Storage storage;
    private final DatabaseConnector databaseConnector;
    private final MessageQueueConnector messageQueueConnector;

    @Inject
    public CorePluginsManager(VezhaConfigManager configManager, OnStatusChangeListener appLoadingListener) {
        VezhaConfig config = (VezhaConfig)configManager.getConfig();
        this.pluginManager = new CustomJarPluginManager("core_plugins");
        this.pluginManager.loadPlugins();
        this.pluginManager.startPlugins();
        this.storage = this.initExtension(Storage.class);
        this.storage.init(appLoadingListener, config.getStorage().getProperties());
        log.info("Running storage: {}", (Object)this.storage);
        this.databaseConnector = this.initExtension(DatabaseConnector.class);
        this.databaseConnector.init(appLoadingListener, configManager.getDatabase().convertToMap());
        log.info("Running database connector: {}", (Object)this.databaseConnector);
        this.messageQueueConnector = this.initExtension(MessageQueueConnector.class);
        this.messageQueueConnector.init(appLoadingListener, Map.of("server", config.getNats().getServer()));
        log.info("Running message queue connector: {}", (Object)this.messageQueueConnector);
    }

    private <T extends ExtensionPoint> T initExtension(Class<T> clazz) {
        List<T> extensions = this.pluginManager.getExtensions(clazz);
        if (extensions.isEmpty()) {
            throw new RuntimeException(String.format("Extension implementing %s not found", clazz.getName()));
        }
        if (extensions.size() > 1) {
            log.warn("Found more than one {} implementation. Implementations: {}", (Object)clazz.getName(), (Object)extensions);
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

