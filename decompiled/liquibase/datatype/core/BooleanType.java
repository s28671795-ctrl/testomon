/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Locale;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MariaDBDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.statement.DatabaseFunction;
import liquibase.util.StringUtil;

@DataTypeInfo(name="boolean", aliases={"java.sql.Types.BOOLEAN", "java.lang.Boolean", "bit", "bool"}, minParameters=0, maxParameters=0, priority=1)
public class BooleanType
extends LiquibaseDataType {
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtil.trimToEmpty(this.getRawDefinition());
        if (database instanceof FirebirdDatabase) {
            try {
                if (database.getDatabaseMajorVersion() <= 2) {
                    return new DatabaseDataType("SMALLINT");
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Error checking database major version, assuming version 3+: " + e.getMessage(), e);
            }
            return new DatabaseDataType("BOOLEAN");
        }
        if (database instanceof Db2zDatabase) {
            return new DatabaseDataType("SMALLINT");
        }
        if (database instanceof MSSQLDatabase) {
            return new DatabaseDataType(database.escapeDataTypeName("bit"));
        }
        if (database instanceof MySQLDatabase) {
            if (originalDefinition.toLowerCase(Locale.US).startsWith("bit")) {
                return new DatabaseDataType("BIT", this.getParameters());
            }
            return database instanceof MariaDBDatabase ? new DatabaseDataType("TINYINT(1)") : new DatabaseDataType("TINYINT");
        }
        if (database instanceof OracleDatabase) {
            try {
                if (database.getDatabaseMajorVersion() >= 23) {
                    return new DatabaseDataType("BOOLEAN");
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Error checking database major version, assuming version <23: " + e.getMessage(), e);
            }
            return new DatabaseDataType("NUMBER", 1);
        }
        if (database instanceof SybaseASADatabase || database instanceof SybaseDatabase) {
            return new DatabaseDataType("BIT");
        }
        if (database instanceof DerbyDatabase) {
            if (((DerbyDatabase)database).supportsBooleanDataType()) {
                return new DatabaseDataType("BOOLEAN");
            }
            return new DatabaseDataType("SMALLINT");
        }
        if (database instanceof DB2Database) {
            if (((DB2Database)database).supportsBooleanDataType()) {
                return new DatabaseDataType("BOOLEAN");
            }
            return new DatabaseDataType("SMALLINT");
        }
        if (database instanceof HsqlDatabase) {
            return new DatabaseDataType("BOOLEAN");
        }
        if (database instanceof PostgresDatabase) {
            if (originalDefinition.toLowerCase(Locale.US).startsWith("bit")) {
                return new DatabaseDataType("BIT", this.getParameters());
            }
        } else if (database instanceof H2Database && this.getParameters().length > 0) {
            return new DatabaseDataType("BOOLEAN");
        }
        return super.toDatabaseDataType(database);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null) return null;
        if ("null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        if (value instanceof String) {
            if ("true".equals(((String)(value = ((String)value).replaceAll("'", ""))).toLowerCase(Locale.US))) return this.getTrueBooleanValue(database);
            if ("1".equals(value)) return this.getTrueBooleanValue(database);
            if ("b'1'".equals(((String)value).toLowerCase(Locale.US))) return this.getTrueBooleanValue(database);
            if ("t".equals(((String)value).toLowerCase(Locale.US))) return this.getTrueBooleanValue(database);
            if (((String)value).toLowerCase(Locale.US).equals(this.getTrueBooleanValue(database).toLowerCase(Locale.US))) {
                return this.getTrueBooleanValue(database);
            }
            if ("false".equals(((String)value).toLowerCase(Locale.US))) return this.getFalseBooleanValue(database);
            if ("0".equals(value)) return this.getFalseBooleanValue(database);
            if ("b'0'".equals(((String)value).toLowerCase(Locale.US))) return this.getFalseBooleanValue(database);
            if ("f".equals(((String)value).toLowerCase(Locale.US))) return this.getFalseBooleanValue(database);
            if (((String)value).toLowerCase(Locale.US).equals(this.getFalseBooleanValue(database).toLowerCase(Locale.US))) {
                return this.getFalseBooleanValue(database);
            }
            if (database instanceof PostgresDatabase && Pattern.matches("b?([01])\\1*(::bit|::\"bit\")?", (String)value)) {
                return "b'" + value.toString().replace("b", "").replace("\"", "").replace("::it", "") + "'::\"bit\"";
            }
            if (!(database instanceof SybaseASADatabase)) throw new UnexpectedLiquibaseException("Unknown boolean value: " + value);
            if (!((String)value).startsWith("COMPUTE")) throw new UnexpectedLiquibaseException("Unknown boolean value: " + value);
            return (String)value;
        }
        if (value instanceof Long) {
            if (!Long.valueOf(1L).equals(value)) return this.getFalseBooleanValue(database);
            return this.getTrueBooleanValue(database);
        }
        if (value instanceof Number) {
            if (value.equals(1)) return this.getTrueBooleanValue(database);
            if ("1".equals(value.toString())) return this.getTrueBooleanValue(database);
            if (!"1.0".equals(value.toString())) return this.getFalseBooleanValue(database);
            return this.getTrueBooleanValue(database);
        }
        if (value instanceof DatabaseFunction) {
            return value.toString();
        }
        if (!(value instanceof Boolean)) throw new UnexpectedLiquibaseException("Cannot convert type " + value.getClass() + " to a boolean value");
        if ((Boolean)value == false) return this.getFalseBooleanValue(database);
        return this.getTrueBooleanValue(database);
    }

    protected boolean isNumericBoolean(Database database) {
        if (database instanceof FirebirdDatabase) {
            try {
                if (database.getDatabaseMajorVersion() <= 2) {
                    return true;
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Error checking database major version, assuming version 3+: " + e.getMessage(), e);
            }
            return false;
        }
        if (database instanceof DerbyDatabase) {
            return !((DerbyDatabase)database).supportsBooleanDataType();
        }
        if (database instanceof DB2Database) {
            return !((DB2Database)database).supportsBooleanDataType();
        }
        return database instanceof Db2zDatabase || database instanceof FirebirdDatabase || database instanceof MSSQLDatabase || database instanceof MySQLDatabase || database instanceof OracleDatabase || database instanceof SQLiteDatabase || database instanceof SybaseASADatabase || database instanceof SybaseDatabase;
    }

    public String getFalseBooleanValue(Database database) {
        if (this.isNumericBoolean(database)) {
            return "0";
        }
        if (database instanceof InformixDatabase) {
            return "'f'";
        }
        return "FALSE";
    }

    public String getTrueBooleanValue(Database database) {
        if (this.isNumericBoolean(database)) {
            return "1";
        }
        if (database instanceof InformixDatabase) {
            return "'t'";
        }
        return "TRUE";
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.BOOLEAN;
    }
}

