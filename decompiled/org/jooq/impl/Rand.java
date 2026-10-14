/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Rand
extends AbstractField<BigDecimal>
implements QOM.Rand {
    Rand() {
        super(Names.N_RAND, Tools.allNotNull(SQLDataType.NUMERIC));
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                return false;
            }
            case ORACLE: {
                return false;
            }
            case TERADATA: {
                return false;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case EXASOL: 
            case INGRES: 
            case REDSHIFT: 
            case VERTICA: 
            case DERBY: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                return true;
            }
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ACCESS: {
                ctx.visit(Names.N_RND);
                break;
            }
            case ORACLE: {
                ctx.visit(Names.N_DBMS_RANDOM_RANDOM);
                break;
            }
            case TERADATA: {
                ctx.sql('(').visit(Keywords.K_CAST).sql("((").visit(Names.N_RANDOM).sql("(-2147483648, 2147483647) + 2147483648) ").visit(Keywords.K_AS).sql(' ').visit(Keywords.K_NUMERIC).sql("(38, 19)) / 4294967295)");
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case EXASOL: 
            case INGRES: 
            case REDSHIFT: 
            case VERTICA: 
            case DERBY: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                ctx.visit(DSL.function(Names.N_RANDOM, this.getDataType(), new Field[0]));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_RAND, this.getDataType(), new Field[0]));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("rand()");
    }

    @Override
    public final Function0<? extends QOM.Rand> $constructor() {
        return () -> new Rand();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Rand) {
            QOM.Rand o = (QOM.Rand)that;
            return true;
        }
        return super.equals(that);
    }
}

