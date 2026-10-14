/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;

final class RegexpLike
extends AbstractCondition
implements QOM.RegexpLike {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_COMPARISON};
    private final Field<?> search;
    private final Field<String> pattern;

    RegexpLike(Field<?> search, Field<String> pattern) {
        this.search = search;
        this.pattern = pattern;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_MYSQL: 
            case MEMSQL: 
            case SYBASE: 
            case CUBRID: 
            case H2: 
            case MARIADB: 
            case MYSQL: 
            case SQLITE: {
                ctx.visit(this.search).sql(' ').visit(Keywords.K_REGEXP).sql(' ').visit(this.pattern);
                break;
            }
            case HSQLDB: {
                ctx.visit(DSL.keyword("regexp_matches")).sql('(').visit(this.search).sql(", ").visit(this.pattern).sql(')');
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case REDSHIFT: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.sql('(').visit(this.search).sql(" ~ ").visit(this.pattern).sql(')');
                break;
            }
            case JAVA: {
                ctx.visit(this.search).sql(".likeRegex(").visit(this.pattern).sql(')');
                break;
            }
            case ORACLE: 
            case VERTICA: {
                ctx.visit(DSL.keyword("regexp_like")).sql('(').visit(this.search).sql(", ").visit(this.pattern).sql(')');
                break;
            }
            default: {
                ctx.sql('(').visit(this.search).sql(' ').visit(Keywords.K_LIKE_REGEX).sql(' ').visit(this.pattern).sql(')');
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Field<?> $search() {
        return this.search;
    }

    @Override
    public final Field<String> $pattern() {
        return this.pattern;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.search, this.pattern);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.search, this.pattern, RegexpLike::new, replacer);
    }
}

