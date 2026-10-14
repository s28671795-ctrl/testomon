/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xmlsec.keyinfo.KeyInfoGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamedKeyInfoGeneratorManager {
    private final Logger log = LoggerFactory.getLogger(NamedKeyInfoGeneratorManager.class);
    private final Map<String, KeyInfoGeneratorManager> managers = new LazyMap<String, KeyInfoGeneratorManager>();
    private final KeyInfoGeneratorManager defaultManager = new KeyInfoGeneratorManager();
    private boolean useDefaultManager = true;

    public void setUseDefaultManager(boolean newValue) {
        this.useDefaultManager = newValue;
    }

    @Nonnull
    public Set<String> getManagerNames() {
        return Collections.unmodifiableSet(this.managers.keySet());
    }

    @Nonnull
    public KeyInfoGeneratorManager getManager(@Nonnull String name) {
        KeyInfoGeneratorManager manager = this.managers.get(name);
        if (manager == null) {
            manager = new KeyInfoGeneratorManager();
            this.managers.put(name, manager);
        }
        return manager;
    }

    public void removeManager(@Nonnull String name) {
        this.managers.remove(name);
    }

    public void registerFactory(@Nonnull String name, @Nonnull KeyInfoGeneratorFactory factory2) {
        KeyInfoGeneratorManager manager = this.getManager(name);
        manager.registerFactory(factory2);
    }

    public void deregisterFactory(@Nonnull String name, @Nonnull KeyInfoGeneratorFactory factory2) {
        KeyInfoGeneratorManager manager = this.managers.get(name);
        if (manager == null) {
            throw new IllegalArgumentException("Manager with name '" + name + "' does not exist");
        }
        manager.deregisterFactory(factory2);
    }

    public void registerDefaultFactory(@Nonnull KeyInfoGeneratorFactory factory2) {
        this.defaultManager.registerFactory(factory2);
    }

    public void deregisterDefaultFactory(@Nonnull KeyInfoGeneratorFactory factory2) {
        this.defaultManager.deregisterFactory(factory2);
    }

    @Nonnull
    public KeyInfoGeneratorManager getDefaultManager() {
        return this.defaultManager;
    }

    @Nullable
    public KeyInfoGeneratorFactory getFactory(@Nonnull String name, @Nonnull Credential credential) {
        KeyInfoGeneratorFactory factory2;
        KeyInfoGeneratorManager manager = this.managers.get(name);
        if (manager == null) {
            if (this.useDefaultManager) {
                this.log.debug("Manger with name '{}' was not registered, using default manager", (Object)name);
                manager = this.defaultManager;
            } else {
                this.log.warn("Manager with name '{}' was not registered, and 'useDefaultManager' is false", (Object)name);
                return null;
            }
        }
        if ((factory2 = manager.getFactory(credential)) == null && this.useDefaultManager && manager != this.defaultManager) {
            this.log.debug("Factory not found in manager with name '{}', attempting lookup in default manager", (Object)name);
            factory2 = this.defaultManager.getFactory(credential);
        }
        return factory2;
    }
}

