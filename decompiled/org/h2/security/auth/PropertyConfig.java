/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

public class PropertyConfig {
    private String name;
    private String value;

    public PropertyConfig() {
    }

    public PropertyConfig(String string, String string2) {
        this.name = string;
        this.value = string2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String string) {
        this.value = string;
    }
}

