/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Style;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class Builder
implements Style {
    private final Cache<String> attributes = new ConcurrentCache<String>();
    private final Cache<String> elements = new ConcurrentCache<String>();
    private final Style style;

    public Builder(Style style) {
        this.style = style;
    }

    @Override
    public String getAttribute(String name) {
        String value = this.attributes.fetch(name);
        if (value != null) {
            return value;
        }
        value = this.style.getAttribute(name);
        if (value != null) {
            this.attributes.cache(name, value);
        }
        return value;
    }

    @Override
    public String getElement(String name) {
        String value = this.elements.fetch(name);
        if (value != null) {
            return value;
        }
        value = this.style.getElement(name);
        if (value != null) {
            this.elements.cache(name, value);
        }
        return value;
    }

    public void setAttribute(String name, String value) {
        this.attributes.cache(name, value);
    }

    public void setElement(String name, String value) {
        this.elements.cache(name, value);
    }
}

