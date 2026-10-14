/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class Execute
extends AbstractStatement
implements QOM.Execute {
    final Field<String> sql;

    Execute(Field<String> sql) {
        this.sql = sql;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case BIGQUERY: 
            case DB2: 
            case HANA: 
            case ORACLE: 
            case MARIADB: {
                ctx.visit(Keywords.K_EXECUTE_IMMEDIATE).sql(' ').visit(this.sql);
                break;
            }
            case FIREBIRD: {
                ctx.visit(Keywords.K_EXECUTE_STATEMENT).sql(' ').visit(this.sql);
                break;
            }
            case MYSQL: {
                ctx.visit(DSL.statements(DSL.statement("{prepare} s {from} {0}", this.sql), DSL.statement("{execute} s"), DSL.statement("{deallocate} {prepare} s")));
                break;
            }
            case SQLSERVER: {
                ctx.visit(Keywords.K_EXECUTE).sql(" (").visit(this.sql).sql(")");
                break;
            }
            default: {
                ctx.visit(Keywords.K_EXECUTE).sql(' ').visit(this.sql);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("execute(");
        ctx.visit(Val.getJavaValue(this.sql));
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.sql;
    }

    @Override
    public final QOM.Execute $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue);
    }

    @Override
    public final Function1<? super Field<String>, ? extends QOM.Execute> $constructor() {
        return a1 -> new Execute((Field<String>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Execute) {
            QOM.Execute o = (QOM.Execute)that;
            return StringUtils.equals(this.$sql(), o.$sql());
        }
        return super.equals(that);
    }
}

