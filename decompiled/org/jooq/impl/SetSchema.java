/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Schema;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class SetSchema
extends AbstractDDLQuery
implements QOM.SetSchema {
    final Schema schema;

    SetSchema(Configuration configuration, Schema schema) {
        super(configuration);
        this.schema = schema;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case EXASOL: {
                ctx.visit(Keywords.K_OPEN).sql(' ').visit(Keywords.K_SCHEMA).sql(' ').visit(this.schema);
                break;
            }
            case TERADATA: {
                ctx.visit(Keywords.K_DATABASE).sql(' ').visit(this.schema);
                break;
            }
            case ORACLE: {
                ctx.visit(Keywords.K_ALTER).sql(' ').visit(Keywords.K_SESSION).sql(' ').visit(Keywords.K_SET).sql(' ').visit(Keywords.K_CURRENT_SCHEMA).sql(" = ").visit(this.schema);
                break;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case SNOWFLAKE: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Keywords.K_USE).sql(' ').visit(this.schema);
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case VERTICA: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Keywords.K_SET).sql(' ').visit(Keywords.K_SEARCH_PATH).sql(" = ").visit(this.schema);
                break;
            }
            default: {
                ctx.visit(Keywords.K_SET).sql(' ').visit(Keywords.K_SCHEMA).sql(' ').visit(this.schema);
            }
        }
    }

    @Override
    public final Schema $schema() {
        return this.schema;
    }

    @Override
    public final QOM.SetSchema $schema(Schema newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Schema, ? extends QOM.SetSchema> $constructor() {
        return a1 -> new SetSchema(this.configuration(), (Schema)a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$schema(), a1 -> this.$constructor().apply((Schema)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$schema());
    }
}

