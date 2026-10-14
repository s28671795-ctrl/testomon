/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class Index
extends AbstractDatabaseObject {
    public static final String MARK_PRIMARY_KEY = "primaryKey";
    public static final String MARK_FOREIGN_KEY = "foreignKey";
    public static final String MARK_UNIQUE_CONSTRAINT = "uniqueConstraint";

    public Index() {
        this.setAttribute("columns", new ArrayList());
        this.setAttribute("associatedWith", new HashSet());
    }

    public Index(String indexName) {
        this();
        this.setName(indexName);
    }

    public Index(String indexName, String catalogName, String schemaName, String tableName, Column ... columns) {
        this();
        this.setName(indexName);
        if (tableName != null) {
            this.setRelation(new Table(catalogName, schemaName, tableName));
            if (columns != null && columns.length > 0) {
                this.setColumns(Arrays.asList(columns));
            }
        }
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return new DatabaseObject[]{this.getRelation()};
    }

    @Override
    public String getName() {
        return (String)((Object)this.getAttribute("name", String.class));
    }

    @Override
    public Index setName(String name) {
        this.setAttribute("name", name);
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
    public Index setTable(Relation table) {
        return this.setRelation(table);
    }

    public Relation getRelation() {
        return (Relation)((Object)this.getAttribute("table", Relation.class));
    }

    public Index setRelation(Relation relation) {
        this.setAttribute("table", relation);
        return this;
    }

    public String getTablespace() {
        return (String)((Object)this.getAttribute("tablespace", String.class));
    }

    public Index setTablespace(String tablespace) {
        this.setAttribute("tablespace", tablespace);
        return this;
    }

    public List<Column> getColumns() {
        return (List)((Object)this.getAttribute("columns", List.class));
    }

    public Index addColumn(Column column) {
        column.setRelation(this.getRelation());
        this.getColumns().add(column);
        return this;
    }

    public Index setColumns(List<Column> columns) {
        if (this.getAttribute("table", Object.class) instanceof Table) {
            for (Column column : columns) {
                column.setRelation(this.getRelation());
            }
        }
        this.setAttribute("columns", columns);
        return this;
    }

    public String getColumnNames() {
        return StringUtil.join(this.getColumns(), ", ", (StringUtil.StringUtilFormatter)new StringUtil.ToStringFormatter());
    }

    public Index setUnique(Boolean value) {
        this.setAttribute("unique", value);
        return this;
    }

    public Boolean isUnique() {
        return (Boolean)((Object)this.getAttribute("unique", Boolean.class));
    }

    public Set<String> getAssociatedWith() {
        return (Set)((Object)this.getAttribute("associatedWith", Set.class));
    }

    public String getAssociatedWithAsString() {
        return StringUtil.join(this.getAssociatedWith(), ",");
    }

    public void addAssociatedWith(String item) {
        this.getAssociatedWith().add(item);
    }

    public boolean isAssociatedWith(String keyword) {
        return this.getAssociatedWith().contains(keyword);
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        if (field2 != null && field2.equals("columns")) {
            ArrayList<Column> returnList = new ArrayList<Column>();
            for (Column column : this.getColumns()) {
                Column c = new Column();
                c.setName(column.getName());
                c.setDescending(column.getDescending());
                c.setComputed(column.getComputed());
                c.setForIndex(true);
                returnList.add(c);
            }
            return returnList;
        }
        return super.getSerializableFieldValue(field2);
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        super.load(parsedNode, resourceAccessor);
        ParsedNode columns = parsedNode.getChild(null, "columns");
        if (columns != null) {
            List<ParsedNode> nodes = columns.getChildren(null, "column");
            for (ParsedNode node : nodes) {
                Column column = new Column();
                column.load(node, resourceAccessor);
                column.setName((String)node.getChildren(null, "name").get(0).getValue());
                column.setDescending(node.getChildValue((String)null, "descending", Boolean.class));
                column.setComputed(node.getChildValue((String)null, "computed", Boolean.class));
                this.getColumns().add(column);
            }
            if (!nodes.isEmpty() && this.allColumnObjects(this.getColumns())) {
                ArrayList<Column> newList = new ArrayList<Column>();
                for (Column column : this.getColumns()) {
                    if (column == null) continue;
                    newList.add(column);
                }
                this.setColumns(newList);
            }
        }
    }

    private boolean allColumnObjects(List columns) {
        for (Object object : columns) {
            if (!(object instanceof String)) continue;
            return false;
        }
        return true;
    }

    public Boolean getClustered() {
        return (Boolean)((Object)this.getAttribute("clustered", Boolean.class));
    }

    public Index setClustered(Boolean clustered) {
        return (Index)this.setAttribute("clustered", clustered);
    }

    @Override
    public int compareTo(Object other) {
        Index o = (Index)other;
        int returnValue = 0;
        if (this.getRelation() != null && o.getRelation() != null && (returnValue = this.getRelation().compareTo(o.getRelation())) == 0 && this.getRelation().getSchema() != null && o.getRelation().getSchema() != null) {
            returnValue = StringUtil.trimToEmpty(this.getRelation().getSchema().getName()).compareToIgnoreCase(StringUtil.trimToEmpty(o.getRelation().getSchema().getName()));
        }
        if (returnValue == 0) {
            String thisName = StringUtil.trimToEmpty(this.getName());
            String oName = StringUtil.trimToEmpty(o.getName());
            returnValue = thisName.compareTo(oName);
        }
        return returnValue;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Index)) {
            return false;
        }
        return this.compareTo(obj) == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.getName() == null ? "(unnamed index)" : this.getName());
        if (this.isUnique() != null && this.isUnique().booleanValue()) {
            result.append(" UNIQUE ");
        }
        if (this.getRelation() != null && this.getColumns() != null) {
            String tableName = this.getRelation().getName();
            if (this.getRelation().getSchema() != null && this.getRelation().getSchema().getName() != null) {
                tableName = this.getRelation().getSchema().getName() + "." + tableName;
            }
            result.append(" ON ").append(tableName);
            if (this.getColumns() != null && !this.getColumns().isEmpty()) {
                result.append("(");
                for (Column column : this.getColumns()) {
                    if (column == null) {
                        result.append("(null), ");
                        continue;
                    }
                    result.append(column.toString(false)).append(", ");
                }
                result.delete(result.length() - 2, result.length());
                result.append(")");
            } else {
                result.append("()");
            }
        }
        return result.toString();
    }
}

