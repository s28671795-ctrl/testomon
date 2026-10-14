/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Locale;
import liquibase.GlobalConfiguration;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.statement.DatabaseFunction;
import liquibase.util.StringUtil;

@DataTypeInfo(name="clob", aliases={"longvarchar", "text", "longtext", "java.sql.Types.LONGVARCHAR", "java.sql.Types.CLOB", "nclob", "longnvarchar", "ntext", "java.sql.Types.LONGNVARCHAR", "java.sql.Types.NCLOB", "tinytext", "mediumtext", "long varchar", "long nvarchar"}, minParameters=0, maxParameters=0, priority=1)
public class ClobType
extends LiquibaseDataType {
    @Override
    public String objectToSql(Object value, Database database) {
        if (value == null || "null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        if (value instanceof DatabaseFunction) {
            return value.toString();
        }
        String val = String.valueOf(value);
        if (val.startsWith("'")) {
            return val;
        }
        if (database instanceof MSSQLDatabase && !StringUtil.isAscii(val)) {
            return "N'" + database.escapeStringForDatabase(val) + "'";
        }
        return "'" + database.escapeStringForDatabase(val) + "'";
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtil.trimToEmpty(this.getRawDefinition());
        if (database instanceof MSSQLDatabase) {
            if (originalDefinition.toLowerCase(Locale.US).startsWith("text") || originalDefinition.toLowerCase(Locale.US).startsWith("[text]") || originalDefinition.toLowerCase(Locale.US).startsWith("ntext") || originalDefinition.toLowerCase(Locale.US).startsWith("[ntext]")) {
                if (!Boolean.TRUE.equals(GlobalConfiguration.CONVERT_DATA_TYPES.getCurrentValue())) {
                    return new DatabaseDataType(database.escapeDataTypeName(originalDefinition));
                }
                String stringType = "varchar";
                if (originalDefinition.toLowerCase(Locale.US).contains("ntext")) {
                    stringType = "nvarchar";
                }
                DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName(stringType));
                String originalExtraInfo = originalDefinition.replaceFirst("^(?i)\\[?ntext\\]?\\s*", "");
                originalExtraInfo = originalExtraInfo.replaceFirst("^(?i)\\[?text\\]?\\s*", "");
                type.addAdditionalInformation("(max)");
                if (!StringUtil.isEmpty(originalExtraInfo) && originalExtraInfo.lastIndexOf(")") < originalExtraInfo.length() - 1) {
                    type.addAdditionalInformation(originalExtraInfo.substring(originalExtraInfo.lastIndexOf(")") + 1));
                }
                return type;
            }
            if (Boolean.TRUE.equals(GlobalConfiguration.CONVERT_DATA_TYPES.getCurrentValue())) {
                if ("nclob".equals(originalDefinition.toLowerCase(Locale.US))) {
                    return new DatabaseDataType(database.escapeDataTypeName("nvarchar"), "MAX");
                }
                return new DatabaseDataType(database.escapeDataTypeName("varchar"), "MAX");
            }
        } else {
            if (database instanceof FirebirdDatabase) {
                return new DatabaseDataType("BLOB SUB_TYPE TEXT");
            }
            if (database instanceof SybaseASADatabase) {
                return new DatabaseDataType("LONG VARCHAR");
            }
            if (database instanceof MySQLDatabase) {
                if (originalDefinition.toLowerCase(Locale.US).startsWith("text")) {
                    return new DatabaseDataType("TEXT");
                }
                if (originalDefinition.toLowerCase(Locale.US).startsWith("tinytext")) {
                    return new DatabaseDataType("TINYTEXT");
                }
                if (originalDefinition.toLowerCase(Locale.US).startsWith("mediumtext")) {
                    return new DatabaseDataType("MEDIUMTEXT");
                }
                if (originalDefinition.toLowerCase(Locale.US).startsWith("nclob")) {
                    DatabaseDataType type = new DatabaseDataType("LONGTEXT");
                    type.addAdditionalInformation("CHARACTER SET utf8");
                    return type;
                }
                return new DatabaseDataType("LONGTEXT");
            }
            if (database instanceof H2Database || database instanceof HsqlDatabase) {
                if (originalDefinition.toLowerCase(Locale.US).startsWith("longvarchar") || originalDefinition.startsWith("java.sql.Types.LONGVARCHAR")) {
                    return new DatabaseDataType("LONGVARCHAR");
                }
                return new DatabaseDataType("CLOB");
            }
            if (database instanceof PostgresDatabase || database instanceof SQLiteDatabase || database instanceof SybaseDatabase) {
                return new DatabaseDataType("TEXT");
            }
            if (database instanceof OracleDatabase) {
                if ("nclob".equals(originalDefinition.toLowerCase(Locale.US))) {
                    return new DatabaseDataType("NCLOB");
                }
                return new DatabaseDataType("CLOB");
            }
            if (database instanceof InformixDatabase && originalDefinition.toLowerCase(Locale.US).startsWith("text")) {
                return new DatabaseDataType("TEXT");
            }
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.CLOB;
    }
}

