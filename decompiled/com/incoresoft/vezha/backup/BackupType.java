/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

public enum BackupType {
    manual,
    auto;


    public String toString() {
        return this.name().toLowerCase();
    }
}

