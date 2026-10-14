/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.math.BigInteger;
import java.util.Locale;
import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeNote;
import liquibase.change.DatabaseChangeProperty;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.AddAutoIncrementStatement;
import liquibase.statement.core.AddDefaultValueStatement;
import liquibase.statement.core.CreateSequenceStatement;
import liquibase.statement.core.SetNullableStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;

@DatabaseChange(name="addAutoIncrement", description="Converts an existing column to be an auto-increment (a.k.a 'identity') column", priority=1, appliesTo={"column"}, databaseNotes={@DatabaseChangeNote(database="sqlite", notes="If the column type is not INTEGER it is converted to INTEGER")})
public class AddAutoIncrementChange
extends AbstractChange {
    private String catalogName;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String columnDataType;
    private BigInteger startWith;
    private BigInteger incrementBy;
    private Boolean defaultOnNull;
    private String generationType;

    @DatabaseChangeProperty(since="3.0", mustEqualExisting="column.relation.catalog", description="Name of the database catalog")
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

    @DatabaseChangeProperty(mustEqualExisting="column.relation", description="Name of the table")
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @DatabaseChangeProperty(mustEqualExisting="column", description="Name of the column")
    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @DatabaseChangeProperty(exampleValue="int", description="Current data type of the column to make auto-increment")
    public String getColumnDataType() {
        return this.columnDataType;
    }

    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    @DatabaseChangeProperty(exampleValue="100", description="Initial value of the increment")
    public BigInteger getStartWith() {
        return this.startWith;
    }

    public void setStartWith(BigInteger startWith) {
        this.startWith = startWith;
    }

    @DatabaseChangeProperty(exampleValue="1", description="Amount to increment by at each call")
    public BigInteger getIncrementBy() {
        return this.incrementBy;
    }

    public void setIncrementBy(BigInteger incrementBy) {
        this.incrementBy = incrementBy;
    }

    @DatabaseChangeProperty(exampleValue="true", since="3.6", description="When using generationType of BY DEFAULT then defaultOnNull=true allows the identity to be used if the identity column is referenced, but a value of NULL is specified.")
    public Boolean getDefaultOnNull() {
        return this.defaultOnNull;
    }

    public void setDefaultOnNull(Boolean defaultOnNull) {
        this.defaultOnNull = defaultOnNull;
    }

    @DatabaseChangeProperty(exampleValue="ALWAYS", since="3.6", description="Type of the generation in \"GENERATED %s AS IDENTITY\". Default: \"|\".")
    public String getGenerationType() {
        return this.generationType;
    }

    public void setGenerationType(String generationType) {
        this.generationType = generationType;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        if (database instanceof PostgresDatabase) {
            String sequenceName = this.getTableName() + "_" + this.getColumnName() + "_seq";
            String escapedTableName = database.escapeObjectName(this.getTableName(), Table.class);
            String escapedColumnName = database.escapeObjectName(this.getColumnName(), Table.class);
            if (escapedTableName != null && escapedColumnName != null && !escapedTableName.startsWith("\"") && !escapedColumnName.startsWith("\"")) {
                sequenceName = sequenceName.toLowerCase(Locale.US);
            }
            String schemaPrefix = this.schemaName == null ? database.getDefaultSchemaName() : this.schemaName;
            SequenceNextValueFunction nvf = new SequenceNextValueFunction(schemaPrefix, sequenceName);
            CreateSequenceStatement createSequenceStatement = new CreateSequenceStatement(this.catalogName, this.schemaName, sequenceName);
            createSequenceStatement.setIncrementBy(this.getIncrementBy());
            createSequenceStatement.setStartValue(this.getStartWith());
            return new SqlStatement[]{createSequenceStatement, new SetNullableStatement(this.catalogName, this.schemaName, this.getTableName(), this.getColumnName(), null, false), new AddDefaultValueStatement(this.catalogName, this.schemaName, this.getTableName(), this.getColumnName(), this.getColumnDataType(), nvf)};
        }
        return new SqlStatement[]{new AddAutoIncrementStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName(), this.getColumnDataType(), this.getStartWith(), this.getIncrementBy(), this.getDefaultOnNull(), this.getGenerationType())};
    }

    @Override
    public String getConfirmationMessage() {
        return "Auto-increment added to " + this.getTableName() + "." + this.getColumnName();
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        Column example = new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.getColumnName());
        try {
            Column column = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database);
            if (column == null) {
                return result.unknown("Column does not exist");
            }
            result.assertComplete(column.isAutoIncrement(), "Column is not auto-increment");
            if (this.getStartWith() != null && column.getAutoIncrementInformation().getStartWith() != null) {
                result.assertCorrect(this.getStartWith().equals(column.getAutoIncrementInformation().getStartWith()), "startsWith incorrect");
            }
            if (this.getIncrementBy() != null && column.getAutoIncrementInformation().getIncrementBy() != null) {
                result.assertCorrect(this.getIncrementBy().equals(column.getAutoIncrementInformation().getIncrementBy()), "Increment by incorrect");
            }
            if (this.getGenerationType() != null && column.getAutoIncrementInformation().getGenerationType() != null) {
                result.assertCorrect(this.getGenerationType().equals(column.getAutoIncrementInformation().getGenerationType()), "Generation type is incorrect");
            }
            if (this.getDefaultOnNull() != null && column.getAutoIncrementInformation().getDefaultOnNull() != null) {
                result.assertCorrect(this.getDefaultOnNull().equals(column.getAutoIncrementInformation().getDefaultOnNull()), "Default on null is incorrect");
            }
            return result;
        }
        catch (DatabaseException | InvalidExampleException e) {
            return result.unknown(e);
        }
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

