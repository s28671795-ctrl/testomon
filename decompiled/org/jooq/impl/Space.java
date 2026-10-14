/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Space
extends AbstractField<String>
implements QOM.Space {
    final Field<? extends Number> count;

    Space(Field<? extends Number> count) {
        super(Names.N_SPACE, Tools.allNotNull(SQLDataType.VARCHAR, count));
        this.count = Tools.nullSafeNotNull(count, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case TERADATA: 
            case FIREBIRD: 
            case SQLITE: {
                return false;
            }
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case REDSHIFT: 
            case DERBY: 
            case HSQLDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                return false;
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
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case TERADATA: 
            case FIREBIRD: 
            case SQLITE: {
                ctx.visit(DSL.rpad(DSL.inline(' '), this.count));
                break;
            }
            case AURORA_POSTGRES: 
            case BIGQUERY: 
            case COCKROACHDB: 
            case REDSHIFT: 
            case DERBY: 
            case HSQLDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(DSL.repeat(DSL.inline(" "), this.count));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_SPACE, this.getDataType(), this.count));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("space(");
        ctx.visit(Val.getJavaValue(this.count));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.count;
    }

    @Override
    public final QOM.Space $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Space> $constructor() {
        return a1 -> new Space((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Space) {
            QOM.Space o = (QOM.Space)that;
            return StringUtils.equals(this.$count(), o.$count());
        }
        return super.equals(that);
    }
}

