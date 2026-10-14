/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Timestamp;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.ConvertDateTime;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class ToTimestamp
extends AbstractField<Timestamp>
implements QOM.ToTimestamp {
    final Field<String> value;
    final Field<String> formatMask;

    ToTimestamp(Field<String> value, Field<String> formatMask) {
        super(Names.N_TO_TIMESTAMP, Tools.allNotNull(SQLDataType.TIMESTAMP, value, formatMask));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.VARCHAR);
        this.formatMask = Tools.nullSafeNotNull(formatMask, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLSERVER: 
            case SQLDATAWAREHOUSE: {
                Integer style = null;
                Field<String> field2 = this.formatMask;
                if (field2 instanceof Param) {
                    Param p = (Param)field2;
                    style = ConvertDateTime.STYLES.get((Object)ctx.family()).get(p.getValue());
                }
                if (style != null) {
                    ctx.visit(DSL.convert(SQLDataType.TIMESTAMP, this.value, (int)style));
                    break;
                }
                ctx.visit(DSL.function(Names.N_TO_TIMESTAMP, this.getDataType(), this.value, this.formatMask));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_TO_TIMESTAMP, this.getDataType(), this.value, this.formatMask));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("toTimestamp(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.formatMask));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.value;
    }

    @Override
    public final Field<String> $arg2() {
        return this.formatMask;
    }

    @Override
    public final QOM.ToTimestamp $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.ToTimestamp $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue);
    }

    @Override
    public final Function2<? super Field<String>, ? super Field<String>, ? extends QOM.ToTimestamp> $constructor() {
        return (a1, a2) -> new ToTimestamp((Field<String>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.ToTimestamp) {
            QOM.ToTimestamp o = (QOM.ToTimestamp)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$formatMask(), o.$formatMask());
        }
        return super.equals(that);
    }
}

