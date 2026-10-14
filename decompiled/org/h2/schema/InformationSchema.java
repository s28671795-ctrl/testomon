/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.schema.MetaSchema;
import org.h2.table.InformationSchemaTable;
import org.h2.table.InformationSchemaTableLegacy;
import org.h2.table.Table;

public final class InformationSchema
extends MetaSchema {
    private volatile HashMap<String, Table> newTables;
    private volatile HashMap<String, Table> oldTables;

    public InformationSchema(Database database, User user) {
        super(database, -1, database.sysIdentifier("INFORMATION_SCHEMA"), user);
    }

    @Override
    protected Map<String, Table> getMap(SessionLocal sessionLocal) {
        HashMap<String, Table> hashMap;
        if (sessionLocal == null) {
            return Collections.emptyMap();
        }
        boolean bl = sessionLocal.isOldInformationSchema();
        HashMap<String, Table> hashMap2 = hashMap = bl ? this.oldTables : this.newTables;
        if (hashMap == null) {
            hashMap = this.fillMap(bl);
        }
        return hashMap;
    }

    private synchronized HashMap<String, Table> fillMap(boolean bl) {
        HashMap<String, Table> hashMap;
        HashMap<String, Table> hashMap2 = hashMap = bl ? this.oldTables : this.newTables;
        if (hashMap == null) {
            hashMap = this.database.newStringMap(64);
            if (bl) {
                for (int i = 0; i < 36; ++i) {
                    InformationSchemaTableLegacy informationSchemaTableLegacy = new InformationSchemaTableLegacy(this, -1 - i, i);
                    hashMap.put(informationSchemaTableLegacy.getName(), informationSchemaTableLegacy);
                }
                this.oldTables = hashMap;
            } else {
                for (int i = 0; i < 35; ++i) {
                    InformationSchemaTable informationSchemaTable = new InformationSchemaTable(this, -1 - i, i);
                    hashMap.put(informationSchemaTable.getName(), informationSchemaTable);
                }
                this.newTables = hashMap;
            }
        }
        return hashMap;
    }
}

