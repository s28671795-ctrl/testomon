/*
 * Decompiled with CFR 0.152.
 */
package liquibase.common.datatype;

import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.LiquibaseDataType;
import liquibase.servicelocator.LiquibaseService;

@LiquibaseService(skip=true)
public class DataTypeWrapper
extends LiquibaseDataType {
    private final LiquibaseDataType underlyingDataType;

    private static LiquibaseDataType stripWrappedDataType(LiquibaseDataType candidate) {
        if (candidate instanceof DataTypeWrapper) {
            return ((DataTypeWrapper)candidate).getUnderlyingDataType();
        }
        return candidate;
    }

    public LiquibaseDataType getUnderlyingDataType() {
        return this.underlyingDataType;
    }

    public DataTypeWrapper(LiquibaseDataType originalType) {
        super(DataTypeWrapper.stripWrappedDataType(originalType));
        this.underlyingDataType = DataTypeWrapper.stripWrappedDataType(originalType);
    }

    @Override
    public String getName() {
        return this.underlyingDataType.getName();
    }

    @Override
    public String[] getAliases() {
        return this.underlyingDataType.getAliases();
    }

    @Override
    public int getPriority() {
        return this.underlyingDataType.getPriority();
    }

    @Override
    public boolean supports(Database database) {
        return this.underlyingDataType.supports(database);
    }

    @Override
    public int getMinParameters(Database database) {
        return this.underlyingDataType.getMinParameters(database);
    }

    @Override
    public int getMaxParameters(Database database) {
        return this.underlyingDataType.getMaxParameters(database);
    }

    @Override
    public Object[] getParameters() {
        return this.underlyingDataType.getParameters();
    }

    @Override
    public void addParameter(Object value) {
        this.underlyingDataType.addParameter(value);
    }

    @Override
    public boolean validate(Database database) {
        return this.underlyingDataType.validate(database);
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return this.underlyingDataType.toDatabaseDataType(database);
    }

    @Override
    public String objectToSql(Object value, Database database) {
        return this.underlyingDataType.objectToSql(value, database);
    }

    public Object stringToObject(String value, Database database) {
        return value;
    }

    @Override
    public String toString() {
        return this.underlyingDataType.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DataTypeWrapper && this.underlyingDataType.equals(((DataTypeWrapper)o).getUnderlyingDataType());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.STRING;
    }
}

