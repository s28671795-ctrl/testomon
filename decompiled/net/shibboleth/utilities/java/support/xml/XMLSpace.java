/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

public enum XMLSpace {
    DEFAULT,
    PRESERVE;


    public String toString() {
        return super.toString().toLowerCase();
    }

    public static XMLSpace parseValue(String value) {
        return XMLSpace.valueOf(value.toUpperCase());
    }
}

