/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.util.Locale;
import liquibase.GlobalConfiguration;
import liquibase.database.Database;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class CatalogAndSchema {
    public static final CatalogAndSchema DEFAULT = new CatalogAndSchema(null, null);
    private final String catalogName;
    private final String schemaName;

    public CatalogAndSchema(String catalogName, String schemaName) {
        this.catalogName = catalogName;
        this.schemaName = schemaName;
    }

    public boolean equals(CatalogAndSchema catalogAndSchema, Database accordingTo) {
        if (!accordingTo.supports(Catalog.class)) {
            return true;
        }
        CatalogAndSchema workCatalogAndSchema = catalogAndSchema.customize(accordingTo);
        CatalogAndSchema thisCatalogAndSchema = this.customize(accordingTo);
        boolean catalogMatches = workCatalogAndSchema.getCatalogName() == null ? thisCatalogAndSchema.getCatalogName() == null : this.equals(accordingTo, workCatalogAndSchema.getCatalogName(), thisCatalogAndSchema.getCatalogName());
        if (!catalogMatches) {
            return false;
        }
        if (accordingTo.supports(Schema.class)) {
            if (workCatalogAndSchema.getSchemaName() == null) {
                return thisCatalogAndSchema.getSchemaName() == null;
            }
            return this.equals(accordingTo, workCatalogAndSchema.getSchemaName(), thisCatalogAndSchema.getSchemaName());
        }
        return true;
    }

    public CatalogAndSchema standardize(Database accordingTo) {
        String workCatalogName = StringUtil.trimToNull(this.getCatalogName());
        String workSchemaName = StringUtil.trimToNull(this.getSchemaName());
        if (!accordingTo.supports(Catalog.class)) {
            return new CatalogAndSchema(null, null);
        }
        if (accordingTo.supports(Schema.class)) {
            if (workSchemaName != null && workSchemaName.equalsIgnoreCase(accordingTo.getDefaultSchemaName())) {
                workSchemaName = null;
            }
        } else {
            if (workCatalogName == null && workSchemaName != null) {
                workCatalogName = workSchemaName;
            }
            workSchemaName = workCatalogName;
        }
        if (workCatalogName != null && this.equals(accordingTo, workCatalogName, accordingTo.getDefaultCatalogName())) {
            workCatalogName = null;
        }
        if (workSchemaName != null && this.equals(accordingTo, workSchemaName, accordingTo.getDefaultSchemaName())) {
            workSchemaName = null;
        }
        if (!accordingTo.supports(Schema.class) && workCatalogName != null && workSchemaName != null && !workCatalogName.equals(workSchemaName)) {
            workSchemaName = null;
        }
        boolean preserveSchemaCase = Boolean.TRUE.equals(GlobalConfiguration.PRESERVE_SCHEMA_CASE.getCurrentValue());
        if (CatalogAndSchemaCase.LOWER_CASE.equals((Object)accordingTo.getSchemaAndCatalogCase())) {
            if (workCatalogName != null && !preserveSchemaCase) {
                workCatalogName = workCatalogName.toLowerCase(Locale.US);
            }
            if (workSchemaName != null && !preserveSchemaCase) {
                workSchemaName = workSchemaName.toLowerCase(Locale.US);
            }
        } else if (CatalogAndSchemaCase.UPPER_CASE.equals((Object)accordingTo.getSchemaAndCatalogCase()) && !accordingTo.isCaseSensitive()) {
            if (workCatalogName != null && !preserveSchemaCase) {
                workCatalogName = workCatalogName.toUpperCase(Locale.US);
            }
            if (workSchemaName != null && !preserveSchemaCase) {
                workSchemaName = workSchemaName.toUpperCase(Locale.US);
            }
        }
        return new CatalogAndSchema(workCatalogName, workSchemaName);
    }

    public CatalogAndSchema customize(Database accordingTo) {
        CatalogAndSchema standard = this.standardize(accordingTo);
        String workCatalogName = standard.getCatalogName();
        String workSchemaName = standard.getSchemaName();
        if (workCatalogName == null) {
            if (!accordingTo.supports(Schema.class) && workSchemaName != null) {
                return new CatalogAndSchema(accordingTo.correctObjectName(workSchemaName, Catalog.class), null);
            }
            workCatalogName = accordingTo.getDefaultCatalogName();
        }
        if (workSchemaName == null) {
            workSchemaName = accordingTo.getDefaultSchemaName();
        }
        if (workCatalogName != null) {
            workCatalogName = accordingTo.correctObjectName(workCatalogName, Catalog.class);
        }
        if (workSchemaName != null) {
            workSchemaName = accordingTo.correctObjectName(workSchemaName, Schema.class);
        }
        return new CatalogAndSchema(workCatalogName, workSchemaName);
    }

    public String toString() {
        String tmpCatalogName = this.getCatalogName();
        String tmpSchemaName = this.getSchemaName();
        if (tmpCatalogName == null) {
            tmpCatalogName = "DEFAULT";
        }
        if (tmpSchemaName == null) {
            tmpSchemaName = "DEFAULT";
        }
        return tmpCatalogName + "." + tmpSchemaName;
    }

    private boolean equals(Database database, String value1, String value2) {
        CatalogAndSchemaCase schemaAndCatalogCase = database.getSchemaAndCatalogCase();
        if (CatalogAndSchemaCase.UPPER_CASE.equals((Object)schemaAndCatalogCase) || CatalogAndSchemaCase.LOWER_CASE.equals((Object)schemaAndCatalogCase)) {
            return value1.equalsIgnoreCase(value2);
        }
        return value1.equals(value2);
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public static enum CatalogAndSchemaCase {
        LOWER_CASE,
        UPPER_CASE,
        ORIGINAL_CASE;

    }
}

