/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateIndexFinalStep;
import org.jooq.CreateIndexIncludeStep;
import org.jooq.CreateIndexStep;
import org.jooq.CreateIndexWhereStep;
import org.jooq.Field;
import org.jooq.Function8;
import org.jooq.Index;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.OrderField;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

final class CreateIndexImpl
extends AbstractDDLQuery
implements QOM.CreateIndex,
CreateIndexStep,
CreateIndexIncludeStep,
CreateIndexWhereStep,
CreateIndexFinalStep {
    final boolean unique;
    final Index index;
    final boolean ifNotExists;
    Table<?> table;
    QueryPartListView<? extends OrderField<?>> on;
    QueryPartListView<? extends Field<?>> include;
    Condition where;
    boolean excludeNullKeys;
    private static final Clause[] CLAUSES = new Clause[]{Clause.CREATE_INDEX};
    private static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_SORT_SPEC = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.TERADATA);
    private static final Set<SQLDialect> SUPPORT_UNNAMED_INDEX = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORT_INCLUDE = SQLDialect.supportedBy(SQLDialect.COCKROACHDB, SQLDialect.POSTGRES_11, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORT_UNIQUE_INCLUDE = SQLDialect.supportedBy(SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.POSTGRES_11, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);

    CreateIndexImpl(Configuration configuration, boolean unique, Index index, boolean ifNotExists) {
        this(configuration, unique, index, ifNotExists, null, null, null, null, false);
    }

    CreateIndexImpl(Configuration configuration, boolean unique, boolean ifNotExists) {
        this(configuration, unique, null, ifNotExists);
    }

    CreateIndexImpl(Configuration configuration, boolean unique, Index index, boolean ifNotExists, Table<?> table, Collection<? extends OrderField<?>> on, Collection<? extends Field<?>> include, Condition where, boolean excludeNullKeys) {
        super(configuration);
        this.unique = unique;
        this.index = index;
        this.ifNotExists = ifNotExists;
        this.table = table;
        this.on = new QueryPartList((Iterable<OrderField<?>>)on);
        this.include = new QueryPartList((Iterable<Field<?>>)include);
        this.where = where;
        this.excludeNullKeys = excludeNullKeys;
    }

    @Override
    public final CreateIndexImpl on(String table, String ... on) {
        return this.on((Table)DSL.table(DSL.name(table)), Tools.fieldsByName(on));
    }

    @Override
    public final CreateIndexImpl on(Name table, Name ... on) {
        return this.on((Table)DSL.table(table), Tools.fieldsByName(on));
    }

    @Override
    public final CreateIndexImpl on(Table<?> table, OrderField<?> ... on) {
        return this.on((Table)table, (Collection)Arrays.asList(on));
    }

    @Override
    public final CreateIndexImpl on(String table, Collection<? extends String> on) {
        return this.on((Table)DSL.table(DSL.name(table)), Tools.fieldsByName(on.toArray(Tools.EMPTY_STRING)));
    }

    @Override
    public final CreateIndexImpl on(Name table, Collection<? extends Name> on) {
        return this.on((Table)DSL.table(table), Tools.fieldsByName(on.toArray(Tools.EMPTY_NAME)));
    }

    @Override
    public final CreateIndexImpl on(Table<?> table, Collection<? extends OrderField<?>> on) {
        this.table = table;
        this.on = new QueryPartList((Iterable<OrderField<?>>)on);
        return this;
    }

    @Override
    public final CreateIndexImpl include(String ... include) {
        return this.include((Field[])Tools.fieldsByName(include));
    }

    @Override
    public final CreateIndexImpl include(Name ... include) {
        return this.include((Field[])Tools.fieldsByName(include));
    }

    @Override
    public final CreateIndexImpl include(Field<?> ... include) {
        return this.include(Arrays.asList(include));
    }

    @Override
    public final CreateIndexImpl include(Collection<? extends Field<?>> include) {
        this.include = new QueryPartList((Iterable<Field<?>>)include);
        return this;
    }

    @Override
    public final CreateIndexImpl where(Field<Boolean> where) {
        return this.where(DSL.condition(where));
    }

    @Override
    public final CreateIndexImpl where(Condition ... where) {
        return this.where(DSL.condition(Operator.AND, where));
    }

    @Override
    public final CreateIndexImpl where(Collection<? extends Condition> where) {
        return this.where(DSL.condition(Operator.AND, where));
    }

    @Override
    public final CreateIndexImpl where(Condition where) {
        this.where = where;
        return this;
    }

    @Override
    public final CreateIndexImpl where(String where, QueryPart ... parts) {
        return this.where(DSL.condition(where, parts));
    }

    @Override
    public final CreateIndexImpl where(String where, Object ... bindings) {
        return this.where(DSL.condition(where, bindings));
    }

    @Override
    public final CreateIndexImpl where(String where) {
        return this.where(DSL.condition(where));
    }

    @Override
    public final CreateIndexImpl where(SQL where) {
        return this.where(DSL.condition(where));
    }

    @Override
    public final CreateIndexImpl excludeNullKeys() {
        this.excludeNullKeys = true;
        return this;
    }

    private final boolean supportsIfNotExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.CREATE_INDEX, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        Condition condition;
        ctx.visit(Keywords.K_CREATE);
        if (this.unique) {
            ctx.sql(' ').visit(Keywords.K_UNIQUE);
        }
        ctx.sql(' ').visit(Keywords.K_INDEX).sql(' ');
        if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
            ctx.visit(Keywords.K_IF_NOT_EXISTS).sql(' ');
        }
        if (this.index != null) {
            ctx.visit(this.index).sql(' ');
        } else if (!SUPPORT_UNNAMED_INDEX.contains((Object)ctx.dialect())) {
            ctx.visit(this.generatedName()).sql(' ');
        }
        boolean supportsInclude = this.unique ? SUPPORT_UNIQUE_INCLUDE.contains((Object)ctx.dialect()) : SUPPORT_INCLUDE.contains((Object)ctx.dialect());
        boolean supportsFieldsBeforeTable = ctx.family() == SQLDialect.TERADATA;
        QueryPartListView list = new QueryPartList().qualify(false);
        if (NO_SUPPORT_SORT_SPEC.contains((Object)ctx.dialect())) {
            list.addAll(Tools.map(Tools.sortFields(this.on), s2 -> s2.$field()));
        } else {
            list.addAll(this.on);
        }
        if (!supportsInclude && !this.unique && this.include != null) {
            list.addAll(this.include);
        }
        if (supportsFieldsBeforeTable) {
            ctx.sql('(').visit(list).sql(") ");
        }
        ctx.visit(Keywords.K_ON).sql(' ').visit(this.table);
        if (!supportsFieldsBeforeTable) {
            ctx.sql('(').visit(list).sql(')');
        }
        if (supportsInclude && !this.include.isEmpty()) {
            Keyword keyword = Keywords.K_INCLUDE;
            if (ctx.family() == SQLDialect.COCKROACHDB) {
                keyword = Keywords.K_STORING;
            }
            ctx.formatSeparator().visit(keyword).sql(" (").visit(QueryPartCollectionView.wrap(this.include).qualify(false)).sql(')');
        }
        if ((condition = this.excludeNullKeys && this.where == null && ctx.family() != SQLDialect.DB2 ? (this.on.size() == 1 ? Tools.field(Tools.first(this.on)).isNotNull() : DSL.row(Tools.fields(this.on)).isNotNull()) : this.where) != null && ctx.configuration().data("org.jooq.ddl.ignore-storage-clauses") == null) {
            ctx.formatSeparator().visit(Keywords.K_WHERE).sql(' ').qualify(false, c -> c.visit(condition));
        } else if (this.excludeNullKeys && ctx.family() == SQLDialect.DB2) {
            ctx.formatSeparator().visit(Keywords.K_EXCLUDE).sql(' ').visit(Keywords.K_NULL).sql(' ').visit(Keywords.K_KEYS);
        }
    }

    private final Name generatedName() {
        Name t2 = this.table.getQualifiedName();
        StringBuilder sb = new StringBuilder(this.table.getName());
        for (OrderField orderField : this.on) {
            sb.append('_').append(Tools.field(orderField).getName());
        }
        sb.append("_idx");
        if (t2.qualified()) {
            return t2.qualifier().append(sb.toString());
        }
        return DSL.name(sb.toString());
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final boolean $unique() {
        return this.unique;
    }

    @Override
    public final Index $index() {
        return this.index;
    }

    @Override
    public final boolean $ifNotExists() {
        return this.ifNotExists;
    }

    @Override
    public final Table<?> $table() {
        return this.table;
    }

    @Override
    public final QOM.UnmodifiableList<? extends OrderField<?>> $on() {
        return QOM.unmodifiable(this.on);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $include() {
        return QOM.unmodifiable(this.include);
    }

    @Override
    public final Condition $where() {
        return this.where;
    }

    @Override
    public final boolean $excludeNullKeys() {
        return this.excludeNullKeys;
    }

    @Override
    public final QOM.CreateIndex $unique(boolean newValue) {
        return this.$constructor().apply(newValue, this.$index(), this.$ifNotExists(), this.$table(), this.$on(), this.$include(), this.$where(), this.$excludeNullKeys());
    }

    @Override
    public final QOM.CreateIndex $index(Index newValue) {
        return this.$constructor().apply(this.$unique(), newValue, this.$ifNotExists(), this.$table(), this.$on(), this.$include(), this.$where(), this.$excludeNullKeys());
    }

    @Override
    public final QOM.CreateIndex $ifNotExists(boolean newValue) {
        return this.$constructor().apply(this.$unique(), this.$index(), newValue, this.$table(), this.$on(), this.$include(), this.$where(), this.$excludeNullKeys());
    }

    @Override
    public final QOM.CreateIndex $table(Table<?> newValue) {
        return this.$constructor().apply(this.$unique(), this.$index(), this.$ifNotExists(), newValue, this.$on(), this.$include(), this.$where(), this.$excludeNullKeys());
    }

    @Override
    public final QOM.CreateIndex $on(Collection<? extends OrderField<?>> newValue) {
        return this.$constructor().apply(this.$unique(), this.$index(), this.$ifNotExists(), this.$table(), newValue, this.$include(), this.$where(), this.$excludeNullKeys());
    }

    @Override
    public final QOM.CreateIndex $include(Collection<? extends Field<?>> newValue) {
        return this.$constructor().apply(this.$unique(), this.$index(), this.$ifNotExists(), this.$table(), this.$on(), newValue, this.$where(), this.$excludeNullKeys());
    }

    @Override
    public final QOM.CreateIndex $where(Condition newValue) {
        return this.$constructor().apply(this.$unique(), this.$index(), this.$ifNotExists(), this.$table(), this.$on(), this.$include(), newValue, this.$excludeNullKeys());
    }

    @Override
    public final QOM.CreateIndex $excludeNullKeys(boolean newValue) {
        return this.$constructor().apply(this.$unique(), this.$index(), this.$ifNotExists(), this.$table(), this.$on(), this.$include(), this.$where(), newValue);
    }

    public final Function8<? super Boolean, ? super Index, ? super Boolean, ? super Table<?>, ? super Collection<? extends OrderField<?>>, ? super Collection<? extends Field<?>>, ? super Condition, ? super Boolean, ? extends QOM.CreateIndex> $constructor() {
        return (a1, a2, a3, a4, a5, a6, a7, a8) -> new CreateIndexImpl(this.configuration(), (boolean)a1, (Index)a2, (boolean)a3, (Table<?>)a4, (Collection<? extends OrderField<?>>)a5, (Collection<? extends Field<?>>)a6, (Condition)a7, (boolean)a8);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$unique(), this.$index(), this.$ifNotExists(), this.$table(), this.$on(), this.$include(), this.$where(), this.$excludeNullKeys(), (a1, a2, a3, a4, a5, a6, a7, a8) -> this.$constructor().apply((Boolean)a1, (Index)a2, (Boolean)a3, (Table<?>)a4, (Collection<OrderField<?>>)a5, (Collection<Field<?>>)a6, (Condition)a7, (Boolean)a8), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$index(), this.$table(), this.$on(), this.$include(), this.$where());
    }
}

