/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.sql.SQLException;
import java.util.Comparator;
import org.h2.api.DatabaseEventListener;
import org.h2.command.Prepared;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.result.SearchRow;
import org.h2.value.ValueInteger;
import org.h2.value.ValueVarchar;

public class MetaRecord
implements Comparable<MetaRecord> {
    static final Comparator<Prepared> CONSTRAINTS_COMPARATOR = (prepared, prepared2) -> {
        boolean bl;
        int n = prepared.getType();
        int n2 = prepared2.getType();
        boolean bl2 = n == 6 || n == 4;
        boolean bl3 = bl = n2 == 6 || n2 == 4;
        if (bl2 == bl) {
            return prepared.getPersistedObjectId() - prepared2.getPersistedObjectId();
        }
        return bl2 ? -1 : 1;
    };
    private final int id;
    private final int objectType;
    private final String sql;

    public static void populateRowFromDBObject(DbObject dbObject, SearchRow searchRow) {
        searchRow.setValue(0, ValueInteger.get(dbObject.getId()));
        searchRow.setValue(1, ValueInteger.get(0));
        searchRow.setValue(2, ValueInteger.get(dbObject.getType()));
        searchRow.setValue(3, ValueVarchar.get(dbObject.getCreateSQLForMeta()));
    }

    public MetaRecord(SearchRow searchRow) {
        this.id = searchRow.getValue(0).getInt();
        this.objectType = searchRow.getValue(2).getInt();
        this.sql = searchRow.getValue(3).getString();
    }

    void prepareAndExecute(Database database, SessionLocal sessionLocal, DatabaseEventListener databaseEventListener) {
        try {
            Prepared prepared = sessionLocal.prepare(this.sql);
            prepared.setPersistedObjectId(this.id);
            prepared.update();
        }
        catch (DbException dbException) {
            MetaRecord.throwException(database, databaseEventListener, dbException, this.sql);
        }
    }

    Prepared prepare(Database database, SessionLocal sessionLocal, DatabaseEventListener databaseEventListener) {
        try {
            Prepared prepared = sessionLocal.prepare(this.sql);
            prepared.setPersistedObjectId(this.id);
            return prepared;
        }
        catch (DbException dbException) {
            MetaRecord.throwException(database, databaseEventListener, dbException, this.sql);
            return null;
        }
    }

    static void execute(Database database, Prepared prepared, DatabaseEventListener databaseEventListener, String string) {
        try {
            prepared.update();
        }
        catch (DbException dbException) {
            MetaRecord.throwException(database, databaseEventListener, dbException, string);
        }
    }

    private static void throwException(Database database, DatabaseEventListener databaseEventListener, DbException dbException, String string) {
        dbException = dbException.addSQL(string);
        SQLException sQLException = dbException.getSQLException();
        database.getTrace(2).error(sQLException, string);
        if (databaseEventListener == null) {
            throw dbException;
        }
        databaseEventListener.exceptionThrown(sQLException, string);
    }

    public int getId() {
        return this.id;
    }

    public int getObjectType() {
        return this.objectType;
    }

    public String getSQL() {
        return this.sql;
    }

    @Override
    public int compareTo(MetaRecord metaRecord) {
        int n;
        int n2 = this.getCreateOrder();
        if (n2 != (n = metaRecord.getCreateOrder())) {
            return n2 - n;
        }
        return this.getId() - metaRecord.getId();
    }

    private int getCreateOrder() {
        switch (this.objectType) {
            case 6: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 10: {
                return 2;
            }
            case 9: {
                return 3;
            }
            case 12: {
                return 4;
            }
            case 3: {
                return 5;
            }
            case 11: {
                return 6;
            }
            case 0: {
                return 7;
            }
            case 1: {
                return 8;
            }
            case 5: {
                return 9;
            }
            case 4: {
                return 10;
            }
            case 15: {
                return 11;
            }
            case 7: {
                return 12;
            }
            case 8: {
                return 13;
            }
            case 14: {
                return 14;
            }
            case 13: {
                return 15;
            }
        }
        throw DbException.getInternalError("type=" + this.objectType);
    }

    public String toString() {
        return "MetaRecord [id=" + this.id + ", objectType=" + this.objectType + ", sql=" + this.sql + ']';
    }
}

