/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Arrays;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;

@DataTypeInfo(name="decimal", aliases={"java.sql.Types.DECIMAL", "java.math.BigDecimal"}, minParameters=0, maxParameters=2, priority=1)
public class DecimalType
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
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof MSSQLDatabase) {
            Object[] parameters = this.getParameters();
            if (parameters.length == 0) {
                parameters = new Object[]{18, 0};
            } else if (parameters.length == 1) {
                parameters = new Object[]{parameters[0], 0};
            } else if (parameters.length > 2) {
                parameters = Arrays.copyOfRange(parameters, 0, 2);
            }
            return new DatabaseDataType(database.escapeDataTypeName("decimal"), parameters);
        }
        if (database instanceof InformixDatabase && this.getParameters() != null && this.getParameters().length == 2 && "255".equals(String.valueOf(this.getParameters()[1]))) {
            return new DatabaseDataType(this.getName(), this.getParameters()[0]);
        }
        return super.toDatabaseDataType(database);
    }
}

