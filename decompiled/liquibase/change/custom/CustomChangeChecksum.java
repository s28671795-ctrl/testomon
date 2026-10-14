/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.custom;

import liquibase.change.CheckSum;

public interface CustomChangeChecksum {
    public CheckSum generateChecksum();
}

