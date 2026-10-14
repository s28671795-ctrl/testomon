/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class FieldCondition
extends AbstractCondition
implements QOM.FieldCondition {
    final Field<Boolean> field;

    FieldCondition(Field<Boolean> field2) {
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.BOOLEAN);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ASE: 
            case DB2: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case CUBRID: 
            case FIREBIRD: {
                ctx.visit(this.field.eq(DSL.inline((Object)true, this.field.getDataType())));
                break;
            }
            default: {
                ctx.visit((Field<?>)(Tools.hasDefaultConverter(this.field) ? this.field : this.field.eq(DSL.inline((Object)true, this.field.getDataType()))));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("condition(");
        ctx.visit(this.field);
        ctx.sql(')');
    }

    @Override
    public final Field<Boolean> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.FieldCondition $arg1(Field<Boolean> newValue) {
        return new FieldCondition(newValue);
    }

    @Override
    public final Function1<? super Field<Boolean>, ? extends QOM.FieldCondition> $constructor() {
        return a1 -> new FieldCondition((Field<Boolean>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.FieldCondition) {
            QOM.FieldCondition o = (QOM.FieldCondition)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

