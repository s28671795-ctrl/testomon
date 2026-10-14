/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import liquibase.Scope;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.DatabaseFunction;
import liquibase.util.StringUtil;

@DataTypeInfo(name="datetime", minParameters=0, maxParameters=1, aliases={"java.sql.Types.DATETIME", "java.util.Date", "smalldatetime", "datetime2"}, priority=1)
public class DateTimeType
extends LiquibaseDataType {
    protected static final String SQL_DATETYPE_TIMESTAMP = "TIMESTAMP";

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtil.trimToEmpty(this.getRawDefinition());
        if (database instanceof DerbyDatabase || database instanceof FirebirdDatabase || database instanceof H2Database || database instanceof HsqlDatabase) {
            return new DatabaseDataType(SQL_DATETYPE_TIMESTAMP);
        }
        if (database instanceof AbstractDb2Database) {
            return new DatabaseDataType(SQL_DATETYPE_TIMESTAMP, this.getParameters());
        }
        if (database instanceof OracleDatabase) {
            if (originalDefinition.toUpperCase(Locale.US).contains("TIME ZONE")) {
                return new DatabaseDataType(originalDefinition.replaceFirst("\\(\\d+\\)$", ""));
            }
            return new DatabaseDataType(SQL_DATETYPE_TIMESTAMP, this.getParameters());
        }
        if (database instanceof MSSQLDatabase) {
            Object[] parameters = this.getParameters();
            if (originalDefinition.matches("(?i)^\\[?smalldatetime.*")) {
                return new DatabaseDataType(database.escapeDataTypeName("smalldatetime"));
            }
            if ("datetime2".equals(originalDefinition.toLowerCase(Locale.US)) || "[datetime2]".equals(originalDefinition.toLowerCase(Locale.US)) || originalDefinition.toLowerCase(Locale.US).matches("(?i)\\[?datetime2\\]?\\s*\\(.+")) {
                if (parameters.length >= 1 && Integer.parseInt(parameters[0].toString()) == database.getDefaultScaleForNativeDataType("datetime2")) {
                    parameters = new Object[]{};
                }
                return new DatabaseDataType(database.escapeDataTypeName("datetime2"), parameters);
            }
            return new DatabaseDataType(database.escapeDataTypeName("datetime"));
        }
        if (database instanceof InformixDatabase) {
            if ((this.getAdditionalInformation() == null || this.getAdditionalInformation().isEmpty()) && this.getParameters() != null && this.getParameters().length > 0) {
                String parameter = String.valueOf(this.getParameters()[0]);
                if ("4365".equals(parameter)) {
                    return new DatabaseDataType("DATETIME YEAR TO FRACTION(3)");
                }
                if ("3594".equals(parameter)) {
                    return new DatabaseDataType("DATETIME YEAR TO SECOND");
                }
                if ("3080".equals(parameter)) {
                    return new DatabaseDataType("DATETIME YEAR TO MINUTE");
                }
                if ("2052".equals(parameter)) {
                    return new DatabaseDataType("DATETIME YEAR TO DAY");
                }
            }
            if (this.getAdditionalInformation() != null && !this.getAdditionalInformation().isEmpty()) {
                return new DatabaseDataType(originalDefinition);
            }
            return new DatabaseDataType("DATETIME YEAR TO FRACTION", 5);
        }
        if (database instanceof PostgresDatabase) {
            String rawDefinition = originalDefinition.toLowerCase(Locale.US);
            Object[] params = this.getParameters();
            if (rawDefinition.contains("tz") || rawDefinition.contains("with time zone")) {
                if (params.length == 0) {
                    return new DatabaseDataType("TIMESTAMP WITH TIME ZONE");
                }
                Object param2 = params[0];
                if (params.length == 2) {
                    param2 = params[1];
                }
                return new DatabaseDataType("TIMESTAMP(" + param2 + ") WITH TIME ZONE");
            }
            if (params.length == 0) {
                return new DatabaseDataType("TIMESTAMP WITHOUT TIME ZONE");
            }
            Object param3 = params[0];
            if (params.length == 2) {
                param3 = params[1];
            }
            return new DatabaseDataType("TIMESTAMP(" + param3 + ") WITHOUT TIME ZONE");
        }
        if (database instanceof SQLiteDatabase) {
            return new DatabaseDataType("TEXT");
        }
        int maxFractionalDigits = database.getMaxFractionalDigitsForTimestamp();
        if (database instanceof MySQLDatabase) {
            if (this.getParameters().length == 0 || maxFractionalDigits == 0) {
                return new DatabaseDataType(this.getName());
            }
            Object[] params = this.getParameters();
            Integer precision = Integer.valueOf(params[0].toString());
            if (precision > 6) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("MySQL does not support a timestamp precision of '" + precision + "' - resetting to the maximum of '6'");
                params = new Object[]{6};
            }
            return new DatabaseDataType(this.getName(), params);
        }
        return new DatabaseDataType(this.getName());
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null || "null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        if (value instanceof DatabaseFunction) {
            return database.generateDatabaseFunctionValue((DatabaseFunction)value);
        }
        if (database.isFunction(value.toString())) {
            return value.toString();
        }
        if (value instanceof String) {
            return "'" + ((String)value).replaceAll("'", "''") + "'";
        }
        return database.getDateTimeLiteral((Timestamp)value);
    }

    @Override
    public Object sqlToObject(String value, Database database) {
        if (this.zeroTime(value)) {
            return value;
        }
        if (database instanceof AbstractDb2Database) {
            return value.replaceFirst("^\"SYSIBM\".\"TIMESTAMP\"\\('", "").replaceFirst("'\\)", "");
        }
        if (database instanceof DerbyDatabase) {
            return value.replaceFirst("^TIMESTAMP\\('", "").replaceFirst("'\\)", "");
        }
        try {
            DateFormat dateTimeFormat = this.getDateTimeFormat(database);
            if (database instanceof OracleDatabase && value.matches("to_date\\('\\d+\\-\\d+\\-\\d+ \\d+:\\d+:\\d+', 'YYYY\\-MM\\-DD HH24:MI:SS'\\)")) {
                dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
                value = value.replaceFirst(".*?'", "").replaceFirst("',.*", "");
            }
            if (database instanceof HsqlDatabase && value.matches("TIMESTAMP'\\d+\\-\\d+\\-\\d+ \\d+:\\d+:\\d+(?:\\.\\d+)?'")) {
                dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:s.S");
                value = value.replaceFirst(".*?'", "").replaceFirst("',.*", "");
            }
            return new Timestamp(dateTimeFormat.parse(value).getTime());
        }
        catch (ParseException e) {
            String[] genericFormats = new String[]{"yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss"};
            String shortenedValue = value.replaceFirst("(\\.\\d{3})\\d+", "$1");
            for (String format : genericFormats) {
                try {
                    return new Timestamp(new SimpleDateFormat(format).parse(shortenedValue).getTime());
                }
                catch (ParseException parseException) {
                }
            }
            if (value.contains("/") || value.contains("-")) {
                return value;
            }
            return new DatabaseFunction(value);
        }
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.DATE;
    }

    private boolean zeroTime(String stringVal) {
        return "".equals(stringVal.replace("-", "").replace(":", "").replace(" ", "").replace("0", ""));
    }

    protected DateFormat getDateTimeFormat(Database database) {
        if (database instanceof MySQLDatabase) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        if (database instanceof MSSQLDatabase) {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        }
        if (database instanceof AbstractDb2Database) {
            return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }
}

