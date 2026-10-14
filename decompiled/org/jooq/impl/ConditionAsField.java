/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class ConditionAsField
extends AbstractField<Boolean>
implements QOM.ConditionAsField {
    final Condition condition;

    ConditionAsField(Condition condition) {
        super(Names.N_FIELD, Tools.allNotNull(SQLDataType.BOOLEAN));
        this.condition = condition;
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
            case INFORMIX: 
            case INGRES: 
            case ORACLE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case CUBRID: 
            case FIREBIRD: {
                if (this.condition instanceof AbstractCondition && !((AbstractCondition)this.condition).isNullable()) {
                    ctx.visit(DSL.when(this.condition, DSL.inline(true)).else_(DSL.inline(false)));
                    break;
                }
                ctx.visit(DSL.when(this.condition, DSL.inline(true)).when(DSL.not(this.condition), (Field<Boolean>)DSL.inline(false)));
                break;
            }
            default: {
                if (this.condition instanceof AbstractField && ((AbstractField)((Object)this.condition)).parenthesised(ctx)) {
                    ctx.visit(this.condition);
                    break;
                }
                ctx.sql('(').visit(this.condition).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("field(");
        ctx.visit(this.condition);
        ctx.sql(')');
    }

    @Override
    public final Condition $arg1() {
        return this.condition;
    }

    @Override
    public final QOM.ConditionAsField $arg1(Condition newValue) {
        return new ConditionAsField(newValue);
    }

    @Override
    public final Function1<? super Condition, ? extends QOM.ConditionAsField> $constructor() {
        return a1 -> new ConditionAsField((Condition)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ConditionAsField) {
            QOM.ConditionAsField o = (QOM.ConditionAsField)that;
            return StringUtils.equals(this.$condition(), o.$condition());
        }
        return super.equals(that);
    }
}

