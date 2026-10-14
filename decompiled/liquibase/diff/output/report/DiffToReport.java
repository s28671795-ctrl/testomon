/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.report;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import liquibase.database.Database;
import liquibase.diff.DiffResult;
import liquibase.diff.Difference;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.StringDiff;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectCollectionComparator;
import liquibase.exception.DatabaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class DiffToReport {
    protected DiffResult diffResult;
    private final PrintStream out;
    private final StringUtil.StringUtilFormatter formatter;

    public DiffToReport(DiffResult diffResult, PrintStream out) {
        this.diffResult = diffResult;
        this.out = out;
        this.formatter = this.createFormatter();
    }

    public void print() throws DatabaseException {
        DatabaseObjectCollectionComparator comparator = new DatabaseObjectCollectionComparator();
        this.out.println("Reference Database: " + this.diffResult.getReferenceSnapshot().getDatabase());
        this.out.println("Comparison Database: " + this.diffResult.getComparisonSnapshot().getDatabase());
        CompareControl.SchemaComparison[] schemas = this.diffResult.getCompareControl().getSchemaComparisons();
        if (schemas != null && schemas.length > 0) {
            this.out.println("Compared Schemas: " + StringUtil.join(Arrays.asList(schemas), ", ", obj -> {
                String comparisonName;
                String referenceName;
                Database referenceDatabase = this.diffResult.getReferenceSnapshot().getDatabase();
                Database comparisonDatabase = this.diffResult.getComparisonSnapshot().getDatabase();
                if (referenceDatabase.supports(Schema.class)) {
                    referenceName = obj.getReferenceSchema().getSchemaName();
                    if (referenceName == null) {
                        referenceName = referenceDatabase.getDefaultSchemaName();
                    }
                } else if (referenceDatabase.supports(Catalog.class)) {
                    referenceName = obj.getReferenceSchema().getCatalogName();
                    if (referenceName == null) {
                        referenceName = referenceDatabase.getDefaultCatalogName();
                    }
                } else {
                    return "";
                }
                if (comparisonDatabase.supports(Schema.class)) {
                    comparisonName = obj.getComparisonSchema().getSchemaName();
                    if (comparisonName == null) {
                        comparisonName = comparisonDatabase.getDefaultSchemaName();
                    }
                } else if (comparisonDatabase.supports(Catalog.class)) {
                    comparisonName = obj.getComparisonSchema().getCatalogName();
                    if (comparisonName == null) {
                        comparisonName = comparisonDatabase.getDefaultCatalogName();
                    }
                } else {
                    return "";
                }
                if (referenceName == null) {
                    referenceName = StringUtil.trimToEmpty(referenceDatabase.getDefaultSchemaName());
                }
                if (comparisonName == null) {
                    comparisonName = StringUtil.trimToEmpty(comparisonDatabase.getDefaultSchemaName());
                }
                if (referenceName.equalsIgnoreCase(comparisonName)) {
                    return referenceName;
                }
                return referenceName + " -> " + comparisonName;
            }, true));
        }
        this.printComparison("Product Name", this.diffResult.getProductNameDiff(), this.out);
        this.printComparison("Product Version", this.diffResult.getProductVersionDiff(), this.out);
        TreeSet<Class> types = new TreeSet<Class>(Comparator.comparing(Class::getSimpleName));
        types.addAll(this.diffResult.getCompareControl().getComparedTypes());
        for (Class type : types) {
            if (type.equals(Schema.class) && !this.diffResult.getComparisonSnapshot().getDatabase().supports(Schema.class)) continue;
            this.printSetComparison("Missing " + this.getTypeName(type), this.diffResult.getMissingObjects(type, comparator), this.out);
            this.printSetComparison("Unexpected " + this.getTypeName(type), this.diffResult.getUnexpectedObjects(type, comparator), this.out);
            this.printChangedComparison("Changed " + this.getTypeName(type), this.diffResult.getChangedObjects(type, comparator), this.out);
        }
    }

    protected String getTypeName(Class<? extends DatabaseObject> type) {
        return type.getSimpleName().replaceAll("([A-Z])", " $1").trim() + "(s)";
    }

    protected boolean getIncludeSchema() {
        return this.diffResult.getCompareControl().getSchemaComparisons().length > 1;
    }

    protected void printChangedComparison(String title, Map<? extends DatabaseObject, ObjectDifferences> objects, PrintStream out) {
        out.print(title + ": ");
        if (objects.isEmpty()) {
            out.println("NONE");
        } else {
            out.println();
            for (Map.Entry<? extends DatabaseObject, ObjectDifferences> object : objects.entrySet()) {
                if (!object.getValue().hasDifferences()) continue;
                out.println("     " + object.getKey());
                for (Difference difference : object.getValue().getDifferences()) {
                    out.println("          " + difference.toString());
                }
            }
        }
    }

    protected void printSetComparison(String title, Set<? extends DatabaseObject> objects, PrintStream out) {
        out.print(title + ": ");
        Schema lastSchema = null;
        if (objects.isEmpty()) {
            out.println("NONE");
        } else {
            out.println();
            for (DatabaseObject databaseObject : objects) {
                if (!this.diffResult.getReferenceSnapshot().getSnapshotControl().shouldInclude(databaseObject)) continue;
                if (this.getIncludeSchema() && databaseObject.getSchema() != null && (lastSchema == null || !lastSchema.equals(databaseObject.getSchema()))) {
                    lastSchema = databaseObject.getSchema();
                    String schemaName = databaseObject.getSchema().getName();
                    if (schemaName == null) {
                        schemaName = databaseObject.getSchema().getCatalogName();
                    }
                    schemaName = this.includeSchemaComparison(schemaName);
                    out.println("  SCHEMA: " + schemaName);
                }
                out.println("     " + databaseObject);
            }
        }
    }

    protected String includeSchemaComparison(String schemaName) {
        String convertedSchemaName = CompareControl.SchemaComparison.convertSchema(schemaName, this.diffResult.getCompareControl().getSchemaComparisons());
        if (convertedSchemaName != null && !convertedSchemaName.equals(schemaName)) {
            schemaName = schemaName + " -> " + convertedSchemaName;
        }
        return schemaName;
    }

    protected void printComparison(String title, StringDiff string, PrintStream out) {
        out.print(title + ":");
        if (string == null) {
            out.print("NULL");
            return;
        }
        if (string.areEqual()) {
            out.println(" EQUAL");
        } else {
            String referenceVersion = string.getReferenceVersion();
            referenceVersion = referenceVersion == null ? "NULL" : "'" + referenceVersion + "'";
            String targetVersion = string.getTargetVersion();
            targetVersion = targetVersion == null ? "NULL" : "'" + targetVersion + "'";
            out.println();
            out.println("     Reference:   " + referenceVersion);
            out.println("     Target: " + targetVersion);
        }
    }

    public StringUtil.StringUtilFormatter createFormatter() {
        return obj -> {
            String comparisonName;
            String referenceName;
            Database referenceDatabase = this.diffResult.getReferenceSnapshot().getDatabase();
            Database comparisonDatabase = this.diffResult.getComparisonSnapshot().getDatabase();
            if (referenceDatabase.supports(Schema.class)) {
                referenceName = obj.getReferenceSchema().getSchemaName();
                if (referenceName == null) {
                    referenceName = referenceDatabase.getDefaultSchemaName();
                }
            } else if (referenceDatabase.supports(Catalog.class)) {
                referenceName = obj.getReferenceSchema().getCatalogName();
                if (referenceName == null) {
                    referenceName = referenceDatabase.getDefaultCatalogName();
                }
            } else {
                return "";
            }
            if (comparisonDatabase.supports(Schema.class)) {
                comparisonName = obj.getComparisonSchema().getSchemaName();
                if (comparisonName == null) {
                    comparisonName = comparisonDatabase.getDefaultSchemaName();
                }
            } else if (comparisonDatabase.supports(Catalog.class)) {
                comparisonName = obj.getComparisonSchema().getCatalogName();
                if (comparisonName == null) {
                    comparisonName = comparisonDatabase.getDefaultCatalogName();
                }
            } else {
                return "";
            }
            if (referenceName == null) {
                referenceName = StringUtil.trimToEmpty(referenceDatabase.getDefaultSchemaName());
            }
            if (comparisonName == null) {
                comparisonName = StringUtil.trimToEmpty(comparisonDatabase.getDefaultSchemaName());
            }
            if (referenceName.equalsIgnoreCase(comparisonName)) {
                return referenceName;
            }
            return referenceName + " -> " + comparisonName;
        };
    }
}

