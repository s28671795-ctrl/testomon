/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class BitCount
extends AbstractField<Integer>
implements QOM.BitCount {
    final Field<? extends Number> value;

    BitCount(Field<? extends Number> value) {
        super(Names.N_BIT_COUNT, Tools.allNotNull(SQLDataType.INTEGER, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                return;
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(Names.N_BIT_COUNT).sql('(').visit(this.value).sql(')');
                return;
            }
            case HANA: {
                ctx.visit(Names.N_BITCOUNT).sql('(').visit(this.value).sql(')');
                return;
            }
            case TERADATA: {
                ctx.visit(Names.N_COUNTSET).sql('(').visit(this.value).sql(", ").visit(DSL.one()).sql(')');
                return;
            }
            case SQLSERVER: {
                if (ctx.dialect().precedesStrictly(SQLDialect.SQLSERVER2022)) {
                    this.bitAndDivEmulation(ctx);
                } else {
                    ctx.visit(Names.N_BIT_COUNT).sql('(').visit(this.value).sql(')');
                }
                return;
            }
            case ASE: 
            case DB2: 
            case INFORMIX: 
            case INGRES: 
            case ORACLE: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case H2: 
            case HSQLDB: {
                this.bitAndDivEmulation(ctx);
                return;
            }
        }
        this.bitAndShrEmulation(ctx);
    }

    private final void bitAndDivEmulation(Context<?> ctx) {
        if (this.value.getType() == Byte.class) {
            Field<? extends Number> f = this.value;
            ctx.visit(DSL.bitAnd(f, DSL.inline((byte)1)).add(DSL.bitAnd(f, DSL.inline((byte)2)).div(DSL.inline((byte)2))).add(DSL.bitAnd(f, DSL.inline((byte)4)).div(DSL.inline((byte)4))).add(DSL.bitAnd(f, DSL.inline((byte)8)).div(DSL.inline((byte)8))).add(DSL.bitAnd(f, DSL.inline((byte)16)).div(DSL.inline((byte)16))).add(DSL.bitAnd(f, DSL.inline((byte)32)).div(DSL.inline((byte)32))).add(DSL.bitAnd(f, DSL.inline((byte)64)).div(DSL.inline((byte)64))).add(DSL.bitAnd(f, DSL.inline((byte)-128)).div(DSL.inline((byte)-128))).cast(Integer.class));
        } else if (this.value.getType() == Short.class) {
            Field<? extends Number> f = this.value;
            ctx.visit(DSL.bitAnd(f, DSL.inline((short)1)).add(DSL.bitAnd(f, DSL.inline((short)2)).div(DSL.inline((short)2))).add(DSL.bitAnd(f, DSL.inline((short)4)).div(DSL.inline((short)4))).add(DSL.bitAnd(f, DSL.inline((short)8)).div(DSL.inline((short)8))).add(DSL.bitAnd(f, DSL.inline((short)16)).div(DSL.inline((short)16))).add(DSL.bitAnd(f, DSL.inline((short)32)).div(DSL.inline((short)32))).add(DSL.bitAnd(f, DSL.inline((short)64)).div(DSL.inline((short)64))).add(DSL.bitAnd(f, DSL.inline((short)128)).div(DSL.inline((short)128))).add(DSL.bitAnd(f, DSL.inline((short)256)).div(DSL.inline((short)256))).add(DSL.bitAnd(f, DSL.inline((short)512)).div(DSL.inline((short)512))).add(DSL.bitAnd(f, DSL.inline((short)1024)).div(DSL.inline((short)1024))).add(DSL.bitAnd(f, DSL.inline((short)2048)).div(DSL.inline((short)2048))).add(DSL.bitAnd(f, DSL.inline((short)4096)).div(DSL.inline((short)4096))).add(DSL.bitAnd(f, DSL.inline((short)8192)).div(DSL.inline((short)8192))).add(DSL.bitAnd(f, DSL.inline((short)16384)).div(DSL.inline((short)16384))).add(DSL.bitAnd(f, DSL.inline((short)Short.MIN_VALUE)).div(DSL.inline((short)Short.MIN_VALUE))).cast(Integer.class));
        } else if (this.value.getType() == Integer.class) {
            Field<? extends Number> f = this.value;
            ctx.visit(DSL.bitAnd(f, DSL.inline(1)).add(DSL.bitAnd(f, DSL.inline(2)).div(DSL.inline(2))).add(DSL.bitAnd(f, DSL.inline(4)).div(DSL.inline(4))).add(DSL.bitAnd(f, DSL.inline(8)).div(DSL.inline(8))).add(DSL.bitAnd(f, DSL.inline(16)).div(DSL.inline(16))).add(DSL.bitAnd(f, DSL.inline(32)).div(DSL.inline(32))).add(DSL.bitAnd(f, DSL.inline(64)).div(DSL.inline(64))).add(DSL.bitAnd(f, DSL.inline(128)).div(DSL.inline(128))).add(DSL.bitAnd(f, DSL.inline(256)).div(DSL.inline(256))).add(DSL.bitAnd(f, DSL.inline(512)).div(DSL.inline(512))).add(DSL.bitAnd(f, DSL.inline(1024)).div(DSL.inline(1024))).add(DSL.bitAnd(f, DSL.inline(2048)).div(DSL.inline(2048))).add(DSL.bitAnd(f, DSL.inline(4096)).div(DSL.inline(4096))).add(DSL.bitAnd(f, DSL.inline(8192)).div(DSL.inline(8192))).add(DSL.bitAnd(f, DSL.inline(16384)).div(DSL.inline(16384))).add(DSL.bitAnd(f, DSL.inline(32768)).div(DSL.inline(32768))).add(DSL.bitAnd(f, DSL.inline(65536)).div(DSL.inline(65536))).add(DSL.bitAnd(f, DSL.inline(131072)).div(DSL.inline(131072))).add(DSL.bitAnd(f, DSL.inline(262144)).div(DSL.inline(262144))).add(DSL.bitAnd(f, DSL.inline(524288)).div(DSL.inline(524288))).add(DSL.bitAnd(f, DSL.inline(0x100000)).div(DSL.inline(0x100000))).add(DSL.bitAnd(f, DSL.inline(0x200000)).div(DSL.inline(0x200000))).add(DSL.bitAnd(f, DSL.inline(0x400000)).div(DSL.inline(0x400000))).add(DSL.bitAnd(f, DSL.inline(0x800000)).div(DSL.inline(0x800000))).add(DSL.bitAnd(f, DSL.inline(0x1000000)).div(DSL.inline(0x1000000))).add(DSL.bitAnd(f, DSL.inline(0x2000000)).div(DSL.inline(0x2000000))).add(DSL.bitAnd(f, DSL.inline(0x4000000)).div(DSL.inline(0x4000000))).add(DSL.bitAnd(f, DSL.inline(0x8000000)).div(DSL.inline(0x8000000))).add(DSL.bitAnd(f, DSL.inline(0x10000000)).div(DSL.inline(0x10000000))).add(DSL.bitAnd(f, DSL.inline(0x20000000)).div(DSL.inline(0x20000000))).add(DSL.bitAnd(f, DSL.inline(0x40000000)).div(DSL.inline(0x40000000))).add(DSL.bitAnd(f, DSL.inline(Integer.MIN_VALUE)).div(DSL.inline(Integer.MIN_VALUE))));
        } else if (this.value.getType() == Long.class) {
            Field<? extends Number> f = this.value;
            ctx.visit(DSL.bitAnd(f, DSL.inline(1L)).add(DSL.bitAnd(f, DSL.inline(2L)).div(DSL.inline(2L))).add(DSL.bitAnd(f, DSL.inline(4L)).div(DSL.inline(4L))).add(DSL.bitAnd(f, DSL.inline(8L)).div(DSL.inline(8L))).add(DSL.bitAnd(f, DSL.inline(16L)).div(DSL.inline(16L))).add(DSL.bitAnd(f, DSL.inline(32L)).div(DSL.inline(32L))).add(DSL.bitAnd(f, DSL.inline(64L)).div(DSL.inline(64L))).add(DSL.bitAnd(f, DSL.inline(128L)).div(DSL.inline(128L))).add(DSL.bitAnd(f, DSL.inline(256L)).div(DSL.inline(256L))).add(DSL.bitAnd(f, DSL.inline(512L)).div(DSL.inline(512L))).add(DSL.bitAnd(f, DSL.inline(1024L)).div(DSL.inline(1024L))).add(DSL.bitAnd(f, DSL.inline(2048L)).div(DSL.inline(2048L))).add(DSL.bitAnd(f, DSL.inline(4096L)).div(DSL.inline(4096L))).add(DSL.bitAnd(f, DSL.inline(8192L)).div(DSL.inline(8192L))).add(DSL.bitAnd(f, DSL.inline(16384L)).div(DSL.inline(16384L))).add(DSL.bitAnd(f, DSL.inline(32768L)).div(DSL.inline(32768L))).add(DSL.bitAnd(f, DSL.inline(65536L)).div(DSL.inline(65536L))).add(DSL.bitAnd(f, DSL.inline(131072L)).div(DSL.inline(131072L))).add(DSL.bitAnd(f, DSL.inline(262144L)).div(DSL.inline(262144L))).add(DSL.bitAnd(f, DSL.inline(524288L)).div(DSL.inline(524288L))).add(DSL.bitAnd(f, DSL.inline(0x100000L)).div(DSL.inline(0x100000L))).add(DSL.bitAnd(f, DSL.inline(0x200000L)).div(DSL.inline(0x200000L))).add(DSL.bitAnd(f, DSL.inline(0x400000L)).div(DSL.inline(0x400000L))).add(DSL.bitAnd(f, DSL.inline(0x800000L)).div(DSL.inline(0x800000L))).add(DSL.bitAnd(f, DSL.inline(0x1000000L)).div(DSL.inline(0x1000000L))).add(DSL.bitAnd(f, DSL.inline(0x2000000L)).div(DSL.inline(0x2000000L))).add(DSL.bitAnd(f, DSL.inline(0x4000000L)).div(DSL.inline(0x4000000L))).add(DSL.bitAnd(f, DSL.inline(0x8000000L)).div(DSL.inline(0x8000000L))).add(DSL.bitAnd(f, DSL.inline(0x10000000L)).div(DSL.inline(0x10000000L))).add(DSL.bitAnd(f, DSL.inline(0x20000000L)).div(DSL.inline(0x20000000L))).add(DSL.bitAnd(f, DSL.inline(0x40000000L)).div(DSL.inline(0x40000000L))).add(DSL.bitAnd(f, DSL.inline(0x80000000L)).div(DSL.inline(0x80000000L))).add(DSL.bitAnd(f, DSL.inline(0x100000000L)).div(DSL.inline(0x100000000L))).add(DSL.bitAnd(f, DSL.inline(0x200000000L)).div(DSL.inline(0x200000000L))).add(DSL.bitAnd(f, DSL.inline(0x400000000L)).div(DSL.inline(0x400000000L))).add(DSL.bitAnd(f, DSL.inline(0x800000000L)).div(DSL.inline(0x800000000L))).add(DSL.bitAnd(f, DSL.inline(0x1000000000L)).div(DSL.inline(0x1000000000L))).add(DSL.bitAnd(f, DSL.inline(0x2000000000L)).div(DSL.inline(0x2000000000L))).add(DSL.bitAnd(f, DSL.inline(0x4000000000L)).div(DSL.inline(0x4000000000L))).add(DSL.bitAnd(f, DSL.inline(0x8000000000L)).div(DSL.inline(0x8000000000L))).add(DSL.bitAnd(f, DSL.inline(0x10000000000L)).div(DSL.inline(0x10000000000L))).add(DSL.bitAnd(f, DSL.inline(0x20000000000L)).div(DSL.inline(0x20000000000L))).add(DSL.bitAnd(f, DSL.inline(0x40000000000L)).div(DSL.inline(0x40000000000L))).add(DSL.bitAnd(f, DSL.inline(0x80000000000L)).div(DSL.inline(0x80000000000L))).add(DSL.bitAnd(f, DSL.inline(0x100000000000L)).div(DSL.inline(0x100000000000L))).add(DSL.bitAnd(f, DSL.inline(0x200000000000L)).div(DSL.inline(0x200000000000L))).add(DSL.bitAnd(f, DSL.inline(0x400000000000L)).div(DSL.inline(0x400000000000L))).add(DSL.bitAnd(f, DSL.inline(0x800000000000L)).div(DSL.inline(0x800000000000L))).add(DSL.bitAnd(f, DSL.inline(0x1000000000000L)).div(DSL.inline(0x1000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x2000000000000L)).div(DSL.inline(0x2000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x4000000000000L)).div(DSL.inline(0x4000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x8000000000000L)).div(DSL.inline(0x8000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x10000000000000L)).div(DSL.inline(0x10000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x20000000000000L)).div(DSL.inline(0x20000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x40000000000000L)).div(DSL.inline(0x40000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x80000000000000L)).div(DSL.inline(0x80000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x100000000000000L)).div(DSL.inline(0x100000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x200000000000000L)).div(DSL.inline(0x200000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x400000000000000L)).div(DSL.inline(0x400000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x800000000000000L)).div(DSL.inline(0x800000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x1000000000000000L)).div(DSL.inline(0x1000000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x2000000000000000L)).div(DSL.inline(0x2000000000000000L))).add(DSL.bitAnd(f, DSL.inline(0x4000000000000000L)).div(DSL.inline(0x4000000000000000L))).add(DSL.bitAnd(f, DSL.inline(Long.MIN_VALUE)).div(DSL.inline(0L))).cast(Integer.class));
        } else {
            ctx.visit(Names.N_BIT_COUNT).sql('(').visit(this.value).sql(')');
        }
    }

    private final void bitAndShrEmulation(Context<?> ctx) {
        if (this.value.getType() == Byte.class) {
            Field<? extends Number> f = this.value;
            byte i = 0;
            i = (byte)(i + 1);
            Field<Byte> field2 = DSL.bitAnd(f, DSL.inline((byte)1)).add(DSL.shr(DSL.bitAnd(f, DSL.inline((byte)2)), DSL.inline(i)));
            i = (byte)(i + 1);
            Field<Byte> field3 = field2.add(DSL.shr(DSL.bitAnd(f, DSL.inline((byte)4)), DSL.inline(i)));
            i = (byte)(i + 1);
            Field<Byte> field4 = field3.add(DSL.shr(DSL.bitAnd(f, DSL.inline((byte)8)), DSL.inline(i)));
            i = (byte)(i + 1);
            Field<Byte> field5 = field4.add(DSL.shr(DSL.bitAnd(f, DSL.inline((byte)16)), DSL.inline(i)));
            i = (byte)(i + 1);
            Field<Byte> field6 = field5.add(DSL.shr(DSL.bitAnd(f, DSL.inline((byte)32)), DSL.inline(i)));
            i = (byte)(i + 1);
            Field<Byte> field7 = field6.add(DSL.shr(DSL.bitAnd(f, DSL.inline((byte)64)), DSL.inline(i)));
            i = (byte)(i + 1);
            ctx.visit(field7.add(DSL.shr(DSL.bitAnd(f, DSL.inline((byte)-128)), DSL.inline(i))).cast(Integer.class));
        } else if (this.value.getType() == Short.class) {
            Field<? extends Number> f = this.value;
            short i = 0;
            i = (short)(i + 1);
            Field<Short> field8 = DSL.bitAnd(f, DSL.inline((short)1)).add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)2)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field9 = field8.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)4)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field10 = field9.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)8)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field11 = field10.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)16)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field12 = field11.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)32)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field13 = field12.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)64)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field14 = field13.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)128)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field15 = field14.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)256)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field16 = field15.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)512)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field17 = field16.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)1024)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field18 = field17.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)2048)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field19 = field18.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)4096)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field20 = field19.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)8192)), DSL.inline(i)));
            i = (short)(i + 1);
            Field<Short> field21 = field20.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)16384)), DSL.inline(i)));
            i = (short)(i + 1);
            ctx.visit(field21.add(DSL.shr(DSL.bitAnd(f, DSL.inline((short)Short.MIN_VALUE)), DSL.inline(i))).cast(Integer.class));
        } else if (this.value.getType() == Integer.class) {
            Field<? extends Number> f = this.value;
            int i = 0;
            ctx.visit(DSL.bitAnd(f, DSL.inline(1)).add(DSL.shr(DSL.bitAnd(f, DSL.inline(2)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(4)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(8)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(16)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(32)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(64)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(128)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(256)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(512)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(1024)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(2048)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(4096)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(8192)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(16384)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(32768)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(65536)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(131072)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(262144)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(524288)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x100000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x200000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x400000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x800000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x1000000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x2000000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x4000000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x8000000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x10000000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x20000000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x40000000)), DSL.inline(++i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(Integer.MIN_VALUE)), DSL.inline(++i))));
        } else if (this.value.getType() == Long.class) {
            Field<? extends Number> f = this.value;
            long i = 0L;
            long l = i + 1L;
            i = l;
            long l2 = i + 1L;
            i = l2;
            long l3 = i + 1L;
            i = l3;
            long l4 = i + 1L;
            i = l4;
            long l5 = i + 1L;
            i = l5;
            long l6 = i + 1L;
            i = l6;
            long l7 = i + 1L;
            i = l7;
            long l8 = i + 1L;
            i = l8;
            long l9 = i + 1L;
            i = l9;
            long l10 = i + 1L;
            i = l10;
            long l11 = i + 1L;
            i = l11;
            long l12 = i + 1L;
            i = l12;
            long l13 = i + 1L;
            i = l13;
            long l14 = i + 1L;
            i = l14;
            long l15 = i + 1L;
            i = l15;
            long l16 = i + 1L;
            i = l16;
            long l17 = i + 1L;
            i = l17;
            long l18 = i + 1L;
            i = l18;
            long l19 = i + 1L;
            i = l19;
            long l20 = i + 1L;
            i = l20;
            long l21 = i + 1L;
            i = l21;
            long l22 = i + 1L;
            i = l22;
            long l23 = i + 1L;
            i = l23;
            long l24 = i + 1L;
            i = l24;
            long l25 = i + 1L;
            i = l25;
            long l26 = i + 1L;
            i = l26;
            long l27 = i + 1L;
            i = l27;
            long l28 = i + 1L;
            i = l28;
            long l29 = i + 1L;
            i = l29;
            long l30 = i + 1L;
            i = l30;
            long l31 = i + 1L;
            i = l31;
            long l32 = i + 1L;
            i = l32;
            long l33 = i + 1L;
            i = l33;
            long l34 = i + 1L;
            i = l34;
            long l35 = i + 1L;
            i = l35;
            long l36 = i + 1L;
            i = l36;
            long l37 = i + 1L;
            i = l37;
            long l38 = i + 1L;
            i = l38;
            long l39 = i + 1L;
            i = l39;
            long l40 = i + 1L;
            i = l40;
            long l41 = i + 1L;
            i = l41;
            long l42 = i + 1L;
            i = l42;
            long l43 = i + 1L;
            i = l43;
            long l44 = i + 1L;
            i = l44;
            long l45 = i + 1L;
            i = l45;
            long l46 = i + 1L;
            i = l46;
            long l47 = i + 1L;
            i = l47;
            long l48 = i + 1L;
            i = l48;
            long l49 = i + 1L;
            i = l49;
            long l50 = i + 1L;
            i = l50;
            long l51 = i + 1L;
            i = l51;
            long l52 = i + 1L;
            i = l52;
            long l53 = i + 1L;
            i = l53;
            long l54 = i + 1L;
            i = l54;
            long l55 = i + 1L;
            i = l55;
            long l56 = i + 1L;
            i = l56;
            long l57 = i + 1L;
            i = l57;
            long l58 = i + 1L;
            i = l58;
            long l59 = i + 1L;
            i = l59;
            long l60 = i + 1L;
            i = l60;
            long l61 = i + 1L;
            i = l61;
            i = i + 1L;
            ctx.visit(DSL.bitAnd(f, DSL.inline(1L)).add(DSL.shr(DSL.bitAnd(f, DSL.inline(2L)), DSL.inline(l))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(4L)), DSL.inline(l2))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(8L)), DSL.inline(l3))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(16L)), DSL.inline(l4))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(32L)), DSL.inline(l5))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(64L)), DSL.inline(l6))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(128L)), DSL.inline(l7))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(256L)), DSL.inline(l8))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(512L)), DSL.inline(l9))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(1024L)), DSL.inline(l10))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(2048L)), DSL.inline(l11))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(4096L)), DSL.inline(l12))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(8192L)), DSL.inline(l13))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(16384L)), DSL.inline(l14))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(32768L)), DSL.inline(l15))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(65536L)), DSL.inline(l16))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(131072L)), DSL.inline(l17))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(262144L)), DSL.inline(l18))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(524288L)), DSL.inline(l19))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x100000L)), DSL.inline(l20))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x200000L)), DSL.inline(l21))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x400000L)), DSL.inline(l22))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x800000L)), DSL.inline(l23))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x1000000L)), DSL.inline(l24))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x2000000L)), DSL.inline(l25))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x4000000L)), DSL.inline(l26))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x8000000L)), DSL.inline(l27))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x10000000L)), DSL.inline(l28))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x20000000L)), DSL.inline(l29))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x40000000L)), DSL.inline(l30))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x80000000L)), DSL.inline(l31))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x100000000L)), DSL.inline(l32))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x200000000L)), DSL.inline(l33))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x400000000L)), DSL.inline(l34))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x800000000L)), DSL.inline(l35))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x1000000000L)), DSL.inline(l36))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x2000000000L)), DSL.inline(l37))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x4000000000L)), DSL.inline(l38))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x8000000000L)), DSL.inline(l39))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x10000000000L)), DSL.inline(l40))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x20000000000L)), DSL.inline(l41))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x40000000000L)), DSL.inline(l42))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x80000000000L)), DSL.inline(l43))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x100000000000L)), DSL.inline(l44))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x200000000000L)), DSL.inline(l45))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x400000000000L)), DSL.inline(l46))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x800000000000L)), DSL.inline(l47))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x1000000000000L)), DSL.inline(l48))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x2000000000000L)), DSL.inline(l49))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x4000000000000L)), DSL.inline(l50))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x8000000000000L)), DSL.inline(l51))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x10000000000000L)), DSL.inline(l52))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x20000000000000L)), DSL.inline(l53))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x40000000000000L)), DSL.inline(l54))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x80000000000000L)), DSL.inline(l55))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x100000000000000L)), DSL.inline(l56))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x200000000000000L)), DSL.inline(l57))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x400000000000000L)), DSL.inline(l58))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x800000000000000L)), DSL.inline(l59))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x1000000000000000L)), DSL.inline(l60))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x2000000000000000L)), DSL.inline(l61))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(0x4000000000000000L)), DSL.inline(i))).add(DSL.shr(DSL.bitAnd(f, DSL.inline(Long.MIN_VALUE)), DSL.inline(++i))).cast(Integer.class));
        } else {
            ctx.visit(Names.N_BIT_COUNT).sql('(').visit(this.value).sql(')');
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("bitCount(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.BitCount $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.BitCount> $constructor() {
        return a1 -> new BitCount((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitCount) {
            QOM.BitCount o = (QOM.BitCount)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

