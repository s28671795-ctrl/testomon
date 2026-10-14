/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TableOuterJoinStep;
import org.jooq.TablePartitionByStep;
import org.jooq.impl.DSL;
import org.jooq.impl.FullJoin;
import org.jooq.impl.LeftJoin;
import org.jooq.impl.NaturalFullJoin;
import org.jooq.impl.NaturalLeftJoin;
import org.jooq.impl.NaturalRightJoin;
import org.jooq.impl.RightJoin;

final class PartitionJoinTable
implements TableOuterJoinStep<Record> {
    @Pro
    private final Table<?> lhs;
    @Pro
    private final Collection<? extends Field<?>> lhsPartitionBy;

    @Pro
    PartitionJoinTable(Table<?> lhs, Collection<? extends Field<?>> lhsPartitionBy) {
        this.lhs = lhs;
        this.lhsPartitionBy = lhsPartitionBy;
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> join(TableLike<?> table, JoinType type) {
        switch (type) {
            case LEFT_OUTER_JOIN: {
                return new LeftJoin(this.lhs, table, this.lhsPartitionBy);
            }
            case RIGHT_OUTER_JOIN: {
                return new RightJoin(this.lhs, table, this.lhsPartitionBy);
            }
            case FULL_OUTER_JOIN: {
                return new FullJoin(this.lhs, table, this.lhsPartitionBy);
            }
            case NATURAL_LEFT_OUTER_JOIN: {
                return new NaturalLeftJoin(this.lhs, table, this.lhsPartitionBy);
            }
            case NATURAL_RIGHT_OUTER_JOIN: {
                return new NaturalRightJoin(this.lhs, table, this.lhsPartitionBy);
            }
            case NATURAL_FULL_OUTER_JOIN: {
                return new NaturalFullJoin(this.lhs, table, this.lhsPartitionBy);
            }
        }
        throw new IllegalArgumentException("Unsupported join type: " + String.valueOf((Object)type));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftJoin(TableLike<?> table) {
        return this.leftOuterJoin(table);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftJoin(SQL sql) {
        return this.leftOuterJoin(sql);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftJoin(String sql) {
        return this.leftOuterJoin(sql);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftJoin(String sql, Object ... bindings) {
        return this.leftOuterJoin(sql, bindings);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftJoin(String sql, QueryPart ... parts) {
        return this.leftOuterJoin(sql, parts);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftJoin(Name name) {
        return this.leftOuterJoin(name);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftOuterJoin(TableLike<?> table) {
        return this.join(table, JoinType.LEFT_OUTER_JOIN);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftOuterJoin(SQL sql) {
        return this.leftOuterJoin(DSL.table(sql));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftOuterJoin(String sql) {
        return this.leftOuterJoin(DSL.table(sql));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftOuterJoin(String sql, Object ... bindings) {
        return this.leftOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftOuterJoin(String sql, QueryPart ... parts) {
        return this.leftOuterJoin(DSL.table(sql, parts));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> leftOuterJoin(Name name) {
        return this.leftOuterJoin(DSL.table(name));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightJoin(TableLike<?> table) {
        return this.rightOuterJoin(table);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightJoin(SQL sql) {
        return this.rightOuterJoin(sql);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightJoin(String sql) {
        return this.rightOuterJoin(sql);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightJoin(String sql, Object ... bindings) {
        return this.rightOuterJoin(sql, bindings);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightJoin(String sql, QueryPart ... parts) {
        return this.rightOuterJoin(sql, parts);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightJoin(Name name) {
        return this.rightOuterJoin(name);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightOuterJoin(TableLike<?> table) {
        return this.join(table, JoinType.RIGHT_OUTER_JOIN);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightOuterJoin(SQL sql) {
        return this.rightOuterJoin(DSL.table(sql));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightOuterJoin(String sql) {
        return this.rightOuterJoin(DSL.table(sql));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightOuterJoin(String sql, Object ... bindings) {
        return this.rightOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightOuterJoin(String sql, QueryPart ... parts) {
        return this.rightOuterJoin(DSL.table(sql, parts));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> rightOuterJoin(Name name) {
        return this.rightOuterJoin(DSL.table(name));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullJoin(TableLike<?> table) {
        return this.fullOuterJoin(table);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullJoin(SQL sql) {
        return this.fullOuterJoin(sql);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullJoin(String sql) {
        return this.fullOuterJoin(sql);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullJoin(String sql, Object ... bindings) {
        return this.fullOuterJoin(sql, bindings);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullJoin(String sql, QueryPart ... parts) {
        return this.fullOuterJoin(sql, parts);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullJoin(Name name) {
        return this.fullOuterJoin(name);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullOuterJoin(TableLike<?> table) {
        return this.join(table, JoinType.FULL_OUTER_JOIN);
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullOuterJoin(SQL sql) {
        return this.fullOuterJoin(DSL.table(sql));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullOuterJoin(String sql) {
        return this.fullOuterJoin(DSL.table(sql));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullOuterJoin(String sql, Object ... bindings) {
        return this.fullOuterJoin(DSL.table(sql, bindings));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullOuterJoin(String sql, QueryPart ... parts) {
        return this.fullOuterJoin(DSL.table(sql, parts));
    }

    @Override
    @Pro
    public final TablePartitionByStep<Record> fullOuterJoin(Name name) {
        return this.fullOuterJoin(DSL.table(name));
    }
}

