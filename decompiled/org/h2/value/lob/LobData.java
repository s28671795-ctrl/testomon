/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value.lob;

import java.io.InputStream;
import org.h2.store.DataHandler;
import org.h2.value.ValueLob;

public abstract class LobData {
    LobData() {
    }

    public abstract InputStream getInputStream(long var1);

    public DataHandler getDataHandler() {
        return null;
    }

    public boolean isLinkedToTable() {
        return false;
    }

    public void remove(ValueLob valueLob) {
    }

    public int getMemory() {
        return 140;
    }
}

