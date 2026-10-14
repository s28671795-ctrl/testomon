/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Internal;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;

@Deprecated
@Internal
public abstract class AbstractKeys {
    @Deprecated
    public static Index createIndex(String name, Table<?> table, OrderField<?>[] sortFields, boolean unique) {
        return org.jooq.impl.Internal.createIndex(name, table, sortFields, unique);
    }

    @Deprecated
    public static <R extends Record, T> Identity<R, T> createIdentity(Table<R> table, TableField<R, T> field2) {
        return org.jooq.impl.Internal.createIdentity(table, field2);
    }

    @Deprecated
    @SafeVarargs
    public static <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, TableField<R, ?> ... fields) {
        return org.jooq.impl.Internal.createUniqueKey(table, fields);
    }

    @Deprecated
    @SafeVarargs
    public static <R extends Record> UniqueKey<R> createUniqueKey(Table<R> table, String name, TableField<R, ?> ... fields) {
        return org.jooq.impl.Internal.createUniqueKey(table, name, fields);
    }

    @Deprecated
    @SafeVarargs
    public static <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, TableField<R, ?> ... fields) {
        return org.jooq.impl.Internal.createForeignKey(key, table, fields);
    }

    @Deprecated
    @SafeVarargs
    public static <R extends Record, U extends Record> ForeignKey<R, U> createForeignKey(UniqueKey<U> key, Table<R> table, String name, TableField<R, ?> ... fields) {
        return org.jooq.impl.Internal.createForeignKey(key, table, name, fields);
    }
}

