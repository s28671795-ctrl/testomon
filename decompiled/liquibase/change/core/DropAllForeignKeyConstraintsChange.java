/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import liquibase.CatalogAndSchema;
import liquibase.change.AbstractChange;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.database.Database;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Table;

@DatabaseChange(name="dropAllForeignKeyConstraints", description="Drops all foreign key constraints on a table", priority=1, appliesTo={"table"})
public class DropAllForeignKeyConstraintsChange
extends AbstractChange {
    private String baseTableCatalogName;
    private String baseTableSchemaName;
    private String baseTableName;

    @DatabaseChangeProperty(mustEqualExisting="table.catalog", description="Name of the database catalog of the base table", since="3.0")
    public String getBaseTableCatalogName() {
        return this.baseTableCatalogName;
    }

    public void setBaseTableCatalogName(String baseTableCatalogName) {
        this.baseTableCatalogName = baseTableCatalogName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table.schema", description="Name of the database schema of the base table")
    public String getBaseTableSchemaName() {
        return this.baseTableSchemaName;
    }

    public void setBaseTableSchemaName(String baseTableSchemaName) {
        this.baseTableSchemaName = baseTableSchemaName;
    }

    @DatabaseChangeProperty(mustEqualExisting="table", requiredForDatabase={"all"}, description="Name of the table containing columns constrained by foreign keys")
    public String getBaseTableName() {
        return this.baseTableName;
    }

    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        ArrayList<SqlStatement> sqlStatements = new ArrayList<SqlStatement>();
        List<DropForeignKeyConstraintChange> childDropChanges = this.generateChildren(database);
        for (DropForeignKeyConstraintChange change : childDropChanges) {
            sqlStatements.addAll(Arrays.asList(change.generateStatements(database)));
        }
        return sqlStatements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    public String getConfirmationMessage() {
        return "Foreign keys on base table " + this.getBaseTableName() + " dropped";
    }

    private List<DropForeignKeyConstraintChange> generateChildren(Database database) {
        ArrayList<DropForeignKeyConstraintChange> childDropChanges = new ArrayList<DropForeignKeyConstraintChange>();
        try {
            CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.getBaseTableCatalogName(), this.getBaseTableSchemaName());
            catalogAndSchema = catalogAndSchema.standardize(database);
            Table target = SnapshotGeneratorFactory.getInstance().createSnapshot(new Table(catalogAndSchema.getCatalogName(), catalogAndSchema.getSchemaName(), database.correctObjectName(this.getBaseTableName(), Table.class)), database, new SnapshotControl(database, Table.class, ForeignKey.class));
            List<ForeignKey> results = target == null ? null : target.getOutgoingForeignKeys();
            HashSet<String> handledConstraints = new HashSet<String>();
            if (results != null && !results.isEmpty()) {
                for (ForeignKey fk : results) {
                    Table baseTable = fk.getForeignKeyTable();
                    String constraintName = fk.getName();
                    if (DatabaseObjectComparatorFactory.getInstance().isSameObject(baseTable, target, null, database)) {
                        if (handledConstraints.contains(constraintName)) continue;
                        DropForeignKeyConstraintChange dropForeignKeyConstraintChange = new DropForeignKeyConstraintChange();
                        dropForeignKeyConstraintChange.setBaseTableSchemaName(this.getBaseTableSchemaName());
                        dropForeignKeyConstraintChange.setBaseTableName(this.baseTableName);
                        dropForeignKeyConstraintChange.setConstraintName(constraintName);
                        childDropChanges.add(dropForeignKeyConstraintChange);
                        handledConstraints.add(constraintName);
                        continue;
                    }
                    throw new UnexpectedLiquibaseException("Expected to return only foreign keys for base table name: " + this.getBaseTableName() + " and got results for table: " + this.baseTableName);
                }
            }
            return childDropChanges;
        }
        catch (DatabaseException | InvalidExampleException e) {
            throw new UnexpectedLiquibaseException("Failed to find foreign keys for table: " + this.getBaseTableName(), e);
        }
    }

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return true;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

