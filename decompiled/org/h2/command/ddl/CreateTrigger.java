/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.SchemaCommand;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.TriggerObject;
import org.h2.table.Table;

public class CreateTrigger
extends SchemaCommand {
    private String triggerName;
    private boolean ifNotExists;
    private boolean insteadOf;
    private boolean before;
    private int typeMask;
    private boolean rowBased;
    private int queueSize = 1024;
    private boolean noWait;
    private String tableName;
    private String triggerClassName;
    private String triggerSource;
    private boolean force;
    private boolean onRollback;

    public CreateTrigger(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setInsteadOf(boolean bl) {
        this.insteadOf = bl;
    }

    public void setBefore(boolean bl) {
        this.before = bl;
    }

    public void setTriggerClassName(String string) {
        this.triggerClassName = string;
    }

    public void setTriggerSource(String string) {
        this.triggerSource = string;
    }

    public void setTypeMask(int n) {
        this.typeMask = n;
    }

    public void setRowBased(boolean bl) {
        this.rowBased = bl;
    }

    public void setQueueSize(int n) {
        this.queueSize = n;
    }

    public void setNoWait(boolean bl) {
        this.noWait = bl;
    }

    public void setTableName(String string) {
        this.tableName = string;
    }

    public void setTriggerName(String string) {
        this.triggerName = string;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    @Override
    public long update() {
        this.session.getUser().checkAdmin();
        Database database = this.getDatabase();
        if (this.getSchema().findTrigger(this.triggerName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(90041, this.triggerName);
        }
        if ((this.typeMask & 8) != 0) {
            if (this.rowBased) {
                throw DbException.get(90005, "SELECT + FOR EACH ROW");
            }
            if (this.onRollback) {
                throw DbException.get(90005, "SELECT + ROLLBACK");
            }
        } else if ((this.typeMask & 7) == 0) {
            if (this.onRollback) {
                throw DbException.get(90005, "(!INSERT & !UPDATE & !DELETE) + ROLLBACK");
            }
            throw DbException.getInternalError();
        }
        int n = this.getObjectId();
        Table table = this.getSchema().getTableOrView(this.session, this.tableName);
        TriggerObject triggerObject = new TriggerObject(this.getSchema(), n, this.triggerName, table);
        triggerObject.setInsteadOf(this.insteadOf);
        triggerObject.setBefore(this.before);
        triggerObject.setNoWait(this.noWait);
        triggerObject.setQueueSize(this.queueSize);
        triggerObject.setRowBased(this.rowBased);
        triggerObject.setTypeMask(this.typeMask);
        triggerObject.setOnRollback(this.onRollback);
        if (this.triggerClassName != null) {
            triggerObject.setTriggerClassName(this.triggerClassName, this.force);
        } else {
            triggerObject.setTriggerSource(this.triggerSource, this.force);
        }
        database.addSchemaObject(this.session, triggerObject);
        table.addTrigger(triggerObject);
        return 0L;
    }

    public void setForce(boolean bl) {
        this.force = bl;
    }

    public void setOnRollback(boolean bl) {
        this.onRollback = bl;
    }

    @Override
    public int getType() {
        return 31;
    }
}

