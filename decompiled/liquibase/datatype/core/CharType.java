/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import liquibase.Scope;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.H2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.statement.DatabaseFunction;
import liquibase.util.StringUtil;

@DataTypeInfo(name="char", aliases={"java.sql.Types.CHAR", "bpchar", "character"}, minParameters=0, maxParameters=1, priority=1)
public class CharType
extends LiquibaseDataType {
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof MSSQLDatabase) {
            Object[] parameters = this.getParameters();
            if (parameters.length > 0) {
                String param1 = parameters[0].toString().replaceFirst("(?<=\\d+)\\s*(?i)CHAR$", "");
                parameters[0] = param1;
                if (!param1.matches("\\d+") || new BigInteger(param1).compareTo(BigInteger.valueOf(8000L)) > 0) {
                    DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("char"), 8000);
                    type.addAdditionalInformation(this.getAdditionalInformation());
                    return type;
                }
            }
            if (parameters.length == 0) {
                parameters = new Object[]{1};
            } else if (parameters.length > 1) {
                parameters = Arrays.copyOfRange(parameters, 0, 1);
            }
            DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("char"), parameters);
            type.addAdditionalInformation(this.getAdditionalInformation());
            return type;
        }
        if (database instanceof PostgresDatabase) {
            Object[] parameters = this.getParameters();
            if (parameters != null && parameters.length == 1) {
                String parameter = parameters[0].toString().replaceFirst("(?<=\\d+)\\s*(?i)CHAR$", "");
                if ("2147483647".equals(parameter)) {
                    DatabaseDataType type = new DatabaseDataType("CHARACTER");
                    type.addAdditionalInformation("VARYING");
                    return type;
                }
                parameters[0] = parameter;
                DatabaseDataType type = new DatabaseDataType(this.getName().toUpperCase(Locale.US), parameters);
                type.addAdditionalInformation(this.getAdditionalInformation());
                return type;
            }
        } else if (database instanceof H2Database && this.getRawDefinition().toLowerCase(Locale.US).contains("large object")) {
            return new DatabaseDataType("CHARACTER LARGE OBJECT");
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null || "null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        String stringValue = value.toString();
        if (value instanceof DatabaseFunction) {
            return stringValue;
        }
        String val = String.valueOf(value);
        if (database instanceof MSSQLDatabase && !StringUtil.isAscii(val)) {
            return "N'" + database.escapeStringForDatabase(val) + "'";
        }
        if (database instanceof OracleDatabase && LiquibaseCommandLineConfiguration.WORKAROUND_ORACLE_CLOB_CHARACTER_LIMIT.getCurrentValue().booleanValue() && stringValue.length() > 4000) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("A string longer than 4000 characters has been detected on an insert statement, and the database is Oracle. Oracle forbids insert statements with strings longer than 4000 characters, so Liquibase is going to workaround this limitation. If an error occurs, this can be disabled by setting " + LiquibaseCommandLineConfiguration.WORKAROUND_ORACLE_CLOB_CHARACTER_LIMIT.getKey() + " to false.");
            List<String> chunks = StringUtil.splitToChunks(stringValue, 4000);
            return "to_clob( '" + StringUtil.join(chunks, "' ) || to_clob( '", obj -> database.escapeStringForDatabase(obj.toString())) + "' )";
        }
        return "'" + database.escapeStringForDatabase(val) + "'";
    }

    protected int getSize() {
        if (this.getParameters().length == 0) {
            return -1;
        }
        if (this.getParameters()[0] instanceof String) {
            return Integer.parseInt((String)this.getParameters()[0]);
        }
        if (this.getParameters()[0] instanceof Number) {
            return ((Number)this.getParameters()[0]).intValue();
        }
        return -1;
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.STRING;
    }
}

