/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Repeat
extends AbstractField<String>
implements QOM.Repeat {
    final Field<String> string;
    final Field<? extends Number> count;

    Repeat(Field<String> string, Field<? extends Number> count) {
        super(Names.N_REPEAT, Tools.allNotNull(SQLDataType.VARCHAR, string, count));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
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
            case FIREBIRD: {
                return false;
            }
            case SQLITE: {
                return false;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
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
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case TERADATA: 
            case FIREBIRD: {
                ctx.visit(DSL.rpad(this.string, Internal.imul(DSL.length(this.string), this.count), this.string));
                break;
            }
            case SQLITE: {
                ctx.visit(Names.N_REPLACE).sql('(').visit(Names.N_HEX).sql('(').visit(Names.N_ZEROBLOB).sql('(').visit(this.count).sql(")), '00', ").visit(this.string).sql(')');
                break;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(DSL.function(Names.N_REPLICATE, this.getDataType(), this.string, this.count));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_REPEAT, this.getDataType(), this.string, this.count));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("repeat(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.string));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.count));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.count;
    }

    @Override
    public final QOM.Repeat $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.Repeat $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<String>, ? super Field<? extends Number>, ? extends QOM.Repeat> $constructor() {
        return (a1, a2) -> new Repeat((Field<String>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Repeat) {
            QOM.Repeat o = (QOM.Repeat)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$count(), o.$count());
        }
        return super.equals(that);
    }
}

