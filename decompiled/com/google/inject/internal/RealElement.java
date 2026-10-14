/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.Element;
import java.lang.annotation.Annotation;
import java.util.concurrent.atomic.AtomicInteger;

class RealElement
implements Element {
    private static final AtomicInteger nextUniqueId = new AtomicInteger(1);
    private final int uniqueId;
    private final String setName;
    private final Element.Type type;
    private final String keyType;

    RealElement(String setName, Element.Type type, String keyType) {
        this(setName, type, keyType, nextUniqueId.incrementAndGet());
    }

    RealElement(String setName, Element.Type type, String keyType, int uniqueId) {
        this.uniqueId = uniqueId;
        this.setName = setName;
        this.type = type;
        this.keyType = keyType;
    }

    @Override
    public String setName() {
        return this.setName;
    }

    @Override
    public int uniqueId() {
        return this.uniqueId;
    }

    @Override
    public Element.Type type() {
        return this.type;
    }

    @Override
    public String keyType() {
        return this.keyType;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Element.class;
    }

    @Override
    public String toString() {
        return "@" + Element.class.getName() + "(setName=" + this.setName + ",uniqueId=" + this.uniqueId + ", type=" + (Object)((Object)this.type) + ", keyType=" + this.keyType + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Element && ((Element)o).setName().equals(this.setName()) && ((Element)o).uniqueId() == this.uniqueId() && ((Element)o).type() == this.type() && ((Element)o).keyType().equals(this.keyType());
    }

    @Override
    public int hashCode() {
        return (127 * "setName".hashCode() ^ this.setName.hashCode()) + (127 * "uniqueId".hashCode() ^ this.uniqueId) + (127 * "type".hashCode() ^ this.type.hashCode()) + (127 * "keyType".hashCode() ^ this.keyType.hashCode());
    }
}

