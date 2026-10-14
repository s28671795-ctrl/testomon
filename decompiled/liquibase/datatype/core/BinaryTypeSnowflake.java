/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Locale;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.util.StringUtil;

@DataTypeInfo(name="binary", aliases={"longblob", "longvarbinary", "java.sql.Types.BLOB", "java.sql.Types.LONGBLOB", "java.sql.Types.LONGVARBINARY", "java.sql.Types.VARBINARY", "java.sql.Types.BINARY", "varbinary", "binary", "image", "tinyblob", "mediumblob"}, minParameters=0, maxParameters=12, priority=5)
public class BinaryTypeSnowflake
extends LiquibaseDataType {
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtil.trimToEmpty(this.getRawDefinition());
        if (database instanceof SnowflakeDatabase) {
            if (originalDefinition.toLowerCase(Locale.US).startsWith("varbinary") || originalDefinition.startsWith("java.sql.Types.VARBINARY")) {
                return new DatabaseDataType("VARBINARY", this.getParameters());
            }
            return new DatabaseDataType("BINARY", this.getParameters());
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.OTHER;
    }

    @Override
    public boolean supports(Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public int getPriority() {
        return 5;
    }
}

