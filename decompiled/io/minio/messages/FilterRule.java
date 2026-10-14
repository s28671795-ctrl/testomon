/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="FilterRule", strict=false)
public class FilterRule {
    @Element(name="Name")
    private String name;
    @Element(name="Value")
    private String value;

    public FilterRule() {
    }

    public FilterRule(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String name() {
        return this.name;
    }

    public String value() {
        return this.value;
    }
}

