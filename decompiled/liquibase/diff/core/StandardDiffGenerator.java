/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.core;

import java.util.Set;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.diff.DiffGenerator;
import liquibase.diff.DiffResult;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.StringDiff;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.logging.mdc.customobjects.DiffResultsSummary;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.EmptyDatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class StandardDiffGenerator
implements DiffGenerator {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean supports(Database referenceDatabase, Database comparisonDatabase) {
        return true;
    }

    @Override
    public DiffResult compare(DatabaseSnapshot referenceSnapshot, DatabaseSnapshot comparisonSnapshot, CompareControl compareControl) throws DatabaseException {
        if (comparisonSnapshot == null) {
            try {
                comparisonSnapshot = new EmptyDatabaseSnapshot(referenceSnapshot.getDatabase());
            }
            catch (InvalidExampleException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        DiffResult diffResult = new DiffResult(referenceSnapshot, comparisonSnapshot, compareControl);
        this.checkVersionInfo(referenceSnapshot, comparisonSnapshot, diffResult);
        Set<Class<? extends DatabaseObject>> typesToCompare = compareControl.getComparedTypes();
        typesToCompare.retainAll(referenceSnapshot.getSnapshotControl().getTypesToInclude());
        typesToCompare.retainAll(comparisonSnapshot.getSnapshotControl().getTypesToInclude());
        for (Class<? extends DatabaseObject> typeToCompare : typesToCompare) {
            this.compareObjectType(typeToCompare, referenceSnapshot, comparisonSnapshot, diffResult);
        }
        Scope.getCurrentScope().addMdcValue("diffResultsSummary", new DiffResultsSummary(diffResult));
        return diffResult;
    }

    protected void checkVersionInfo(DatabaseSnapshot referenceSnapshot, DatabaseSnapshot comparisonSnapshot, DiffResult diffResult) throws DatabaseException {
        if (comparisonSnapshot != null && comparisonSnapshot.getDatabase() != null) {
            diffResult.setProductNameDiff(new StringDiff(referenceSnapshot.getDatabase().getDatabaseProductName(), comparisonSnapshot.getDatabase().getDatabaseProductName()));
            diffResult.setProductVersionDiff(new StringDiff(referenceSnapshot.getDatabase().getDatabaseProductVersion(), comparisonSnapshot.getDatabase().getDatabaseProductVersion()));
        }
    }

    protected <T extends DatabaseObject> void compareObjectType(Class<T> type, DatabaseSnapshot referenceSnapshot, DatabaseSnapshot comparisonSnapshot, DiffResult diffResult) {
        Database comparisonDatabase = comparisonSnapshot.getDatabase();
        Database referenceDatabase = referenceSnapshot.getDatabase();
        CompareControl.SchemaComparison[] schemaComparisons = diffResult.getCompareControl().getSchemaComparisons();
        if (schemaComparisons != null) {
            for (CompareControl.SchemaComparison schemaComparison : schemaComparisons) {
                for (DatabaseObject referenceObject : referenceSnapshot.get(type)) {
                    Schema referenceObjectSchema = referenceObject.getSchema();
                    if (referenceObjectSchema != null && referenceObjectSchema.getName() != null && !StringUtil.trimToEmpty(referenceObjectSchema.toCatalogAndSchema().standardize(referenceDatabase).getSchemaName()).equalsIgnoreCase(StringUtil.trimToEmpty(schemaComparison.getReferenceSchema().standardize(referenceDatabase).getSchemaName()))) continue;
                    DatabaseObject comparisonObject = comparisonSnapshot.get(referenceObject);
                    if (comparisonObject == null) {
                        diffResult.addMissingObject(referenceObject);
                        continue;
                    }
                    ObjectDifferences differences = DatabaseObjectComparatorFactory.getInstance().findDifferences(referenceObject, comparisonObject, comparisonDatabase, diffResult.getCompareControl());
                    if (!differences.hasDifferences()) continue;
                    diffResult.addChangedObject(referenceObject, differences);
                }
                for (DatabaseObject comparisonObject : comparisonSnapshot.get(type)) {
                    Schema comparisonObjectSchema = comparisonObject.getSchema();
                    if (comparisonObjectSchema != null) {
                        String comparisonObjectSchemaName = StringUtil.trimToEmpty(comparisonObjectSchema.toCatalogAndSchema().standardize(comparisonDatabase).getSchemaName());
                        String schemaComparisonName1 = StringUtil.trimToEmpty(schemaComparison.getComparisonSchema().standardize(comparisonDatabase).getSchemaName());
                        String schemaComparisonName2 = StringUtil.trimToEmpty(schemaComparison.getReferenceSchema().standardize(comparisonDatabase).getSchemaName());
                        if ("".equals(comparisonObjectSchemaName) && !"".equals(schemaComparisonName1) && !"".equals(schemaComparisonName2)) {
                            comparisonObjectSchemaName = StringUtil.trimToEmpty(comparisonObjectSchema.getName());
                        }
                        if (!comparisonObjectSchemaName.equalsIgnoreCase(schemaComparisonName1) && !comparisonObjectSchemaName.equals(schemaComparisonName2)) continue;
                    }
                    if (referenceSnapshot.get(comparisonObject) != null) continue;
                    diffResult.addUnexpectedObject(comparisonObject);
                }
            }
        }
    }
}

