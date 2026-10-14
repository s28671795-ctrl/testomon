/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.CommonTableExpression;
import org.jooq.DerivedColumnList;
import org.jooq.DerivedColumnList1;
import org.jooq.DerivedColumnList10;
import org.jooq.DerivedColumnList11;
import org.jooq.DerivedColumnList12;
import org.jooq.DerivedColumnList13;
import org.jooq.DerivedColumnList14;
import org.jooq.DerivedColumnList15;
import org.jooq.DerivedColumnList16;
import org.jooq.DerivedColumnList17;
import org.jooq.DerivedColumnList18;
import org.jooq.DerivedColumnList19;
import org.jooq.DerivedColumnList2;
import org.jooq.DerivedColumnList20;
import org.jooq.DerivedColumnList21;
import org.jooq.DerivedColumnList22;
import org.jooq.DerivedColumnList3;
import org.jooq.DerivedColumnList4;
import org.jooq.DerivedColumnList5;
import org.jooq.DerivedColumnList6;
import org.jooq.DerivedColumnList7;
import org.jooq.DerivedColumnList8;
import org.jooq.DerivedColumnList9;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;

public interface Name
extends QueryPart,
Comparable<Name> {
    @Nullable
    public String first();

    @Nullable
    public String last();

    public boolean empty();

    public boolean qualified();

    public boolean qualifierQualified();

    @Nullable
    public Name qualifier();

    @NotNull
    public Name unqualifiedName();

    @NotNull
    public Quoted quoted();

    @NotNull
    public Name quotedName();

    @NotNull
    public Name unquotedName();

    @NotNull
    public @NotNull Name @NotNull [] parts();

    @NotNull
    public Name append(String var1);

    @NotNull
    public Name append(Name var1);

    @NotNull
    public @NotNull String @NotNull [] getName();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowDefinition as();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public WindowDefinition as(WindowSpecification var1);

    @Support
    @NotNull
    public <R extends Record> CommonTableExpression<R> as(ResultQuery<R> var1);

    @Support
    @NotNull
    public <R extends Record> CommonTableExpression<R> asMaterialized(ResultQuery<R> var1);

    @Support
    @NotNull
    public <R extends Record> CommonTableExpression<R> asNotMaterialized(ResultQuery<R> var1);

    @Support
    @NotNull
    public DerivedColumnList fields(String ... var1);

    @Support
    @NotNull
    public DerivedColumnList fields(Name ... var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public DerivedColumnList fields(Function<? super Field<?>, ? extends String> var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public DerivedColumnList fields(BiFunction<? super Field<?>, ? super Integer, ? extends String> var1);

    @Support
    @NotNull
    public DerivedColumnList1 fields(String var1);

    @Support
    @NotNull
    public DerivedColumnList2 fields(String var1, String var2);

    @Support
    @NotNull
    public DerivedColumnList3 fields(String var1, String var2, String var3);

    @Support
    @NotNull
    public DerivedColumnList4 fields(String var1, String var2, String var3, String var4);

    @Support
    @NotNull
    public DerivedColumnList5 fields(String var1, String var2, String var3, String var4, String var5);

    @Support
    @NotNull
    public DerivedColumnList6 fields(String var1, String var2, String var3, String var4, String var5, String var6);

    @Support
    @NotNull
    public DerivedColumnList7 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

    @Support
    @NotNull
    public DerivedColumnList8 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    @Support
    @NotNull
    public DerivedColumnList9 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

    @Support
    @NotNull
    public DerivedColumnList10 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10);

    @Support
    @NotNull
    public DerivedColumnList11 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11);

    @Support
    @NotNull
    public DerivedColumnList12 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12);

    @Support
    @NotNull
    public DerivedColumnList13 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13);

    @Support
    @NotNull
    public DerivedColumnList14 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14);

    @Support
    @NotNull
    public DerivedColumnList15 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15);

    @Support
    @NotNull
    public DerivedColumnList16 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16);

    @Support
    @NotNull
    public DerivedColumnList17 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17);

    @Support
    @NotNull
    public DerivedColumnList18 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18);

    @Support
    @NotNull
    public DerivedColumnList19 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19);

    @Support
    @NotNull
    public DerivedColumnList20 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20);

    @Support
    @NotNull
    public DerivedColumnList21 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21);

    @Support
    @NotNull
    public DerivedColumnList22 fields(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22);

    @Support
    @NotNull
    public DerivedColumnList1 fields(Name var1);

    @Support
    @NotNull
    public DerivedColumnList2 fields(Name var1, Name var2);

    @Support
    @NotNull
    public DerivedColumnList3 fields(Name var1, Name var2, Name var3);

    @Support
    @NotNull
    public DerivedColumnList4 fields(Name var1, Name var2, Name var3, Name var4);

    @Support
    @NotNull
    public DerivedColumnList5 fields(Name var1, Name var2, Name var3, Name var4, Name var5);

    @Support
    @NotNull
    public DerivedColumnList6 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6);

    @Support
    @NotNull
    public DerivedColumnList7 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7);

    @Support
    @NotNull
    public DerivedColumnList8 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8);

    @Support
    @NotNull
    public DerivedColumnList9 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9);

    @Support
    @NotNull
    public DerivedColumnList10 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10);

    @Support
    @NotNull
    public DerivedColumnList11 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11);

    @Support
    @NotNull
    public DerivedColumnList12 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12);

    @Support
    @NotNull
    public DerivedColumnList13 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13);

    @Support
    @NotNull
    public DerivedColumnList14 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14);

    @Support
    @NotNull
    public DerivedColumnList15 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15);

    @Support
    @NotNull
    public DerivedColumnList16 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16);

    @Support
    @NotNull
    public DerivedColumnList17 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17);

    @Support
    @NotNull
    public DerivedColumnList18 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18);

    @Support
    @NotNull
    public DerivedColumnList19 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19);

    @Support
    @NotNull
    public DerivedColumnList20 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20);

    @Support
    @NotNull
    public DerivedColumnList21 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21);

    @Support
    @NotNull
    public DerivedColumnList22 fields(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22);

    @Override
    public boolean equals(Object var1);

    public boolean equalsIgnoreCase(Name var1);

    @Override
    public int compareTo(Name var1);

    public static enum Quoted {
        QUOTED,
        UNQUOTED,
        SYSTEM,
        DEFAULT,
        MIXED;

    }
}

