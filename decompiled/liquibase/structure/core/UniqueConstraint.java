/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class UniqueConstraint
extends AbstractDatabaseObject {
    public UniqueConstraint() {
        this.setAttribute("columns", new ArrayList());
        this.setAttribute("deferrable", false);
        this.setAttribute("initiallyDeferred", false);
        this.setAttribute("disabled", false);
        this.setAttribute("validate", true);
    }

    public UniqueConstraint(String name, String tableCatalog, String tableSchema, String tableName, Column ... columns) {
        this();
        this.setName(name);
        if (tableName != null && columns != null) {
            this.setRelation(new Table(tableCatalog, tableSchema, tableName));
            this.setColumns(new ArrayList<Column>(Arrays.asList(columns)));
        }
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return this.getColumns().toArray(new Column[0]);
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    @Override
    public UniqueConstraint setName(String constraintName) {
        this.setAttribute("name", constraintName);
        return this;
    }

    @Override
    public Schema getSchema() {
        if (this.getRelation() == null) {
            return null;
        }
        return this.getRelation().getSchema();
    }

    @Deprecated
    public Table getTable() {
        Relation relation = this.getRelation();
        if (relation instanceof Table) {
            return (Table)relation;
        }
        return null;
    }

    @Deprecated
    public UniqueConstraint setTable(Table table) {
        return this.setRelation(table);
    }

    public Relation getRelation() {
        return (Relation)((Object)this.getAttribute("table", Relation.class));
    }

    public UniqueConstraint setRelation(Relation relation) {
        this.setAttribute("table", relation);
        return this;
    }

    public List<Column> getColumns() {
        return (List)((Object)this.getAttribute("columns", List.class));
    }

    public UniqueConstraint setColumns(List<Column> columns) {
        this.setAttribute("columns", columns);
        if (this.getAttribute("table", Object.class) instanceof Table) {
            for (Column column : this.getColumns()) {
                column.setRelation(this.getRelation());
            }
        }
        return this;
    }

    public UniqueConstraint addColumn(int position, Column column) {
        if (position >= this.getColumns().size()) {
            for (int i = this.getColumns().size() - 1; i < position; ++i) {
                this.getColumns().add(null);
            }
        }
        this.getColumns().set(position, column);
        return this;
    }

    public boolean isDeferrable() {
        return (Boolean)((Object)this.getAttribute("deferrable", Boolean.class));
    }

    public UniqueConstraint setDeferrable(boolean deferrable) {
        this.setAttribute("deferrable", deferrable);
        return this;
    }

    public boolean shouldValidate() {
        return this.getAttribute("validate", true);
    }

    public UniqueConstraint setShouldValidate(boolean shouldValidate) {
        this.setAttribute("validate", shouldValidate);
        return this;
    }

    public boolean isInitiallyDeferred() {
        return (Boolean)((Object)this.getAttribute("initiallyDeferred", Boolean.class));
    }

    public UniqueConstraint setInitiallyDeferred(boolean initiallyDeferred) {
        this.setAttribute("initiallyDeferred", initiallyDeferred);
        return this;
    }

    public String getColumnNames() {
        return StringUtil.join(this.getColumns(), ", ", obj -> ((Column)obj).toString(false));
    }

    public UniqueConstraint setDisabled(boolean disabled) {
        this.setAttribute("disabled", disabled);
        return this;
    }

    public boolean isDisabled() {
        return (Boolean)((Object)this.getAttribute("disabled", Boolean.class));
    }

    public Index getBackingIndex() {
        return (Index)((Object)this.getAttribute("backingIndex", Index.class));
    }

    public UniqueConstraint setBackingIndex(Index backingIndex) {
        this.setAttribute("backingIndex", backingIndex);
        return this;
    }

    public UniqueConstraint setClustered(boolean clustered) {
        this.setAttribute("clustered", clustered);
        return this;
    }

    public boolean isClustered() {
        return this.getAttribute("clustered", false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) return false;
        if (this.getClass() != o.getClass()) {
            return false;
        }
        if (null == this.getColumnNames()) {
            return false;
        }
        UniqueConstraint that = (UniqueConstraint)o;
        boolean result = false;
        if (this.getColumnNames() != null) {
            if (!this.getColumnNames().equalsIgnoreCase(that.getColumnNames())) {
                return false;
            }
        } else if (that.getColumnNames() != null) return false;
        if (this.isDeferrable() != that.isDeferrable()) return false;
        if (this.isInitiallyDeferred() != that.isInitiallyDeferred()) return false;
        if (this.isDisabled() != that.isDisabled()) return false;
        boolean bl = true;
        result = bl;
        if (!result) return result;
        if (null == this.getRelation()) {
            if (null != that.getRelation()) return false;
            return true;
        }
        if (null != that.getRelation()) return this.getRelation().equals(that.getRelation());
        return false;
    }

    @Override
    public int compareTo(Object other) {
        UniqueConstraint o = (UniqueConstraint)other;
        int returnValue = 0;
        if (this.getTable() != null && o.getTable() != null) {
            returnValue = this.getTable().compareTo(o.getTable());
        } else {
            if (this.getTable() != null) {
                return 1;
            }
            if (o.getTable() != null) {
                return -1;
            }
        }
        if (returnValue == 0) {
            returnValue = this.getName().compareTo(o.getName());
        }
        if (returnValue == 0) {
            returnValue = this.getColumnNames().compareTo(o.getColumnNames());
        }
        return returnValue;
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        super.load(parsedNode, resourceAccessor);
    }

    public int hashCode() {
        int result = 0;
        if (this.getRelation() != null) {
            result = this.getRelation().hashCode();
        }
        if (this.getName() != null) {
            result = 31 * result + this.getName().toUpperCase().hashCode();
        }
        if (this.getColumnNames() != null) {
            result = 31 * result + this.getColumnNames().hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        if (this.getRelation() == null) {
            return this.getName();
        }
        return this.getName() + " on " + this.getRelation().getName() + "(" + this.getColumnNames() + ")";
    }
}

