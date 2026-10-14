/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.Prepared;
import org.h2.engine.IsolationLevel;
import org.h2.engine.SessionLocal;
import org.h2.result.ResultInterface;

public class SetSessionCharacteristics
extends Prepared {
    private final IsolationLevel isolationLevel;

    public SetSessionCharacteristics(SessionLocal sessionLocal, IsolationLevel isolationLevel) {
        super(sessionLocal);
        this.isolationLevel = isolationLevel;
    }

    @Override
    public boolean isTransactional() {
        return false;
    }

    @Override
    public long update() {
        this.session.setIsolationLevel(this.isolationLevel);
        return 0L;
    }

    @Override
    public boolean needRecompile() {
        return false;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }

    @Override
    public int getType() {
        return 67;
    }
}

