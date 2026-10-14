/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DropSchemaFinalStep;
import org.jooq.DropSchemaStep;
import org.jooq.Function3;
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

final class DropSchemaImpl
extends AbstractDDLQuery
implements QOM.DropSchema,
DropSchemaStep,
DropSchemaFinalStep {
    final Schema schema;
    final boolean ifExists;
    QOM.Cascade cascade;
    private static final Clause[] CLAUSES = new Clause[]{Clause.DROP_SCHEMA};
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> REQUIRES_RESTRICT = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.DERBY);
    @Pro
    private static final Set<SQLDialect> SUPPORT_SCHEMA_AS_DATABASE = SQLDialect.supportedBy(SQLDialect.INFORMIX);

    DropSchemaImpl(Configuration configuration, Schema schema, boolean ifExists) {
        this(configuration, schema, ifExists, null);
    }

    DropSchemaImpl(Configuration configuration, Schema schema, boolean ifExists, QOM.Cascade cascade) {
        super(configuration);
        this.schema = schema;
        this.ifExists = ifExists;
        this.cascade = cascade;
    }

    @Override
    public final DropSchemaImpl cascade() {
        this.cascade = QOM.Cascade.CASCADE;
        return this;
    }

    @Override
    public final DropSchemaImpl restrict() {
        this.cascade = QOM.Cascade.RESTRICT;
        return this;
    }

    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.SQLSERVER && this.schema.getQualifiedName().qualified()) {
            Variable<String> db = DSL.var(DSL.unquotedName("db"), SQLDataType.VARCHAR);
            Name catalog = this.schema.getQualifiedName().qualifier();
            ctx.visit(DSL.begin(DSL.declare(db).set(DSL.select(DSL.currentCatalog())), DSL.if_(DSL.val(catalog.last()).ne(db)).then(DSL.setCatalog(catalog)), DSL.execute(new DropSchemaImpl(ctx.configuration(), DSL.schema(this.schema.getUnqualifiedName()), this.ifExists, this.cascade)), DSL.if_(DSL.val(catalog.last()).ne(db)).then(DSL.statement("execute ('use ' + @db)"))));
        } else {
            this.accept0(ctx);
        }
    }

    private void accept0(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.DROP_SCHEMA, c -> this.accept1((Context<?>)c));
        } else {
            this.accept1(ctx);
        }
    }

    private void accept1(Context<?> ctx) {
        ctx.start(Clause.DROP_SCHEMA_SCHEMA).visit(Keywords.K_DROP);
        if (SUPPORT_SCHEMA_AS_DATABASE.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_DATABASE);
        } else {
            ctx.sql(' ').visit(Keywords.K_SCHEMA);
        }
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(this.schema);
        if (this.cascade == QOM.Cascade.CASCADE) {
            ctx.sql(' ').visit(Keywords.K_CASCADE);
        } else if (this.cascade == QOM.Cascade.RESTRICT || REQUIRES_RESTRICT.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_RESTRICT);
        }
        ctx.end(Clause.DROP_SCHEMA_SCHEMA);
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
    public final boolean $ifExists() {
        return this.ifExists;
    }

    @Override
    public final QOM.Cascade $cascade() {
        return this.cascade;
    }

    @Override
    public final QOM.DropSchema $schema(Schema newValue) {
        return this.$constructor().apply(newValue, (Boolean)this.$ifExists(), this.$cascade());
    }

    @Override
    public final QOM.DropSchema $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$schema(), (Boolean)newValue, this.$cascade());
    }

    @Override
    public final QOM.DropSchema $cascade(QOM.Cascade newValue) {
        return this.$constructor().apply(this.$schema(), (Boolean)this.$ifExists(), newValue);
    }

    public final Function3<? super Schema, ? super Boolean, ? super QOM.Cascade, ? extends QOM.DropSchema> $constructor() {
        return (a1, a2, a3) -> new DropSchemaImpl(this.configuration(), (Schema)a1, (boolean)a2, (QOM.Cascade)((Object)a3));
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$schema(), this.$ifExists(), this.$cascade(), (a1, a2, a3) -> this.$constructor().apply((Schema)a1, (Boolean)a2, (QOM.Cascade)((Object)a3)), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$schema());
    }
}

