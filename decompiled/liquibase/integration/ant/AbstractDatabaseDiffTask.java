/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.BuildException
 *  org.apache.tools.ant.types.Reference
 */
package liquibase.integration.ant;

import liquibase.CatalogAndSchema;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.exception.LiquibaseException;
import liquibase.integration.ant.BaseLiquibaseTask;
import liquibase.integration.ant.type.DatabaseType;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Reference;

public abstract class AbstractDatabaseDiffTask
extends BaseLiquibaseTask {
    private DatabaseType referenceDatabaseType;
    private String diffTypes;

    protected DiffResult getDiffResult() {
        DatabaseSnapshot referenceSnapshot;
        Liquibase liquibase = this.getLiquibase();
        Database targetDatabase = liquibase.getDatabase();
        Database referenceDatabase = this.createDatabaseFromType(this.referenceDatabaseType, this.getResourceAccessor());
        CatalogAndSchema targetCatalogAndSchema = this.buildCatalogAndSchema(targetDatabase);
        CatalogAndSchema referenceCatalogAndSchema = this.buildCatalogAndSchema(referenceDatabase);
        CompareControl.SchemaComparison[] schemaComparisons = new CompareControl.SchemaComparison[]{new CompareControl.SchemaComparison(referenceCatalogAndSchema, targetCatalogAndSchema)};
        SnapshotGeneratorFactory snapshotGeneratorFactory = SnapshotGeneratorFactory.getInstance();
        try {
            referenceSnapshot = snapshotGeneratorFactory.createSnapshot(referenceDatabase.getDefaultSchema(), referenceDatabase, new SnapshotControl(referenceDatabase, this.diffTypes));
        }
        catch (LiquibaseException e) {
            throw new BuildException("Unable to create a DatabaseSnapshot: " + e.getMessage(), (Throwable)e);
        }
        CompareControl compareControl = new CompareControl(schemaComparisons, referenceSnapshot.getSnapshotControl().getTypesToInclude());
        try {
            return liquibase.diff(referenceDatabase, targetDatabase, compareControl);
        }
        catch (LiquibaseException e) {
            throw new BuildException("Unable to diff databases: " + e.getMessage(), (Throwable)e);
        }
    }

    @Override
    protected void validateParameters() {
        super.validateParameters();
        if (this.referenceDatabaseType == null) {
            throw new BuildException("Reference database element or reference required.");
        }
    }

    private CatalogAndSchema buildCatalogAndSchema(Database database) {
        return new CatalogAndSchema(database.getDefaultCatalogName(), database.getDefaultSchemaName());
    }

    public void addReferenceDatabase(DatabaseType referenceDatabase) {
        if (this.referenceDatabaseType != null) {
            throw new BuildException("Only one <referenceDatabase> element is allowed.");
        }
        this.referenceDatabaseType = referenceDatabase;
    }

    public void setReferenceDatabaseRef(Reference referenceDatabaseRef) {
        this.referenceDatabaseType = new DatabaseType(this.getProject());
        this.referenceDatabaseType.setRefid(referenceDatabaseRef);
    }

    public String getDiffTypes() {
        return this.diffTypes;
    }

    public void setDiffTypes(String diffTypes) {
        this.diffTypes = diffTypes;
    }
}

