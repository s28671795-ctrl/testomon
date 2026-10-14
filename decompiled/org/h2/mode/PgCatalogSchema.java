/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.HashMap;
import java.util.Map;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.mode.PgCatalogTable;
import org.h2.schema.MetaSchema;
import org.h2.table.Table;

public final class PgCatalogSchema
extends MetaSchema {
    private volatile HashMap<String, Table> tables;

    public PgCatalogSchema(Database database, User user) {
        super(database, -1000, database.sysIdentifier("PG_CATALOG"), user);
    }

    @Override
    protected Map<String, Table> getMap(SessionLocal sessionLocal) {
        HashMap<String, Table> hashMap = this.tables;
        if (hashMap == null) {
            hashMap = this.fillMap();
        }
        return hashMap;
    }

    private synchronized HashMap<String, Table> fillMap() {
        HashMap<String, Table> hashMap = this.tables;
        if (hashMap == null) {
            hashMap = this.database.newStringMap();
            for (int i = 0; i < 19; ++i) {
                PgCatalogTable pgCatalogTable = new PgCatalogTable(this, -1000 - i, i);
                hashMap.put(pgCatalogTable.getName(), pgCatalogTable);
            }
            this.tables = hashMap;
        }
        return hashMap;
    }
}

