/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.UniqueConstraint;

public abstract class Relation
extends AbstractDatabaseObject {
    private String name;

    protected Relation() {
        this.setAttribute("columns", new ArrayList());
        this.setAttribute("uniqueConstraints", new ArrayList());
        this.setAttribute("indexes", new ArrayList());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Relation setName(String name) {
        this.setAttribute("name", name);
        this.name = name;
        return this;
    }

    public List<Index> getIndexes() {
        return (List)((Object)this.getAttribute("indexes", List.class));
    }

    public List<UniqueConstraint> getUniqueConstraints() {
        return (List)((Object)this.getAttribute("uniqueConstraints", List.class));
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return new DatabaseObject[]{this.getSchema()};
    }

    public String getRemarks() {
        return (String)((Object)this.getAttribute("remarks", String.class));
    }

    public Relation setRemarks(String remarks) {
        this.setAttribute("remarks", remarks);
        return this;
    }

    public List<Column> getColumns() {
        return (List)((Object)this.getAttribute("columns", List.class));
    }

    public Relation addColumn(Column column) {
        this.getColumns().add(column);
        return this;
    }

    public Column getColumn(String columnName) {
        for (Column column : this.getColumns()) {
            if (!column.getName().equalsIgnoreCase(columnName)) continue;
            return column;
        }
        return null;
    }

    @Override
    public Schema getSchema() {
        return (Schema)((Object)this.getAttribute("schema", Schema.class));
    }

    public Relation setSchema(Schema schema) {
        this.setAttribute("schema", schema);
        return this;
    }

    public Relation setSchema(String catalog, String schema) {
        return this.setSchema(new Schema(catalog, schema));
    }

    @Override
    public int compareTo(Object o) {
        Relation that = (Relation)o;
        int returnValue = 0;
        if (this.getSchema() != null && that.getSchema() != null) {
            returnValue = this.getSchema().compareTo(that.getSchema());
        }
        if (returnValue == 0) {
            returnValue = this.getName().compareToIgnoreCase(((Relation)o).getName());
        }
        return returnValue;
    }
}

