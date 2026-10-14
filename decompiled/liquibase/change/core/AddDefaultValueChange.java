/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropDefaultValueChange;
import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddDefaultValueStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;
import liquibase.util.ISODateFormat;

@DatabaseChange(name="addDefaultValue", description="Adds a default value to the database definition for the specified column.\nOne of defaultValue, defaultValueNumeric, defaultValueBoolean or defaultValueDate must be set", priority=1, appliesTo={"column"})
public class AddDefaultValueChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String columnDataType;
    private String defaultValue;
    private String defaultValueNumeric;
    private String defaultValueDate;
    private Boolean defaultValueBoolean;
    private DatabaseFunction defaultValueComputed;
    private SequenceNextValueFunction defaultValueSequenceNext;
    private String defaultValueConstraintName;

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validate = new ValidationErrors();
        int nonNullValues = 0;
        if (this.defaultValue != null) {
            ++nonNullValues;
        }
        if (this.defaultValueNumeric != null) {
            ++nonNullValues;
        }
        if (this.defaultValueBoolean != null) {
            ++nonNullValues;
        }
        if (this.defaultValueDate != null) {
            ++nonNullValues;
        }
        if (this.defaultValueComputed != null) {
            ++nonNullValues;
        }
        if (this.defaultValueSequenceNext != null) {
            ++nonNullValues;
        }
        if (nonNullValues > 1) {
            validate.addError("Only one defaultValue* value can be specified");
        } else {
            validate.addAll(super.validate(database));
        }
        return validate;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation.catalog", since="3.0", description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation.schema", description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column.relation", description="Name of the table containing the column to modify", exampleValue="file")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column", description="Name of the column to add a default value to", exampleValue="fileName")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(description="Current data type of the column to add a default value to", exampleValue="varchar(50)")
    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    @DatabaseChangeProperty(exampleValue="Something Else", requiredForDatabase={"none"}, description="Default value for fields in the column. Either this property or another defaultValue* property is required.")
    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @DatabaseChangeProperty(requiredForDatabase={"none"}, exampleValue="439.2", description="Default value for a column of a numeric type. For example: integer, bigint, bigdecimal, and others.")
    public String getDefaultValueNumeric() {
        return this.defaultValueNumeric;
    }

    public void setDefaultValueNumeric(String defaultValueNumeric) {
        this.defaultValueNumeric = defaultValueNumeric;
    }

    @DatabaseChangeProperty(requiredForDatabase={"none"}, exampleValue="2008-02-12T12:34:03", description="Default date and time value for column. The value is specified in one of the following forms: YYYY-MM-DD, hh:mm:ss, or YYYY-MM-DDThh:mm:ss.")
    public String getDefaultValueDate() {
        return this.defaultValueDate;
    }

    public void setDefaultValueDate(String defaultValueDate) {
        this.defaultValueDate = defaultValueDate;
    }

    @DatabaseChangeProperty(requiredForDatabase={"none"}, description="Default value for a column of a boolean type.")
    public Boolean getDefaultValueBoolean() {
        return this.defaultValueBoolean;
    }

    public void setDefaultValueBoolean(Boolean defaultValueBoolean) {
        this.defaultValueBoolean = defaultValueBoolean;
    }

    @DatabaseChangeProperty(requiredForDatabase={"none"}, description="Default value that is returned from a function or procedure call of the same type as the column. Contains the function or column name to call. Differs from defaultValue by returning the value of the function or column you specify instead of the name of the function/column as a string. Can also perform operations on the returned value.")
    public DatabaseFunction getDefaultValueComputed() {
        return this.defaultValueComputed;
    }

    public void setDefaultValueComputed(DatabaseFunction defaultValueComputed) {
        this.defaultValueComputed = defaultValueComputed;
    }

    @DatabaseChangeProperty(requiredForDatabase={"none"}, description="Sets value for a specified column by using the value of the existing sequence. With every new input, the next value of the sequence will be taken.")
    public SequenceNextValueFunction getDefaultValueSequenceNext() {
        return this.defaultValueSequenceNext;
    }

    public void setDefaultValueSequenceNext(SequenceNextValueFunction defaultValueSequenceNext) {
        this.defaultValueSequenceNext = defaultValueSequenceNext;
    }

    @DatabaseChangeProperty(description="Sets a unique name for the default constraint used for a specific column. Works only along with any of the defaultValue* properties listed.")
    public String getDefaultValueConstraintName() {
        return this.defaultValueConstraintName;
    }

    public void setDefaultValueConstraintName(String defaultValueConstraintName) {
        this.defaultValueConstraintName = defaultValueConstraintName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        Object defaultValue = null;
        if (this.getDefaultValue() != null) {
            defaultValue = this.getDefaultValue();
        } else if (this.getDefaultValueBoolean() != null) {
            defaultValue = this.getDefaultValueBoolean();
        } else if (this.getDefaultValueNumeric() != null) {
            try {
                defaultValue = NumberFormat.getInstance(Locale.US).parse(this.getDefaultValueNumeric());
            }
            catch (ParseException e) {
                defaultValue = new DatabaseFunction(this.getDefaultValueNumeric());
            }
        } else if (this.getDefaultValueDate() != null) {
            try {
                defaultValue = new ISODateFormat().parse(this.getDefaultValueDate());
            }
            catch (ParseException e) {
                defaultValue = new DatabaseFunction(this.getDefaultValueDate());
            }
        } else if (this.getDefaultValueComputed() != null) {
            defaultValue = this.getDefaultValueComputed();
        } else if (this.getDefaultValueSequenceNext() != null) {
            defaultValue = new SequenceNextValueFunction(this.getSchemaName(), this.getDefaultValueSequenceNext().getValue());
        }
        AddDefaultValueStatement statement = new AddDefaultValueStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName(), this.getColumnDataType(), defaultValue);
        statement.setDefaultValueConstraintName(this.getDefaultValueConstraintName());
        return new SqlStatement[]{statement};
    }

    @Override
    protected Change[] createInverses() {
        DropDefaultValueChange inverse = new DropDefaultValueChange();
        inverse.setSchemaName(this.getSchemaName());
        inverse.setTableName(this.getTableName());
        inverse.setColumnName(this.getColumnName());
        inverse.setColumnDataType(this.getColumnDataType());
        return new Change[]{inverse};
    }

    @Override
    public String getConfirmationMessage() {
        return "Default value added to " + this.getTableName() + "." + this.getColumnName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            Column column = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName()), database);
            if (column == null) {
                return result.unknown("Column " + this.getColumnName() + " does not exist");
            }
            result.assertComplete(column.getDefaultValue() != null, "Column " + this.getColumnName() + " has no default value");
            if (column.getDefaultValue() == null) {
                return result;
            }
            if (this.getDefaultValue() != null) {
                return result.assertCorrect(this.getDefaultValue().equals(column.getDefaultValue()), "Default value was " + column.getDefaultValue());
            }
            if (this.getDefaultValueDate() != null) {
                return result.assertCorrect(this.getDefaultValueDate().equals(new ISODateFormat().format((Date)column.getDefaultValue())), "Default value was " + column.getDefaultValue());
            }
            if (this.getDefaultValueNumeric() != null) {
                return result.assertCorrect(this.getDefaultValueNumeric().equals(column.getDefaultValue().toString()), "Default value was " + column.getDefaultValue());
            }
            if (this.getDefaultValueBoolean() != null) {
                return result.assertCorrect(this.getDefaultValueBoolean().equals(column.getDefaultValue()), "Default value was " + column.getDefaultValue());
            }
            if (this.getDefaultValueComputed() != null) {
                return result.assertCorrect(this.getDefaultValueComputed().equals(column.getDefaultValue()), "Default value was " + column.getDefaultValue());
            }
            if (this.getDefaultValueSequenceNext() != null) {
                return result.assertCorrect(this.getDefaultValueSequenceNext().equals(column.getDefaultValue()), "Default value was " + column.getDefaultValue());
            }
            return result.unknown("Unknown default value type");
        }
        catch (Exception e) {
            return result.unknown(e);
        }
    }
}

