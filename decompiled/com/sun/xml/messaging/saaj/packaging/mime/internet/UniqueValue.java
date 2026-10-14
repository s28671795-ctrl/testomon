/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.packaging.mime.internet;

class UniqueValue {
    private static int part = 0;

    UniqueValue() {
    }

    public static String getUniqueBoundaryValue() {
        StringBuilder s2 = new StringBuilder();
        s2.append("----=_Part_").append(part++).append("_").append(s2.hashCode()).append('.').append(System.currentTimeMillis());
        return s2.toString();
    }
}

