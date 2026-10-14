/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.sorting;

public enum SortFields {
    ID,
    NAME,
    CREATION_TIME;


    public String toString() {
        return this.name().toLowerCase();
    }
}

