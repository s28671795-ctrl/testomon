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
final class StGeomFromWKB
extends AbstractField<Geometry>
implements QOM.StGeomFromWKB {
    final Field<byte[]> wkb;
    final Field<? extends Number> srid;

    StGeomFromWKB(Field<byte[]> wkb) {
        super(Names.N_ST_GEOMFROMWKB, Tools.allNotNull(SQLDataType.GEOMETRY, wkb));
        this.wkb = Tools.nullSafeNotNull(wkb, SQLDataType.VARBINARY);
        this.srid = null;
    }

    StGeomFromWKB(Field<byte[]> wkb, Field<? extends Number> srid) {
        super(Names.N_ST_GEOMFROMWKB, Tools.allNotNull(SQLDataType.GEOMETRY, wkb, srid));
        this.wkb = Tools.nullSafeNotNull(wkb, SQLDataType.VARBINARY);
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
                ctx.visit(DSL.field("sdo_geometry(wkb => {0}, srid => {1})", this.getDataType(), new QueryPart[]{this.wkb, this.srid == null ? DSL.inline(null, SQLDataType.NUMERIC) : DSL.nullif(this.srid, DSL.zero())}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("geometry::STGeomFromWKB({0}, {1})", this.getDataType(), new QueryPart[]{this.wkb, this.srid == null ? DSL.zero() : this.srid}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_GEOMFROMWKB, this.getDataType(), this.wkb, this.srid == null ? DSL.zero() : this.srid));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stGeomFromWKB(");
        String s2 = "";
        ctx.sql(s2).visit(this.wkb);
        s2 = ", ";
        if (this.srid != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.srid));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<byte[]> $arg1() {
        return this.wkb;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.srid;
    }

    @Override
    public final QOM.StGeomFromWKB $arg1(Field<byte[]> newValue) {
        return this.$constructor().apply((Field<byte[]>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.StGeomFromWKB $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<byte[]>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<byte[]>, ? super Field<? extends Number>, ? extends QOM.StGeomFromWKB> $constructor() {
        return (a1, a2) -> new StGeomFromWKB((Field<byte[]>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StGeomFromWKB) {
            QOM.StGeomFromWKB o = (QOM.StGeomFromWKB)that;
            return StringUtils.equals(this.$wkb(), o.$wkb()) && StringUtils.equals(this.$srid(), o.$srid());
        }
        return super.equals(that);
    }
}

