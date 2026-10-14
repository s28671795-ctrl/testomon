/*
 * Decompiled with CFR 0.152.
 */
package org.h2.constraint;

public enum ConstraintActionType {
    RESTRICT,
    CASCADE,
    SET_DEFAULT,
    SET_NULL;


    public String getSqlName() {
        if (this == SET_DEFAULT) {
            return "SET DEFAULT";
        }
        if (this == SET_NULL) {
            return "SET NULL";
        }
        return this.name();
    }
}

