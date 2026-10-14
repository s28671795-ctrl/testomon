/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare.core;

import liquibase.CatalogAndSchema;
import liquibase.database.Database;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparator;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public abstract class CommonCatalogSchemaComparator
implements DatabaseObjectComparator {
    protected boolean equalsSchemas(Database accordingTo, String schemaName1, String schemaName2) {
        if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)accordingTo.getSchemaAndCatalogCase())) {
            return StringUtil.trimToEmpty(schemaName1).equals(StringUtil.trimToEmpty(schemaName2));
        }
        return StringUtil.trimToEmpty(schemaName1).equalsIgnoreCase(StringUtil.trimToEmpty(schemaName2));
    }

    protected String getComparisonSchemaOrCatalog(Database accordingTo, CompareControl.SchemaComparison comparison) {
        if (accordingTo.supports(Schema.class)) {
            return comparison.getComparisonSchema().getSchemaName();
        }
        if (accordingTo.supports(Catalog.class)) {
            return comparison.getComparisonSchema().getCatalogName();
        }
        return null;
    }

    protected String getReferenceSchemaOrCatalog(Database accordingTo, CompareControl.SchemaComparison comparison) {
        if (accordingTo.supports(Schema.class)) {
            return comparison.getReferenceSchema().getSchemaName();
        }
        if (accordingTo.supports(Catalog.class)) {
            return comparison.getReferenceSchema().getCatalogName();
        }
        return null;
    }
}

