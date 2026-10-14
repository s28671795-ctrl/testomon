/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.CreateTable;
import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.command.ddl.TruncateTable;
import org.h2.engine.SessionLocal;
import org.h2.schema.Schema;
import org.h2.table.MaterializedView;

public class RefreshMaterializedView
extends SchemaOwnerCommand {
    private MaterializedView view;

    public RefreshMaterializedView(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setView(MaterializedView materializedView) {
        this.view = materializedView;
    }

    @Override
    long update(Schema schema) {
        TruncateTable truncateTable = new TruncateTable(this.session);
        truncateTable.setTable(this.view.getUnderlyingTable());
        truncateTable.update();
        CreateTable createTable = new CreateTable(this.session, schema);
        createTable.setQuery(this.view.getSelect());
        createTable.insertAsData(this.view.getUnderlyingTable());
        this.view.setModified();
        return 0L;
    }

    @Override
    public int getType() {
        return 103;
    }
}

