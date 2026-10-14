/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Sequence;
import org.h2.table.Column;
import org.h2.table.Table;

public class TruncateTable
extends DefineCommand {
    private Table table;
    private boolean restart;

    public TruncateTable(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setRestart(boolean bl) {
        this.restart = bl;
    }

    @Override
    public long update() {
        if (!this.table.canTruncate()) {
            throw DbException.get(90106, this.table.getTraceSQL());
        }
        this.session.getUser().checkTableRight(this.table, 2);
        this.table.lock(this.session, 2);
        long l = this.table.truncate(this.session);
        if (this.restart) {
            for (Column column : this.table.getColumns()) {
                Sequence sequence2 = column.getSequence();
                if (sequence2 == null) continue;
                sequence2.modify(sequence2.getStartValue(), null, null, null, null, null, null);
                this.getDatabase().updateMeta(this.session, sequence2);
            }
        }
        return l;
    }

    @Override
    public int getType() {
        return 53;
    }
}

