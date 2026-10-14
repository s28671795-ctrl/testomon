/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Locale;
import liquibase.database.Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.CharType;

@DataTypeInfo(name="varchar", aliases={"java.sql.Types.VARCHAR", "java.lang.String", "varchar2", "character varying"}, minParameters=0, maxParameters=1, priority=1)
public class VarcharType
extends CharType {
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        Object[] parameters;
        if (database instanceof OracleDatabase || database instanceof HsqlDatabase && ((HsqlDatabase)database).isUsingOracleSyntax()) {
            return new DatabaseDataType("VARCHAR2", this.getParameters());
        }
        if (database instanceof InformixDatabase && this.getSize() > 255) {
            return new DatabaseDataType("LVARCHAR", this.getParameters());
        }
        if (database instanceof MSSQLDatabase) {
            Object[] parameters2 = this.getParameters();
            if (parameters2.length > 0) {
                String param1 = parameters2[0].toString().replaceFirst("(?<=\\d+)\\s*(?i)CHAR$", "");
                parameters2[0] = param1;
                if (!param1.matches("\\d+") || new BigInteger(param1).compareTo(BigInteger.valueOf(8000L)) > 0) {
                    DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("varchar"), "MAX");
                    type.addAdditionalInformation(this.getAdditionalInformation());
                    return type;
                }
            }
            if (parameters2.length == 0) {
                parameters2 = new Object[]{1};
            } else if (parameters2.length > 1) {
                parameters2 = Arrays.copyOfRange(parameters2, 0, 1);
            }
            DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("varchar"), parameters2);
            type.addAdditionalInformation(this.getAdditionalInformation());
            return type;
        }
        if (database instanceof PostgresDatabase && (parameters = this.getParameters()) != null && parameters.length == 1) {
            String parameter = parameters[0].toString().replaceFirst("(?<=\\d+)\\s*(?i)CHAR$", "");
            if ("2147483647".equals(parameter)) {
                DatabaseDataType type = new DatabaseDataType("CHARACTER");
                type.addAdditionalInformation("VARYING");
                return type;
            }
            parameters[0] = parameter;
            DatabaseDataType type = new DatabaseDataType(this.getName().toUpperCase(Locale.US), parameters);
            type.addAdditionalInformation(this.getAdditionalInformation());
            return type;
        }
        return super.toDatabaseDataType(database);
    }
}

