/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.DatabaseObjectFactory;
import liquibase.util.StringUtil;

public class CompareControl {
    public static CompareControl STANDARD = new CompareControl();
    private final SchemaComparison[] schemaComparisons;
    private Set<Class<? extends DatabaseObject>> compareTypes = new HashSet<Class<? extends DatabaseObject>>();
    private final Map<Class<? extends DatabaseObject>, Set<String>> suppressedFields = new HashMap<Class<? extends DatabaseObject>, Set<String>>();

    public CompareControl() {
        this(null);
    }

    public CompareControl(Set<Class<? extends DatabaseObject>> compareTypes) {
        this.schemaComparisons = new SchemaComparison[]{new SchemaComparison(new CatalogAndSchema(null, null), new CatalogAndSchema(null, null))};
        this.setTypes(compareTypes);
    }

    public CompareControl(SchemaComparison[] schemaComparison, Set<Class<? extends DatabaseObject>> compareTypes) {
        this.schemaComparisons = schemaComparison;
        this.setTypes(compareTypes);
    }

    public CompareControl(SchemaComparison[] schemaComparison, String compareTypes) {
        this.schemaComparisons = schemaComparison != null && schemaComparison.length > 0 ? schemaComparison : new SchemaComparison[]{new SchemaComparison(new CatalogAndSchema(null, null), new CatalogAndSchema(null, null))};
        this.setTypes(DatabaseObjectFactory.getInstance().parseTypes(compareTypes));
    }

    public CompareControl(String[] referenceVsComparisonSchemas, Set<Class<? extends DatabaseObject>> compareTypes) {
        String[] splitReferenceSchemas = referenceVsComparisonSchemas[0].split(",");
        String[] splitComparisonSchemas = referenceVsComparisonSchemas[1].split(",");
        this.schemaComparisons = new SchemaComparison[splitReferenceSchemas.length];
        for (int i = 0; i < splitReferenceSchemas.length; ++i) {
            String referenceCatalogName = null;
            String referenceSchemaName = splitReferenceSchemas[i];
            if (referenceSchemaName.contains(".")) {
                referenceCatalogName = referenceSchemaName.split("\\.", 2)[0];
                referenceSchemaName = referenceSchemaName.split("\\.", 2)[1];
            }
            String comparisonCatalogName = null;
            String comparisonSchemaName = splitComparisonSchemas[i];
            if (comparisonSchemaName.contains(".")) {
                comparisonCatalogName = comparisonSchemaName.split("\\.", 2)[0];
                comparisonSchemaName = comparisonSchemaName.split("\\.", 2)[1];
            }
            CatalogAndSchema referenceSchema = new CatalogAndSchema(referenceCatalogName, referenceSchemaName);
            CatalogAndSchema comparisonSchema = new CatalogAndSchema(comparisonCatalogName, comparisonSchemaName);
            this.schemaComparisons[i] = new SchemaComparison(referenceSchema, comparisonSchema);
            this.setTypes(compareTypes);
        }
    }

    public static ComputedSchemas computeSchemas(String schemaNames, String referenceSchemaNames, String outputSchemaNames, String defaultCatalogName, String defaultSchemaName, String referenceDefaultCatalogName, String referenceDefaultSchemaName, Database database) {
        if (schemaNames != null || referenceSchemaNames != null) {
            if (schemaNames == null && referenceSchemaNames != null) {
                schemaNames = referenceSchemaNames;
            } else if (schemaNames != null && referenceSchemaNames == null) {
                referenceSchemaNames = schemaNames;
            }
        }
        if (schemaNames == null && outputSchemaNames != null) {
            schemaNames = defaultSchemaName == null ? database.getDefaultSchemaName() : defaultSchemaName;
            referenceSchemaNames = schemaNames;
        }
        ComputedSchemas returnObj = new ComputedSchemas();
        if (referenceSchemaNames == null) {
            returnObj.finalSchemaComparisons = new SchemaComparison[]{new SchemaComparison(new CatalogAndSchema(referenceDefaultCatalogName, referenceDefaultSchemaName), new CatalogAndSchema(defaultCatalogName, defaultSchemaName))};
            returnObj.finalTargetSchemas = new CatalogAndSchema[]{new CatalogAndSchema(defaultCatalogName, defaultSchemaName)};
        } else {
            ArrayList<SchemaComparison> schemaComparisons = new ArrayList<SchemaComparison>();
            ArrayList<CatalogAndSchema> referenceSchemas = new ArrayList<CatalogAndSchema>();
            ArrayList<CatalogAndSchema> targetSchemas = new ArrayList<CatalogAndSchema>();
            List<String> splitReferenceSchemaNames = StringUtil.splitAndTrim(referenceSchemaNames, ",");
            List<String> splitSchemaNames = StringUtil.splitAndTrim(schemaNames, ",");
            List<String> splitOutputSchemaNames = StringUtil.splitAndTrim(StringUtil.trimToNull(outputSchemaNames), ",");
            if (splitReferenceSchemaNames.size() != splitSchemaNames.size()) {
                throw new UnexpectedLiquibaseException("You must specify the same number of schemas in --schemas and --referenceSchemas");
            }
            if (splitOutputSchemaNames != null && splitOutputSchemaNames.size() != splitSchemaNames.size()) {
                throw new UnexpectedLiquibaseException("You must specify the same number of schemas in --schemas and --outputSchemasAs");
            }
            for (int i = 0; i < splitReferenceSchemaNames.size(); ++i) {
                String referenceSchema = splitReferenceSchemaNames.get(i);
                String targetSchema = splitSchemaNames.get(i);
                String outputSchema = null;
                if (splitOutputSchemaNames != null) {
                    outputSchema = splitOutputSchemaNames.get(i);
                }
                CatalogAndSchema correctedTargetSchema = new CatalogAndSchema(null, targetSchema).customize(database);
                CatalogAndSchema correctedReferenceSchema = new CatalogAndSchema(null, referenceSchema).customize(database);
                SchemaComparison comparison = new SchemaComparison(correctedReferenceSchema, correctedTargetSchema);
                comparison.setOutputSchemaAs(outputSchema);
                schemaComparisons.add(comparison);
                referenceSchemas.add(correctedReferenceSchema);
                targetSchemas.add(correctedTargetSchema);
            }
            returnObj.finalSchemaComparisons = schemaComparisons.toArray(new SchemaComparison[0]);
            returnObj.finalTargetSchemas = targetSchemas.toArray(new CatalogAndSchema[0]);
        }
        return returnObj;
    }

    protected void setTypes(Set<Class<? extends DatabaseObject>> types) {
        if (types == null || types.isEmpty()) {
            types = DatabaseObjectFactory.getInstance().getStandardTypes();
        }
        this.compareTypes = types;
    }

    public Set<Class<? extends DatabaseObject>> getComparedTypes() {
        return this.compareTypes;
    }

    public CompareControl addSuppressedField(Class<? extends DatabaseObject> type, String field2) {
        if (!this.suppressedFields.containsKey(type)) {
            this.suppressedFields.put(type, new HashSet());
        }
        this.suppressedFields.get(type).add(field2);
        return this;
    }

    public boolean isSuppressedField(Class<? extends DatabaseObject> type, String field2) {
        if (!this.suppressedFields.containsKey(type)) {
            return false;
        }
        return this.suppressedFields.get(type).contains(field2);
    }

    public SchemaComparison[] getSchemaComparisons() {
        return this.schemaComparisons;
    }

    public CatalogAndSchema[] getSchemas(DatabaseRole databaseRole) {
        CatalogAndSchema[] schemas = new CatalogAndSchema[this.schemaComparisons.length];
        for (int i = 0; i < this.schemaComparisons.length; ++i) {
            if (databaseRole.equals((Object)DatabaseRole.COMPARISON)) {
                schemas[i] = this.schemaComparisons[i].getComparisonSchema();
                continue;
            }
            if (databaseRole.equals((Object)DatabaseRole.REFERENCE)) {
                schemas[i] = this.schemaComparisons[i].getReferenceSchema();
                continue;
            }
            throw new UnexpectedLiquibaseException("Unknown diff type: " + (Object)((Object)databaseRole));
        }
        return schemas;
    }

    public static class SchemaComparison {
        private final CatalogAndSchema comparisonSchema;
        private final CatalogAndSchema referenceSchema;
        private String outputSchemaAs;

        public SchemaComparison(CatalogAndSchema reference, CatalogAndSchema comparison) {
            this.referenceSchema = reference;
            this.comparisonSchema = comparison;
        }

        public static String convertSchema(String schemaName, SchemaComparison[] schemaComparisons) {
            if (schemaComparisons == null || schemaComparisons.length == 0 || schemaName == null) {
                return schemaName;
            }
            String convertedSchemaName = null;
            for (SchemaComparison comparison : schemaComparisons) {
                if (schemaName.equals(comparison.getComparisonSchema().getSchemaName())) {
                    convertedSchemaName = comparison.getReferenceSchema().getSchemaName();
                    continue;
                }
                if (schemaName.equals(comparison.getComparisonSchema().getCatalogName())) {
                    convertedSchemaName = comparison.getReferenceSchema().getCatalogName();
                    continue;
                }
                if (schemaName.equals(comparison.getReferenceSchema().getSchemaName())) {
                    convertedSchemaName = comparison.getComparisonSchema().getSchemaName();
                    continue;
                }
                if (!schemaName.equals(comparison.getReferenceSchema().getCatalogName())) continue;
                convertedSchemaName = comparison.getComparisonSchema().getCatalogName();
            }
            if (convertedSchemaName == null) {
                return schemaName;
            }
            return convertedSchemaName;
        }

        public CatalogAndSchema getComparisonSchema() {
            return this.comparisonSchema;
        }

        public CatalogAndSchema getReferenceSchema() {
            return this.referenceSchema;
        }

        public String getOutputSchemaAs() {
            return this.outputSchemaAs;
        }

        public void setOutputSchemaAs(String outputSchemaAs) {
            this.outputSchemaAs = outputSchemaAs;
        }
    }

    public static class ComputedSchemas {
        public SchemaComparison[] finalSchemaComparisons;
        public CatalogAndSchema[] finalTargetSchemas;
    }

    public static enum DatabaseRole {
        REFERENCE,
        COMPARISON;

    }
}

