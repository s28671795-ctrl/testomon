/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class NotField
extends AbstractField<Boolean>
implements QOM.NotField {
    final Field<Boolean> field;

    NotField(Field<Boolean> field2) {
        super(Names.N_NOT, SQLDataType.BOOLEAN);
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
                ctx.visit(DSL.field(DSL.not(DSL.condition(this.field))));
                break;
            }
            default: {
                ctx.visit(Keywords.K_NOT).sql(" (").visit((QueryPart)(Tools.hasDefaultConverter(this.field) ? this.field : DSL.condition(this.field))).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("not(");
        ctx.visit(this.field);
        ctx.sql(')');
    }

    @Override
    public final Field<Boolean> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.NotField $arg1(Field<Boolean> newValue) {
        return this.$constructor().apply((Field<Boolean>)newValue);
    }

    @Override
    public final Function1<? super Field<Boolean>, ? extends QOM.NotField> $constructor() {
        return a1 -> new NotField((Field<Boolean>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.NotField) {
            QOM.NotField o = (QOM.NotField)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

