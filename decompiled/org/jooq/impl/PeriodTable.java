/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Keyword;
import org.jooq.PeriodSpecification;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.AbstractDelegatingTable;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.Alias;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

@Pro
final class PeriodTable<R extends Record, T>
extends AbstractDelegatingTable<R>
implements QOM.UNotYetImplemented {
    private final Prefix prefix;
    private final PeriodSpecification periodSpecification;

    PeriodTable(AbstractTable<R> table, Prefix prefix, PeriodSpecification periodSpecification) {
        super(table);
        this.prefix = prefix;
        this.periodSpecification = periodSpecification;
    }

    private PeriodTable(Table<R> table, PeriodTable<R, T> o) {
        this((AbstractTable)table, o.prefix, o.periodSpecification);
    }

    final <O extends Record> PeriodTable<O, T> construct(AbstractTable<O> newDelegate) {
        return new PeriodTable<O, T>(newDelegate, this.prefix, this.periodSpecification);
    }

    @Override
    public final void accept(Context<?> ctx) {
        Table aliased = Tools.aliased(this.delegate);
        if (aliased != null) {
            Alias alias = Tools.alias(this.delegate);
            ctx.visit(new Alias<PeriodTable>(new PeriodTable(aliased, this), this, alias.alias, alias.fieldAliases, alias.wrapInParentheses));
        } else {
            switch (ctx.family()) {
                case COCKROACHDB: 
                case ORACLE: {
                    ctx.visit(this.delegate).sql(' ').visit(this.periodSpecification);
                    break;
                }
                case JAVA: {
                    ctx.visit(this.delegate);
                    switch (this.prefix) {
                        case FOR: {
                            ctx.sql(".for_(");
                            break;
                        }
                        case FOR_PORTION_OF: {
                            ctx.sql(".forPortionOf(");
                        }
                    }
                    ctx.visit(this.periodSpecification).sql(')');
                    break;
                }
                default: {
                    ctx.visit(this.delegate).sql(' ').visit(this.prefix.toKeyword()).sql(' ').visit(this.periodSpecification);
                }
            }
        }
    }

    @Pro
    static enum Prefix {
        FOR(Keywords.K_FOR),
        FOR_PORTION_OF(Keywords.K_FOR_PORTION_OF);

        private final Keyword keyword;

        private Prefix(Keyword keyword) {
            this.keyword = keyword;
        }

        final Keyword toKeyword() {
            return this.keyword;
        }
    }
}

