/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import net.sf.ehcache.Ehcache;

public class EventMessage
implements Serializable {
    private static final long serialVersionUID = -293616939110963630L;
    private final Serializable key;
    private final transient Ehcache cache;

    public EventMessage(Ehcache cache, Serializable key) {
        this.cache = cache;
        this.key = key;
    }

    public final Ehcache getEhcache() {
        return this.cache;
    }

    public final Serializable getSerializableKey() {
        return this.key;
    }
}

