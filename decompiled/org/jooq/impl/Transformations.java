/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import java.util.function.Predicate;
import org.jooq.Configuration;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.conf.Transformation;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Transformations {
    static final Set<SQLDialect> NO_SUPPORT_IN_LIMIT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    static final Set<SQLDialect> SUPPORT_MISSING_TABLE_REFERENCES = SQLDialect.supportedBy(SQLDialect.TERADATA);
    static final Set<SQLDialect> EMULATE_QUALIFY = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> EMULATE_ROWNUM = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> EMULATE_GROUP_BY_COLUMN_INDEX = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE);
    static final Set<SQLDialect> NO_SUPPORT_CTE = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.INGRES, SQLDialect.MYSQL_5_7, SQLDialect.REDSHIFT);

    Transformations() {
    }

    static final SelectQueryImpl<?> subqueryWithLimit(QueryPart source2) {
        SelectQueryImpl s2 = Tools.selectQueryImpl(source2);
        return s2 != null && s2.getLimit().isApplicable() ? s2 : null;
    }

    static final boolean transformInConditionSubqueryWithLimitToDerivedTable(Configuration configuration) {
        return Transformations.transform(configuration, "Settings.transformInConditionSubqueryWithLimitToDerivedTable", configuration.settings().getTransformInConditionSubqueryWithLimitToDerivedTable(), c -> NO_SUPPORT_IN_LIMIT.contains((Object)c.dialect()));
    }

    static final boolean transformInlineCTE(Configuration configuration) {
        return Transformations.transform(configuration, "Settings.transformInlineCTE", configuration.settings().getTransformInlineCTE(), c -> NO_SUPPORT_CTE.contains((Object)c.dialect()));
    }

    static final boolean transformQualify(Configuration configuration) {
        return Transformations.transform(configuration, "Settings.transformQualify", configuration.settings().getTransformQualify(), c -> EMULATE_QUALIFY.contains((Object)c.dialect()));
    }

    static final boolean transformRownum(Configuration configuration) {
        return Transformations.transform(configuration, "Settings.transformRownum", configuration.settings().getTransformRownum(), c -> EMULATE_ROWNUM.contains((Object)c.dialect()));
    }

    static final boolean transformAppendMissingTableReferences(Configuration configuration) {
        return Transformations.transform(configuration, "Settings.transformAppendMissingTableReferences", configuration.settings().getParseAppendMissingTableReferences(), c -> SUPPORT_MISSING_TABLE_REFERENCES.contains((Object)c.settings().getParseDialect()));
    }

    static final boolean transformGroupByColumnIndex(Configuration configuration) {
        return Transformations.transform(configuration, "Settings.transformGroupByColumnIndex", configuration.settings().getTransformGroupByColumnIndex(), c -> EMULATE_GROUP_BY_COLUMN_INDEX.contains((Object)c.dialect()));
    }

    static final boolean transform(Configuration configuration, String label, Transformation transformation, Predicate<? super Configuration> whenNeeded) {
        return (switch (StringUtils.defaultIfNull(transformation, Transformation.WHEN_NEEDED)) {
            case Transformation.NEVER -> false;
            case Transformation.ALWAYS -> true;
            case Transformation.WHEN_NEEDED -> whenNeeded.test(configuration);
            default -> throw new IllegalStateException("Transformation configuration not supported: " + String.valueOf((Object)transformation));
        }) && configuration.requireCommercial(() -> "SQL transformation " + label + " required. SQL transformations are a commercial only feature. Please consider upgrading to the jOOQ Professional Edition or jOOQ Enterprise Edition.");
    }
}

