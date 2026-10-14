/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.AlterSchemaFinalStep;
import org.jooq.AlterSchemaStep;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class AlterSchemaImpl
extends AbstractDDLQuery
implements QOM.AlterSchema,
AlterSchemaStep,
AlterSchemaFinalStep {
    final Schema schema;
    final boolean ifExists;
    Schema renameTo;
    private static final Clause[] CLAUSES = new Clause[]{Clause.ALTER_SCHEMA};
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_IF_EXISTS = SQLDialect.supportedBy(SQLDialect.HANA);
    @Pro
    private static final Set<SQLDialect> SUPPORT_RENAME_SCHEMA = SQLDialect.supportedBy(SQLDialect.HANA);

    AlterSchemaImpl(Configuration configuration, Schema schema, boolean ifExists) {
        this(configuration, schema, ifExists, null);
    }

    AlterSchemaImpl(Configuration configuration, Schema schema, boolean ifExists, Schema renameTo) {
        super(configuration);
        this.schema = schema;
        this.ifExists = ifExists;
        this.renameTo = renameTo;
    }

    @Override
    public final AlterSchemaImpl renameTo(String renameTo) {
        return this.renameTo(DSL.schema(DSL.name(renameTo)));
    }

    @Override
    public final AlterSchemaImpl renameTo(Name renameTo) {
        return this.renameTo(DSL.schema(renameTo));
    }

    @Override
    public final AlterSchemaImpl renameTo(Schema renameTo) {
        this.renameTo = renameTo;
        return this;
    }

    @Pro
    private final boolean supportsIfExists(Context<?> ctx) {
        return !NO_SUPPORT_IF_EXISTS.contains((Object)ctx.dialect());
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.ifExists && !this.supportsIfExists(ctx)) {
            Tools.tryCatch(ctx, DDLStatementType.ALTER_SCHEMA, c -> this.accept0((Context<?>)c));
        } else {
            this.accept0(ctx);
        }
    }

    private final void accept0(Context<?> ctx) {
        ctx.start(Clause.ALTER_SCHEMA_SCHEMA);
        boolean supportRename = SUPPORT_RENAME_SCHEMA.contains((Object)ctx.dialect());
        if (supportRename) {
            ctx.visit(Keywords.K_RENAME).sql(' ').visit(Keywords.K_SCHEMA);
        } else {
            ctx.visit(Keywords.K_ALTER_SCHEMA);
        }
        if (this.ifExists && this.supportsIfExists(ctx)) {
            ctx.sql(' ').visit(Keywords.K_IF_EXISTS);
        }
        ctx.sql(' ').visit(this.schema).end(Clause.ALTER_SCHEMA_SCHEMA).formatIndentStart().formatSeparator();
        if (this.renameTo != null) {
            ctx.start(Clause.ALTER_SCHEMA_RENAME).visit(supportRename ? Keywords.K_TO : Keywords.K_RENAME_TO).sql(' ').qualify(false, c -> c.visit(this.renameTo)).end(Clause.ALTER_SCHEMA_RENAME);
        }
        ctx.formatIndentEnd();
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
    public final Schema $renameTo() {
        return this.renameTo;
    }

    @Override
    public final QOM.AlterSchema $schema(Schema newValue) {
        return this.$constructor().apply(newValue, (Boolean)this.$ifExists(), this.$renameTo());
    }

    @Override
    public final QOM.AlterSchema $ifExists(boolean newValue) {
        return this.$constructor().apply(this.$schema(), (Boolean)newValue, this.$renameTo());
    }

    @Override
    public final QOM.AlterSchema $renameTo(Schema newValue) {
        return this.$constructor().apply(this.$schema(), (Boolean)this.$ifExists(), newValue);
    }

    public final Function3<? super Schema, ? super Boolean, ? super Schema, ? extends QOM.AlterSchema> $constructor() {
        return (a1, a2, a3) -> new AlterSchemaImpl(this.configuration(), (Schema)a1, (boolean)a2, (Schema)a3);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$schema(), this.$ifExists(), this.$renameTo(), (a1, a2, a3) -> this.$constructor().apply((Schema)a1, (Boolean)a2, (Schema)a3), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$schema(), this.$renameTo());
    }
}

