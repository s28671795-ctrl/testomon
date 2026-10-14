/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
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
import liquibase.exception.DatabaseException;
import liquibase.util.StringUtil;

@DataTypeInfo(name="xml", aliases={"xmltype", "java.sql.Types.SQLXML"}, minParameters=0, maxParameters=1, priority=1)
public class XMLType
extends LiquibaseDataType {
    @Override
    protected String otherToSql(Object value, Database database) {
        if (value == null) {
            return null;
        }
        String val = value.toString();
        if (database instanceof MSSQLDatabase && !StringUtil.isAscii(val)) {
            return "N'" + database.escapeStringForDatabase(val) + "'";
        }
        if (database instanceof PostgresDatabase) {
            try {
                if (database.getDatabaseMajorVersion() <= 7 || database.getDatabaseMajorVersion() == 8 && database.getDatabaseMinorVersion() <= 2) {
                    return "'" + database.escapeStringForDatabase(val) + "'";
                }
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
            return "xml '" + database.escapeStringForDatabase(val) + "'";
        }
        if (database instanceof OracleDatabase) {
            return "XMLType('" + database.escapeStringForDatabase(val) + "')";
        }
        return "'" + database.escapeStringForDatabase(val) + "'";
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof MSSQLDatabase) {
            Object[] parameters = this.getParameters();
            if (parameters.length > 1) {
                parameters = new Object[]{parameters[0]};
            }
            return new DatabaseDataType(database.escapeDataTypeName("xml"), parameters);
        }
        if (database instanceof PostgresDatabase) {
            try {
                if (database.getDatabaseMajorVersion() <= 7 || database.getDatabaseMajorVersion() == 8 && database.getDatabaseMinorVersion() <= 2) {
                    return new DatabaseDataType("TEXT");
                }
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
            return new DatabaseDataType("XML");
        }
        if (database instanceof AbstractDb2Database) {
            return new DatabaseDataType("XML");
        }
        if (database instanceof OracleDatabase) {
            return new DatabaseDataType("XMLTYPE");
        }
        if (database instanceof FirebirdDatabase) {
            return new DatabaseDataType("BLOB SUB_TYPE TEXT");
        }
        if (database instanceof SybaseASADatabase) {
            return new DatabaseDataType("LONG VARCHAR");
        }
        if (database instanceof MySQLDatabase) {
            return new DatabaseDataType("LONGTEXT");
        }
        if (database instanceof H2Database || database instanceof HsqlDatabase || database instanceof InformixDatabase) {
            return new DatabaseDataType("CLOB");
        }
        if (database instanceof SQLiteDatabase || database instanceof SybaseDatabase) {
            return new DatabaseDataType("TEXT");
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.CLOB;
    }
}

