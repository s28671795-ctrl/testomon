/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.TableLike;
import org.jooq.TablePartitionByStep;

public interface TableOuterJoinStep<R extends Record> {
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> join(TableLike<?> var1, JoinType var2);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftJoin(SQL var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftJoin(String var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftJoin(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftOuterJoin(SQL var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftOuterJoin(String var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> leftOuterJoin(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightJoin(SQL var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightJoin(String var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightJoin(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightOuterJoin(SQL var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightOuterJoin(String var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> rightOuterJoin(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullJoin(SQL var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullJoin(String var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullJoin(Name var1);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullOuterJoin(TableLike<?> var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullOuterJoin(SQL var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullOuterJoin(String var1);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullOuterJoin(String var1, Object ... var2);

    @Support(value={SQLDialect.ORACLE})
    @PlainSQL
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullOuterJoin(String var1, QueryPart ... var2);

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    @Pro
    public TablePartitionByStep<Record> fullOuterJoin(Name var1);
}

