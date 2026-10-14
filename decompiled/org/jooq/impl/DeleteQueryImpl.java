/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DeleteQuery;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.ConditionProviderImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.JoinTable;
import org.jooq.impl.Keywords;
import org.jooq.impl.NoField;
import org.jooq.impl.QOM;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.TableList;
import org.jooq.impl.Tools;
import org.jooq.impl.WithImpl;

final class DeleteQueryImpl<R extends Record>
extends AbstractDMLQuery<R>
implements DeleteQuery<R>,
QOM.Delete<R> {
    private static final Clause[] CLAUSES = new Clause[]{Clause.DELETE};
    private static final Set<SQLDialect> SPECIAL_DELETE_AS_SYNTAX = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final Set<SQLDialect> NO_SUPPORT_LIMIT = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2_10, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_ORDER_BY_LIMIT = SQLDialect.supportedBy(SQLDialect.IGNITE, SQLDialect.MEMSQL);
    private static final Set<SQLDialect> SUPPORT_MULTITABLE_DELETE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MYSQL);
    private static final Set<SQLDialect> REQUIRE_REPEAT_FROM_IN_USING = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MYSQL);
    private static final Set<SQLDialect> NO_SUPPORT_REPEAT_FROM_IN_USING = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> REQUIRES_WHERE = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
    @Pro
    private static final Set<SQLDialect> SUPPORT_USING_AS_FROM = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private final TableList using = new TableList();
    private final ConditionProviderImpl condition = new ConditionProviderImpl();
    private final SortFieldList orderBy = new SortFieldList();
    private Field<? extends Number> limit;

    DeleteQueryImpl(Configuration configuration, WithImpl with, Table<R> table) {
        super(configuration, with, table);
    }

    final Condition getWhere() {
        return this.condition.getWhere();
    }

    final boolean hasWhere() {
        return this.condition.hasWhere();
    }

    final TableList getUsing() {
        return this.using;
    }

    @Override
    public final void addUsing(Collection<? extends TableLike<?>> f) {
        for (TableLike<?> provider : f) {
            this.using.add(provider.asTable());
        }
    }

    @Override
    public final void addUsing(TableLike<?> f) {
        this.using.add(f.asTable());
    }

    @Override
    public final void addUsing(TableLike<?> ... f) {
        for (TableLike<?> provider : f) {
            this.using.add(provider.asTable());
        }
    }

    @Override
    public final void addConditions(Collection<? extends Condition> conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Condition conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Condition ... conditions) {
        this.condition.addConditions(conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Condition ... conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addConditions(Operator operator, Collection<? extends Condition> conditions) {
        this.condition.addConditions(operator, conditions);
    }

    @Override
    public final void addOrderBy(OrderField<?> ... fields) {
        this.addOrderBy(Arrays.asList(fields));
    }

    @Override
    public final void addOrderBy(Collection<? extends OrderField<?>> fields) {
        this.orderBy.addAll(Tools.sortFields(fields));
    }

    @Override
    public final void addLimit(Number numberOfRows) {
        this.addLimit(DSL.val(numberOfRows));
    }

    @Override
    public final void addLimit(Field<? extends Number> numberOfRows) {
        if (numberOfRows instanceof NoField) {
            return;
        }
        this.limit = numberOfRows;
    }

    @Override
    final void accept0(Context<?> ctx) {
        boolean noSupportParametersInWhere;
        ctx.start(Clause.DELETE_DELETE).visit(Keywords.K_DELETE).sql(' ');
        Table<?> t2 = this.table(ctx);
        boolean multiTableJoin = SUPPORT_MULTITABLE_DELETE.contains((Object)ctx.dialect()) && t2 instanceof JoinTable;
        boolean specialDeleteAsSyntax = SPECIAL_DELETE_AS_SYNTAX.contains((Object)ctx.dialect());
        if (multiTableJoin) {
            ctx.visit(Keywords.K_FROM).sql(' ').visit(Tools.traverseJoins(t2, new TableList(), null, (r, x) -> {
                r.add(x);
                return r;
            })).formatSeparator();
        } else if (ctx.family() == SQLDialect.TERADATA && !this.using.isEmpty()) {
            ctx.declareTables(true, c -> c.visit(t2));
        } else {
            ctx.visit(Keywords.K_FROM).sql(' ').declareTables(!specialDeleteAsSyntax, c -> c.visit(t2));
        }
        if (!this.using.isEmpty() || multiTableJoin || specialDeleteAsSyntax && Tools.alias(t2) != null) {
            TableList u;
            if (REQUIRE_REPEAT_FROM_IN_USING.contains((Object)ctx.dialect()) && !Tools.containsDeclaredTable(this.using, t2)) {
                u = new TableList(t2);
                u.addAll(this.using);
            } else if (NO_SUPPORT_REPEAT_FROM_IN_USING.contains((Object)ctx.dialect()) && Tools.containsDeclaredTable(this.using, t2)) {
                u = new TableList(this.using);
                u.remove(t2);
            } else {
                u = this.using;
            }
            ctx.formatSeparator().visit(SUPPORT_USING_AS_FROM.contains((Object)ctx.dialect()) ? Keywords.K_FROM : Keywords.K_USING).sql(' ').declareTables(true, c -> c.visit(u));
        }
        ctx.end(Clause.DELETE_DELETE);
        this.toSQLOutput(ctx);
        boolean bl = noSupportParametersInWhere = ctx.family() == SQLDialect.EXASOL;
        if (this.limit != null && NO_SUPPORT_LIMIT.contains((Object)ctx.dialect()) || !this.orderBy.isEmpty() && NO_SUPPORT_ORDER_BY_LIMIT.contains((Object)ctx.dialect())) {
            Field[] fieldArray;
            if (this.table().getKeys().isEmpty()) {
                Field[] fieldArray2 = new Field[1];
                fieldArray = fieldArray2;
                fieldArray2[0] = this.table().rowid();
            } else {
                fieldArray = (this.table().getPrimaryKey() != null ? this.table().getPrimaryKey() : this.table().getKeys().get(0)).getFieldsArray();
            }
            Field[] keyFields = fieldArray;
            ctx.start(Clause.DELETE_WHERE).formatSeparator().visit(Keywords.K_WHERE).sql(' ');
            ctx.paramTypeIf(ParamType.INLINED, noSupportParametersInWhere, c -> {
                if (keyFields.length == 1) {
                    c.visit(keyFields[0].in(DSL.select(keyFields[0]).from((TableLike<?>)this.table()).where(this.getWhere()).orderBy(this.orderBy).limit(this.limit)));
                } else {
                    c.visit(DSL.row(keyFields).in(DSL.select(keyFields).from((TableLike<?>)this.table()).where(this.getWhere()).orderBy(this.orderBy).limit(this.limit)));
                }
            });
            ctx.end(Clause.DELETE_WHERE);
        } else {
            ctx.start(Clause.DELETE_WHERE);
            if (this.hasWhere()) {
                ctx.paramTypeIf(ParamType.INLINED, noSupportParametersInWhere, c -> c.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(this.getWhere()));
            } else if (REQUIRES_WHERE.contains((Object)ctx.dialect())) {
                ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(DSL.trueCondition());
            }
            ctx.end(Clause.DELETE_WHERE);
            if (!this.orderBy.isEmpty()) {
                ctx.formatSeparator().visit(Keywords.K_ORDER_BY).sql(' ').visit(this.orderBy);
            }
            if (this.limit != null) {
                ctx.formatSeparator().visit(Keywords.K_LIMIT).sql(' ').visit(this.limit);
            }
        }
        ctx.start(Clause.DELETE_RETURNING);
        this.toSQLReturning(ctx);
        ctx.end(Clause.DELETE_RETURNING);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final boolean isExecutable() {
        if (!this.condition.hasWhere()) {
            this.executeWithoutWhere("DELETE without WHERE", SettingsTools.getExecuteDeleteWithoutWhere(this.configuration().settings()));
        }
        return super.isExecutable();
    }

    @Override
    @Pro
    final int estimatedRowCount(Scope ctx) {
        return Integer.MAX_VALUE;
    }

    final DeleteQueryImpl<R> copy(Consumer<? super DeleteQueryImpl<R>> finisher) {
        return this.copy(finisher, this.table);
    }

    final <O extends Record> DeleteQueryImpl<O> copy(Consumer<? super DeleteQueryImpl<O>> finisher, Table<O> t2) {
        DeleteQueryImpl<O> r = new DeleteQueryImpl<O>(this.configuration(), this.with, t2);
        r.using.addAll(this.using);
        r.condition.addConditions(ConditionProviderImpl.extractCondition(this.condition));
        r.orderBy.addAll(this.orderBy);
        r.limit = this.limit;
        if (!this.returning.isEmpty()) {
            r.setReturning(this.returning);
        }
        finisher.accept(r);
        return r;
    }

    @Override
    public final WithImpl $with() {
        return this.with;
    }

    @Override
    public final Table<R> $from() {
        return this.table;
    }

    @Override
    public final QOM.Delete<?> $from(Table<?> newFrom) {
        if (this.$from() == newFrom) {
            return this;
        }
        return this.copy(d -> {}, newFrom);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Table<?>> $using() {
        return QOM.unmodifiable(this.using);
    }

    @Override
    public final QOM.Delete<R> $using(Collection<? extends Table<?>> using2) {
        return this.copy(d -> {
            d.using.clear();
            d.using.addAll(using2);
        });
    }

    @Override
    public final Condition $where() {
        return this.condition.getWhereOrNull();
    }

    @Override
    public final QOM.Delete<R> $where(Condition newWhere) {
        if (this.$where() == newWhere) {
            return this;
        }
        return this.copy(d -> d.condition.setWhere(newWhere));
    }

    @Override
    public final QOM.UnmodifiableList<? extends SortField<?>> $orderBy() {
        return QOM.unmodifiable(this.orderBy);
    }

    @Override
    public final QOM.Delete<R> $orderBy(Collection<? extends SortField<?>> newOrderBy) {
        return this.copy(d -> {
            d.orderBy.clear();
            d.orderBy.addAll(newOrderBy);
        });
    }

    @Override
    public final Field<? extends Number> $limit() {
        return this.limit;
    }

    @Override
    public final QOM.Delete<R> $limit(Field<? extends Number> newLimit) {
        if (this.$limit() == newLimit) {
            return this;
        }
        return this.copy(d -> {
            d.limit = newLimit;
        });
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$with(), this.$from(), this.$using(), this.$where(), this.$orderBy(), this.$limit());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$with(), this.$from(), this.$using(), this.$where(), this.$orderBy(), this.$limit(), (cte, f, u, w, o, l) -> {
            DeleteQueryImpl<R> r = new DeleteQueryImpl<R>(this.configuration(), (WithImpl)cte, f);
            r.using.addAll(u);
            r.condition.addConditions(ConditionProviderImpl.extractCondition(w));
            r.orderBy.addAll(o);
            r.limit = l;
            r.setReturning(this.returning);
            return r;
        }, replacer);
    }
}

