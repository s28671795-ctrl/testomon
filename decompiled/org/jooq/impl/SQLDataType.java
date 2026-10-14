/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.util.UUID;
import org.jooq.DataType;
import org.jooq.Geography;
import org.jooq.Geometry;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.RowId;
import org.jooq.SQLDialect;
import org.jooq.XML;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.types.DayToSecond;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;
import org.jooq.types.YearToMonth;
import org.jooq.types.YearToSecond;
import org.jooq.util.access.AccessDataType;
import org.jooq.util.ase.ASEDataType;
import org.jooq.util.auroramysql.AuroraMySQLDataType;
import org.jooq.util.aurorapostgres.AuroraPostgresDataType;
import org.jooq.util.bigquery.BigQueryDataType;
import org.jooq.util.cockroachdb.CockroachDBDataType;
import org.jooq.util.cubrid.CUBRIDDataType;
import org.jooq.util.db2.DB2DataType;
import org.jooq.util.derby.DerbyDataType;
import org.jooq.util.exasol.ExasolDataType;
import org.jooq.util.firebird.FirebirdDataType;
import org.jooq.util.h2.H2DataType;
import org.jooq.util.hana.HanaDataType;
import org.jooq.util.hsqldb.HSQLDBDataType;
import org.jooq.util.ignite.IgniteDataType;
import org.jooq.util.informix.InformixDataType;
import org.jooq.util.ingres.IngresDataType;
import org.jooq.util.mariadb.MariaDBDataType;
import org.jooq.util.memsql.MemSQLDataType;
import org.jooq.util.mysql.MySQLDataType;
import org.jooq.util.oracle.OracleDataType;
import org.jooq.util.postgres.PostgresDataType;
import org.jooq.util.redshift.RedshiftDataType;
import org.jooq.util.snowflake.SnowflakeDataType;
import org.jooq.util.sqldatawarehouse.SQLDataWarehouseDataType;
import org.jooq.util.sqlite.SQLiteDataType;
import org.jooq.util.sqlserver.SQLServerDataType;
import org.jooq.util.sybase.SybaseDataType;
import org.jooq.util.teradata.TeradataDataType;
import org.jooq.util.vertica.VerticaDataType;
import org.jooq.util.yugabytedb.YugabyteDBDataType;

public final class SQLDataType {
    public static final DataType<String> VARCHAR = new BuiltInDataType<String>(String.class, "varchar(l)");
    public static final DataType<String> CHAR = new BuiltInDataType<String>(String.class, "char(l)");
    public static final DataType<String> LONGVARCHAR = new BuiltInDataType<String>(String.class, "longvarchar(l)");
    public static final DataType<String> CLOB = new BuiltInDataType<String>(String.class, "clob");
    public static final DataType<String> NVARCHAR = new BuiltInDataType<String>(String.class, "nvarchar(l)");
    public static final DataType<String> NCHAR = new BuiltInDataType<String>(String.class, "nchar(l)");
    public static final DataType<String> LONGNVARCHAR = new BuiltInDataType<String>(String.class, "longnvarchar(l)");
    public static final DataType<String> NCLOB = new BuiltInDataType<String>(String.class, "nclob");
    public static final DataType<Boolean> BOOLEAN = new BuiltInDataType<Boolean>(Boolean.class, "boolean");
    public static final DataType<Boolean> BIT = new BuiltInDataType<Boolean>(Boolean.class, "bit");
    public static final DataType<Byte> TINYINT = new BuiltInDataType<Byte>(Byte.class, "tinyint");
    public static final DataType<Short> SMALLINT = new BuiltInDataType<Short>(Short.class, "smallint");
    public static final DataType<Integer> INTEGER = new BuiltInDataType<Integer>(Integer.class, "integer");
    public static final DataType<Long> BIGINT = new BuiltInDataType<Long>(Long.class, "bigint");
    public static final DataType<BigInteger> DECIMAL_INTEGER = new BuiltInDataType<BigInteger>(BigInteger.class, "decimal_integer");
    public static final DataType<UByte> TINYINTUNSIGNED = new BuiltInDataType<UByte>(UByte.class, "tinyint unsigned");
    public static final DataType<UShort> SMALLINTUNSIGNED = new BuiltInDataType<UShort>(UShort.class, "smallint unsigned");
    public static final DataType<UInteger> INTEGERUNSIGNED = new BuiltInDataType<UInteger>(UInteger.class, "integer unsigned");
    public static final DataType<ULong> BIGINTUNSIGNED = new BuiltInDataType<ULong>(ULong.class, "bigint unsigned");
    public static final DataType<Double> DOUBLE = new BuiltInDataType<Double>(Double.class, "double");
    public static final DataType<Double> FLOAT = new BuiltInDataType<Double>(Double.class, "float");
    public static final DataType<Float> REAL = new BuiltInDataType<Float>(Float.class, "real");
    public static final DataType<BigDecimal> NUMERIC = new BuiltInDataType<BigDecimal>(BigDecimal.class, "numeric(p, s)");
    public static final DataType<BigDecimal> DECIMAL = new BuiltInDataType<BigDecimal>(BigDecimal.class, "decimal(p, s)");
    public static final DataType<Date> DATE = new BuiltInDataType<Date>(Date.class, "date");
    public static final DataType<Timestamp> TIMESTAMP = new BuiltInDataType<Timestamp>(Timestamp.class, "timestamp(p)");
    public static final DataType<Time> TIME = new BuiltInDataType<Time>(Time.class, "time(p)");
    public static final DataType<YearToSecond> INTERVAL = new BuiltInDataType<YearToSecond>(YearToSecond.class, "interval");
    public static final DataType<YearToMonth> INTERVALYEARTOMONTH = new BuiltInDataType<YearToMonth>(YearToMonth.class, "interval year to month");
    public static final DataType<DayToSecond> INTERVALDAYTOSECOND = new BuiltInDataType<DayToSecond>(DayToSecond.class, "interval day to second");
    public static final DataType<LocalDate> LOCALDATE = new BuiltInDataType<LocalDate>(LocalDate.class, "date");
    public static final DataType<LocalTime> LOCALTIME = new BuiltInDataType<LocalTime>(LocalTime.class, "time(p)");
    public static final DataType<LocalDateTime> LOCALDATETIME = new BuiltInDataType<LocalDateTime>(LocalDateTime.class, "timestamp(p)");
    public static final DataType<OffsetTime> OFFSETTIME = new BuiltInDataType<OffsetTime>(OffsetTime.class, "time(p) with time zone");
    public static final DataType<OffsetDateTime> OFFSETDATETIME = new BuiltInDataType<OffsetDateTime>(OffsetDateTime.class, "timestamp(p) with time zone");
    public static final DataType<OffsetTime> TIMEWITHTIMEZONE = OFFSETTIME;
    public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE = OFFSETDATETIME;
    public static final DataType<Instant> INSTANT = new BuiltInDataType<Instant>(Instant.class, "instant");
    public static final DataType<Year> YEAR = new BuiltInDataType<Year>(Year.class, "year");
    public static final DataType<byte[]> BINARY = new BuiltInDataType<byte[]>(byte[].class, "binary(l)");
    public static final DataType<byte[]> VARBINARY = new BuiltInDataType<byte[]>(byte[].class, "varbinary(l)");
    public static final DataType<byte[]> LONGVARBINARY = new BuiltInDataType<byte[]>(byte[].class, "longvarbinary(l)");
    public static final DataType<byte[]> BLOB = new BuiltInDataType<byte[]>(byte[].class, "blob");
    public static final DataType<Object> OTHER = new BuiltInDataType<Object>(Object.class, "other");
    public static final DataType<RowId> ROWID = new BuiltInDataType<RowId>(RowId.class, "rowid");
    public static final DataType<Record> RECORD = new BuiltInDataType<Record>(Record.class, "record");
    public static final DataType<Result<Record>> RESULT = new BuiltInDataType<Result>(Result.class, "result");
    public static final DataType<UUID> UUID = new BuiltInDataType<UUID>(UUID.class, "uuid");
    public static final DataType<JSON> JSON = new BuiltInDataType<JSON>(JSON.class, "json");
    public static final DataType<JSONB> JSONB = new BuiltInDataType<JSONB>(JSONB.class, "jsonb");
    public static final DataType<XML> XML = new BuiltInDataType<XML>(XML.class, "xml");
    public static final DataType<Geography> GEOGRAPHY = new BuiltInDataType<Geography>(Geography.class, "geography");
    public static final DataType<Geometry> GEOMETRY = new BuiltInDataType<Geometry>(Geometry.class, "geometry");

    public static final DataType<String> VARCHAR(int length) {
        return VARCHAR.length(length);
    }

    public static final DataType<String> CHAR(int length) {
        return CHAR.length(length);
    }

    public static final DataType<String> LONGVARCHAR(int length) {
        return LONGVARCHAR.length(length);
    }

    public static final DataType<String> CLOB(int length) {
        return CLOB.length(length);
    }

    public static final DataType<String> NVARCHAR(int length) {
        return NVARCHAR.length(length);
    }

    public static final DataType<String> NCHAR(int length) {
        return NCHAR.length(length);
    }

    public static final DataType<String> LONGNVARCHAR(int length) {
        return LONGNVARCHAR.length(length);
    }

    public static final DataType<String> NCLOB(int length) {
        return NCLOB.length(length);
    }

    public static final DataType<BigInteger> DECIMAL_INTEGER(int precision) {
        return DECIMAL_INTEGER.precision(precision, 0);
    }

    public static final DataType<BigDecimal> NUMERIC(int precision) {
        return NUMERIC.precision(precision, 0);
    }

    public static final DataType<BigDecimal> NUMERIC(int precision, int scale) {
        return NUMERIC.precision(precision, scale);
    }

    public static final DataType<BigDecimal> DECIMAL(int precision) {
        return DECIMAL.precision(precision, 0);
    }

    public static final DataType<BigDecimal> DECIMAL(int precision, int scale) {
        return DECIMAL.precision(precision, scale);
    }

    public static final DataType<Timestamp> TIMESTAMP(int precision) {
        return TIMESTAMP.precision(precision);
    }

    public static final DataType<Time> TIME(int precision) {
        return TIME.precision(precision);
    }

    public static final DataType<LocalTime> LOCALTIME(int precision) {
        return LOCALTIME.precision(precision);
    }

    public static final DataType<LocalDateTime> LOCALDATETIME(int precision) {
        return LOCALDATETIME.precision(precision);
    }

    public static final DataType<OffsetTime> OFFSETTIME(int precision) {
        return OFFSETTIME.precision(precision);
    }

    public static final DataType<OffsetDateTime> OFFSETDATETIME(int precision) {
        return OFFSETDATETIME.precision(precision);
    }

    public static final DataType<OffsetTime> TIMEWITHTIMEZONE(int precision) {
        return TIMEWITHTIMEZONE.precision(precision);
    }

    public static final DataType<OffsetDateTime> TIMESTAMPWITHTIMEZONE(int precision) {
        return TIMESTAMPWITHTIMEZONE.precision(precision);
    }

    public static final DataType<Instant> INSTANT(int precision) {
        return INSTANT.precision(precision);
    }

    public static final DataType<byte[]> BINARY(int length) {
        return BINARY.length(length);
    }

    public static final DataType<byte[]> VARBINARY(int length) {
        return VARBINARY.length(length);
    }

    public static final DataType<byte[]> LONGVARBINARY(int length) {
        return LONGVARBINARY.length(length);
    }

    public static final DataType<byte[]> BLOB(int length) {
        return BLOB.length(length);
    }

    private static final void initJSR310Types(SQLDialect family) {
        DefaultConfiguration configuration = new DefaultConfiguration(family);
        new BuiltInDataType<LocalDate>(family, LOCALDATE, DATE.getTypeName(configuration), DATE.getCastTypeName(configuration));
        new BuiltInDataType<LocalTime>(family, LOCALTIME, TIME.getTypeName(configuration), TIME.getCastTypeName(configuration));
        new BuiltInDataType<LocalDateTime>(family, LOCALDATETIME, TIMESTAMP.getTypeName(configuration), TIMESTAMP.getCastTypeName(configuration));
    }

    private SQLDataType() {
    }

    static {
        try {
            Class.forName(AccessDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.ACCESS);
            Class.forName(ASEDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.ASE);
            Class.forName(AuroraMySQLDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.AURORA_MYSQL);
            Class.forName(AuroraPostgresDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.AURORA_POSTGRES);
            Class.forName(BigQueryDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.BIGQUERY);
            Class.forName(CockroachDBDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.COCKROACHDB);
            Class.forName(DB2DataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.DB2);
            Class.forName(ExasolDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.EXASOL);
            Class.forName(HanaDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.HANA);
            Class.forName(InformixDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.INFORMIX);
            Class.forName(IngresDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.INGRES);
            Class.forName(MemSQLDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.MEMSQL);
            Class.forName(OracleDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.ORACLE);
            Class.forName(RedshiftDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.REDSHIFT);
            Class.forName(SnowflakeDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.SNOWFLAKE);
            Class.forName(SQLDataWarehouseDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.SQLDATAWAREHOUSE);
            Class.forName(SQLServerDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.SQLSERVER);
            Class.forName(SybaseDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.SYBASE);
            Class.forName(TeradataDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.TERADATA);
            Class.forName(VerticaDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.VERTICA);
            Class.forName(CUBRIDDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.CUBRID);
            Class.forName(DerbyDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.DERBY);
            Class.forName(FirebirdDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.FIREBIRD);
            Class.forName(H2DataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.H2);
            Class.forName(HSQLDBDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.HSQLDB);
            Class.forName(IgniteDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.IGNITE);
            Class.forName(MariaDBDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.MARIADB);
            Class.forName(MySQLDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.MYSQL);
            Class.forName(PostgresDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.POSTGRES);
            Class.forName(SQLiteDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.SQLITE);
            Class.forName(YugabyteDBDataType.class.getName());
            SQLDataType.initJSR310Types(SQLDialect.YUGABYTEDB);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

