/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Arrays;
import java.util.Locale;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.DatabaseFunction;

public class UnknownType
extends LiquibaseDataType {
    private boolean autoIncrement;

    public UnknownType() {
        super("UNKNOWN", 0, 2);
    }

    public UnknownType(String name) {
        super(name, 0, 2);
    }

    public UnknownType(String name, int minParameters, int maxParameters) {
        super(name, minParameters, maxParameters);
    }

    @Override
    public boolean isAutoIncrement() {
        return this.autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        DatabaseDataType type;
        int dataTypeMaxParameters = "enum".equals(this.getName().toLowerCase(Locale.US)) || "set".equals(this.getName().toLowerCase(Locale.US)) ? Integer.MAX_VALUE : database.getDataTypeMaxParameters(this.getName());
        Object[] parameters = this.getParameters();
        if (database instanceof OracleDatabase) {
            if ("LONG".equals(this.getName().toUpperCase(Locale.US)) || "BFILE".equals(this.getName().toUpperCase(Locale.US)) || "ROWID".equals(this.getName().toUpperCase(Locale.US)) || "ANYDATA".equals(this.getName().toUpperCase(Locale.US)) || "SDO_GEOMETRY".equals(this.getName().toUpperCase(Locale.US))) {
                parameters = new Object[]{};
            } else {
                if ("RAW".equals(this.getName().toUpperCase(Locale.US))) {
                    return new DatabaseDataType(this.getName(), parameters);
                }
                if (this.getName().toUpperCase(Locale.US).startsWith("INTERVAL ")) {
                    return new DatabaseDataType(this.getName().replaceAll("\\(\\d+\\)", ""));
                }
                return new DatabaseDataType(this.getName().toUpperCase(Locale.US));
            }
        }
        if (dataTypeMaxParameters < parameters.length) {
            parameters = Arrays.copyOfRange(parameters, 0, dataTypeMaxParameters);
        }
        if (database instanceof MSSQLDatabase) {
            if (parameters.length >= 1 && this.getRawDefinition().matches("(?i)\\[?datetimeoffset\\]?.*") && Integer.parseInt(parameters[0].toString()) == database.getDefaultScaleForNativeDataType("datetimeoffset")) {
                parameters = new Object[]{};
            }
            type = new DatabaseDataType(database.escapeDataTypeName(this.getName()), parameters);
        } else {
            type = new DatabaseDataType(this.getName().toUpperCase(Locale.US), parameters);
        }
        type.addAdditionalInformation(this.getAdditionalInformation());
        return type;
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if (value instanceof DatabaseFunction) {
            return super.objectToSql(value, database);
        }
        return "'" + super.objectToSql(value, database) + "'";
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.STRING;
    }
}

