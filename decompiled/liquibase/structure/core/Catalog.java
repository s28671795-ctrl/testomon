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
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;

public class Catalog
extends AbstractDatabaseObject {
    public Catalog() {
        this.setAttribute("objects", new ConcurrentHashMap());
    }

    public Catalog(String name) {
        this();
        this.setAttribute("name", name);
    }

    @Override
    public String toString() {
        String name = this.getName();
        if (name == null) {
            return "DEFAULT";
        }
        return name;
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return null;
    }

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    @Override
    public Catalog setName(String name) {
        this.setAttribute("name", name);
        return this;
    }

    public boolean isDefault() {
        return this.getAttribute("default", false) != false || this.getName() == null;
    }

    public Catalog setDefault(Boolean isDefault) {
        this.setAttribute("default", isDefault);
        return this;
    }

    protected Map<Class<? extends DatabaseObject>, Set<DatabaseObject>> getObjects() {
        return (Map)((Object)this.getAttribute("objects", Map.class));
    }

    public <DatabaseObjectType extends DatabaseObject> List<DatabaseObjectType> getDatabaseObjects(Class<DatabaseObjectType> type) {
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Catalog catalog = (Catalog)o;
        return this.getName() != null ? this.getName().equalsIgnoreCase(catalog.getName()) : catalog.getName() == null;
    }

    public int hashCode() {
        return this.getName() != null ? this.getName().hashCode() : 0;
    }

    @Override
    public Set<String> getSerializableFields() {
        Set<String> fields = super.getSerializableFields();
        fields.remove("objects");
        return fields;
    }
}

