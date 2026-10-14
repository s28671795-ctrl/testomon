/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.websocket.core.Extension;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.WebSocketComponents;

public class WebSocketExtensionRegistry
implements Iterable<Class<? extends Extension>> {
    private final Map<String, Class<? extends Extension>> availableExtensions = new HashMap<String, Class<? extends Extension>>();

    public WebSocketExtensionRegistry() {
        TypeUtil.serviceStream(ServiceLoader.load(Extension.class, this.getClass().getClassLoader())).forEach(ext -> this.availableExtensions.put(ext.getName(), ext.getClass()));
    }

    public Map<String, Class<? extends Extension>> getAvailableExtensions() {
        return this.availableExtensions;
    }

    public Class<? extends Extension> getExtension(String name) {
        return this.availableExtensions.get(name);
    }

    public Set<String> getAvailableExtensionNames() {
        return this.availableExtensions.keySet();
    }

    public boolean isAvailable(String name) {
        return this.availableExtensions.containsKey(name);
    }

    @Override
    public Iterator<Class<? extends Extension>> iterator() {
        return this.availableExtensions.values().iterator();
    }

    public Extension newInstance(ExtensionConfig config, WebSocketComponents components) {
        if (config == null) {
            return null;
        }
        String name = config.getName();
        if (StringUtil.isBlank(name)) {
            return null;
        }
        Class<? extends Extension> extClass = this.getExtension(name);
        if (extClass == null) {
            return null;
        }
        try {
            Extension ext = components.getObjectFactory().createInstance(extClass);
            ext.init(config, components);
            return ext;
        }
        catch (Throwable t2) {
            throw new BadMessageException("Cannot instantiate extension: " + extClass, t2);
        }
    }

    public void register(String name, Class<? extends Extension> extension) {
        this.availableExtensions.put(name, extension);
    }

    public void unregister(String name) {
        this.availableExtensions.remove(name);
    }
}

