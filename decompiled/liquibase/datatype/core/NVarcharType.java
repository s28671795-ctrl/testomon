/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Locale;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.CharType;

@DataTypeInfo(name="nvarchar", aliases={"java.sql.Types.NVARCHAR", "nvarchar2", "national"}, minParameters=0, maxParameters=1, priority=1)
public class NVarcharType
extends CharType {
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (this.getRawDefinition() != null && this.getRawDefinition().toLowerCase(Locale.US).contains("national character varying")) {
            this.setAdditionalInformation(null);
        }
        if (database instanceof HsqlDatabase || database instanceof PostgresDatabase || database instanceof DerbyDatabase) {
            return new DatabaseDataType("VARCHAR", this.getParameters());
        }
        if (database instanceof OracleDatabase) {
            return new DatabaseDataType("NVARCHAR2", this.getParameters());
        }
        if (database instanceof MSSQLDatabase) {
            String param1;
            Object[] parameters = this.getParameters();
            if (!(parameters.length <= 0 || (param1 = parameters[0].toString()).matches("\\d+") && new BigInteger(param1).compareTo(BigInteger.valueOf(4000L)) <= 0)) {
                DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("nvarchar"), "MAX");
                type.addAdditionalInformation(this.getAdditionalInformation());
                return type;
            }
            if (parameters.length == 0) {
                parameters = new Object[]{1};
            } else if (parameters.length > 1) {
                parameters = Arrays.copyOfRange(parameters, 0, 1);
            }
            DatabaseDataType type = new DatabaseDataType(database.escapeDataTypeName("nvarchar"), parameters);
            type.addAdditionalInformation(this.getAdditionalInformation());
            return type;
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.STRING;
    }
}

