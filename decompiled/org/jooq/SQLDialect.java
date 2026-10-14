/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.SQLDialectCategory;

public enum SQLDialect {
    DEFAULT("", false, false),
    CUBRID("CUBRID", false, true, SQLDialectCategory.MYSQL),
    DERBY("Derby", false, true),
    FIREBIRD("Firebird", false, true, new RequiredVersion(4, null, null)),
    FIREBIRD_2_5("Firebird", true, true, new RequiredVersion(2, null, null), FIREBIRD, null),
    FIREBIRD_3_0("Firebird", true, true, new RequiredVersion(3, null, null), null, FIREBIRD, FIREBIRD_2_5),
    FIREBIRD_4_0("Firebird", true, true, new RequiredVersion(4, null, null), null, FIREBIRD, FIREBIRD_3_0),
    H2("H2", false, true, new RequiredVersion(2, 1, 214)),
    H2_1_4_197("H2", true, true, new RequiredVersion(1, 4, 197), H2),
    H2_1_4_200("H2", true, true, new RequiredVersion(1, 4, 200), H2, H2_1_4_197),
    H2_2_0_202("H2", true, true, new RequiredVersion(2, 0, 202), H2, H2_1_4_200),
    H2_2_1_214("H2", true, true, new RequiredVersion(2, 1, 214), H2, H2_2_0_202),
    HSQLDB("HSQLDB", false, true),
    IGNITE("Ignite", false, true),
    MARIADB("MariaDB", false, true, new RequiredVersion(10, 7, null), SQLDialectCategory.MYSQL),
    MARIADB_10_0("MariaDB", true, true, new RequiredVersion(10, 0, null), MARIADB, null),
    MARIADB_10_1("MariaDB", true, true, new RequiredVersion(10, 1, null), MARIADB, MARIADB_10_0),
    MARIADB_10_2("MariaDB", true, true, new RequiredVersion(10, 2, null), MARIADB, MARIADB_10_1),
    MARIADB_10_3("MariaDB", true, true, new RequiredVersion(10, 3, null), MARIADB, MARIADB_10_2),
    MARIADB_10_4("MariaDB", true, true, new RequiredVersion(10, 4, null), MARIADB, MARIADB_10_3),
    MARIADB_10_5("MariaDB", true, true, new RequiredVersion(10, 5, null), MARIADB, MARIADB_10_4),
    MARIADB_10_6("MariaDB", true, true, new RequiredVersion(10, 6, null), MARIADB, MARIADB_10_5),
    MARIADB_10_7("MariaDB", true, true, new RequiredVersion(10, 7, null), MARIADB, MARIADB_10_6),
    MYSQL("MySQL", false, true, new RequiredVersion(8, 0, 31), SQLDialectCategory.MYSQL),
    MYSQL_5_6("MySQL", true, true, new RequiredVersion(5, 6, null), MYSQL, null),
    MYSQL_5_7("MySQL", true, true, new RequiredVersion(5, 7, null), MYSQL, MYSQL_5_6),
    MYSQL_8_0("MySQL", true, true, new RequiredVersion(8, 0, null), MYSQL, MYSQL_5_7),
    MYSQL_8_0_19("MySQL", true, true, new RequiredVersion(8, 0, 19), MYSQL, MYSQL_8_0),
    MYSQL_8_0_20("MySQL", true, true, new RequiredVersion(8, 0, 20), MYSQL, MYSQL_8_0_19),
    MYSQL_8_0_31("MySQL", true, true, new RequiredVersion(8, 0, 31), MYSQL, MYSQL_8_0_20),
    POSTGRES("Postgres", false, true, new RequiredVersion(15, null, null), SQLDialectCategory.POSTGRES),
    POSTGRES_9_3("Postgres", true, true, new RequiredVersion(9, 3, null), POSTGRES, null),
    POSTGRES_9_4("Postgres", true, true, new RequiredVersion(9, 4, null), POSTGRES, POSTGRES_9_3),
    POSTGRES_9_5("Postgres", true, true, new RequiredVersion(9, 5, null), POSTGRES, POSTGRES_9_4),
    POSTGRES_10("Postgres", true, true, new RequiredVersion(10, null, null), POSTGRES, POSTGRES_9_5),
    POSTGRES_11("Postgres", true, true, new RequiredVersion(11, null, null), POSTGRES, POSTGRES_10),
    POSTGRES_12("Postgres", true, true, new RequiredVersion(12, null, null), POSTGRES, POSTGRES_11),
    POSTGRES_13("Postgres", true, true, new RequiredVersion(13, null, null), POSTGRES, POSTGRES_12),
    POSTGRES_14("Postgres", true, true, new RequiredVersion(14, null, null), POSTGRES, POSTGRES_13),
    POSTGRES_15("Postgres", true, true, new RequiredVersion(15, null, null), POSTGRES, POSTGRES_14),
    SQLITE("SQLite", false, true, new RequiredVersion(3, 39, null)),
    SQLITE_3_25("SQLite", true, true, new RequiredVersion(3, 25, null), SQLITE, null),
    SQLITE_3_28("SQLite", true, true, new RequiredVersion(3, 28, null), SQLITE, SQLITE_3_25),
    SQLITE_3_30("SQLite", true, true, new RequiredVersion(3, 30, null), SQLITE, SQLITE_3_28),
    SQLITE_3_38("SQLite", true, true, new RequiredVersion(3, 38, null), SQLITE, SQLITE_3_30),
    SQLITE_3_39("SQLite", true, true, new RequiredVersion(3, 39, null), SQLITE, SQLITE_3_38),
    YUGABYTEDB("YugabyteDB", false, true, new RequiredVersion(2, 9, null), SQLDialectCategory.POSTGRES),
    YUGABYTEDB_2_9("YugabyteDB", true, true, new RequiredVersion(2, 9, null), YUGABYTEDB, null),
    ACCESS("Access", true, true),
    ACCESS2013("Access", true, true, (RequiredVersion)null, ACCESS),
    ASE("ASE", true, true, new RequiredVersion(16, 0, null), SQLDialectCategory.TSQL),
    ASE_12_5("ASE", true, true, new RequiredVersion(12, 5, null), ASE, null),
    ASE_15_5("ASE", true, true, new RequiredVersion(15, 5, null), ASE, ASE_12_5),
    ASE_15_7("ASE", true, true, new RequiredVersion(15, 7, null), ASE, ASE_15_5),
    ASE_16_0("ASE", true, true, new RequiredVersion(16, 0, null), ASE, ASE_15_7),
    AURORA_MYSQL("Aurora MySQL", true, true, SQLDialectCategory.MYSQL),
    AURORA_POSTGRES("Aurora Postgres", true, true, SQLDialectCategory.POSTGRES),
    BIGQUERY("BigQuery", true, true),
    COCKROACHDB("CockroachDB", true, true, new RequiredVersion(22, null, null), SQLDialectCategory.POSTGRES),
    COCKROACHDB_20("CockroachDB", true, true, new RequiredVersion(20, null, null), COCKROACHDB, null),
    COCKROACHDB_21("CockroachDB", true, true, new RequiredVersion(21, null, null), COCKROACHDB, COCKROACHDB_20),
    COCKROACHDB_22("CockroachDB", true, true, new RequiredVersion(22, null, null), COCKROACHDB, COCKROACHDB_21),
    DB2("DB2", true, true, new RequiredVersion(11, null, null)),
    DB2_9("DB2", true, true, new RequiredVersion(9, null, null), DB2),
    DB2_10("DB2", true, true, new RequiredVersion(10, null, null), DB2, DB2_9),
    DB2_11("DB2", true, true, new RequiredVersion(11, null, null), DB2, DB2_10),
    EXASOL("Exasol", true, true),
    HANA("Hana", true, true),
    INFORMIX("Informix", true, true),
    INGRES("Ingres", true, true),
    MEMSQL("MemSQL", true, true, new RequiredVersion(8, null, null), SQLDialectCategory.MYSQL),
    MEMSQL_6("MemSQL", true, true, new RequiredVersion(6, null, null), MEMSQL, null),
    MEMSQL_7("MemSQL", true, true, new RequiredVersion(6, null, null), MEMSQL, MEMSQL_6),
    MEMSQL_8("MemSQL", true, true, new RequiredVersion(6, null, null), MEMSQL, MEMSQL_7),
    ORACLE("Oracle", true, true, new RequiredVersion(21, null, null)),
    ORACLE10G("Oracle", true, true, new RequiredVersion(10, null, null), ORACLE),
    ORACLE11G("Oracle", true, true, new RequiredVersion(11, null, null), ORACLE, ORACLE10G),
    ORACLE12C("Oracle", true, true, new RequiredVersion(12, null, null), ORACLE, ORACLE11G),
    ORACLE18C("Oracle", true, true, new RequiredVersion(18, null, null), ORACLE, ORACLE12C),
    ORACLE20C("Oracle", true, true, new RequiredVersion(20, null, null), ORACLE, ORACLE18C),
    ORACLE21C("Oracle", true, true, new RequiredVersion(21, null, null), ORACLE, ORACLE20C),
    POSTGRESPLUS("PostgresPlus", true, false, SQLDialectCategory.POSTGRES),
    REDSHIFT("Redshift", true, true, SQLDialectCategory.POSTGRES),
    SNOWFLAKE("Snowflake", true, true),
    SQLSERVER("SQLServer", true, true, new RequiredVersion(16, null, null), SQLDialectCategory.TSQL),
    SQLSERVER2008("SQLServer", true, true, new RequiredVersion(10, null, null), SQLSERVER),
    SQLSERVER2012("SQLServer", true, true, new RequiredVersion(11, null, null), SQLSERVER, SQLSERVER2008),
    SQLSERVER2014("SQLServer", true, true, new RequiredVersion(12, null, null), SQLSERVER, SQLSERVER2012),
    SQLSERVER2016("SQLServer", true, true, new RequiredVersion(13, null, null), SQLSERVER, SQLSERVER2014),
    SQLSERVER2017("SQLServer", true, true, new RequiredVersion(14, null, null), SQLSERVER, SQLSERVER2016),
    SQLSERVER2022("SQLServer", true, true, new RequiredVersion(16, null, null), SQLSERVER, SQLSERVER2017),
    SQLDATAWAREHOUSE("SQLDataWarehouse", true, true, SQLDialectCategory.TSQL),
    SYBASE("Sybase", true, true, SQLDialectCategory.TSQL),
    TERADATA("Teradata", true, true),
    VERTICA("Vertica", true, true, SQLDialectCategory.POSTGRES),
    JAVA("", true, false);

    private static final SQLDialect[] FAMILIES;
    private final String name;
    private final boolean commercial;
    private final boolean supported;
    private final RequiredVersion requiredVersion;
    private final SQLDialect family;
    private final SQLDialectCategory category;
    private SQLDialect predecessor;
    private transient EnumSet<SQLDialect> predecessors;
    private final ThirdParty thirdParty;
    private static final Pattern P_PATCH_VERSION_MYSQL;

    @NotNull
    public static final @NotNull SQLDialect @NotNull [] families() {
        return (SQLDialect[])FAMILIES.clone();
    }

    @NotNull
    public static final Set<SQLDialect> predecessors(SQLDialect ... dialects) {
        EnumSet<SQLDialect> result = EnumSet.noneOf(SQLDialect.class);
        for (SQLDialect dialect : dialects) {
            result.addAll(dialect.predecessors());
        }
        return result;
    }

    @NotNull
    public static final Set<SQLDialect> supportedUntil(SQLDialect dialect) {
        return SQLDialect.predecessors(dialect);
    }

    @NotNull
    public static final Set<SQLDialect> supportedUntil(SQLDialect ... dialects) {
        return SQLDialect.predecessors(dialects);
    }

    @NotNull
    public static final Set<SQLDialect> supportedBy(SQLDialect dialect) {
        EnumSet<SQLDialect> result = EnumSet.noneOf(SQLDialect.class);
        SQLDialect.addSupportedBy(dialect, result);
        return result;
    }

    @NotNull
    public static final Set<SQLDialect> supportedBy(SQLDialect ... dialects) {
        EnumSet<SQLDialect> result = EnumSet.noneOf(SQLDialect.class);
        for (SQLDialect dialect : dialects) {
            SQLDialect.addSupportedBy(dialect, result);
        }
        return result;
    }

    private static final void addSupportedBy(SQLDialect dialect, EnumSet<SQLDialect> supported) {
        supported.add(dialect);
        if (dialect.isFamily()) {
            supported.addAll(dialect.predecessors());
        } else {
            for (SQLDialect candidate = dialect.family(); candidate != dialect; candidate = candidate.predecessor()) {
                supported.add(candidate);
            }
        }
    }

    private SQLDialect(String name, boolean commercial, boolean supported) {
        this(name, commercial, supported, (RequiredVersion)null);
    }

    private SQLDialect(String name, boolean commercial, boolean supported, SQLDialectCategory category) {
        this(name, commercial, supported, null, category);
    }

    private SQLDialect(String name, boolean commercial, boolean supported, RequiredVersion requiredVersion) {
        this(name, commercial, supported, requiredVersion, SQLDialectCategory.OTHER, null, null);
    }

    private SQLDialect(String name, boolean commercial, boolean supported, RequiredVersion requiredVersion, SQLDialectCategory category) {
        this(name, commercial, supported, requiredVersion, category, null, null);
    }

    private SQLDialect(String name, boolean commercial, boolean supported, RequiredVersion requiredVersion, SQLDialect family) {
        this(name, commercial, supported, requiredVersion, family, null);
    }

    private SQLDialect(String name, boolean commercial, boolean supported, RequiredVersion requiredVersion, SQLDialect family, SQLDialect predecessor) {
        this(name, commercial, supported, requiredVersion, family.category(), family, predecessor);
    }

    private SQLDialect(String name, boolean commercial, boolean supported, RequiredVersion requiredVersion, SQLDialectCategory category, SQLDialect family, SQLDialect predecessor) {
        this.name = name;
        this.commercial = commercial;
        this.supported = supported;
        this.requiredVersion = requiredVersion;
        this.family = family == null ? this : family;
        this.category = category == null ? this.family.category() : category;
        SQLDialect sQLDialect = this.predecessor = predecessor == null ? this : predecessor;
        if (family != null) {
            family.predecessor = this;
        }
        this.thirdParty = new ThirdParty();
    }

    public final boolean commercial() {
        return this.commercial;
    }

    public final boolean supported() {
        return this.supported;
    }

    @NotNull
    public final SQLDialect family() {
        return this.family;
    }

    @NotNull
    public final SQLDialectCategory category() {
        return this.category;
    }

    public final boolean isFamily() {
        return this == this.family;
    }

    public final boolean isVersioned() {
        return this.requiredVersion != null;
    }

    @NotNull
    public final SQLDialect predecessor() {
        return this.predecessor;
    }

    @NotNull
    public final Set<SQLDialect> predecessors() {
        if (this.predecessors == null) {
            SQLDialect curr = this;
            EnumSet<SQLDialect> result = EnumSet.of(curr);
            while (true) {
                SQLDialect pred = curr.predecessor();
                result.add(pred);
                if (curr == pred) break;
                curr = pred;
            }
            this.predecessors = result;
        }
        return Collections.unmodifiableSet(this.predecessors);
    }

    public final boolean precedes(SQLDialect other) {
        if (this.family != other.family) {
            return false;
        }
        return other.predecessors().contains((Object)this);
    }

    public final boolean precedesStrictly(SQLDialect other) {
        return this.precedes(other) && this != other;
    }

    public final boolean supports(SQLDialect other) {
        if (this.family != other.family) {
            return false;
        }
        if (this.isFamily() || other.isFamily()) {
            return true;
        }
        return other.precedes(this);
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final boolean supports(Collection<SQLDialect> other) {
        if (other.contains((Object)this.family)) {
            return true;
        }
        SQLDialect candidate = this.family.predecessor();
        boolean successor = this == this.family;
        do {
            boolean bl = successor = successor || this == candidate;
            if (!other.contains((Object)candidate)) continue;
            return successor;
        } while (candidate != (candidate = candidate.predecessor()));
        return false;
    }

    public final boolean supportsDatabaseVersion(int majorVersion, int minorVersion, String productVersion) {
        return this.requiredVersion == null || this.requiredVersion.major == null || this.requiredVersion.major < majorVersion || this.requiredVersion.major == majorVersion && (this.requiredVersion.minor == null || this.requiredVersion.minor < minorVersion || this.requiredVersion.minor == minorVersion && (this.requiredVersion.patch == null || this.requiredVersion.patch <= this.patchVersion(productVersion)));
    }

    private final int patchVersion(String productVersion) {
        if (productVersion == null) {
            return Integer.MAX_VALUE;
        }
        switch (this.family()) {
            case H2: {
                return Integer.parseInt(productVersion.split(" ")[0].split("\\.")[2]);
            }
            case MYSQL: {
                return Integer.parseInt(P_PATCH_VERSION_MYSQL.matcher(productVersion).replaceFirst("$1"));
            }
        }
        return Integer.MAX_VALUE;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getNameLC() {
        return this.name.toLowerCase();
    }

    @NotNull
    public final String getNameUC() {
        return this.name.toUpperCase();
    }

    @NotNull
    public final ThirdParty thirdParty() {
        return this.thirdParty;
    }

    static {
        EnumSet<SQLDialect> set = EnumSet.noneOf(SQLDialect.class);
        for (SQLDialect dialect : SQLDialect.values()) {
            set.add(dialect.family());
        }
        FAMILIES = set.toArray(new SQLDialect[0]);
        P_PATCH_VERSION_MYSQL = Pattern.compile("^\\d+\\.\\d+\\.(\\d+).*$");
    }

    record RequiredVersion(Integer major, Integer minor, Integer patch) {
    }

    public final class ThirdParty {
        @NotNull
        public final String driver() {
            try {
                Class<?> utils = Class.forName("org.jooq.tools.jdbc.JDBCUtils");
                return (String)utils.getMethod("driver", SQLDialect.class).invoke(utils, new Object[]{SQLDialect.this});
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Nullable
        public final String springDbName() {
            switch (SQLDialect.this.family) {
                case ASE: {
                    return "Sybase";
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case REDSHIFT: {
                    return "PostgreSQL";
                }
                case DB2: {
                    return "DB2";
                }
                case HANA: {
                    return "Hana";
                }
                case INFORMIX: {
                    return "Informix";
                }
                case ORACLE: {
                    return "Oracle";
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: {
                    return "MS-SQL";
                }
                case DERBY: {
                    return "Derby";
                }
                case H2: {
                    return "H2";
                }
                case HSQLDB: {
                    return "HSQL";
                }
                case MYSQL: 
                case MARIADB: 
                case AURORA_MYSQL: 
                case MEMSQL: {
                    return "MySQL";
                }
                case POSTGRES: 
                case YUGABYTEDB: {
                    return "PostgreSQL";
                }
            }
            return null;
        }

        @Nullable
        public final String hibernateDialect() {
            switch (SQLDialect.this) {
                case ACCESS: 
                case ACCESS2013: {
                    return null;
                }
                case ASE: {
                    return "org.hibernate.dialect.SybaseASE15Dialect";
                }
                case AURORA_MYSQL: {
                    return "org.hibernate.dialect.MySQL5Dialect";
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: {
                    return "org.hibernate.dialect.PostgreSQL10Dialect";
                }
                case DB2: 
                case DB2_9: 
                case DB2_10: {
                    return "org.hibernate.dialect.DB2Dialect";
                }
                case EXASOL: {
                    return "com.exasol.dialect.ExasolDialect";
                }
                case HANA: {
                    return "org.hibernate.dialect.HANARowStoreDialect";
                }
                case INFORMIX: {
                    return "org.hibernate.dialect.InformixDialect";
                }
                case INGRES: {
                    return "org.hibernate.dialect.Ingres10Dialect";
                }
                case ORACLE10G: 
                case ORACLE11G: {
                    return "org.hibernate.dialect.Oracle10gDialect";
                }
                case ORACLE: 
                case ORACLE12C: 
                case ORACLE18C: {
                    return "org.hibernate.dialect.Oracle12cDialect";
                }
                case REDSHIFT: {
                    return "org.hibernate.dialect.PostgreSQL81Dialect";
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER2008: {
                    return "org.hibernate.dialect.SQLServer2008Dialect";
                }
                case SQLSERVER: 
                case SQLSERVER2012: 
                case SQLSERVER2014: 
                case SQLSERVER2016: 
                case SQLSERVER2017: {
                    return "org.hibernate.dialect.SQLServer2012Dialect";
                }
                case SYBASE: {
                    return "org.hibernate.dialect.SybaseAnywhereDialect";
                }
                case TERADATA: {
                    return "org.hibernate.dialect.TeradataDialect";
                }
                case VERTICA: {
                    return null;
                }
                case CUBRID: {
                    return "org.hibernate.dialect.CUBRIDDialect";
                }
                case DERBY: {
                    return "org.hibernate.dialect.DerbyTenSevenDialect";
                }
                case FIREBIRD: {
                    return "org.hibernate.dialect.FirebirdDialect";
                }
                case H2: {
                    return "org.hibernate.dialect.H2Dialect";
                }
                case HSQLDB: {
                    return "org.hibernate.dialect.HSQLDialect";
                }
                case MYSQL: 
                case MARIADB: 
                case MEMSQL: 
                case MARIADB_10_0: 
                case MARIADB_10_1: 
                case MARIADB_10_2: 
                case MARIADB_10_3: 
                case MARIADB_10_4: 
                case MARIADB_10_5: 
                case MEMSQL_6: 
                case MEMSQL_7: 
                case MEMSQL_8: 
                case MYSQL_5_6: 
                case MYSQL_5_7: 
                case MYSQL_8_0: 
                case MYSQL_8_0_19: 
                case MYSQL_8_0_20: 
                case MYSQL_8_0_31: {
                    return "org.hibernate.dialect.MySQL5Dialect";
                }
                case POSTGRES_9_3: {
                    return "org.hibernate.dialect.PostgreSQL92Dialect";
                }
                case POSTGRES: 
                case YUGABYTEDB: 
                case POSTGRES_9_4: 
                case POSTGRES_9_5: 
                case POSTGRES_10: {
                    return "org.hibernate.dialect.PostgreSQL94Dialect";
                }
                case SQLITE: {
                    return null;
                }
            }
            return null;
        }
    }
}

