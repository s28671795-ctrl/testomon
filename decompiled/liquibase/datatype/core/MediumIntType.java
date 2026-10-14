/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Locale;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.DatabaseFunction;

@DataTypeInfo(name="mediumint", minParameters=0, maxParameters=1, priority=1)
public class MediumIntType
extends LiquibaseDataType {
    private boolean autoIncrement;

    @Override
    public boolean isAutoIncrement() {
        return this.autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof MSSQLDatabase) {
            return new DatabaseDataType(database.escapeDataTypeName("int"));
        }
        if (database instanceof MySQLDatabase) {
            DatabaseDataType type = new DatabaseDataType("MEDIUMINT");
            type.addAdditionalInformation(this.getAdditionalInformation());
            return type;
        }
        if (database instanceof AbstractDb2Database || database instanceof DerbyDatabase || database instanceof FirebirdDatabase || database instanceof OracleDatabase) {
            return new DatabaseDataType("MEDIUMINT");
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null || "null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        if (value instanceof DatabaseFunction) {
            return value.toString();
        }
        if (value instanceof Boolean) {
            return Boolean.TRUE.equals(value) ? "1" : "0";
        }
        return this.formatNumber(value.toString());
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }
}

