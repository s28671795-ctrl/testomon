/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class BitXorAgg<T extends Number>
extends AbstractAggregateFunction<T>
implements QOM.BitXorAgg<T> {
    public static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE18C, SQLDialect.POSTGRES_13, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB);

    BitXorAgg(Field<T> value) {
        super(false, Names.N_BIT_XOR_AGG, Tools.nullSafeDataType(value), Tools.nullSafeNotNull(value, SQLDataType.INTEGER));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_NATIVE.contains((Object)ctx.dialect())) {
            Field field2 = (Field)this.getArguments().get(0);
            if (field2.getType() == Byte.class) {
                Field f = field2;
                Param<Byte> b0 = DSL.inline((byte)0);
                Param<Byte> b2 = DSL.inline((byte)2);
                ctx.visit(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)1))).mod(b2).eq(DSL.one()), DSL.inline((byte)1)).else_(b0).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)2))).mod(b2).eq(DSL.one()), DSL.inline((byte)2)).else_(b0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)4))).mod(b2).eq(DSL.one()), DSL.inline((byte)4)).else_(b0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)8))).mod(b2).eq(DSL.one()), DSL.inline((byte)8)).else_(b0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)16))).mod(b2).eq(DSL.one()), DSL.inline((byte)16)).else_(b0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)32))).mod(b2).eq(DSL.one()), DSL.inline((byte)32)).else_(b0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)64))).mod(b2).eq(DSL.one()), DSL.inline((byte)64)).else_(b0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (byte)-128))).mod(b2).eq(DSL.one()), DSL.inline((byte)-128)).else_(b0)));
            } else if (field2.getType() == Short.class) {
                Field f = field2;
                Param<Short> s0 = DSL.inline((short)0);
                Param<Short> s2 = DSL.inline((short)2);
                ctx.visit(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)1))).mod(s2).eq(DSL.one()), DSL.inline((short)1)).else_(s0).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)2))).mod(s2).eq(DSL.one()), DSL.inline((short)2)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)4))).mod(s2).eq(DSL.one()), DSL.inline((short)4)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)8))).mod(s2).eq(DSL.one()), DSL.inline((short)8)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)16))).mod(s2).eq(DSL.one()), DSL.inline((short)16)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)32))).mod(s2).eq(DSL.one()), DSL.inline((short)32)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)64))).mod(s2).eq(DSL.one()), DSL.inline((short)64)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)128))).mod(s2).eq(DSL.one()), DSL.inline((short)128)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)256))).mod(s2).eq(DSL.one()), DSL.inline((short)256)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)512))).mod(s2).eq(DSL.one()), DSL.inline((short)512)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)1024))).mod(s2).eq(DSL.one()), DSL.inline((short)1024)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)2048))).mod(s2).eq(DSL.one()), DSL.inline((short)2048)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)4096))).mod(s2).eq(DSL.one()), DSL.inline((short)4096)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)8192))).mod(s2).eq(DSL.one()), DSL.inline((short)8192)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)16384))).mod(s2).eq(DSL.one()), DSL.inline((short)16384)).else_(s0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, (short)Short.MIN_VALUE))).mod(s2).eq(DSL.one()), DSL.inline((short)Short.MIN_VALUE)).else_(s0)));
            } else if (field2.getType() == Integer.class) {
                Field f = field2;
                Param<Integer> i0 = DSL.inline(0);
                Param<Integer> i2 = DSL.inline(2);
                ctx.visit(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 1))).mod(i2).eq(DSL.one()), DSL.inline(1)).else_(i0).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 2))).mod(i2).eq(DSL.one()), DSL.inline(2)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 4))).mod(i2).eq(DSL.one()), DSL.inline(4)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 8))).mod(i2).eq(DSL.one()), DSL.inline(8)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 16))).mod(i2).eq(DSL.one()), DSL.inline(16)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 32))).mod(i2).eq(DSL.one()), DSL.inline(32)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 64))).mod(i2).eq(DSL.one()), DSL.inline(64)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 128))).mod(i2).eq(DSL.one()), DSL.inline(128)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 256))).mod(i2).eq(DSL.one()), DSL.inline(256)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 512))).mod(i2).eq(DSL.one()), DSL.inline(512)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 1024))).mod(i2).eq(DSL.one()), DSL.inline(1024)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 2048))).mod(i2).eq(DSL.one()), DSL.inline(2048)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 4096))).mod(i2).eq(DSL.one()), DSL.inline(4096)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 8192))).mod(i2).eq(DSL.one()), DSL.inline(8192)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 16384))).mod(i2).eq(DSL.one()), DSL.inline(16384)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 32768))).mod(i2).eq(DSL.one()), DSL.inline(32768)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 65536))).mod(i2).eq(DSL.one()), DSL.inline(65536)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 131072))).mod(i2).eq(DSL.one()), DSL.inline(131072)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 262144))).mod(i2).eq(DSL.one()), DSL.inline(262144)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 524288))).mod(i2).eq(DSL.one()), DSL.inline(524288)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x100000))).mod(i2).eq(DSL.one()), DSL.inline(0x100000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x200000))).mod(i2).eq(DSL.one()), DSL.inline(0x200000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x400000))).mod(i2).eq(DSL.one()), DSL.inline(0x400000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x800000))).mod(i2).eq(DSL.one()), DSL.inline(0x800000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x1000000))).mod(i2).eq(DSL.one()), DSL.inline(0x1000000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x2000000))).mod(i2).eq(DSL.one()), DSL.inline(0x2000000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x4000000))).mod(i2).eq(DSL.one()), DSL.inline(0x4000000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x8000000))).mod(i2).eq(DSL.one()), DSL.inline(0x8000000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x10000000))).mod(i2).eq(DSL.one()), DSL.inline(0x10000000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x20000000))).mod(i2).eq(DSL.one()), DSL.inline(0x20000000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x40000000))).mod(i2).eq(DSL.one()), DSL.inline(0x40000000)).else_(i0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, Integer.MIN_VALUE))).mod(i2).eq(DSL.one()), DSL.inline(Integer.MIN_VALUE)).else_(i0)));
            } else if (field2.getType() == Long.class) {
                Field f = field2;
                Param<Long> l0 = DSL.inline(0L);
                Param<Long> l2 = DSL.inline(2L);
                ctx.visit(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 1L))).mod(l2).eq(DSL.one()), DSL.inline(1L)).else_(l0).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 2L))).mod(l2).eq(DSL.one()), DSL.inline(2L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 4L))).mod(l2).eq(DSL.one()), DSL.inline(4L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 8L))).mod(l2).eq(DSL.one()), DSL.inline(8L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 16L))).mod(l2).eq(DSL.one()), DSL.inline(16L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 32L))).mod(l2).eq(DSL.one()), DSL.inline(32L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 64L))).mod(l2).eq(DSL.one()), DSL.inline(64L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 128L))).mod(l2).eq(DSL.one()), DSL.inline(128L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 256L))).mod(l2).eq(DSL.one()), DSL.inline(256L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 512L))).mod(l2).eq(DSL.one()), DSL.inline(512L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 1024L))).mod(l2).eq(DSL.one()), DSL.inline(1024L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 2048L))).mod(l2).eq(DSL.one()), DSL.inline(2048L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 4096L))).mod(l2).eq(DSL.one()), DSL.inline(4096L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 8192L))).mod(l2).eq(DSL.one()), DSL.inline(8192L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 16384L))).mod(l2).eq(DSL.one()), DSL.inline(16384L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 32768L))).mod(l2).eq(DSL.one()), DSL.inline(32768L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 65536L))).mod(l2).eq(DSL.one()), DSL.inline(65536L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 131072L))).mod(l2).eq(DSL.one()), DSL.inline(131072L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 262144L))).mod(l2).eq(DSL.one()), DSL.inline(262144L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 524288L))).mod(l2).eq(DSL.one()), DSL.inline(524288L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x100000L))).mod(l2).eq(DSL.one()), DSL.inline(0x100000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x200000L))).mod(l2).eq(DSL.one()), DSL.inline(0x200000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x400000L))).mod(l2).eq(DSL.one()), DSL.inline(0x400000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x800000L))).mod(l2).eq(DSL.one()), DSL.inline(0x800000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x1000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x1000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x2000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x2000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x4000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x4000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x8000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x8000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x10000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x10000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x20000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x20000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x40000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x40000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x80000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x80000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x100000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x100000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x200000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x200000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x400000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x400000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x800000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x800000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x1000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x1000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x2000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x2000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x4000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x4000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x8000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x8000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x10000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x10000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x20000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x20000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x40000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x40000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x80000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x80000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x100000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x100000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x200000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x200000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x400000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x400000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x800000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x800000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x1000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x1000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x2000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x2000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x4000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x4000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x8000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x8000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x10000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x10000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x20000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x20000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x40000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x40000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x80000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x80000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x100000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x100000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x200000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x200000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x400000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x400000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x800000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x800000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x1000000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x1000000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x2000000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x2000000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, 0x4000000000000000L))).mod(l2).eq(DSL.one()), DSL.inline(0x4000000000000000L)).else_(l0)).plus(DSL.when(this.o(DSL.count().filterWhere(this.bitCheck(f, Long.MIN_VALUE))).mod(l2).eq(DSL.one()), DSL.inline(Long.MIN_VALUE)).else_(l0)));
            } else {
                super.accept(ctx);
            }
        } else if (ctx.family() == SQLDialect.VERTICA) {
            ctx.visit(DSL.function(Names.N_HEX_TO_INTEGER, this.getDataType(), DSL.function(Names.N_TO_HEX, SQLDataType.VARCHAR, this.fo(DSL.aggregate(Names.N_BIT_XOR, this.getDataType(), DSL.function(Names.N_HEX_TO_BINARY, SQLDataType.VARBINARY, DSL.function(Names.N_TO_HEX, SQLDataType.VARCHAR, (Field)this.getArguments().get(0))))))));
        } else {
            super.accept(ctx);
        }
    }

    private final <N extends Number> Condition bitCheck(Field<N> field2, N mask) {
        return this.f(DSL.bitAnd(field2, DSL.inline(mask)).eq((Field<N>)DSL.inline(mask)));
    }

    @Override
    final void acceptFunctionName(Context<?> ctx) {
        switch (ctx.family()) {
            case BIGQUERY: 
            case COCKROACHDB: 
            case REDSHIFT: 
            case SYBASE: 
            case VERTICA: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: {
                ctx.visit(Names.N_BIT_XOR);
                break;
            }
            case H2: {
                if (ctx.dialect().precedes(SQLDialect.H2_1_4_200)) {
                    ctx.visit(Names.N_BIT_XOR);
                    break;
                }
                ctx.visit(Names.N_BIT_XOR_AGG);
                break;
            }
            default: {
                super.acceptFunctionName(ctx);
            }
        }
    }

    @Override
    public final Field<T> $value() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.BitXorAgg<T> $value(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Field<T>, ? extends QOM.BitXorAgg<T>> $constructor() {
        return a1 -> new BitXorAgg(a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$value(), a1 -> this.$constructor().apply((Field<T>)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$value());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitXorAgg) {
            QOM.BitXorAgg o = (QOM.BitXorAgg)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

