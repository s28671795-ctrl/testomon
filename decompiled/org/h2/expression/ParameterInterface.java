/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public interface ParameterInterface {
    public void setValue(Value var1, boolean var2);

    public Value getParamValue();

    public void checkSet() throws DbException;

    public boolean isValueSet();

    public TypeInfo getType();

    public int getNullable();
}

