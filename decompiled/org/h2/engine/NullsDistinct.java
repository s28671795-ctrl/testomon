/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.util.HasSQL;

public enum NullsDistinct implements HasSQL
{
    DISTINCT,
    ALL_DISTINCT,
    NOT_DISTINCT;


    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("NULLS ");
        switch (this) {
            case DISTINCT: {
                stringBuilder.append("DISTINCT");
                break;
            }
            case ALL_DISTINCT: {
                stringBuilder.append("ALL DISTINCT");
                break;
            }
            case NOT_DISTINCT: {
                stringBuilder.append("NOT DISTINCT");
            }
        }
        return stringBuilder;
    }
}

