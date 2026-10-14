/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.math.BigInteger;
import java.util.Arrays;
import liquibase.database.Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.CharType;

@DataTypeInfo(name="nchar", aliases={"java.sql.Types.NCHAR", "nchar2"}, minParameters=0, maxParameters=1, priority=1)
public class NCharType
extends CharType {
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof HsqlDatabase) {
            return new DatabaseDataType("CHAR", this.getParameters());
        }
        if (database instanceof OracleDatabase) {
            return new DatabaseDataType("NCHAR", this.getParameters());
        }
        if (database instanceof MSSQLDatabase) {
            String param1;
            Object[] parameters = this.getParameters();
            if (!(parameters.length <= 0 || (param1 = parameters[0].toString()).matches("\\d+") && new BigInteger(param1).compareTo(BigInteger.valueOf(4000L)) <= 0)) {
                DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("nchar"), 4000);
                type.addAdditionalInformation(this.getAdditionalInformation());
                return type;
            }
            if (parameters.length == 0) {
                parameters = new Object[]{1};
            } else if (parameters.length > 1) {
                parameters = Arrays.copyOfRange(parameters, 0, 1);
            }
            DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("nchar"), parameters);
            type.addAdditionalInformation(this.getAdditionalInformation());
            return type;
        }
        return super.toDatabaseDataType(database);
    }
}

