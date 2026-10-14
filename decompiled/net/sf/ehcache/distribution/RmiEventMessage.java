/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.EventMessage;

public final class RmiEventMessage
extends EventMessage {
    private final RmiEventType type;
    private final Element element;

    public RmiEventMessage(Ehcache cache, RmiEventType type, Serializable key, Element element) {
        super(cache, key);
        this.type = type;
        this.element = element;
    }

    public final RmiEventType getType() {
        return this.type;
    }

    public final Element getElement() {
        return this.element;
    }

    public static enum RmiEventType {
        PUT,
        REMOVE,
        REMOVE_ALL;

    }
}

