/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.JoinType;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnConditionStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.TableOptions;
import org.jooq.TableOuterJoinStep;
import org.jooq.TablePartitionByStep;
import org.jooq.Traverser;
import org.jooq.conf.RenderOptionalKeyword;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.Alias;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.Patterns;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.TableAlias;
import org.jooq.impl.Tools;

abstract class JoinTable<J extends JoinTable<J>>
extends AbstractTable<Record>
implements TableOuterJoinStep<Record>,
TableOptionalOnStep<Record>,
TablePartitionByStep<Record>,
TableOnConditionStep<Record> {
    static final Clause[] CLAUSES = new Clause[]{Clause.TABLE, Clause.TABLE_JOIN};
    @Pro
    static final Set<SQLDialect> EMULATE_CROSS_JOIN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE);
    @Pro
    static final Set<SQLDialect> EMULATE_LATERAL = SQLDialect.supportedBy(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SYBASE);
    static final Set<SQLDialect> EMULATE_NATURAL_JOIN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    static final Set<SQLDialect> EMULATE_NATURAL_OUTER_JOIN = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    static final Set<SQLDialect> EMULATE_JOIN_USING = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    static final Set<SQLDialect> EMULATE_APPLY = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB);
    final Table<?> lhs;
    final Table<?> rhs;
    final QueryPartList<Field<?>> lhsPartitionBy;
    final QueryPartList<Field<?>> rhsPartitionBy;
    final JoinType type;
    final ConditionProviderImpl condition;
    final QueryPartList<Field<?>> using;

    JoinTable(TableLike<?> lhs, TableLike<?> rhs, JoinType type) {
        this(lhs, rhs, type, Collections.emptyList());
    }

    JoinTable(TableLike<?> lhs, TableLike<?> rhs, JoinType type, Collection<? extends Field<?>> lhsPartitionBy) {
        super(TableOptions.expression(), Names.N_JOIN);
        this.lhs = lhs.asTable();
        this.rhs = rhs.asTable();
        this.lhsPartitionBy = new QueryPartList((Iterable<Field<?>>)lhsPartitionBy);
        this.rhsPartitionBy = new QueryPartList();
        this.type = type;
        this.condition = new ConditionProviderImpl();
        this.using = new QueryPartList();
    }

    @Deprecated
    final J transform(Table<?> newLhs, Table<?> newRhs) {
        if (this.lhs == newLhs && this.rhs == newRhs) {
            return (J)this;
        }
        return this.construct(newLhs, this.lhsPartitionBy, this.rhsPartitionBy, newRhs, this.condition, this.using);
    }

    @Override
    public final List<ForeignKey<Record, ?>> getReferences() {
        List<ForeignKey<?, ?>> lhsReferences = this.lhs.getReferences();
        List<ForeignKey<?, ?>> rhsReferences = this.rhs.getReferences();
        ArrayList result = new ArrayList(lhsReferences.size() + rhsReferences.size());
        result.addAll(lhsReferences);
        result.addAll(rhsReferences);
        return result;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if (EMULATE_LATERAL.contains((Object)ctx.dialect()) && this.rhs instanceof QOM.Lateral) {
            this.acceptLateralEmulation(ctx);
            return;
        }
        this.accept0(ctx);
    }

    private final void accept0(Context<?> ctx) {
        JoinType translatedType = this.translateType(ctx);
        Clause translatedClause = this.translateClause(translatedType);
        Keyword keyword = this.translateKeyword(ctx, translatedType);
        this.toSQLTable(ctx, this.lhs);
        if (!this.lhsPartitionBy.isEmpty()) {
            ctx.formatSeparator().start(Clause.TABLE_JOIN_PARTITION_BY).visit(Keywords.K_PARTITION_BY).sql(" (").visit(this.lhsPartitionBy).sql(')').end(Clause.TABLE_JOIN_PARTITION_BY);
        }
        switch (translatedType) {
            case LEFT_SEMI_JOIN: 
            case LEFT_ANTI_JOIN: {
                if (!Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_COLLECT_SEMI_ANTI_JOIN))) break;
                ArrayList<Condition> semiAntiJoinPredicates = (ArrayList<Condition>)ctx.data(Tools.SimpleDataKey.DATA_COLLECTED_SEMI_ANTI_JOIN);
                if (semiAntiJoinPredicates == null) {
                    semiAntiJoinPredicates = new ArrayList<Condition>();
                    ctx.data(Tools.SimpleDataKey.DATA_COLLECTED_SEMI_ANTI_JOIN, semiAntiJoinPredicates);
                }
                Condition c = !this.using.isEmpty() ? this.usingCondition() : this.condition;
                switch (translatedType) {
                    case LEFT_SEMI_JOIN: {
                        semiAntiJoinPredicates.add(DSL.exists(DSL.selectOne().from((TableLike<?>)this.rhs).where(c)));
                        break;
                    }
                    case LEFT_ANTI_JOIN: {
                        semiAntiJoinPredicates.add(DSL.notExists(DSL.selectOne().from((TableLike<?>)this.rhs).where(c)));
                    }
                }
                return;
            }
        }
        ctx.formatIndentStart().formatSeparator().start(translatedClause).visit(keyword).sql(' ');
        this.toSQLTable(ctx, this.rhs);
        if (!this.rhsPartitionBy.isEmpty()) {
            ctx.formatSeparator().start(Clause.TABLE_JOIN_PARTITION_BY).visit(Keywords.K_PARTITION_BY).sql(" (").visit(this.rhsPartitionBy).sql(')').end(Clause.TABLE_JOIN_PARTITION_BY);
        }
        if (translatedType.qualified()) {
            ctx.formatIndentStart();
            this.toSQLJoinCondition(ctx);
            ctx.formatIndentEnd();
        } else if (JoinType.OUTER_APPLY == translatedType && EMULATE_APPLY.contains((Object)ctx.dialect())) {
            ctx.formatIndentStart().formatSeparator().start(Clause.TABLE_JOIN_ON).visit(Keywords.K_ON).sql(' ').visit(DSL.trueCondition()).end(Clause.TABLE_JOIN_ON).formatIndentEnd();
        }
        ctx.end(translatedClause).formatIndentEnd();
    }

    @Pro
    private final void acceptLateralEmulation(Context<?> ctx) {
        switch (this.type) {
            case CROSS_JOIN: {
                ctx.visit(this.lhs.crossApply((TableLike)((QOM.Lateral)this.rhs).$arg1()));
                break;
            }
            case JOIN: 
            case LEFT_OUTER_JOIN: {
                if (this.using.isEmpty() && (Patterns.isEffectivelyTrue(this.condition) || Patterns.isEffectivelyTrue((Condition)Patterns.replace(ctx.configuration(), this.condition)))) {
                    if (this.type == JoinType.JOIN) {
                        ctx.visit(this.lhs.crossApply((TableLike)((QOM.Lateral)this.rhs).$arg1()));
                        break;
                    }
                    ctx.visit(this.lhs.outerApply((TableLike)((QOM.Lateral)this.rhs).$arg1()));
                    break;
                }
                this.accept0(ctx);
                break;
            }
            default: {
                this.accept0(ctx);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.lhs).sqlIndentStart().sql('.');
        boolean inner = ctx.settings().getRenderOptionalInnerKeyword() == RenderOptionalKeyword.ON;
        boolean outer = ctx.settings().getRenderOptionalOuterKeyword() == RenderOptionalKeyword.ON;
        switch (this.type) {
            case CROSS_APPLY: {
                ctx.sql("crossApply");
                break;
            }
            case CROSS_JOIN: {
                ctx.sql("crossJoin");
                break;
            }
            case FULL_OUTER_JOIN: {
                ctx.sql(outer ? "fullOuterJoin" : "fullJoin");
                break;
            }
            case JOIN: {
                ctx.sql(inner ? "innerJoin" : "join");
                break;
            }
            case LEFT_ANTI_JOIN: {
                ctx.sql("leftAntiJoin");
                break;
            }
            case LEFT_OUTER_JOIN: {
                ctx.sql(outer ? "leftOuterJoin" : "leftJoin");
                break;
            }
            case LEFT_SEMI_JOIN: {
                ctx.sql("leftSemiJoin");
                break;
            }
            case NATURAL_FULL_OUTER_JOIN: {
                ctx.sql("naturalFullOuterJoin");
                break;
            }
            case NATURAL_JOIN: {
                ctx.sql("naturalJoin");
                break;
            }
            case NATURAL_LEFT_OUTER_JOIN: {
                ctx.sql("naturalLeftOuterJoin");
                break;
            }
            case NATURAL_RIGHT_OUTER_JOIN: {
                ctx.sql("naturalRightOuterJoin");
                break;
            }
            case OUTER_APPLY: {
                ctx.sql("outerApply");
                break;
            }
            case RIGHT_OUTER_JOIN: {
                ctx.sql(outer ? "rightOuterJoin" : "rightJoin");
                break;
            }
            case STRAIGHT_JOIN: {
                ctx.sql("straightJoin");
            }
        }
        ctx.sql('(').visit(this.rhs).sql(')');
        if (this.type.qualified()) {
            ctx.sqlIndentStart();
            if (!this.using.isEmpty()) {
                ctx.sql(".using(").visit(this.using).sql(')');
            } else {
                ctx.sql(".on(").visit(this.condition).sql(')');
            }
            ctx.sqlIndentEnd();
        }
        ctx.sqlIndentEnd();
    }

    private final Keyword translateKeyword(Context<?> ctx, JoinType translatedType) {
        Keyword keyword = switch (translatedType) {
            case JoinType.JOIN, JoinType.NATURAL_JOIN -> {
                if (ctx.settings().getRenderOptionalInnerKeyword() == RenderOptionalKeyword.ON) {
                    yield translatedType.toKeyword(true);
                }
                if (ctx.family() == SQLDialect.ACCESS) {
                    yield translatedType.toKeyword(true);
                }
                yield translatedType.toKeyword();
            }
            case JoinType.LEFT_OUTER_JOIN, JoinType.FULL_OUTER_JOIN, JoinType.NATURAL_FULL_OUTER_JOIN, JoinType.NATURAL_LEFT_OUTER_JOIN, JoinType.NATURAL_RIGHT_OUTER_JOIN, JoinType.RIGHT_OUTER_JOIN -> {
                if (ctx.settings().getRenderOptionalOuterKeyword() == RenderOptionalKeyword.OFF) {
                    yield translatedType.toKeyword(false);
                }
                if (ctx.family() == SQLDialect.ACCESS) {
                    yield translatedType.toKeyword();
                }
                yield translatedType.toKeyword();
            }
            default -> translatedType.toKeyword();
        };
        if (translatedType == JoinType.CROSS_APPLY && EMULATE_APPLY.contains((Object)ctx.dialect())) {
            keyword = Keywords.K_CROSS_JOIN_LATERAL;
        } else if (translatedType == JoinType.OUTER_APPLY && EMULATE_APPLY.contains((Object)ctx.dialect())) {
            keyword = ctx.settings().getRenderOptionalOuterKeyword() == RenderOptionalKeyword.OFF ? Keywords.K_LEFT_JOIN_LATERAL : Keywords.K_LEFT_OUTER_JOIN_LATERAL;
        }
        return keyword;
    }

    private void toSQLTable(Context<?> ctx, Table<?> table) {
        boolean wrap;
        boolean bl = wrap = table instanceof JoinTable && (table == this.rhs || ctx.family() == SQLDialect.ACCESS);
        if (wrap) {
            ctx.sqlIndentStart('(');
        }
        Tools.visitAutoAliased(ctx, table, Context::declareTables, (c, t2) -> c.visit((QueryPart)t2));
        if (wrap) {
            ctx.sqlIndentEnd(')');
        }
    }

    final Clause translateClause(JoinType translatedType) {
        switch (translatedType) {
            case JOIN: {
                return Clause.TABLE_JOIN_INNER;
            }
            case CROSS_JOIN: {
                return Clause.TABLE_JOIN_CROSS;
            }
            case NATURAL_JOIN: {
                return Clause.TABLE_JOIN_NATURAL;
            }
            case LEFT_OUTER_JOIN: {
                return Clause.TABLE_JOIN_OUTER_LEFT;
            }
            case RIGHT_OUTER_JOIN: {
                return Clause.TABLE_JOIN_OUTER_RIGHT;
            }
            case FULL_OUTER_JOIN: {
                return Clause.TABLE_JOIN_OUTER_FULL;
            }
            case NATURAL_LEFT_OUTER_JOIN: {
                return Clause.TABLE_JOIN_NATURAL_OUTER_LEFT;
            }
            case NATURAL_RIGHT_OUTER_JOIN: {
                return Clause.TABLE_JOIN_NATURAL_OUTER_RIGHT;
            }
            case NATURAL_FULL_OUTER_JOIN: {
                return Clause.TABLE_JOIN_NATURAL_OUTER_FULL;
            }
            case CROSS_APPLY: {
                return Clause.TABLE_JOIN_CROSS_APPLY;
            }
            case OUTER_APPLY: {
                return Clause.TABLE_JOIN_OUTER_APPLY;
            }
            case LEFT_SEMI_JOIN: {
                return Clause.TABLE_JOIN_SEMI_LEFT;
            }
            case LEFT_ANTI_JOIN: {
                return Clause.TABLE_JOIN_ANTI_LEFT;
            }
            case STRAIGHT_JOIN: {
                return Clause.TABLE_JOIN_STRAIGHT;
            }
        }
        throw new IllegalArgumentException("Bad join type: " + String.valueOf((Object)translatedType));
    }

    final JoinType translateType(Context<?> ctx) {
        if (this.emulateCrossJoin(ctx)) {
            return JoinType.JOIN;
        }
        if (this.emulateNaturalJoin(ctx)) {
            return JoinType.JOIN;
        }
        if (this.emulateNaturalLeftOuterJoin(ctx)) {
            return JoinType.LEFT_OUTER_JOIN;
        }
        if (this.emulateNaturalRightOuterJoin(ctx)) {
            return JoinType.RIGHT_OUTER_JOIN;
        }
        if (this.emulateNaturalFullOuterJoin(ctx)) {
            return JoinType.FULL_OUTER_JOIN;
        }
        return this.type;
    }

    private final boolean emulateCrossJoin(Context<?> ctx) {
        return this.type == JoinType.CROSS_JOIN && EMULATE_CROSS_JOIN.contains((Object)ctx.dialect());
    }

    private final boolean emulateNaturalJoin(Context<?> ctx) {
        return this.type == JoinType.NATURAL_JOIN && EMULATE_NATURAL_JOIN.contains((Object)ctx.dialect());
    }

    private final boolean emulateNaturalLeftOuterJoin(Context<?> ctx) {
        return this.type == JoinType.NATURAL_LEFT_OUTER_JOIN && EMULATE_NATURAL_OUTER_JOIN.contains((Object)ctx.dialect());
    }

    private final boolean emulateNaturalRightOuterJoin(Context<?> ctx) {
        return this.type == JoinType.NATURAL_RIGHT_OUTER_JOIN && EMULATE_NATURAL_OUTER_JOIN.contains((Object)ctx.dialect());
    }

    private final boolean emulateNaturalFullOuterJoin(Context<?> ctx) {
        return this.type == JoinType.NATURAL_FULL_OUTER_JOIN && EMULATE_NATURAL_OUTER_JOIN.contains((Object)ctx.dialect());
    }

    private final void toSQLJoinCondition(Context<?> ctx) {
        if (!this.using.isEmpty()) {
            if (EMULATE_JOIN_USING.contains((Object)ctx.dialect())) {
                this.toSQLJoinCondition(ctx, this.usingCondition());
            } else {
                ctx.formatSeparator().start(Clause.TABLE_JOIN_USING).visit(Keywords.K_USING).sql(" (").visit(QueryPartListView.wrap(this.using).qualify(false)).sql(')').end(Clause.TABLE_JOIN_USING);
            }
        } else if (this.emulateNaturalJoin(ctx) || this.emulateNaturalLeftOuterJoin(ctx) || this.emulateNaturalRightOuterJoin(ctx) || this.emulateNaturalFullOuterJoin(ctx)) {
            this.toSQLJoinCondition(ctx, this.naturalCondition());
        } else {
            this.toSQLJoinCondition(ctx, this.condition);
        }
    }

    final Condition naturalCondition() {
        ArrayList<Condition> conditions = new ArrayList<Condition>(this.using.size());
        for (Field<?> field2 : this.lhs.fields()) {
            Field<?> other = this.rhs.field(field2);
            if (other == null) continue;
            conditions.add(field2.eq(other));
        }
        return DSL.and(conditions);
    }

    final Condition usingCondition() {
        return DSL.and(Tools.map(this.using, f -> Tools.qualify(this.lhs, f).eq(Tools.qualify(this.rhs, f))));
    }

    private final void toSQLJoinCondition(Context<?> context, Condition c) {
        context.formatSeparator().start(Clause.TABLE_JOIN_ON).visit(Keywords.K_ON).sql(' ').visit(c).end(Clause.TABLE_JOIN_ON);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Table<Record> as(Name alias) {
        return new TableAlias<Record>(this, alias, c -> true);
    }

    @Override
    public final Table<Record> as(Name alias, Name ... fieldAliases) {
        return new TableAlias<Record>(this, alias, fieldAliases, c -> true);
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        if (this.type == JoinType.LEFT_SEMI_JOIN || this.type == JoinType.LEFT_ANTI_JOIN) {
            return this.lhs.getRecordType();
        }
        return RecordImplN.class;
    }

    @Override
    final FieldsImpl<Record> fields0() {
        if (this.type == JoinType.LEFT_SEMI_JOIN || this.type == JoinType.LEFT_ANTI_JOIN) {
            return new FieldsImpl<Record>(this.lhs.asTable().fields());
        }
        Field<?>[] l = this.lhs.asTable().fields();
        Field<?>[] r = this.rhs.asTable().fields();
        Field[] all = new Field[l.length + r.length];
        System.arraycopy(l, 0, all, 0, l.length);
        System.arraycopy(r, 0, all, l.length, r.length);
        return new FieldsImpl<Record>(all);
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }

    public final J partitionBy(Field<?> ... fields) {
        return this.partitionBy0(Tools.list(fields));
    }

    public final J partitionBy(Collection<? extends Field<?>> fields) {
        return this.partitionBy0(fields);
    }

    final J partitionBy0(Collection<? extends Field<?>> fields) {
        this.rhsPartitionBy.addAll((Collection<Field<?>>)fields);
        return (J)this;
    }

    public final J on(Condition conditions) {
        this.condition.addConditions(conditions);
        return (J)this;
    }

    public final J on(Condition ... conditions) {
        this.condition.addConditions(conditions);
        return (J)this;
    }

    public final J on(Field<Boolean> c) {
        return (J)this.on(DSL.condition(c));
    }

    public final J on(SQL sql) {
        this.and(sql);
        return (J)this;
    }

    public final J on(String sql) {
        this.and(sql);
        return (J)this;
    }

    public final J on(String sql, Object ... bindings) {
        this.and(sql, bindings);
        return (J)this;
    }

    public final J on(String sql, QueryPart ... parts) {
        this.and(sql, parts);
        return (J)this;
    }

    public final J onKey() throws DataAccessException {
        List<ForeignKey<?, ?>> leftToRight = this.lhs.getReferencesTo(this.rhs);
        List<ForeignKey<?, ?>> rightToLeft = this.rhs.getReferencesTo(this.lhs);
        if (leftToRight.size() == 1 && rightToLeft.size() == 0) {
            return this.onKey(leftToRight.get(0), this.lhs, this.rhs);
        }
        if (rightToLeft.size() == 1 && leftToRight.size() == 0) {
            return this.onKey(rightToLeft.get(0), this.rhs, this.lhs);
        }
        if (rightToLeft.isEmpty() && leftToRight.isEmpty()) {
            throw this.onKeyException(OnKeyExceptionReason.NOT_FOUND, null, null);
        }
        throw this.onKeyException(OnKeyExceptionReason.AMBIGUOUS, leftToRight, rightToLeft);
    }

    public final J onKey(TableField<?, ?> ... keyFields) throws DataAccessException {
        if (keyFields != null && keyFields.length > 0) {
            ArrayList unaliased = new ArrayList(Arrays.asList(keyFields));
            for (int i = 0; i < unaliased.size(); ++i) {
                TableField f = (TableField)unaliased.get(i);
                Alias alias = Tools.alias(f.getTable());
                if (alias == null) continue;
                unaliased.set(i, (TableField)alias.wrapped().field(f));
            }
            for (boolean unalias : new boolean[]{false, true}) {
                if (Tools.containsTable(this.lhs, keyFields[0].getTable(), unalias)) {
                    for (ForeignKey<?, ?> key : this.lhs.getReferences()) {
                        if (!key.getFields().containsAll(unaliased) || !unaliased.containsAll(key.getFields())) continue;
                        return this.onKey(key, this.lhs, this.rhs);
                    }
                    for (ForeignKey<?, ?> key : this.lhs.getReferences()) {
                        if (!key.getFields().containsAll(unaliased)) continue;
                        return this.onKey(key, this.lhs, this.rhs);
                    }
                    continue;
                }
                if (!Tools.containsTable(this.rhs, keyFields[0].getTable(), unalias)) continue;
                for (ForeignKey<?, ?> key : this.rhs.getReferences()) {
                    if (!key.getFields().containsAll(unaliased) || !unaliased.containsAll(key.getFields())) continue;
                    return this.onKey(key, this.rhs, this.lhs);
                }
                for (ForeignKey<?, ?> key : this.rhs.getReferences()) {
                    if (!key.getFields().containsAll(unaliased)) continue;
                    return this.onKey(key, this.rhs, this.lhs);
                }
            }
        }
        throw this.onKeyException(OnKeyExceptionReason.NOT_FOUND, null, null);
    }

    public final J onKey(ForeignKey<?, ?> key) {
        if (Tools.containsUnaliasedTable(this.lhs, key.getTable()) && Tools.containsUnaliasedTable(this.rhs, key.getKey().getTable())) {
            return this.onKey(key, this.lhs, this.rhs);
        }
        if (Tools.containsUnaliasedTable(this.rhs, key.getTable()) && Tools.containsUnaliasedTable(this.lhs, key.getKey().getTable())) {
            return this.onKey(key, this.rhs, this.lhs);
        }
        throw this.onKeyException(OnKeyExceptionReason.NOT_FOUND, null, null);
    }

    private final J onKey(ForeignKey<?, ?> key, Table<?> fk, Table<?> pk) {
        return (J)this.and(JoinTable.onKey0(key, fk, pk));
    }

    static final Condition onKey0(ForeignKey<?, ?> key, Table<?> fk, Table<?> pk) {
        Condition result = DSL.noCondition();
        TableField<R, ?>[] references = key.getFieldsArray();
        TableField<?, ?>[] referenced = key.getKeyFieldsArray();
        for (int i = 0; i < references.length; ++i) {
            Field f1 = fk.field(references[i]);
            Field f2 = pk.field(referenced[i]);
            result = result.and(f1.equal(f2));
        }
        return result;
    }

    private final DataAccessException onKeyException(OnKeyExceptionReason reason, List<?> leftToRight, List<?> rightToLeft) {
        switch (reason) {
            case AMBIGUOUS: {
                return new DataAccessException("Key ambiguous between tables [" + String.valueOf(this.lhs) + "] and [" + String.valueOf(this.rhs) + "]. Found: " + String.valueOf(leftToRight) + " and " + String.valueOf(rightToLeft));
            }
        }
        return new DataAccessException("No matching Key found between tables [" + String.valueOf(this.lhs) + "] and [" + String.valueOf(this.rhs) + "]");
    }

    public final J using(Field<?> ... fields) {
        return (J)this.using(Arrays.asList(fields));
    }

    public final J using(Collection<? extends Field<?>> fields) {
        this.using.addAll((Collection<Field<?>>)fields);
        return (J)this;
    }

    public final J and(Condition c) {
        this.condition.addConditions(c);
        return (J)this;
    }

    public final J and(Field<Boolean> c) {
        return (J)this.and(DSL.condition(c));
    }

    public final J and(SQL sql) {
        return (J)this.and(DSL.condition(sql));
    }

    public final J and(String sql) {
        return (J)this.and(DSL.condition(sql));
    }

    public final J and(String sql, Object ... bindings) {
        return (J)this.and(DSL.condition(sql, bindings));
    }

    public final J and(String sql, QueryPart ... parts) {
        return (J)this.and(DSL.condition(sql, parts));
    }

    public final J andNot(Condition c) {
        return (J)this.and(c.not());
    }

    public final J andNot(Field<Boolean> c) {
        return (J)this.andNot(DSL.condition(c));
    }

    public final J andExists(Select<?> select) {
        return (J)this.and(DSL.exists(select));
    }

    public final J andNotExists(Select<?> select) {
        return (J)this.and(DSL.notExists(select));
    }

    public final J or(Condition c) {
        this.condition.addConditions(Operator.OR, c);
        return (J)this;
    }

    public final J or(Field<Boolean> c) {
        return (J)this.or(DSL.condition(c));
    }

    public final J or(SQL sql) {
        return (J)this.or(DSL.condition(sql));
    }

    public final J or(String sql) {
        return (J)this.or(DSL.condition(sql));
    }

    public final J or(String sql, Object ... bindings) {
        return (J)this.or(DSL.condition(sql, bindings));
    }

    public final J or(String sql, QueryPart ... parts) {
        return (J)this.or(DSL.condition(sql, parts));
    }

    public final J orNot(Condition c) {
        return (J)this.or(c.not());
    }

    public final J orNot(Field<Boolean> c) {
        return (J)this.orNot(DSL.condition(c));
    }

    public final J orExists(Select<?> select) {
        return (J)this.or(DSL.exists(select));
    }

    public final J orNotExists(Select<?> select) {
        return (J)this.or(DSL.notExists(select));
    }

    public final J join(TableLike<?> table, JoinType type) {
        return (J)((JoinTable)super.join(table, type));
    }

    abstract J construct(Table<?> var1, Collection<? extends Field<?>> var2, Collection<? extends Field<?>> var3, Table<?> var4, Condition var5, Collection<? extends Field<?>> var6);

    public final Table<?> $table1() {
        return this.lhs;
    }

    public final J $table1(Table<?> t1) {
        return this.construct(t1, this.$partitionBy1(), this.$partitionBy2(), this.$table2(), this.$on(), this.$using());
    }

    public final QOM.UnmodifiableList<Field<?>> $partitionBy1() {
        return QOM.unmodifiable(this.lhsPartitionBy);
    }

    public final J $partitionBy1(Collection<? extends Field<?>> p1) {
        return this.construct(this.$table1(), p1, this.$partitionBy2(), this.$table2(), this.$on(), this.$using());
    }

    public final QOM.UnmodifiableList<Field<?>> $partitionBy2() {
        return QOM.unmodifiable(this.rhsPartitionBy);
    }

    public final J $partitionBy2(Collection<? extends Field<?>> p2) {
        return this.construct(this.$table1(), this.$partitionBy1(), p2, this.$table2(), this.$on(), this.$using());
    }

    public final Table<?> $table2() {
        return this.rhs;
    }

    public final J $table2(Table<?> t2) {
        return this.construct(this.$table1(), this.$partitionBy1(), this.$partitionBy2(), t2, this.$on(), this.$using());
    }

    public final Condition $on() {
        return this.condition.getWhereOrNull();
    }

    public final J $on(Condition o) {
        return this.construct(this.$table1(), this.$partitionBy1(), this.$partitionBy2(), this.$table2(), o, Collections.emptyList());
    }

    public final QOM.UnmodifiableList<Field<?>> $using() {
        return QOM.unmodifiable(this.using);
    }

    public final J $using(Collection<? extends Field<?>> u) {
        return this.construct(this.$table1(), this.$partitionBy1(), this.$partitionBy2(), this.$table2(), null, u);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$table1(), this.$partitionBy1(), this.$partitionBy2(), this.$table2(), this.$on(), this.$using());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$table1(), this.$partitionBy1(), this.$partitionBy2(), this.$table2(), this.$on(), this.$using(), this::construct, replacer);
    }

    private static enum OnKeyExceptionReason {
        AMBIGUOUS,
        NOT_FOUND;

    }
}

