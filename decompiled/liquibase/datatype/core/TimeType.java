/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.DatabaseFunction;
import liquibase.util.StringUtil;

@DataTypeInfo(name="time", aliases={"java.sql.Types.TIME", "java.sql.Time", "timetz"}, minParameters=0, maxParameters=0, priority=1)
public class TimeType
extends LiquibaseDataType {
    protected static final int MSSQL_TYPE_TIME_DEFAULT_PRECISION = 7;
    private static final String PARENTHESIS_REGEX = "(\\(\\d+\\))";
    public static final Pattern PARENTHESIS_PATTERN = Pattern.compile("(\\(\\d+\\))");

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtil.trimToEmpty(this.getRawDefinition());
        if (database instanceof InformixDatabase) {
            return new DatabaseDataType("DATETIME HOUR TO FRACTION", 5);
        }
        if (database instanceof MSSQLDatabase) {
            Object[] parameters = this.getParameters();
            if (parameters.length >= 1 && Integer.parseInt(parameters[0].toString()) == database.getDefaultScaleForNativeDataType("time")) {
                parameters = new Object[]{};
            }
            return new DatabaseDataType(database.escapeDataTypeName("time"), parameters);
        }
        if (database instanceof MySQLDatabase) {
            boolean supportsParameters = true;
            try {
                supportsParameters = database.getDatabaseMajorVersion() >= 5 && database.getDatabaseMinorVersion() >= 6 && ((MySQLDatabase)database).getDatabasePatchVersion() >= 4;
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (supportsParameters && this.getParameters().length > 0 && Integer.parseInt(this.getParameters()[0].toString()) <= 6) {
                return new DatabaseDataType(this.getName(), this.getParameters());
            }
            return new DatabaseDataType(this.getName());
        }
        if (database instanceof OracleDatabase) {
            return new DatabaseDataType("DATE");
        }
        if (database instanceof PostgresDatabase) {
            DatabaseDataType datatype = new DatabaseDataType(this.getName(), this.getValidatedParameters());
            String rawDefinition = originalDefinition.toLowerCase(Locale.US);
            Object[] parameters = this.getParameters();
            if (parameters == null || parameters.length == 0) {
                Matcher precisionMatcher = PARENTHESIS_PATTERN.matcher(rawDefinition);
                if (precisionMatcher.find()) {
                    datatype = new DatabaseDataType(datatype.getType() + precisionMatcher.group(1));
                }
            } else if (this.getValidatedParameters().length == 0 && this.getParameters().length >= 2) {
                datatype = new DatabaseDataType(datatype.getType(), this.getParameters()[1]);
            }
            if (rawDefinition.contains("tz") || rawDefinition.contains("with time zone")) {
                datatype.addAdditionalInformation("WITH TIME ZONE");
            } else {
                datatype.addAdditionalInformation("WITHOUT TIME ZONE");
            }
            return datatype;
        }
        return new DatabaseDataType(this.getName());
    }

    Object[] getValidatedParameters() {
        Object[] parameters = this.getParameters();
        return parameters.length > 0 && Integer.parseInt(this.getParameters()[0].toString()) <= 6 ? parameters : new Object[]{};
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null || "null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        if (value instanceof DatabaseFunction) {
            return database.generateDatabaseFunctionValue((DatabaseFunction)value);
        }
        if (value instanceof Time) {
            return database.getTimeLiteral((Time)value);
        }
        return "'" + ((String)value).replaceAll("'", "''") + "'";
    }

    @Override
    public Object sqlToObject(String value, Database database) {
        if (this.zeroTime(value)) {
            return value;
        }
        if (database instanceof AbstractDb2Database) {
            return value.replaceFirst("^\"SYSIBM\".\"TIME\"\\('", "").replaceFirst("'\\)", "");
        }
        if (database instanceof DerbyDatabase) {
            return value.replaceFirst("^TIME\\('", "").replaceFirst("'\\)", "");
        }
        try {
            DateFormat timeFormat = this.getTimeFormat(database);
            if (database instanceof OracleDatabase && value.matches("to_date\\('\\d+:\\d+:\\d+', 'HH24:MI:SS'\\)")) {
                timeFormat = new SimpleDateFormat("HH:mm:s");
                value = value.replaceFirst(".*?'", "").replaceFirst("',.*", "");
            }
            return new Time(timeFormat.parse(value).getTime());
        }
        catch (ParseException e) {
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

    protected DateFormat getTimeFormat(Database database) {
        if (database instanceof AbstractDb2Database) {
            return new SimpleDateFormat("HH.mm.ss");
        }
        return new SimpleDateFormat("HH:mm:ss");
    }
}

