/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.TableLink;

public class CreateLinkedTable
extends SchemaCommand {
    private String tableName;
    private String driver;
    private String url;
    private String user;
    private String password;
    private String originalSchema;
    private String originalTable;
    private boolean ifNotExists;
    private String comment;
    private boolean emitUpdates;
    private boolean force;
    private boolean temporary;
    private boolean globalTemporary;
    private boolean readOnly;
    private int fetchSize;
    private boolean autocommit = true;

    public CreateLinkedTable(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setTableName(String string) {
        this.tableName = string;
    }

    public void setDriver(String string) {
        this.driver = string;
    }

    public void setOriginalTable(String string) {
        this.originalTable = string;
    }

    public void setPassword(String string) {
        this.password = string;
    }

    public void setUrl(String string) {
        this.url = string;
    }

    public void setUser(String string) {
        this.user = string;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setFetchSize(int n) {
        this.fetchSize = n;
    }

    public void setAutoCommit(boolean bl) {
        this.autocommit = bl;
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        if (this.getSchema().resolveTableOrView(this.session, this.tableName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(42101, this.tableName);
        }
        int n = this.getObjectId();
        TableLink tableLink = this.getSchema().createTableLink(n, this.tableName, this.driver, this.url, this.user, this.password, this.originalSchema, this.originalTable, this.emitUpdates, this.force);
        tableLink.setTemporary(this.temporary);
        tableLink.setGlobalTemporary(this.globalTemporary);
        tableLink.setComment(this.comment);
        tableLink.setReadOnly(this.readOnly);
        if (this.fetchSize > 0) {
            tableLink.setFetchSize(this.fetchSize);
        }
        tableLink.setAutoCommit(this.autocommit);
        if (this.temporary && !this.globalTemporary) {
            this.session.addLocalTempTable(tableLink);
        } else {
            database.addSchemaObject(this.session, tableLink);
        }
        return 0L;
    }

    public void setEmitUpdates(boolean bl) {
        this.emitUpdates = bl;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setForce(boolean bl) {
        this.force = bl;
    }

    public void setTemporary(boolean bl) {
        this.temporary = bl;
    }

    public void setGlobalTemporary(boolean bl) {
        this.globalTemporary = bl;
    }

    public void setReadOnly(boolean bl) {
        this.readOnly = bl;
    }

    public void setOriginalSchema(String string) {
        this.originalSchema = string;
    }

    @Override
    public int getType() {
        return 26;
    }
}

