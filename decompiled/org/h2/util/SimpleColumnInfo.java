/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

public final class SimpleColumnInfo {
    public final String name;
    public final int type;
    public final String typeName;
    public final int precision;
    public final int scale;

    public SimpleColumnInfo(String string, int n, String string2, int n2, int n3) {
        this.name = string;
        this.type = n;
        this.typeName = string2;
        this.precision = n2;
        this.scale = n3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        SimpleColumnInfo simpleColumnInfo = (SimpleColumnInfo)object;
        return this.name.equals(simpleColumnInfo.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}

