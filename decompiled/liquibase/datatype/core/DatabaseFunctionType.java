/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Locale;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.DatabaseFunction;

@DataTypeInfo(name="function", aliases={"liquibase.statement.DatabaseFunction"}, minParameters=0, maxParameters=0, priority=1)
public class DatabaseFunctionType
extends LiquibaseDataType {
    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null || "null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        if (value instanceof DatabaseFunction) {
            return database.generateDatabaseFunctionValue((DatabaseFunction)value);
        }
        return value.toString();
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.COMPUTED;
    }
}

