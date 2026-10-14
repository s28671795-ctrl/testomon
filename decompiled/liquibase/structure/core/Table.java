/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.util.ArrayList;
import java.util.List;
import liquibase.statement.NotNullConstraint;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.BooleanUtil;
import liquibase.util.StringUtil;

public class Table
extends Relation {
    public Table() {
        this.setAttribute("outgoingForeignKeys", new ArrayList());
        this.setAttribute("indexes", new ArrayList());
        this.setAttribute("uniqueConstraints", new ArrayList());
        this.setAttribute("notNullConstraints", new ArrayList());
    }

    public Table(String catalogName, String schemaName, String tableName) {
        this.setSchema(new Schema(catalogName, schemaName));
        this.setName(tableName);
    }

    public PrimaryKey getPrimaryKey() {
        return (PrimaryKey)((Object)this.getAttribute("primaryKey", PrimaryKey.class));
    }

    public void setPrimaryKey(PrimaryKey primaryKey) {
        this.setAttribute("primaryKey", primaryKey);
    }

    public List<ForeignKey> getOutgoingForeignKeys() {
        List fkList = (List)((Object)this.getAttribute("outgoingForeignKeys", List.class));
        return fkList == null ? new ArrayList(0) : fkList;
    }

    @Override
    public List<Index> getIndexes() {
        return (List)((Object)this.getAttribute("indexes", List.class));
    }

    @Override
    public List<UniqueConstraint> getUniqueConstraints() {
        return (List)((Object)this.getAttribute("uniqueConstraints", List.class));
    }

    public List<NotNullConstraint> getNotNullConstraints() {
        return (List)((Object)this.getAttribute("notNullConstraints", List.class));
    }

    public boolean equals(Object o) {
        boolean schemasTheSame;
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Table that = (Table)o;
        if (this.getSchema() != null && that.getSchema() != null && !(schemasTheSame = this.getSchema().equals(that.getSchema()))) {
            return false;
        }
        return this.getName().equalsIgnoreCase(that.getName());
    }

    public int hashCode() {
        return StringUtil.trimToEmpty(this.getName()).toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public Table setName(String name) {
        return (Table)super.setName(name);
    }

    public String getTablespace() {
        return (String)((Object)this.getAttribute("tablespace", String.class));
    }

    public Table setTablespace(String tablespace) {
        this.setAttribute("tablespace", tablespace);
        return this;
    }

    public Boolean getDefaultTablespace() {
        return (Boolean)((Object)this.getAttribute("default_tablespace", Boolean.class));
    }

    public Table setDefaultTablespace(Boolean tablespace) {
        this.setAttribute("default_tablespace", tablespace);
        return this;
    }

    public boolean isDefaultTablespace() {
        Boolean b = (Boolean)((Object)this.getAttribute("default_tablespace", Boolean.class));
        return BooleanUtil.isTrue(b);
    }
}

