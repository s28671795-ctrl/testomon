/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.Set;
import org.jooq.AlterViewFinalStep;
import org.jooq.AlterViewStep;
import org.jooq.Clause;
import org.jooq.Comment;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function6;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Select;
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

final class AlterViewImpl
extends AbstractDDLQuery
implements QOM.AlterView,
AlterViewStep,
AlterViewFinalStep {
    final Table<?> view;
    final QueryPartListView<? extends Field<?>> fields;
    final boolean ifExists;
    Comment comment;
    Table<?> renameTo;
    Select<?> as;
    private static final Clause[] CLAUSES = new Clause[]{Clause.ALTER_VIEW};
    private static final Set<SQLDialect> SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> SUPPORT_ALTER_TABLE_RENAME = SQLDialect.supportedBy(SQLDialect.HSQLDB, SQLDialect.YUGABYTEDB);

    AlterViewImpl(Configuration configuration, Table<?> view, Collection<? extends Field<?>> fields, boolean ifExists) {
        this(configuration, view, fields, ifExists, null, null, null);
    }

    AlterViewImpl(Configuration configuration, Table<?> view, boolean ifExists) {
        this(configuration, view, null, ifExists);
    }

    AlterViewImpl(Configuration configuration, Table<?> view, Collection<? extends Field<?>> fields, boolean ifExists, Comment comment, Table<?> renameTo, Select<?> as) {
        super(configuration);
        this.view = view;
        this.fields = new QueryPartList((Iterable<Field<?>>)fields);
        this.ifExists = ifExists;
        this.comment = comment;
        this.renameTo = renameTo;
        this.as = as;
    }

    @Override
    public final AlterViewImpl comment(String comment) {
        return this.comment(DSL.comment(comment));
    }

    @Override
    public final AlterViewImpl comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public final AlterViewImpl renameTo(String renameTo) {
        return this.renameTo((Table)DSL.table(DSL.name(renameTo)));
    }

    @Override
    public final AlterViewImpl renameTo(Name renameTo) {
        return this.renameTo((Table)DSL.table(renameTo));
    }

    @Override
    public final AlterViewImpl renameTo(Table<?> renameTo) {
        this.renameTo = renameTo;
        return this;
    }

    @Override
    public final AlterViewImpl as(Select<?> as) {
        this.as = as;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.ALTER_VIEW, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        if (this.as != null) {
            switch (ctx.family()) {
                case ACCESS: 
                case ASE: 
                case AURORA_MYSQL: 
                case AURORA_POSTGRES: 
                case BIGQUERY: 
                case COCKROACHDB: 
                case DB2: 
                case EXASOL: 
                case HANA: 
                case INFORMIX: 
                case INGRES: 
                case MEMSQL: 
                case ORACLE: 
                case REDSHIFT: 
                case SNOWFLAKE: 
                case SQLDATAWAREHOUSE: 
                case SYBASE: 
                case TERADATA: 
                case VERTICA: 
                case CUBRID: 
                case DERBY: 
                case FIREBIRD: 
                case H2: 
                case HSQLDB: 
                case IGNITE: 
                case MARIADB: 
                case MYSQL: 
                case POSTGRES: 
                case SQLITE: 
                case YUGABYTEDB: {
                    ctx.visit(DSL.begin(DSL.dropView(this.view), DSL.createView(this.view, this.fields.toArray((E[])Tools.EMPTY_FIELD)).as(this.as)));
                    break;
                }
                default: {
                    ctx.visit(Keywords.K_ALTER).sql(' ').visit(Keywords.K_VIEW).sql(' ').visit(this.view);
                    if (!this.fields.isEmpty()) {
                        ctx.sql(" (").visit(QueryPartCollectionView.wrap(this.fields).qualify(false)).sql(')');
                    }
                    ctx.formatSeparator().visit(Keywords.K_AS).formatSeparator().visit(this.as);
                }
            }
            return;
        }
        if (this.comment != null) {
            ctx.visit(DSL.commentOnView(this.view).is(this.comment));
            return;
        }
        if (this.renameTo != null) {
            switch (ctx.family()) {
                case ORACLE: {
                    this.acceptOracleRenameTo(ctx);
                    return;
                }
                case ASE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    this.acceptSQLServerRenameTo(ctx);
                    return;
                }
                case EXASOL: 
                case TERADATA: {
                    this.acceptTeradataRenameTo(ctx);
                    return;
                }
            }
        }
        this.accept1(ctx);
    }

    @Pro
    private final void acceptTeradataRenameTo(Context<?> ctx) {
        ctx.start(Clause.ALTER_VIEW_VIEW).start(Clause.ALTER_VIEW_RENAME).visit(Keywords.K_RENAME).sql(' ').visit(Keywords.K_VIEW).sql(' ').visit(this.view).sql(' ').visit(Keywords.K_TO).sql(' ').visit(this.renameTo).end(Clause.ALTER_VIEW_RENAME).end(Clause.ALTER_VIEW_VIEW);
    }

    @Pro
    private final void acceptOracleRenameTo(Context<?> ctx) {
        ctx.start(Clause.ALTER_VIEW_VIEW).start(Clause.ALTER_VIEW_RENAME).visit(Keywords.K_RENAME).sql(' ').visit(this.view).sql(' ').visit(Keywords.K_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_VIEW_RENAME).end(Clause.ALTER_VIEW_VIEW);
    }

    @Pro
    private final void acceptSQLServerRenameTo(Context<?> ctx) {
        boolean qualify = ctx.qualify();
        ctx.start(Clause.ALTER_VIEW_VIEW).start(Clause.ALTER_VIEW_RENAME).visit(Keywords.K_EXEC).sql(' ').sql("sp_rename").sql(' ').visit(this.view).sql(", ").qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_VIEW_RENAME).end(Clause.ALTER_VIEW_VIEW);
    }

    private final void accept1(Context<?> ctx) {
        ctx.start(Clause.ALTER_VIEW_VIEW).visit(Keywords.K_ALTER).sql(' ').visit(SUPPORT_ALTER_TABLE_RENAME.contains((Object)ctx.dialect()) ? Keywords.K_TABLE : Keywords.K_VIEW);
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(this.view).end(Clause.ALTER_VIEW_VIEW).formatIndentStart().formatSeparator();
        if (this.renameTo != null) {
            ctx.start(Clause.ALTER_VIEW_RENAME).visit(Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_VIEW_RENAME);
        }
        ctx.formatIndentEnd();
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Table<?> $view() {
        return this.view;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $fields() {
        return QOM.unmodifiable(this.fields);
    }

    @Override
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final Comment $comment() {
        return this.comment;
    }

    @Override
    public final Table<?> $renameTo() {
        return this.renameTo;
    }

    @Override
    public final Select<?> $as() {
        return this.as;
    }

    @Override
    public final QOM.AlterView $view(Table<?> newValue) {
        return this.$constructor().apply(newValue, this.$fields(), this.$ifExists(), this.$comment(), this.$renameTo(), this.$as());
    }

    @Override
    public final QOM.AlterView $fields(Collection<? extends Field<?>> newValue) {
        return this.$constructor().apply(this.$view(), newValue, this.$ifExists(), this.$comment(), this.$renameTo(), this.$as());
    }

    @Override
    public final QOM.AlterView $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$view(), this.$fields(), newValue, this.$comment(), this.$renameTo(), this.$as());
    }

    @Override
    public final QOM.AlterView $comment(Comment newValue) {
        return this.$constructor().apply(this.$view(), this.$fields(), this.$ifExists(), newValue, this.$renameTo(), this.$as());
    }

    @Override
    public final QOM.AlterView $renameTo(Table<?> newValue) {
        return this.$constructor().apply(this.$view(), this.$fields(), this.$ifExists(), this.$comment(), newValue, this.$as());
    }

    @Override
    public final QOM.AlterView $as(Select<?> newValue) {
        return this.$constructor().apply(this.$view(), this.$fields(), this.$ifExists(), this.$comment(), this.$renameTo(), newValue);
    }

    public final Function6<? super Table<?>, ? super Collection<? extends Field<?>>, ? super Boolean, ? super Comment, ? super Table<?>, ? super Select<?>, ? extends QOM.AlterView> $constructor() {
        return (a1, a2, a3, a4, a5, a6) -> new AlterViewImpl(this.configuration(), (Table<?>)a1, (Collection<? extends Field<?>>)a2, (boolean)a3, (Comment)a4, (Table<?>)a5, (Select<?>)a6);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$view(), this.$fields(), this.$ifExists(), this.$comment(), this.$renameTo(), this.$as(), (a1, a2, a3, a4, a5, a6) -> this.$constructor().apply((Table<?>)a1, (Collection<Field<?>>)a2, (Boolean)a3, (Comment)a4, (Table<?>)a5, (Select<?>)a6), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$view(), this.$fields(), this.$comment(), this.$renameTo(), this.$as());
    }
}

