/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.sorting;

public enum SortOrders {
    ASC,
    DESC;


    public String toString() {
        return this.name().toLowerCase();
    }

    public static SortOrders fromString(String value) {
        return SortOrders.valueOf(value.toUpperCase());
    }
}

