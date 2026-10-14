/*
 * Decompiled with CFR 0.152.
 */
package liquibase.datatype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.servicelocator.PrioritizedService;
import liquibase.statement.DatabaseFunction;

public abstract class LiquibaseDataType
implements PrioritizedService {
    private final String name;
    private final String[] aliases;
    private final int priority;
    private final int minParameters;
    private final int maxParameters;
    private final List<Object> parameters = new ArrayList<Object>();
    private String additionalInformation;
    private String rawDefinition;

    protected LiquibaseDataType(LiquibaseDataType originalType) {
        this.name = originalType.name;
        this.minParameters = originalType.minParameters;
        this.maxParameters = originalType.maxParameters;
        this.aliases = originalType.aliases;
        this.priority = originalType.priority;
    }

    public LiquibaseDataType() {
        DataTypeInfo dataTypeAnnotation = this.getClass().getAnnotation(DataTypeInfo.class);
        this.name = dataTypeAnnotation.name();
        this.minParameters = dataTypeAnnotation.minParameters();
        this.maxParameters = dataTypeAnnotation.maxParameters();
        this.aliases = dataTypeAnnotation.aliases();
        this.priority = dataTypeAnnotation.priority();
    }

    protected LiquibaseDataType(String name, int minParameters, int maxParameters) {
        this.name = name;
        this.minParameters = minParameters;
        this.maxParameters = maxParameters;
        this.aliases = new String[0];
        this.priority = 0;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    public boolean supports(Database database) {
        return true;
    }

    public int getMinParameters(Database database) {
        return this.minParameters;
    }

    public int getMaxParameters(Database database) {
        return this.maxParameters;
    }

    public Object[] getParameters() {
        return this.parameters.toArray();
    }

    public void addParameter(Object value) {
        this.parameters.add(value);
    }

    public String getAdditionalInformation() {
        return this.additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getRawDefinition() {
        return this.rawDefinition;
    }

    public boolean validate(Database database) {
        int maxParameters = this.getMaxParameters(database);
        int minParameters = this.getMinParameters(database);
        if (this.parameters.size() > maxParameters) {
            throw new UnexpectedLiquibaseException("Type " + this.getClass() + " doesn't support " + maxParameters + " parameters");
        }
        if (this.parameters.size() < minParameters) {
            throw new UnexpectedLiquibaseException("Type " + this.getClass() + " requires " + minParameters + " parameters");
        }
        return true;
    }

    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof MSSQLDatabase) {
            Object[] parameters;
            String name = database.escapeDataTypeName(this.getName());
            int dataTypeMaxParameters = database.getDataTypeMaxParameters(this.getName());
            if (dataTypeMaxParameters < (parameters = this.getParameters()).length) {
                parameters = Arrays.copyOfRange(parameters, 0, dataTypeMaxParameters);
            }
            return new DatabaseDataType(name, parameters);
        }
        DatabaseDataType type = new DatabaseDataType(this.name.toUpperCase(Locale.US), this.getParameters());
        type.addAdditionalInformation(this.additionalInformation);
        return type;
    }

    public String objectToSql(Object value, Database database) {
        if (value == null || "null".equals(value.toString().toLowerCase(Locale.US))) {
            return null;
        }
        if (value instanceof DatabaseFunction) {
            return this.functionToSql((DatabaseFunction)value, database);
        }
        if (value instanceof Number) {
            return this.numberToSql((Number)value, database);
        }
        return this.otherToSql(value, database);
    }

    protected String functionToSql(DatabaseFunction function, Database database) {
        return function == null ? null : database.generateDatabaseFunctionValue(function);
    }

    protected String numberToSql(Number number, Database database) {
        if (number == null) {
            return null;
        }
        if (number instanceof BigDecimal) {
            return this.formatNumber(((BigDecimal)number).toPlainString());
        }
        return this.formatNumber(number.toString());
    }

    protected String otherToSql(Object value, Database database) {
        return value == null ? null : value.toString();
    }

    public Object sqlToObject(String value, Database database) {
        return value;
    }

    public String toString() {
        StringBuilder returnString = new StringBuilder(this.getName());
        if (this.parameters != null && !this.parameters.isEmpty() && this.maxParameters > 0) {
            returnString.append("(");
            for (Object param2 : this.parameters) {
                returnString.append(param2.toString()).append(",");
            }
            returnString = new StringBuilder(returnString.toString().replaceFirst(",$", ""));
            returnString.append(")");
        }
        if (this.additionalInformation != null) {
            returnString.append(" ").append(this.additionalInformation);
        }
        return returnString.toString().trim();
    }

    public boolean equals(Object o) {
        return o instanceof LiquibaseDataType && this.toString().equals(o.toString());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    protected boolean isCurrentDateTimeFunction(String string, Database database) {
        return string.toLowerCase(Locale.US).startsWith("current_timestamp") || string.toLowerCase(Locale.US).startsWith("current_datetime") || database.getCurrentDateTimeFunction().toLowerCase(Locale.US).equals(string.toLowerCase(Locale.US));
    }

    public void finishInitialization(String originalDefinition) {
        this.rawDefinition = originalDefinition;
    }

    protected String formatNumber(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceFirst("\\.0+$", "");
    }

    public abstract LoadDataChange.LOAD_DATA_TYPE getLoadTypeName();

    public boolean isAutoIncrement() {
        return false;
    }
}

