/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.CatalogAndSchema;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.util.StringUtil;

public class Schema
extends AbstractDatabaseObject {
    public Schema() {
        this.setAttribute("objects", new ConcurrentHashMap());
    }

    public Schema(String catalog, String schemaName) {
        this(new Catalog(catalog), schemaName);
    }

    public Schema(Catalog catalog, String schemaName) {
        schemaName = StringUtil.trimToNull(schemaName);
        this.setAttribute("name", schemaName);
        this.setAttribute("catalog", catalog);
        this.setAttribute("objects", new ConcurrentHashMap());
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return null;
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    @Override
    public Schema setName(String name) {
        this.setAttribute("name", name);
        return this;
    }

    public boolean isDefault() {
        return this.getAttribute("default", false) != false || this.getName() == null;
    }

    public Schema setDefault(Boolean isDefault) {
        this.setAttribute("default", isDefault);
        return this;
    }

    @Override
    public Schema getSchema() {
        return this;
    }

    public Catalog getCatalog() {
        return (Catalog)((Object)this.getAttribute("catalog", Catalog.class));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Schema schema = (Schema)o;
        if (this.shouldIncludeCatalogInSpecification() && (this.getCatalog() != null ? !this.getCatalog().equals(schema.getCatalog()) : schema.getCatalog() != null)) {
            return false;
        }
        return this.getName() != null ? this.getName().equalsIgnoreCase(schema.getName()) : schema.getName() == null;
    }

    public int hashCode() {
        int result = this.shouldIncludeCatalogInSpecification() && this.getCatalog() != null ? this.getCatalog().hashCode() : 0;
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        return result;
    }

    public String getCatalogName() {
        if (this.getCatalog() == null) {
            return null;
        }
        return this.getCatalog().getName();
    }

    @Override
    public String toString() {
        String catalogName = this.getCatalogName();
        String schemaName = this.getName();
        if (schemaName == null) {
            schemaName = "DEFAULT";
        }
        if (catalogName == null || catalogName.equals(schemaName)) {
            return schemaName;
        }
        return catalogName + "." + schemaName;
    }

    public CatalogAndSchema toCatalogAndSchema() {
        String catalogName = this.getCatalog() != null && this.getCatalog().isDefault() ? null : this.getCatalogName();
        String schemaName = this.isDefault() ? null : this.getName();
        return new CatalogAndSchema(catalogName, schemaName);
    }

    protected Map<Class<? extends DatabaseObject>, Set<DatabaseObject>> getObjects() {
        return (Map)((Object)this.getAttribute("objects", Map.class));
    }

    public <T extends DatabaseObject> List<T> getDatabaseObjects(Class<T> type) {
        Set<DatabaseObject> databaseObjects = this.getObjects().get(type);
        if (databaseObjects == null) {
            return new ArrayList();
        }
        return new ArrayList<DatabaseObject>(databaseObjects);
    }

    public void addDatabaseObject(DatabaseObject databaseObject) {
        if (databaseObject == null) {
            return;
        }
        Set objects = this.getObjects().computeIfAbsent(databaseObject.getClass(), k -> new HashSet());
        objects.add(databaseObject);
    }

    @Override
    public Set<String> getSerializableFields() {
        Set<String> fields = super.getSerializableFields();
        fields.remove("objects");
        return fields;
    }
}

