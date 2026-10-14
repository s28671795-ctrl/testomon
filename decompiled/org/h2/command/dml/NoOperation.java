/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.Prepared;
import org.h2.engine.SessionLocal;
import org.h2.result.ResultInterface;

public class NoOperation
extends Prepared {
    public NoOperation(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public long update() {
        return 0L;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public boolean needRecompile() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }

    @Override
    public int getType() {
        return 63;
    }
}

