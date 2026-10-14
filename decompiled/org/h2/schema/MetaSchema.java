/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.schema.Schema;
import org.h2.table.Table;

public abstract class MetaSchema
extends Schema {
    public MetaSchema(Database database, int n, String string, User user) {
        super(database, n, string, user, true);
    }

    @Override
    public Table findTableOrView(SessionLocal sessionLocal, String string) {
        Map<String, Table> map = this.getMap(sessionLocal);
        Table table = map.get(string);
        if (table != null) {
            return table;
        }
        return super.findTableOrView(sessionLocal, string);
    }

    @Override
    public Collection<Table> getAllTablesAndViews(SessionLocal sessionLocal) {
        Collection<Table> collection = super.getAllTablesAndViews(sessionLocal);
        if (sessionLocal == null) {
            return collection;
        }
        Collection<Table> collection2 = this.getMap(sessionLocal).values();
        if (collection.isEmpty()) {
            return collection2;
        }
        ArrayList<Table> arrayList = new ArrayList<Table>(collection2.size() + collection.size());
        arrayList.addAll(collection2);
        arrayList.addAll(collection);
        return arrayList;
    }

    @Override
    public Table getTableOrView(SessionLocal sessionLocal, String string) {
        Map<String, Table> map = this.getMap(sessionLocal);
        Table table = map.get(string);
        if (table != null) {
            return table;
        }
        return super.getTableOrView(sessionLocal, string);
    }

    @Override
    public Table getTableOrViewByName(SessionLocal sessionLocal, String string) {
        Map<String, Table> map = this.getMap(sessionLocal);
        Table table = map.get(string);
        if (table != null) {
            return table;
        }
        return super.getTableOrViewByName(sessionLocal, string);
    }

    protected abstract Map<String, Table> getMap(SessionLocal var1);

    @Override
    public boolean isEmpty() {
        return false;
    }
}

