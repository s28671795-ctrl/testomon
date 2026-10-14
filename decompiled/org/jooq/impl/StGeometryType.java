/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Spatial;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class StGeometryType
extends AbstractField<String>
implements QOM.StGeometryType {
    final Field<? extends Spatial> value;

    StGeometryType(Field<? extends Spatial> value) {
        super(Names.N_ST_GEOMETRYTYPE, Tools.allNotNull(SQLDataType.VARCHAR, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.GEOMETRY);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: {
                return false;
            }
            case SQLSERVER: {
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
            case ORACLE: {
                ctx.visit(DSL.decode(DSL.field("({0}).sdo_gtype", this.value).mod(DSL.inline(100)), DSL.inline(1), DSL.inline("POINT"), new Object[]{DSL.inline(2), DSL.inline("LINE"), DSL.inline(3), DSL.inline("POLYGON"), DSL.inline(4), DSL.inline("COLLECTION"), DSL.inline(5), DSL.inline("MULTIPOINT"), DSL.inline(6), DSL.inline("MULTILINE"), DSL.inline(7), DSL.inline("MULTIPOLYGON"), DSL.inline(8), DSL.inline("SOLID"), DSL.inline(9), DSL.inline("MULTISOLID"), DSL.inline("UNKNOWN_GEOMETRY")}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("{0}.STGeometryType()", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_GEOMETRYTYPE, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stGeometryType(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Spatial> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.StGeometryType $arg1(Field<? extends Spatial> newValue) {
        return this.$constructor().apply((Field<? extends Spatial>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Spatial>, ? extends QOM.StGeometryType> $constructor() {
        return a1 -> new StGeometryType((Field<? extends Spatial>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StGeometryType) {
            QOM.StGeometryType o = (QOM.StGeometryType)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

