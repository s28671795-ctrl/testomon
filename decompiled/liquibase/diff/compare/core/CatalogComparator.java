/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare.core;

import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparatorChain;
import liquibase.diff.compare.core.CommonCatalogSchemaComparator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class CatalogComparator
extends CommonCatalogSchemaComparator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (Catalog.class.isAssignableFrom(objectType)) {
            return 5;
        }
        return -1;
    }

    @Override
    public String[] hash(DatabaseObject databaseObject, Database accordingTo, DatabaseObjectComparatorChain chain) {
        return null;
    }

    @Override
    public boolean isSameObject(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, DatabaseObjectComparatorChain chain) {
        if (!(databaseObject1 instanceof Catalog) || !(databaseObject2 instanceof Catalog)) {
            return false;
        }
        if (!accordingTo.supports(Catalog.class)) {
            return true;
        }
        boolean shouldIncludeCatalog = GlobalConfiguration.INCLUDE_CATALOG_IN_SPECIFICATION.getCurrentValue();
        String object1Name = !shouldIncludeCatalog && ((Catalog)databaseObject1).isDefault() ? null : databaseObject1.getName();
        String object2Name = !shouldIncludeCatalog && ((Catalog)databaseObject2).isDefault() ? null : databaseObject2.getName();
        CatalogAndSchema thisSchema = new CatalogAndSchema(object1Name, null).standardize(accordingTo);
        CatalogAndSchema otherSchema = new CatalogAndSchema(object2Name, null).standardize(accordingTo);
        if (thisSchema.getCatalogName() == null) {
            return otherSchema.getCatalogName() == null;
        }
        if (this.equalsSchemas(accordingTo, object1Name, object2Name)) {
            return true;
        }
        if (chain.getSchemaComparisons() != null && chain.getSchemaComparisons().length > 0) {
            for (CompareControl.SchemaComparison comparison : chain.getSchemaComparisons()) {
                String comparisonCatalog1 = this.getComparisonSchemaOrCatalog(accordingTo, comparison);
                String comparisonCatalog2 = this.getReferenceSchemaOrCatalog(accordingTo, comparison);
                String finalCatalog1 = thisSchema.getCatalogName();
                String finalCatalog2 = otherSchema.getCatalogName();
                if (comparisonCatalog1 != null && comparisonCatalog1.equalsIgnoreCase(finalCatalog1)) {
                    finalCatalog1 = comparisonCatalog2;
                } else if (comparisonCatalog2 != null && comparisonCatalog2.equalsIgnoreCase(finalCatalog1)) {
                    finalCatalog1 = comparisonCatalog1;
                }
                if (StringUtil.trimToEmpty(finalCatalog1).equalsIgnoreCase(StringUtil.trimToEmpty(finalCatalog2))) {
                    return true;
                }
                if (comparisonCatalog1 != null && comparisonCatalog1.equalsIgnoreCase(finalCatalog2)) {
                    finalCatalog2 = comparisonCatalog2;
                } else if (comparisonCatalog2 != null && comparisonCatalog2.equalsIgnoreCase(finalCatalog2)) {
                    finalCatalog2 = comparisonCatalog1;
                }
                if (!StringUtil.trimToEmpty(finalCatalog1).equalsIgnoreCase(StringUtil.trimToEmpty(finalCatalog2))) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public ObjectDifferences findDifferences(DatabaseObject databaseObject1, DatabaseObject databaseObject2, Database accordingTo, CompareControl compareControl, DatabaseObjectComparatorChain chain, Set<String> exclude) {
        ObjectDifferences differences = new ObjectDifferences(compareControl);
        differences.compare("name", databaseObject1, databaseObject2, new ObjectDifferences.DatabaseObjectNameCompareFunction(Schema.class, accordingTo));
        return differences;
    }
}

