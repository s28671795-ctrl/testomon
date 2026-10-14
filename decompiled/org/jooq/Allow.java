/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jooq.SQLDialect;

@Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.PACKAGE})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Allow {
    public SQLDialect[] value() default {SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MEMSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.CUBRID, SQLDialect.DEFAULT, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB};

    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.PACKAGE})
    @Retention(value=RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    public static @interface PlainSQL {
    }
}

