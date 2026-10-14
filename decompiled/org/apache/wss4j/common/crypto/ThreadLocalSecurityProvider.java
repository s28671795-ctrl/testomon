/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;
import java.security.Security;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public final class ThreadLocalSecurityProvider
extends Provider {
    private static final long serialVersionUID = 3556396671069994931L;
    private static final String NAME = "TLSP";
    private static final ThreadLocal<Provider> PROVIDER = new ThreadLocal();
    private static boolean installed = false;

    public static synchronized void install() {
        Security.insertProviderAt(new ThreadLocalSecurityProvider(), Security.getProviders().length);
        installed = true;
    }

    public static synchronized void uninstall() {
        Security.removeProvider(NAME);
        installed = false;
    }

    public static boolean isInstalled() {
        return installed;
    }

    private ThreadLocalSecurityProvider() {
        super(NAME, 1.0, "ThreadLocal Security Provider");
    }

    public static void setProvider(Provider p) {
        PROVIDER.set(p);
    }

    public static void unsetProvider() {
        PROVIDER.remove();
    }

    private Provider getProvider() {
        return PROVIDER.get();
    }

    @Override
    public synchronized void clear() {
        Provider p = this.getProvider();
        if (p != null) {
            p.clear();
        }
    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        Provider p = this.getProvider();
        if (p != null) {
            p.load(inStream);
        }
    }

    @Override
    public synchronized void putAll(Map<?, ?> t2) {
        Provider p = this.getProvider();
        if (p != null) {
            p.putAll(t2);
        }
    }

    @Override
    public synchronized Set<Map.Entry<Object, Object>> entrySet() {
        Provider p = this.getProvider();
        if (p != null) {
            return p.entrySet();
        }
        return Collections.emptySet();
    }

    @Override
    public Set<Object> keySet() {
        Provider p = this.getProvider();
        if (p != null) {
            return p.keySet();
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<Object> values() {
        Provider p = this.getProvider();
        if (p != null) {
            return p.values();
        }
        return Collections.emptyList();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        Provider p = this.getProvider();
        if (p != null) {
            return p.put(key, value);
        }
        return null;
    }

    @Override
    public synchronized Object remove(Object key) {
        Provider p = this.getProvider();
        if (p != null) {
            return p.remove(key);
        }
        return null;
    }

    @Override
    public Object get(Object key) {
        Provider p = this.getProvider();
        if (p != null) {
            return p.get(key);
        }
        return null;
    }

    @Override
    public Enumeration<Object> keys() {
        Provider p = this.getProvider();
        if (p != null) {
            return p.keys();
        }
        return Collections.emptyEnumeration();
    }

    @Override
    public Enumeration<Object> elements() {
        Provider p = this.getProvider();
        if (p != null) {
            return p.elements();
        }
        return Collections.emptyEnumeration();
    }

    @Override
    public String getProperty(String key) {
        Provider p = this.getProvider();
        if (p != null) {
            return p.getProperty(key);
        }
        return null;
    }

    @Override
    public synchronized Provider.Service getService(String type, String algorithm) {
        Provider p = this.getProvider();
        if (p != null) {
            return p.getService(type, algorithm);
        }
        return null;
    }

    @Override
    public synchronized Set<Provider.Service> getServices() {
        Provider p = this.getProvider();
        if (p != null) {
            return p.getServices();
        }
        return Collections.emptySet();
    }
}

