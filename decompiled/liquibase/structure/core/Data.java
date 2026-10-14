/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import liquibase.GlobalConfiguration;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class Data
extends AbstractDatabaseObject {
    @Override
    public boolean snapshotByDefault() {
        return GlobalConfiguration.SHOULD_SNAPSHOT_DATA.getCurrentValue();
    }

    public Table getTable() {
        return (Table)((Object)this.getAttribute("table", Table.class));
    }

    public Data setTable(Table table) {
        this.setAttribute("table", table);
        return this;
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return new DatabaseObject[]{this.getTable()};
    }

    @Override
    public String getName() {
        Table table = this.getTable();
        if (table == null) {
            return null;
        }
        return table.getName();
    }

    @Override
    public Data setName(String name) {
        Table table = this.getTable();
        if (table == null) {
            this.setTable(new Table().setName(name));
        } else {
            table.setName(name);
        }
        return this;
    }

    @Override
    public Schema getSchema() {
        Table table = this.getTable();
        if (table == null) {
            return null;
        }
        return table.getSchema();
    }
}

