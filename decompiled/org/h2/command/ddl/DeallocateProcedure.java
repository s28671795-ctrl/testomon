/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.SessionLocal;

public class DeallocateProcedure
extends DefineCommand {
    private String procedureName;

    public DeallocateProcedure(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public long update() {
        this.session.removeProcedure(this.procedureName);
        return 0L;
    }

    public void setProcedureName(String string) {
        this.procedureName = string;
    }

    @Override
    public int getType() {
        return 35;
    }
}

