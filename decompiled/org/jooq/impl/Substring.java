/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Substring
extends AbstractField<String>
implements QOM.Substring {
    final Field<String> string;
    final Field<? extends Number> startingPosition;
    final Field<? extends Number> length;

    Substring(Field<String> string, Field<? extends Number> startingPosition) {
        super(Names.N_SUBSTRING, Tools.allNotNull(SQLDataType.VARCHAR, string, startingPosition));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.startingPosition = Tools.nullSafeNotNull(startingPosition, SQLDataType.INTEGER);
        this.length = null;
    }

    Substring(Field<String> string, Field<? extends Number> startingPosition, Field<? extends Number> length) {
        super(Names.N_SUBSTRING, Tools.allNotNull(SQLDataType.VARCHAR, string, startingPosition, length));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.startingPosition = Tools.nullSafeNotNull(startingPosition, SQLDataType.INTEGER);
        this.length = Tools.nullSafeNotNull(length, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        Name functionName = Names.N_SUBSTRING;
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                return;
            }
            case TERADATA: 
            case FIREBIRD: {
                if (this.length == null) {
                    ctx.visit(Names.N_SUBSTRING).sql('(').visit(this.string).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.startingPosition).sql(')');
                } else {
                    ctx.visit(Names.N_SUBSTRING).sql('(').visit(this.string).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.startingPosition).sql(' ').visit(Keywords.K_FOR).sql(' ').visit(this.length).sql(')');
                }
                return;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                if (this.length != null) break;
                ctx.visit(DSL.function(Names.N_SUBSTRING, this.getDataType(), this.string, this.startingPosition, DSL.inline(Integer.MAX_VALUE)));
                return;
            }
            case INGRES: {
                if (this.length == null) {
                    ctx.visit(DSL.function(Names.N_SUBSTRING, this.getDataType(), this.string, DSL.cast(this.startingPosition, SQLDataType.INTEGER)));
                } else {
                    ctx.visit(DSL.function(Names.N_SUBSTRING, this.getDataType(), this.string, DSL.cast(this.startingPosition, SQLDataType.INTEGER), DSL.cast(this.length, SQLDataType.INTEGER)));
                }
                return;
            }
            case ACCESS: {
                functionName = Names.N_MID;
                break;
            }
            case DB2: 
            case INFORMIX: 
            case ORACLE: 
            case DERBY: 
            case SQLITE: {
                functionName = Names.N_SUBSTR;
            }
        }
        if (this.length == null) {
            ctx.visit(DSL.function(functionName, this.getDataType(), this.string, this.startingPosition));
        } else {
            ctx.visit(DSL.function(functionName, this.getDataType(), this.string, this.startingPosition, this.length));
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("substring(");
        String s2 = "";
        ctx.sql(s2).visit(this.string);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.startingPosition));
        s2 = ", ";
        if (this.length != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.length));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.startingPosition;
    }

    @Override
    public final Field<? extends Number> $arg3() {
        return this.length;
    }

    @Override
    public final QOM.Substring $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<? extends Number>)this.$arg2(), (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.Substring $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)newValue, (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.Substring $arg3(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)this.$arg2(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<? extends Number>, ? super Field<? extends Number>, ? extends QOM.Substring> $constructor() {
        return (a1, a2, a3) -> new Substring((Field<String>)a1, (Field<? extends Number>)a2, (Field<? extends Number>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Substring) {
            QOM.Substring o = (QOM.Substring)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$startingPosition(), o.$startingPosition()) && StringUtils.equals(this.$length(), o.$length());
        }
        return super.equals(that);
    }
}

