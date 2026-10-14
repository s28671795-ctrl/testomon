/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateSchemaFinalStep;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.Variable;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class CreateSchemaImpl
extends AbstractDDLQuery
implements QOM.CreateSchema,
CreateSchemaFinalStep {
    final Schema schema;
    final boolean ifNotExists;
    private static final Clause[] CLAUSES = new Clause[]{Clause.CREATE_SCHEMA};
    private static final Set<SQLDialect> NO_SUPPORT_IF_NOT_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    private static final Set<SQLDialect> SUPPORT_SCHEMA_AS_DATABASE = SQLDialect.supportedBy(SQLDialect.INFORMIX);

    CreateSchemaImpl(Configuration configuration, Schema schema, boolean ifNotExists) {
        super(configuration);
        this.schema = schema;
        this.ifNotExists = ifNotExists;
    }

    private final boolean supportsIfNotExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_NOT_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.SQLSERVER && this.schema.getQualifiedName().qualified()) {
            Variable<String> db = DSL.var(DSL.unquotedName("db"), SQLDataType.VARCHAR);
            Name catalog = this.schema.getQualifiedName().qualifier();
            ctx.visit(DSL.begin(DSL.declare(db).set(DSL.select(DSL.currentCatalog())), DSL.if_(DSL.val(catalog.last()).ne(db)).then(DSL.setCatalog(catalog)), DSL.execute(new CreateSchemaImpl(ctx.configuration(), DSL.schema(this.schema.getUnqualifiedName()), this.ifNotExists)), DSL.if_(DSL.val(catalog.last()).ne(db)).then(DSL.statement("execute ('use ' + @db)"))));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        if (this.ifNotExists && !this.supportsIfNotExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.CREATE_SCHEMA, c -> this.accept1((Context<?>)c));
        } else {
            this.accept1(ctx);
        }
    }

    private final void accept1(Context<?> ctx) {
        if (this.ifNotExists && (ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE)) {
            ctx.sql(' ').visit(Keywords.K_EXEC).sql(" ('").stringLiteral(true);
            this.accept2(ctx);
            ctx.stringLiteral(false).sql("')");
        } else {
            this.accept2(ctx);
        }
    }

    private final void accept2(Context<?> ctx) {
        ctx.start(Clause.CREATE_SCHEMA_NAME).visit(Keywords.K_CREATE);
        if (SUPPORT_SCHEMA_AS_DATABASE.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_DATABASE);
        } else {
            ctx.sql(' ').visit(Keywords.K_SCHEMA);
        }
        if (this.ifNotExists && this.supportsIfNotExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_NOT_EXISTS);
        }
        ctx.sql(' ').visit(this.schema).end(Clause.CREATE_SCHEMA_NAME);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Schema $schema() {
        return this.schema;
    }

    @Override
    public final boolean $ifNotExists() {
        return this.ifNotExists;
    }

    @Override
    public final QOM.CreateSchema $schema(Schema newValue) {
        return this.$constructor().apply(newValue, (Boolean)this.$ifNotExists());
    }

    @Override
    public final QOM.CreateSchema $ifNotExists(boolean newValue) {
        return this.$constructor().apply(this.$schema(), (Boolean)newValue);
    }

    public final Function2<? super Schema, ? super Boolean, ? extends QOM.CreateSchema> $constructor() {
        return (a1, a2) -> new CreateSchemaImpl(this.configuration(), (Schema)a1, (boolean)a2);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$schema(), this.$ifNotExists(), (a1, a2) -> this.$constructor().apply((Schema)a1, (Boolean)a2), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$schema());
    }
}

