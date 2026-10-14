/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Condition;
import org.jooq.Constraint;
import org.jooq.CreateTableAsStep;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.TableElement;

public interface CreateTableElementListStep
extends CreateTableAsStep {
    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep tableElements(TableElement ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep tableElements(Collection<? extends TableElement> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep columns(String ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep columns(Name ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep columns(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep columns(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep column(Field<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep column(String var1, DataType<?> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep column(Name var1, DataType<?> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep column(Field<?> var1, DataType<?> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep constraints(Constraint ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep constraints(Collection<? extends Constraint> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep constraint(Constraint var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep primaryKey(String ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep primaryKey(Name ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep primaryKey(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep primaryKey(Collection<? extends Field<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep unique(String ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep unique(Name ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep unique(Field<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep unique(Collection<? extends Field<?>> var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0_19, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep check(Condition var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep indexes(Index ... var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep indexes(Collection<? extends Index> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep index(Index var1);
}

