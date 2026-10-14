/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import java.util.function.BiFunction;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateViewAsStep;
import org.jooq.CreateViewFinalStep;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.ResultQuery;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

final class CreateViewImpl<R extends Record>
extends AbstractDDLQuery
implements CreateViewAsStep<R>,
CreateViewFinalStep,
QOM.CreateView<R> {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CREATE_VIEW};
    private static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    private final boolean ifNotExists;
    private final boolean orReplace;
    private final Table<?> view;
    private final BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction;
    private Field<?>[] fields;
    private ResultQuery<?> select;
    private transient Select<?> parsed;

    CreateViewImpl(Configuration configuration, Table<?> view, Field<?>[] fields, boolean ifNotExists, boolean orReplace) {
        super(configuration);
        this.view = view;
        this.fields = fields;
        this.fieldNameFunction = null;
        this.ifNotExists = ifNotExists;
        this.orReplace = orReplace;
    }

    CreateViewImpl(Configuration configuration, Table<?> view, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction, boolean ifNotExists, boolean orReplace) {
        super(configuration);
        this.view = view;
        this.fields = null;
        this.fieldNameFunction = fieldNameFunction;
        this.ifNotExists = ifNotExists;
        this.orReplace = orReplace;
    }

    @Override
    public final CreateViewFinalStep as(Select<? extends R> s2) {
        this.select = s2;
        if (this.fieldNameFunction != null) {
            this.fields = Tools.map(s2.getSelect(), this.fieldNameFunction::apply, Field[]::new);
        }
        return this;
    }

    @Override
    public final CreateViewFinalStep as(SQL sql) {
        this.select = DSL.resultQuery(sql);
        if (this.fieldNameFunction != null) {
            this.fields = Tools.map(this.parsed().getSelect(), this.fieldNameFunction::apply, Field[]::new);
        }
        return this;
    }

    @Override
    public final CreateViewFinalStep as(String sql) {
        return this.as(DSL.sql(sql));
    }

    @Override
    public final CreateViewFinalStep as(String sql, Object ... bindings) {
        return this.as(DSL.sql(sql, bindings));
    }

    @Override
    public final CreateViewFinalStep as(String sql, QueryPart ... parts) {
        return this.as(DSL.sql(sql, parts));
    }

    private final boolean supportsIfNotExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.CREATE_VIEW, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        boolean replaceSupported;
        QueryPart[] f = this.fields;
        boolean rename = f != null && f.length > 0;
        boolean renameSupported = ctx.family() != SQLDialect.MEMSQL;
        boolean bl = replaceSupported = ctx.family() == SQLDialect.TERADATA;
        if (ctx.family() == SQLDialect.INFORMIX && !rename) {
            f = this.parsed().getSelect().toArray(Tools.EMPTY_FIELD);
            rename = true;
        }
        ctx.start(Clause.CREATE_VIEW_NAME).visit(replaceSupported && this.orReplace ? Keywords.K_REPLACE : Keywords.K_CREATE);
        if (this.orReplace && !replaceSupported) {
            ctx.sql(' ').visit(Keywords.K_OR);
            switch (ctx.family()) {
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case FIREBIRD: {
                    ctx.sql(' ').visit(Keywords.K_ALTER);
                    break;
                }
                default: {
                    ctx.sql(' ').visit(Keywords.K_REPLACE);
                }
            }
        }
        ctx.sql(' ').visit(Keywords.K_VIEW).sql(' ');
        if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
            ctx.visit(Keywords.K_IF_NOT_EXISTS).sql(' ');
        }
        if (ctx.family() == SQLDialect.SQLSERVER) {
            ctx.qualifyCatalog(false, c -> c.visit(this.view));
        } else {
            ctx.visit(this.view);
        }
        if (rename && renameSupported) {
            ctx.sql('(').visit(QueryPartListView.wrap((QueryPart[])f).qualify(false)).sql(')');
        }
        ctx.end(Clause.CREATE_VIEW_NAME).formatSeparator().visit(Keywords.K_AS).formatSeparator().start(Clause.CREATE_VIEW_AS).visit(rename && !renameSupported ? DSL.selectFrom(this.parsed().asTable(DSL.name("t"), Tools.map(f, Named::getUnqualifiedName, Name[]::new))) : this.select, ParamType.INLINED).end(Clause.CREATE_VIEW_AS);
    }

    private final Select<?> parsed() {
        if (this.parsed != null) {
            return this.parsed;
        }
        if (this.select instanceof Select) {
            this.parsed = (Select)this.select;
            return this.parsed;
        }
        DSLContext dsl = this.configuration().dsl();
        return dsl.parser().parseSelect(dsl.renderInlined(this.select));
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final boolean $ifNotExists() {
        return this.ifNotExists;
    }

    @Override
    public final boolean $orReplace() {
        return this.orReplace;
    }

    @Override
    public final Table<?> $view() {
        return this.view;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $fields() {
        return QOM.unmodifiable((QueryPart[])this.fields);
    }

    @Override
    public final ResultQuery<R> $query() {
        return this.select;
    }

    final Select<?> $select() {
        return this.parsed();
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.$view(), this.$fields(), this.$query());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.view, this.fields, this.select, (v, f, s2) -> {
            CreateViewImpl<R> r = new CreateViewImpl<R>(this.configuration(), (Table<?>)v, (Field<?>[])f, this.ifNotExists, this.orReplace);
            r.select = s2;
            return r;
        }, replacer);
    }
}

