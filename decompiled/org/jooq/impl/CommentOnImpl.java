/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Comment;
import org.jooq.CommentOnFinalStep;
import org.jooq.CommentOnIsStep;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function4;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class CommentOnImpl
extends AbstractDDLQuery
implements QOM.CommentOn,
CommentOnIsStep,
CommentOnFinalStep {
    final Table<?> table;
    final boolean isView;
    final Field<?> field;
    Comment comment;
    private static final Set<SQLDialect> SUPPORTS_COMMENT_ON_VIEW = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.POSTGRES, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);

    CommentOnImpl(Configuration configuration, Table<?> table, boolean isView, Field<?> field2) {
        this(configuration, table, isView, field2, null);
    }

    CommentOnImpl(Configuration configuration, Table<?> table, boolean isView) {
        this(configuration, table, isView, null);
    }

    CommentOnImpl(Configuration configuration, Table<?> table, boolean isView, Field<?> field2, Comment comment) {
        super(configuration);
        this.table = table;
        this.isView = isView;
        this.field = field2;
        this.comment = comment;
    }

    @Override
    public final CommentOnImpl is(String comment) {
        return this.is(DSL.comment(comment));
    }

    @Override
    public final CommentOnImpl is(Comment comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case BIGQUERY: {
                this.acceptBigQuery(ctx);
                break;
            }
            case SQLSERVER: {
                this.acceptSQLServer(ctx);
                break;
            }
            case SNOWFLAKE: {
                this.acceptSnowflake(ctx);
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                if (this.table != null) {
                    this.acceptMySQL(ctx);
                    break;
                }
                this.acceptDefault(ctx);
                break;
            }
            default: {
                this.acceptDefault(ctx);
            }
        }
    }

    @Pro
    private final void acceptSQLServer(Context<?> ctx) {
        ctx.visit(Keywords.K_BEGIN_TRY).formatIndentStart().formatSeparator();
        this.acceptSQLServer0(ctx, "sp_addextendedproperty", true);
        ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END_TRY).formatSeparator().visit(Keywords.K_BEGIN_CATCH).formatIndentStart().formatSeparator();
        this.acceptSQLServer0(ctx, "sp_updateextendedproperty", false);
        ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END_CATCH);
    }

    @Pro
    private final void acceptSQLServer0(Context<?> ctx, String spName, boolean declareU) {
        Name schemaName;
        Name tableName = this.field != null ? this.field.getQualifiedName().qualifier() : this.table.getUnqualifiedName();
        Name name = schemaName = tableName.qualified() ? tableName.qualifier() : null;
        if (declareU && schemaName == null) {
            ctx.visit(Keywords.K_DECLARE).sql(" @u varchar(max) = schema_name();").formatSeparator();
        }
        ctx.visit(Keywords.K_EXEC).sql(" ").sql(spName).sql(" 'MS_Description', ").visit(this.comment).sql(", ");
        if (schemaName != null) {
            ctx.sql("'schema', ").visit(DSL.inline(schemaName.last())).sql(", ");
        } else {
            ctx.sql("'schema', @u, ");
        }
        ctx.sql('\'').sql(this.isView ? "view" : "table").sql("', ").visit(DSL.inline(tableName.last())).sql(", ");
        if (this.field != null) {
            ctx.sql("'column', ").visit(DSL.inline(this.field.getName()));
        } else {
            ctx.visit(Keywords.K_DEFAULT).sql(", ").visit(Keywords.K_DEFAULT);
        }
    }

    private final void acceptSnowflake(Context<?> ctx) {
        if (this.field != null) {
            ctx.visit(Keywords.K_ALTER_TABLE).sql(' ').visit(DSL.table(this.field.getQualifiedName().qualifier())).sql(' ').visit(Keywords.K_ALTER_COLUMN).sql(' ').qualify(false, c -> c.visit(this.field)).sql(' ').visit(Keywords.K_COMMENT).sql(' ').visit(this.comment);
        } else {
            ctx.visit(Keywords.K_ALTER).sql(' ').visit(this.isView ? Keywords.K_VIEW : Keywords.K_TABLE).sql(' ').visit(this.table).sql(' ').visit(Keywords.K_SET).sql(' ').visit(Keywords.K_COMMENT).sql(" = ").visit(this.comment);
        }
    }

    @Pro
    private final void acceptBigQuery(Context<?> ctx) {
        ctx.visit(Keywords.K_ALTER).sql(' ').visit(this.isView ? Keywords.K_VIEW : Keywords.K_TABLE).sql(' ').visit(this.table).sql(' ').visit(Keywords.K_SET).sql(' ').visit(Keywords.K_OPTIONS).sql(" (").visit(Keywords.K_DESCRIPTION).sql(" = ").visit(this.comment).sql(')');
    }

    private final void acceptMySQL(Context<?> ctx) {
        ctx.visit(Keywords.K_ALTER_TABLE).sql(' ').visit(this.table).sql(' ').visit(Keywords.K_COMMENT).sql(" = ").visit(this.comment);
    }

    private final void acceptDefault(Context<?> ctx) {
        ctx.visit(Keywords.K_COMMENT).sql(' ').visit(Keywords.K_ON).sql(' ');
        if (this.table != null) {
            ctx.visit(this.isView && SUPPORTS_COMMENT_ON_VIEW.contains((Object)ctx.dialect()) ? Keywords.K_VIEW : Keywords.K_TABLE).sql(' ').visit(this.table);
        } else if (this.field != null) {
            ctx.visit(Keywords.K_COLUMN).sql(' ').visit(this.field);
        } else {
            throw new IllegalStateException();
        }
        ctx.sql(' ').visit(Keywords.K_IS).sql(' ').visit(this.comment);
    }

    @Override
    public final Table<?> $table() {
        return this.table;
    }

    @Override
    public final boolean $isView() {
        return this.isView;
    }

    @Override
    public final Field<?> $field() {
        return this.field;
    }

    @Override
    public final Comment $comment() {
        return this.comment;
    }

    @Override
    public final QOM.CommentOn $table(Table<?> newValue) {
        return this.$constructor().apply(newValue, this.$isView(), this.$field(), this.$comment());
    }

    @Override
    public final QOM.CommentOn $isView(boolean newValue) {
        return this.$constructor().apply(this.$table(), newValue, this.$field(), this.$comment());
    }

    @Override
    public final QOM.CommentOn $field(Field<?> newValue) {
        return this.$constructor().apply(this.$table(), this.$isView(), newValue, this.$comment());
    }

    @Override
    public final QOM.CommentOn $comment(Comment newValue) {
        return this.$constructor().apply(this.$table(), this.$isView(), this.$field(), newValue);
    }

    public final Function4<? super Table<?>, ? super Boolean, ? super Field<?>, ? super Comment, ? extends QOM.CommentOn> $constructor() {
        return (a1, a2, a3, a4) -> new CommentOnImpl(this.configuration(), (Table<?>)a1, (boolean)a2, (Field<?>)a3, (Comment)a4);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$table(), this.$isView(), this.$field(), this.$comment(), (a1, a2, a3, a4) -> this.$constructor().apply((Table<?>)a1, (Boolean)a2, (Field<?>)a3, (Comment)a4), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$table(), this.$field(), this.$comment());
    }
}

