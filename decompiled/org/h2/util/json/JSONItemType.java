/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

public enum JSONItemType {
    VALUE,
    ARRAY,
    OBJECT,
    SCALAR;


    public boolean includes(JSONItemType jSONItemType) {
        if (jSONItemType == null) {
            throw new NullPointerException();
        }
        return this == VALUE || this == jSONItemType;
    }
}

