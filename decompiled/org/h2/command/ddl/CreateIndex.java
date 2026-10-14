/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.Database;
import org.h2.engine.NullsDistinct;
import org.h2.engine.SessionLocal;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.IndexColumn;
import org.h2.table.Table;

public class CreateIndex
extends SchemaCommand {
    private String tableName;
    private String indexName;
    private IndexColumn[] indexColumns;
    private NullsDistinct nullsDistinct;
    private int uniqueColumnCount;
    private boolean primaryKey;
    private boolean hash;
    private boolean spatial;
    private boolean ifTableExists;
    private boolean ifNotExists;
    private String comment;

    public CreateIndex(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setIfTableExists(boolean bl) {
        this.ifTableExists = bl;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setTableName(String string) {
        this.tableName = string;
    }

    public void setIndexName(String string) {
        this.indexName = string;
    }

    public void setIndexColumns(IndexColumn[] indexColumnArray) {
        this.indexColumns = indexColumnArray;
    }

    @Override
    public long update() {
        IndexType indexType;
        Database database = this.getDatabase();
        boolean bl = database.isPersistent();
        Table table = this.getSchema().findTableOrView(this.session, this.tableName);
        if (table == null) {
            if (this.ifTableExists) {
                return 0L;
            }
            throw DbException.get(42102, this.tableName);
        }
        if (this.indexName != null && this.getSchema().findIndex(this.session, this.indexName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(42111, this.indexName);
        }
        this.session.getUser().checkTableRight(table, 32);
        table.lock(this.session, 2);
        if (!table.isPersistIndexes()) {
            bl = false;
        }
        int n = this.getObjectId();
        if (this.indexName == null) {
            this.indexName = this.primaryKey ? table.getSchema().getUniqueIndexName(this.session, table, "PRIMARY_KEY_") : table.getSchema().getUniqueIndexName(this.session, table, "INDEX_");
        }
        if (this.primaryKey) {
            if (table.findPrimaryKey() != null) {
                throw DbException.get(90017);
            }
            indexType = IndexType.createPrimaryKey(bl, this.hash);
        } else {
            indexType = this.uniqueColumnCount > 0 ? IndexType.createUnique(bl, this.hash, this.uniqueColumnCount, this.nullsDistinct) : IndexType.createNonUnique(bl, this.hash, this.spatial);
        }
        IndexColumn.mapColumns(this.indexColumns, table);
        table.addIndex(this.session, this.indexName, n, this.indexColumns, this.uniqueColumnCount, indexType, this.create, this.comment);
        return 0L;
    }

    public void setPrimaryKey(boolean bl) {
        this.primaryKey = bl;
    }

    public void setUnique(NullsDistinct nullsDistinct, int n) {
        this.nullsDistinct = nullsDistinct;
        this.uniqueColumnCount = n;
    }

    public void setHash(boolean bl) {
        this.hash = bl;
    }

    public void setSpatial(boolean bl) {
        this.spatial = bl;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    @Override
    public int getType() {
        return 25;
    }
}

