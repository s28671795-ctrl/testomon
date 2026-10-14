/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.config.Lookup;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public final class CookieSpecRegistry
implements Lookup<CookieSpecProvider> {
    private final ConcurrentHashMap<String, CookieSpecFactory> registeredSpecs = new ConcurrentHashMap();

    public void register(String name, CookieSpecFactory factory2) {
        Args.notNull(name, "Name");
        Args.notNull(factory2, "Cookie spec factory");
        this.registeredSpecs.put(name.toLowerCase(Locale.ENGLISH), factory2);
    }

    public void unregister(String id) {
        Args.notNull(id, "Id");
        this.registeredSpecs.remove(id.toLowerCase(Locale.ENGLISH));
    }

    public CookieSpec getCookieSpec(String name, HttpParams params) throws IllegalStateException {
        Args.notNull(name, "Name");
        CookieSpecFactory factory2 = this.registeredSpecs.get(name.toLowerCase(Locale.ENGLISH));
        if (factory2 != null) {
            return factory2.newInstance(params);
        }
        throw new IllegalStateException("Unsupported cookie spec: " + name);
    }

    public CookieSpec getCookieSpec(String name) throws IllegalStateException {
        return this.getCookieSpec(name, null);
    }

    public List<String> getSpecNames() {
        return new ArrayList<String>(this.registeredSpecs.keySet());
    }

    public void setItems(Map<String, CookieSpecFactory> map) {
        if (map == null) {
            return;
        }
        this.registeredSpecs.clear();
        this.registeredSpecs.putAll(map);
    }

    @Override
    public CookieSpecProvider lookup(final String name) {
        return new CookieSpecProvider(){

            @Override
            public CookieSpec create(HttpContext context) {
                HttpRequest request = (HttpRequest)context.getAttribute("http.request");
                return CookieSpecRegistry.this.getCookieSpec(name, request.getParams());
            }
        };
    }
}

