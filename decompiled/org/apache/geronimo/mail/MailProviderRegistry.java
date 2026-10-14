/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 */
package org.apache.geronimo.mail;

import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.osgi.framework.Bundle;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MailProviderRegistry {
    static ConcurrentMap<Long, URL> providers = new ConcurrentHashMap<Long, URL>();
    static ConcurrentMap<Long, URL> defaultProviders = new ConcurrentHashMap<Long, URL>();

    static Object registerBundle(Bundle bundle2) {
        URL result = null;
        URL url2 = bundle2.getResource("META-INF/javamail.providers");
        if (url2 != null) {
            providers.put(bundle2.getBundleId(), url2);
            result = url2;
        }
        if ((url2 = bundle2.getResource("META-INF/javamail.default.providers")) != null) {
            defaultProviders.put(bundle2.getBundleId(), url2);
            result = url2;
        }
        return result;
    }

    static void unregisterBundle(Bundle bundle2) {
        providers.remove(bundle2.getBundleId());
        defaultProviders.remove(bundle2.getBundleId());
    }

    public static Collection<URL> getProviders() {
        return providers.values();
    }

    public static Collection<URL> getDefaultProviders() {
        return defaultProviders.values();
    }
}

