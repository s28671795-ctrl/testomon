/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.Prepared;
import org.h2.engine.SessionLocal;
import org.h2.result.ResultInterface;

public abstract class DefineCommand
extends Prepared {
    protected boolean transactional;

    DefineCommand(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }

    public void setTransactional(boolean bl) {
        this.transactional = bl;
    }

    @Override
    public boolean isTransactional() {
        return this.transactional;
    }

    @Override
    public boolean isRetryable() {
        return false;
    }
}

