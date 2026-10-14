/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.JSONFormat;
import org.jooq.XMLFormat;
import org.jooq.impl.AbstractFormattable;
import org.jooq.impl.DSL;
import org.jooq.impl.Tools;

abstract class AbstractStore
extends AbstractFormattable {
    AbstractStore() {
        this(null);
    }

    AbstractStore(Configuration configuration) {
        super(configuration);
    }

    @Deprecated
    protected final DSLContext create() {
        return DSL.using(this.configuration());
    }

    @Override
    final JSONFormat defaultJSONFormat() {
        return Tools.configuration(this).formattingProvider().jsonFormatForRecords();
    }

    @Override
    final XMLFormat defaultXMLFormat() {
        return Tools.configuration(this).formattingProvider().xmlFormatForRecords();
    }

    abstract int size();

    abstract Object get(int var1);

    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < this.size(); ++i) {
            Object obj = this.get(i);
            hashCode = obj == null ? 31 * hashCode : (obj.getClass().isArray() ? 31 * hashCode : 31 * hashCode + obj.hashCode());
        }
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AbstractStore) {
            AbstractStore that = (AbstractStore)obj;
            if (this.size() == that.size()) {
                for (int i = 0; i < this.size(); ++i) {
                    Object thisValue = this.get(i);
                    Object thatValue = that.get(i);
                    if (thisValue == null && thatValue == null) continue;
                    if (thisValue == null || thatValue == null) {
                        return false;
                    }
                    if (thisValue.getClass().isArray() && thatValue.getClass().isArray()) {
                        if (thisValue.getClass() == byte[].class && thatValue.getClass() == byte[].class) {
                            if (Arrays.equals((byte[])thisValue, (byte[])thatValue)) continue;
                            return false;
                        }
                        if (!thisValue.getClass().getComponentType().isPrimitive() && !thatValue.getClass().getComponentType().isPrimitive()) {
                            if (Arrays.deepEquals((Object[])thisValue, (Object[])thatValue)) continue;
                            return false;
                        }
                        return false;
                    }
                    if (thisValue.equals(thatValue)) continue;
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}

