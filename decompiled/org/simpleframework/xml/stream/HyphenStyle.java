/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Builder;
import org.simpleframework.xml.stream.HyphenBuilder;
import org.simpleframework.xml.stream.Style;

public class HyphenStyle
implements Style {
    private final Builder builder;
    private final Style style = new HyphenBuilder();

    public HyphenStyle() {
        this.builder = new Builder(this.style);
    }

    @Override
    public String getAttribute(String name) {
        return this.builder.getAttribute(name);
    }

    public void setAttribute(String name, String value) {
        this.builder.setAttribute(name, value);
    }

    @Override
    public String getElement(String name) {
        return this.builder.getElement(name);
    }

    public void setElement(String name, String value) {
        this.builder.setElement(name, value);
    }
}

