/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Geometry;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class StGeomFromText
extends AbstractField<Geometry>
implements QOM.StGeomFromText {
    final Field<String> text;
    final Field<? extends Number> srid;

    StGeomFromText(Field<String> text) {
        super(Names.N_ST_GEOMFROMTEXT, Tools.allNotNull(SQLDataType.GEOMETRY, text));
        this.text = Tools.nullSafeNotNull(text, SQLDataType.VARCHAR);
        this.srid = null;
    }

    StGeomFromText(Field<String> text, Field<? extends Number> srid) {
        super(Names.N_ST_GEOMFROMTEXT, Tools.allNotNull(SQLDataType.GEOMETRY, text, srid));
        this.text = Tools.nullSafeNotNull(text, SQLDataType.VARCHAR);
        this.srid = Tools.nullSafeNotNull(srid, SQLDataType.INTEGER);
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
                ctx.visit(DSL.field("sdo_geometry(wkt => {0}, srid => {1})", this.getDataType(), new QueryPart[]{this.text, this.srid == null ? DSL.inline(null, SQLDataType.NUMERIC) : DSL.nullif(this.srid, DSL.zero())}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("geometry::STGeomFromText({0}, {1})", this.getDataType(), new QueryPart[]{this.text, this.srid == null ? DSL.zero() : this.srid}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_GEOMFROMTEXT, this.getDataType(), this.text, this.srid == null ? DSL.zero() : this.srid));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stGeomFromText(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.text));
        s2 = ", ";
        if (this.srid != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.srid));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.text;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.srid;
    }

    @Override
    public final QOM.StGeomFromText $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.StGeomFromText $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<String>, ? super Field<? extends Number>, ? extends QOM.StGeomFromText> $constructor() {
        return (a1, a2) -> new StGeomFromText((Field<String>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StGeomFromText) {
            QOM.StGeomFromText o = (QOM.StGeomFromText)that;
            return StringUtils.equals(this.$text(), o.$text()) && StringUtils.equals(this.$srid(), o.$srid());
        }
        return super.equals(that);
    }
}

