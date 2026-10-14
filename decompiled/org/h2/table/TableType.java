/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

public enum TableType {
    TABLE_LINK,
    SYSTEM_TABLE,
    TABLE,
    VIEW,
    EXTERNAL_TABLE_ENGINE,
    MATERIALIZED_VIEW;


    public String toString() {
        if (this == EXTERNAL_TABLE_ENGINE) {
            return "EXTERNAL";
        }
        if (this == SYSTEM_TABLE) {
            return "SYSTEM TABLE";
        }
        if (this == TABLE_LINK) {
            return "TABLE LINK";
        }
        if (this == MATERIALIZED_VIEW) {
            return "MATERIALIZED VIEW";
        }
        return super.toString();
    }
}

