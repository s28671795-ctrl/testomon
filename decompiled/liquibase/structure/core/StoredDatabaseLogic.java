/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import liquibase.diff.compare.DatabaseObjectCollectionComparator;
import liquibase.license.LicenseServiceUtils;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public abstract class StoredDatabaseLogic<T extends StoredDatabaseLogic>
extends AbstractDatabaseObject {
    @Override
    public DatabaseObject[] getContainingObjects() {
        return new DatabaseObject[]{this.getSchema()};
    }

    @Override
    public boolean snapshotByDefault() {
        return LicenseServiceUtils.isProLicenseValid();
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    public T setName(String name) {
        this.setAttribute("name", name);
        return (T)this;
    }

    @Override
    public Schema getSchema() {
        return (Schema)((Object)this.getAttribute("schema", Schema.class));
    }

    public T setSchema(Schema schema) {
        this.setAttribute("schema", schema);
        return (T)this;
    }

    public Boolean isValid() {
        return (Boolean)((Object)this.getAttribute("valid", Boolean.class));
    }

    public T setValid(Boolean valid) {
        this.setAttribute("valid", valid);
        return (T)this;
    }

    public String getBody() {
        return (String)((Object)this.getAttribute("body", String.class));
    }

    public T setBody(String body2) {
        this.setAttribute("body", body2);
        return (T)this;
    }

    public DatabaseObjectCollectionComparator getDbObjectComparator() {
        return null;
    }

    public boolean equals(Object obj) {
        boolean schemasEqual;
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        StoredDatabaseLogic that = (StoredDatabaseLogic)obj;
        if (this.getSchema() != null && that.getSchema() != null && !(schemasEqual = this.getSchema().equals(that.getSchema()))) {
            return false;
        }
        return this.getName().equalsIgnoreCase(that.getName());
    }

    public int hashCode() {
        return StringUtil.trimToEmpty(this.getName()).toLowerCase().hashCode();
    }
}

