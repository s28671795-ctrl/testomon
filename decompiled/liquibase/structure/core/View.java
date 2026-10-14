/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;

public class View
extends Relation {
    private boolean containsFullDefinition;

    public View() {
    }

    public View(String catalogName, String schemaName, String tableName) {
        this.setSchema(new Schema(catalogName, schemaName));
        this.setName(tableName);
    }

    @Override
    public Relation setSchema(Schema schema) {
        return super.setSchema(schema);
    }

    public String getDefinition() {
        return (String)((Object)this.getAttribute("definition", String.class));
    }

    public void setDefinition(String definition) {
        this.setAttribute("definition", definition);
    }

    public boolean equals(Object o) {
        boolean schemasEqual;
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        View that = (View)o;
        if (this.getSchema() != null && that.getSchema() != null && !(schemasEqual = this.getSchema().equals(that.getSchema()))) {
            return false;
        }
        return this.getName().equalsIgnoreCase(that.getName());
    }

    public int hashCode() {
        return this.getName().toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public View setName(String name) {
        return (View)super.setName(name);
    }

    public boolean getContainsFullDefinition() {
        return this.containsFullDefinition;
    }

    public View setContainsFullDefinition(boolean fullDefinition) {
        this.containsFullDefinition = fullDefinition;
        return this;
    }
}

