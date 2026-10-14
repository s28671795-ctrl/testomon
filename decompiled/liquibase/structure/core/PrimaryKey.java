/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class PrimaryKey
extends AbstractDatabaseObject {
    public PrimaryKey() {
        this.setAttribute("columns", new ArrayList());
    }

    public PrimaryKey(String name, String tableCatalogName, String tableSchemaName, String tableName, Column ... columns) {
        this();
        this.setName(name);
        if (tableName != null) {
            Table table = new Table(tableCatalogName, tableSchemaName, tableName);
            if (columns != null) {
                this.setAttribute("columns", Arrays.asList(columns));
                for (Column column : this.getColumns()) {
                    column.setRelation(table);
                }
            }
            this.setTable(table);
        }
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return new DatabaseObject[]{this.getTable()};
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    @Override
    public PrimaryKey setName(String name) {
        this.setAttribute("name", name);
        return this;
    }

    @Override
    public Schema getSchema() {
        if (this.getTable() == null) {
            return null;
        }
        return this.getTable().getSchema();
    }

    public String getColumnNames() {
        return StringUtil.join(this.getColumns(), ", ", obj -> ((Column)obj).toString(false));
    }

    public PrimaryKey addColumn(int position, Column column) {
        if (position >= this.getColumns().size()) {
            for (int i = this.getColumns().size() - 1; i < position; ++i) {
                this.getColumns().add(null);
            }
        }
        this.getColumns().set(position, column);
        return this;
    }

    public Table getTable() {
        return (Table)((Object)this.getAttribute("table", Table.class));
    }

    public PrimaryKey setTable(Table table) {
        this.setAttribute("table", table);
        return this;
    }

    @Override
    public int compareTo(Object other) {
        PrimaryKey o = (PrimaryKey)other;
        int returnValue = this.getTable().compareTo(o.getTable());
        if (returnValue == 0) {
            returnValue = this.getColumnNames().compareTo(o.getColumnNames());
        }
        return returnValue;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PrimaryKey that = (PrimaryKey)o;
        return !(this.getColumnNames() == null ? that.getColumnNames() != null : !this.getColumnNames().equals(that.getColumnNames())) && !(this.getTable() == null ? that.getTable() != null : !this.getTable().equals(that.getTable()));
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        if (this.getTable() == null) {
            return this.getName();
        }
        String tableName = this.getTable().getName();
        if (this.getTable().getSchema() != null) {
            tableName = this.getTable().getSchema().getName() + "." + tableName;
        }
        return this.getName() + " on " + tableName + "(" + this.getColumnNames() + ")";
    }

    public List<Column> getColumns() {
        return (List)((Object)this.getAttribute("columns", List.class));
    }

    public List<String> getColumnNamesAsList() {
        ArrayList<String> names = new ArrayList<String>();
        for (Column col : this.getColumns()) {
            names.add(col.getName());
        }
        return names;
    }

    public boolean isCertainName() {
        return (Boolean)((Object)this.getAttribute("certainName", Boolean.class));
    }

    public PrimaryKey setCertainName(boolean certainName) {
        this.setAttribute("certainName", certainName);
        return this;
    }

    public String getTablespace() {
        return (String)((Object)this.getAttribute("tablespace", String.class));
    }

    public PrimaryKey setTablespace(String tablespace) {
        this.setAttribute("tablespace", tablespace);
        return this;
    }

    public Index getBackingIndex() {
        return (Index)((Object)this.getAttribute("backingIndex", Index.class));
    }

    public PrimaryKey setBackingIndex(Index backingIndex) {
        this.setAttribute("backingIndex", backingIndex);
        return this;
    }

    public PrimaryKey setShouldValidate(boolean shouldValidate) {
        this.setAttribute("validate", shouldValidate);
        return this;
    }
}

