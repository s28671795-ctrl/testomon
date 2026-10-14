/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jetbrains.annotations.ApiStatus;
import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDataType;

@ApiStatus.Internal
public class BuiltInDataType<T>
extends DefaultDataType<T> {
    public BuiltInDataType(Class<T> type, String typeName) {
        super(null, type, typeName);
    }

    public BuiltInDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName) {
        super(dialect, sqlDataType, typeName);
    }

    public BuiltInDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName, String castTypeName) {
        super(dialect, sqlDataType, typeName, castTypeName);
    }
}

