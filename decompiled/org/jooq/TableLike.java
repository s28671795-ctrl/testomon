/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public interface TableLike<R extends Record>
extends Fields,
QueryPart {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Field<Result<R>> asMultiset();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Field<Result<R>> asMultiset(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Field<Result<R>> asMultiset(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL_5_7, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    public Field<Result<R>> asMultiset(Field<?> var1);

    @Support
    @NotNull
    public Table<R> asTable();

    @Support
    @NotNull
    public Table<R> asTable(String var1);

    @Support
    @NotNull
    public Table<R> asTable(String var1, String ... var2);

    @Support
    @NotNull
    public Table<R> asTable(String var1, Collection<? extends String> var2);

    @Support
    @NotNull
    public Table<R> asTable(Name var1);

    @Support
    @NotNull
    public Table<R> asTable(Name var1, Name ... var2);

    @Support
    @NotNull
    public Table<R> asTable(Name var1, Collection<? extends Name> var2);

    @Support
    @NotNull
    public Table<R> asTable(Table<?> var1);

    @Support
    @NotNull
    public Table<R> asTable(Table<?> var1, Field<?> ... var2);

    @Support
    @NotNull
    public Table<R> asTable(Table<?> var1, Collection<? extends Field<?>> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> asTable(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    public Table<R> asTable(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);
}

