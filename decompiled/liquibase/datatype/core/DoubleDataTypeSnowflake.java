/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.DoubleType;

@DataTypeInfo(name="double", aliases={"java.sql.Types.DOUBLE", "java.lang.Double"}, minParameters=0, maxParameters=2, priority=5)
public class DoubleDataTypeSnowflake
extends DoubleType {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return new DatabaseDataType("FLOAT");
    }
}

