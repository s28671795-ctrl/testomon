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
import liquibase.database.core.H2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.DatabaseFunction;

@DataTypeInfo(name="tinyint", aliases={"java.sql.Types.TINYINT"}, minParameters=0, maxParameters=1, priority=1)
public class TinyIntType
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
            return new DatabaseDataType(database.escapeDataTypeName("tinyint"));
        }
        if (database instanceof DerbyDatabase || database instanceof PostgresDatabase || database instanceof FirebirdDatabase || database instanceof AbstractDb2Database) {
            return new DatabaseDataType("SMALLINT");
        }
        if (database instanceof MySQLDatabase) {
            DatabaseDataType type = new DatabaseDataType("TINYINT", this.getParameters());
            type.addAdditionalInformation(this.getAdditionalInformation());
            return type;
        }
        if (database instanceof OracleDatabase) {
            return new DatabaseDataType("NUMBER", 3);
        }
        if (database instanceof SybaseDatabase) {
            return new DatabaseDataType("TINYINT");
        }
        if (database instanceof H2Database) {
            return new DatabaseDataType("TINYINT");
        }
        if (database instanceof SybaseASADatabase) {
            return new DatabaseDataType("TINYINT");
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
        return this.formatNumber(value.toString());
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }
}

