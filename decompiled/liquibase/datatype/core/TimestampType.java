/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype.core;

import java.util.Locale;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.DateTimeType;
import liquibase.exception.DatabaseIncapableOfOperation;
import liquibase.util.StringUtil;
import liquibase.util.grammar.ParseException;

@DataTypeInfo(name="timestamp", aliases={"java.sql.Types.TIMESTAMP", "java.sql.Types.TIMESTAMP_WITH_TIMEZONE", "java.sql.Timestamp", "timestamptz"}, minParameters=0, maxParameters=1, priority=1)
public class TimestampType
extends DateTimeType {
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        DatabaseDataType type;
        Object[] parameters;
        String originalDefinition = StringUtil.trimToEmpty(this.getRawDefinition());
        if (this.getParameters().length > 0) {
            int maxFractionalDigits;
            Integer desiredLength = null;
            try {
                desiredLength = Integer.parseInt(String.valueOf(this.getParameters()[0]));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            if (desiredLength != null && (maxFractionalDigits = database.getMaxFractionalDigitsForTimestamp()) < desiredLength) {
                throw new DatabaseIncapableOfOperation(String.format("Using a TIMESTAMP data type with a fractional precision of %d", desiredLength), String.format("A timestamp datatype with %d fractional digits was requested, but %s only supports %d digits.", desiredLength, database.getDatabaseProductName(), maxFractionalDigits), database);
            }
        }
        if (database instanceof MySQLDatabase) {
            if (originalDefinition.contains(" ") || originalDefinition.contains("(")) {
                return new DatabaseDataType(this.getRawDefinition());
            }
            return super.toDatabaseDataType(database);
        }
        if (database instanceof MSSQLDatabase) {
            int paramValue;
            if (Boolean.TRUE.equals(GlobalConfiguration.CONVERT_DATA_TYPES.getCurrentValue() == false) && originalDefinition.toLowerCase(Locale.US).startsWith("timestamp")) {
                return new DatabaseDataType(database.escapeDataTypeName("timestamp"));
            }
            parameters = this.getParameters();
            if (parameters.length >= 1 && ((paramValue = Integer.parseInt(parameters[0].toString())) > 7 || paramValue == database.getDefaultScaleForNativeDataType("datetime2"))) {
                parameters = new Object[]{};
            }
            return new DatabaseDataType(database.escapeDataTypeName("datetime2"), parameters);
        }
        if (database instanceof SybaseDatabase) {
            return new DatabaseDataType(database.escapeDataTypeName("datetime"));
        }
        if (database instanceof AbstractDb2Database) {
            parameters = this.getParameters();
            if (parameters != null && parameters.length > 1) {
                parameters = new Object[]{parameters[1]};
            }
            return new DatabaseDataType(database.escapeDataTypeName("timestamp"), parameters);
        }
        if (this.getParameters().length > 0 && !(database instanceof SybaseASADatabase)) {
            int fractionalDigits = 0;
            String fractionalDigitsInput = this.getParameters()[0].toString();
            try {
                fractionalDigits = Integer.parseInt(fractionalDigitsInput);
            }
            catch (NumberFormatException e) {
                throw new RuntimeException(new ParseException(String.format("A timestamp with '%s' fractional digits was requested, but '%s' does not seem to be an integer.", fractionalDigitsInput, fractionalDigitsInput)));
            }
            int maxFractionalDigits = database.getMaxFractionalDigitsForTimestamp();
            if (maxFractionalDigits < fractionalDigits) {
                Scope.getCurrentScope().getLog(this.getClass()).warning(String.format("A timestamp datatype with %d fractional digits was requested, but the DBMS %s only supports %d digits. Because of this, the number of digits was reduced to %d.", fractionalDigits, database.getDatabaseProductName(), maxFractionalDigits, maxFractionalDigits));
                fractionalDigits = maxFractionalDigits;
            }
            type = new DatabaseDataType("TIMESTAMP", fractionalDigits);
        } else {
            type = new DatabaseDataType("TIMESTAMP");
        }
        if (originalDefinition.startsWith("java.sql.Types.TIMESTAMP_WITH_TIMEZONE") && (database instanceof PostgresDatabase || database instanceof OracleDatabase || database instanceof H2Database || database instanceof HsqlDatabase || database instanceof SybaseASADatabase)) {
            if (database instanceof PostgresDatabase || database instanceof H2Database || database instanceof SybaseASADatabase) {
                type.addAdditionalInformation("WITH TIME ZONE");
            } else {
                type.addAdditionalInformation("WITH TIMEZONE");
            }
            return type;
        }
        if (this.getAdditionalInformation() != null && (database instanceof PostgresDatabase || database instanceof OracleDatabase) || database instanceof H2Database || database instanceof HsqlDatabase || database instanceof SybaseASADatabase) {
            String additionalInformation = this.getAdditionalInformation();
            if (additionalInformation != null) {
                String additionInformation = additionalInformation.toUpperCase(Locale.US);
                if ((database instanceof PostgresDatabase || database instanceof H2Database || database instanceof SybaseASADatabase) && additionInformation.toUpperCase(Locale.US).contains("TIMEZONE")) {
                    additionalInformation = additionInformation.toUpperCase(Locale.US).replace("TIMEZONE", "TIME ZONE");
                }
                if (database instanceof OracleDatabase && additionInformation.startsWith("WITHOUT")) {
                    additionalInformation = null;
                }
                if (database instanceof H2Database && additionInformation.startsWith("WITHOUT")) {
                    additionalInformation = null;
                }
                if (database instanceof SybaseASADatabase && additionInformation.startsWith("WITHOUT")) {
                    additionalInformation = null;
                }
            }
            type.addAdditionalInformation(additionalInformation);
            return type;
        }
        return super.toDatabaseDataType(database);
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.DATE;
    }
}

