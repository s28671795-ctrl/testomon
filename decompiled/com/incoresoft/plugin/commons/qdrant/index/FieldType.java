/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant.index;

import java.util.Objects;

public enum FieldType {
    INTEGER("integer");

    final String name;

    private FieldType(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }
}

