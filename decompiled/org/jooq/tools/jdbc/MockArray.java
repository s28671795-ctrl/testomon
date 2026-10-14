/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.tools.jdbc.MockResultSet;

public class MockArray<T>
implements java.sql.Array {
    private final SQLDialect dialect;
    private final T[] array;
    private final Class<? extends T[]> type;

    public MockArray(SQLDialect dialect, T[] array, Class<? extends T[]> type) {
        this.dialect = dialect;
        this.array = array;
        this.type = type;
    }

    @Override
    public String getBaseTypeName() {
        return DefaultDataType.getDataType(this.dialect, this.type.getComponentType()).getTypeName();
    }

    @Override
    public int getBaseType() {
        return DefaultDataType.getDataType(this.dialect, this.type.getComponentType()).getSQLType();
    }

    public T[] getArray() {
        return this.array;
    }

    public T[] getArray(Map<String, Class<?>> map) {
        return this.array;
    }

    public T[] getArray(long index, int count) throws SQLException {
        if (index - 1L > Integer.MAX_VALUE) {
            throw new SQLException("Cannot access array indexes beyond Integer.MAX_VALUE");
        }
        return this.array == null ? null : Arrays.asList(this.array).subList((int)index - 1, (int)index - 1 + count).toArray((Object[])Array.newInstance(this.array.getClass().getComponentType(), count));
    }

    public T[] getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        return this.getArray(index, count);
    }

    @Override
    public ResultSet getResultSet() {
        return this.getResultSet0(this.array);
    }

    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) {
        return this.getResultSet();
    }

    @Override
    public ResultSet getResultSet(long index, int count) throws SQLException {
        return this.getResultSet0(this.getArray(index, count));
    }

    @Override
    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        return this.getResultSet(index, count);
    }

    private ResultSet getResultSet0(T[] a) {
        DSLContext create = DSL.using(this.dialect);
        Field<Long> index = DSL.field(DSL.name("INDEX"), Long.class);
        Field<?> value = DSL.field(DSL.name("VALUE"), this.type.getComponentType());
        Result<Record2<Long, ?>> result = create.newResult(index, value);
        for (int i = 0; i < a.length; ++i) {
            Record2<Long, ?> record = create.newRecord(index, value);
            record.setValue(index, (long)i + 1L);
            record.setValue(value, a[i]);
            result.add(record);
        }
        return new MockResultSet(result);
    }

    @Override
    public void free() {
    }
}

