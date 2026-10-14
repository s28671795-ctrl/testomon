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
import liquibase.structure.core.ForeignKeyConstraintType;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class ForeignKey
extends AbstractDatabaseObject {
    public ForeignKey() {
        this.setForeignKeyColumns(new ArrayList<Column>());
        this.setPrimaryKeyColumns(new ArrayList<Column>());
    }

    public ForeignKey(String name) {
        this();
        this.setName(name);
    }

    public ForeignKey(String name, String foreignKeyCatalog, String foreignKeySchema, String foreignKeyTable, Column ... baseTableColumns) {
        this(name);
        if (foreignKeyTable != null) {
            this.setForeignKeyTable(new Table(foreignKeyCatalog, foreignKeySchema, foreignKeyTable));
        }
        if (baseTableColumns != null && baseTableColumns.length > 0 && baseTableColumns[0] != null) {
            this.setForeignKeyColumns(new ArrayList<Column>(Arrays.asList(baseTableColumns)));
        }
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        ArrayList<Column> objects = new ArrayList<Column>();
        if (this.getPrimaryKeyColumns() != null) {
            objects.addAll(this.getPrimaryKeyColumns());
        }
        if (this.getForeignKeyColumns() != null) {
            objects.addAll(this.getForeignKeyColumns());
        }
        return objects.toArray(new DatabaseObject[0]);
    }

    @Override
    public Schema getSchema() {
        if (this.getForeignKeyTable() == null) {
            return null;
        }
        return this.getForeignKeyTable().getSchema();
    }

    public Table getPrimaryKeyTable() {
        return (Table)((Object)this.getAttribute("primaryKeyTable", Table.class));
    }

    public ForeignKey setPrimaryKeyTable(Table primaryKeyTable) {
        this.setAttribute("primaryKeyTable", primaryKeyTable);
        return this;
    }

    public List<Column> getPrimaryKeyColumns() {
        return (List)((Object)this.getAttribute("primaryKeyColumns", List.class));
    }

    public ForeignKey setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
        this.setAttribute("primaryKeyColumns", primaryKeyColumns);
        for (Column column : this.getPrimaryKeyColumns()) {
            if (column.getAttribute("relation", Object.class) != null) continue;
            column.setRelation(this.getPrimaryKeyTable());
        }
        return this;
    }

    public ForeignKey addPrimaryKeyColumn(Column primaryKeyColumn) {
        ((List)((Object)this.getAttribute("primaryKeyColumns", List.class))).add(primaryKeyColumn);
        if (primaryKeyColumn.getAttribute("relation", Object.class) == null) {
            primaryKeyColumn.setRelation(this.getPrimaryKeyTable());
        }
        return this;
    }

    public Table getForeignKeyTable() {
        return (Table)((Object)this.getAttribute("foreignKeyTable", Table.class));
    }

    public ForeignKey setForeignKeyTable(Table foreignKeyTable) {
        this.setAttribute("foreignKeyTable", foreignKeyTable);
        return this;
    }

    public List<Column> getForeignKeyColumns() {
        return (List)((Object)this.getAttribute("foreignKeyColumns", List.class));
    }

    public ForeignKey setForeignKeyColumns(List<Column> foreignKeyColumns) {
        this.setAttribute("foreignKeyColumns", foreignKeyColumns);
        for (Column column : this.getForeignKeyColumns()) {
            if (column.getAttribute("relation", Object.class) != null) continue;
            column.setRelation(this.getForeignKeyTable());
        }
        return this;
    }

    public ForeignKey addForeignKeyColumn(Column foreignKeyColumn) {
        if (foreignKeyColumn.getAttribute("relation", Object.class) == null) {
            foreignKeyColumn.setRelation(this.getForeignKeyTable());
        }
        ((List)((Object)this.getAttribute("foreignKeyColumns", List.class))).add(foreignKeyColumn);
        return this;
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    @Override
    public ForeignKey setName(String name) {
        this.setAttribute("name", name);
        return this;
    }

    @Override
    public String toString() {
        StringUtil.StringUtilFormatter<Column> columnFormatter = Column::getName;
        return this.getName() + "(" + this.getForeignKeyTable() + "[" + StringUtil.join(this.getForeignKeyColumns(), ", ", columnFormatter) + "] -> " + this.getPrimaryKeyTable() + "[" + StringUtil.join(this.getPrimaryKeyColumns(), ", ", columnFormatter) + "])";
    }

    public boolean isDeferrable() {
        return this.getAttribute("deferrable", false);
    }

    public ForeignKey setDeferrable(boolean deferrable) {
        this.setAttribute("deferrable", deferrable);
        return this;
    }

    public boolean isInitiallyDeferred() {
        return this.getAttribute("initiallyDeferred", false);
    }

    public ForeignKey setInitiallyDeferred(boolean initiallyDeferred) {
        this.setAttribute("initiallyDeferred", initiallyDeferred);
        return this;
    }

    public boolean shouldValidate() {
        return this.getAttribute("validate", true);
    }

    public ForeignKey setShouldValidate(boolean shouldValidate) {
        this.setAttribute("validate", shouldValidate);
        return this;
    }

    public ForeignKeyConstraintType getUpdateRule() {
        return (ForeignKeyConstraintType)((Object)this.getAttribute("updateRule", ForeignKeyConstraintType.class));
    }

    public ForeignKey setUpdateRule(ForeignKeyConstraintType rule) {
        this.setAttribute("updateRule", (Object)rule);
        return this;
    }

    public ForeignKeyConstraintType getDeleteRule() {
        return (ForeignKeyConstraintType)((Object)this.getAttribute("deleteRule", ForeignKeyConstraintType.class));
    }

    public ForeignKey setDeleteRule(ForeignKeyConstraintType rule) {
        this.setAttribute("deleteRule", (Object)rule);
        return this;
    }

    public boolean equals(Object o) {
        boolean schemasEqual;
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ForeignKey that = (ForeignKey)o;
        if (!this.getName().equalsIgnoreCase(that.getName())) {
            return false;
        }
        if (this.getSchema() != null && that.getSchema() != null && !(schemasEqual = this.getSchema().equals(that.getSchema()))) {
            return false;
        }
        if (this.getForeignKeyColumns() == null) {
            return true;
        }
        StringUtil.StringUtilFormatter<Column> formatter = obj -> obj.toString(false);
        return StringUtil.join(this.getForeignKeyColumns(), ",", formatter).equalsIgnoreCase(StringUtil.join(that.getForeignKeyColumns(), ",", formatter)) && this.getForeignKeyTable() != null && that.getForeignKeyTable() != null && this.getForeignKeyTable().equals(that.getForeignKeyTable()) && StringUtil.join(this.getPrimaryKeyColumns(), ",", formatter).equalsIgnoreCase(StringUtil.join(that.getPrimaryKeyColumns(), ",", formatter)) && this.getPrimaryKeyTable() != null && that.getPrimaryKeyTable() != null && this.getPrimaryKeyTable().equals(that.getPrimaryKeyTable());
    }

    public int hashCode() {
        StringUtil.StringUtilFormatter<Column> formatter = obj -> obj.toString(false);
        int result = 0;
        if (this.getPrimaryKeyTable() != null) {
            result = this.getPrimaryKeyTable().hashCode();
        }
        if (this.getPrimaryKeyColumns() != null) {
            result = 31 * result + StringUtil.join(this.getPrimaryKeyColumns(), ",", formatter).toUpperCase().hashCode();
        }
        if (this.getForeignKeyTable() != null) {
            result = 31 * result + this.getForeignKeyTable().hashCode();
        }
        if (this.getForeignKeyColumns() != null) {
            result = 31 * result + StringUtil.join(this.getForeignKeyColumns(), ",", formatter).toUpperCase().hashCode();
        }
        return result;
    }

    @Override
    public int compareTo(Object other) {
        StringUtil.StringUtilFormatter<Column> formatter = obj -> obj.toString(false);
        ForeignKey o = (ForeignKey)other;
        int returnValue = 0;
        if (this.getForeignKeyTable() != null && o.getForeignKeyTable() != null) {
            returnValue = this.getForeignKeyTable().compareTo(o.getForeignKeyTable());
        }
        if (returnValue == 0 && this.getForeignKeyColumns() != null && o.getForeignKeyColumns() != null) {
            returnValue = StringUtil.join(this.getForeignKeyColumns(), ",", formatter).compareToIgnoreCase(StringUtil.join(o.getForeignKeyColumns(), ",", formatter));
        }
        if (returnValue == 0 && this.getName() != null && o.getName() != null) {
            returnValue = this.getName().compareToIgnoreCase(o.getName());
        }
        if (returnValue == 0 && this.getPrimaryKeyTable() != null && o.getPrimaryKeyTable() != null) {
            returnValue = this.getPrimaryKeyTable().compareTo(o.getPrimaryKeyTable());
        }
        if (returnValue == 0 && this.getPrimaryKeyColumns() != null && o.getPrimaryKeyColumns() != null) {
            returnValue = StringUtil.join(this.getPrimaryKeyColumns(), ",", formatter).compareToIgnoreCase(StringUtil.join(o.getPrimaryKeyColumns(), ",", formatter));
        }
        if (returnValue == 0 && this.getUpdateRule() != null && o.getUpdateRule() != null) {
            returnValue = this.getUpdateRule().compareTo(o.getUpdateRule());
        }
        if (returnValue == 0 && this.getDeleteRule() != null && o.getDeleteRule() != null) {
            returnValue = this.getDeleteRule().compareTo(o.getDeleteRule());
        }
        return returnValue;
    }

    private String toDisplayString(List<String> columnsNames) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String columnName : columnsNames) {
            sb.append(columnName);
            if (++i >= columnsNames.size()) continue;
            sb.append(", ");
        }
        return sb.toString();
    }

    public Index getBackingIndex() {
        return (Index)((Object)this.getAttribute("backingIndex", Index.class));
    }

    public ForeignKey setBackingIndex(Index backingIndex) {
        this.setAttribute("backingIndex", backingIndex);
        return this;
    }
}

