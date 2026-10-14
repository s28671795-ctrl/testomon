/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.https.httpclient;

final class SubjectName {
    static final int DNS = 2;
    static final int IP = 7;
    private final String value;
    private final int type;

    SubjectName(String value, int type) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        this.value = value;
        if (type <= 0) {
            throw new IllegalArgumentException("Type must not be negative or zero: " + type);
        }
        this.type = type;
    }

    static SubjectName IP(String value) {
        return new SubjectName(value, 7);
    }

    static SubjectName DNS(String value) {
        return new SubjectName(value, 2);
    }

    public int getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}

