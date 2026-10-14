/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Arrays;
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
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;

@DataTypeInfo(name="number", aliases={"numeric", "java.sql.Types.NUMERIC"}, minParameters=0, maxParameters=2, priority=1)
public class NumberType
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
            Object[] parameters = this.getParameters();
            if (parameters.length == 0) {
                parameters = new Object[]{18, 0};
            } else if (parameters.length == 1) {
                parameters = new Object[]{parameters[0], 0};
            } else if (parameters.length > 2) {
                parameters = Arrays.copyOfRange(parameters, 0, 2);
            }
            return new DatabaseDataType(database.escapeDataTypeName("numeric"), parameters);
        }
        if (database instanceof MySQLDatabase || database instanceof AbstractDb2Database || database instanceof HsqlDatabase || database instanceof DerbyDatabase || database instanceof FirebirdDatabase || database instanceof InformixDatabase || database instanceof SybaseASADatabase || database instanceof SybaseDatabase || database instanceof H2Database) {
            return new DatabaseDataType("numeric", this.getParameters());
        }
        if (database instanceof OracleDatabase) {
            if (this.getParameters().length > 1 && "0".equals(this.getParameters()[0]) && "-127".equals(this.getParameters()[1])) {
                return new DatabaseDataType("NUMBER");
            }
            return new DatabaseDataType("NUMBER", this.getParameters());
        }
        if (database instanceof PostgresDatabase) {
            if (this.getParameters().length > 0) {
                try {
                    if (Integer.parseInt(this.getParameters()[0].toString()) > 1000) {
                        return new DatabaseDataType("numeric");
                    }
                }
                catch (NumberFormatException e) {
                    return new DatabaseDataType("numeric", this.getParameters());
                }
            }
            return new DatabaseDataType("numeric", this.getParameters());
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }
}

