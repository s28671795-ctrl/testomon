/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.table.TableView;

public class AlterView
extends DefineCommand {
    private boolean ifExists;
    private TableView view;

    public AlterView(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setIfExists(boolean bl) {
        this.ifExists = bl;
    }

    public void setView(TableView tableView) {
        this.view = tableView;
    }

    @Override
    public long update() {
        if (this.view == null && this.ifExists) {
            return 0L;
        }
        this.session.getUser().checkSchemaOwner(this.view.getSchema());
        DbException dbException = this.view.recompile(this.session, false, true);
        if (dbException != null) {
            throw dbException;
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 20;
    }
}

