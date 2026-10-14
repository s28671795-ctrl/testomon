/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog.core;

import java.util.ArrayList;
import liquibase.CatalogAndSchema;
import liquibase.change.Change;
import liquibase.change.core.AddPrimaryKeyChange;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class MissingPrimaryKeyChangeGenerator
extends AbstractChangeGenerator
implements MissingObjectChangeGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (PrimaryKey.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] runAfterTypes() {
        return new Class[]{Table.class, Column.class};
    }

    @Override
    public Class<? extends DatabaseObject>[] runBeforeTypes() {
        return new Class[]{Index.class};
    }

    @Override
    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl control, Database referenceDatabase, Database comparisonDatabase, ChangeGeneratorChain chain) {
        Index backingIndex;
        ArrayList<Change> returnList = new ArrayList<Change>();
        PrimaryKey pk = (PrimaryKey)missingObject;
        AddPrimaryKeyChange change = this.createAddPrimaryKeyChange();
        change.setTableName(pk.getTable().getName());
        if (control.getIncludeCatalog()) {
            change.setCatalogName(pk.getTable().getSchema().getCatalogName());
        }
        if (control.getIncludeSchema()) {
            change.setSchemaName(pk.getTable().getSchema().getName());
        }
        change.setConstraintName(pk.getName());
        change.setColumnNames(pk.getColumnNames());
        if (control.getIncludeTablespace()) {
            change.setTablespace(pk.getTablespace());
        }
        if (referenceDatabase instanceof MSSQLDatabase && pk.getBackingIndex() != null && pk.getBackingIndex().getClustered() != null && !pk.getBackingIndex().getClustered().booleanValue()) {
            change.setClustered(false);
        }
        if (referenceDatabase instanceof PostgresDatabase && pk.getBackingIndex() != null && pk.getBackingIndex().getClustered() != null && pk.getBackingIndex().getClustered().booleanValue()) {
            change.setClustered(true);
        }
        if ((comparisonDatabase instanceof OracleDatabase || comparisonDatabase instanceof AbstractDb2Database && pk.getBackingIndex() != null && !comparisonDatabase.isSystemObject(pk.getBackingIndex())) && (backingIndex = pk.getBackingIndex()) != null && backingIndex.getName() != null) {
            Object schema;
            Schema originalRelationSchema = backingIndex.getRelation().getSchema();
            try {
                Change[] fixes;
                if (!control.getIncludeCatalog() && !control.getIncludeSchema()) {
                    schema = comparisonDatabase.getDefaultSchema().customize(comparisonDatabase);
                    backingIndex.getRelation().setSchema(((CatalogAndSchema)schema).getCatalogName(), ((CatalogAndSchema)schema).getSchemaName());
                }
                if ((referenceDatabase.equals(comparisonDatabase) || !SnapshotGeneratorFactory.getInstance().has(backingIndex, comparisonDatabase)) && (fixes = ChangeGeneratorFactory.getInstance().fixMissing(backingIndex, control, referenceDatabase, comparisonDatabase)) != null) {
                    for (Change fix : fixes) {
                        if (fix == null) continue;
                        returnList.add(fix);
                    }
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
            change.setForIndexName(backingIndex.getName());
            schema = backingIndex.getSchema();
            if (schema != null) {
                if (control.getIncludeCatalog()) {
                    change.setForIndexCatalogName(((Schema)schema).getCatalogName());
                }
                if (control.getIncludeSchema()) {
                    change.setForIndexSchemaName(((Schema)schema).getName());
                }
            }
            backingIndex.getRelation().setSchema(originalRelationSchema);
        }
        control.setAlreadyHandledMissing(pk.getBackingIndex());
        returnList.add(change);
        return returnList.toArray(EMPTY_CHANGE);
    }

    protected AddPrimaryKeyChange createAddPrimaryKeyChange() {
        return new AddPrimaryKeyChange();
    }
}

