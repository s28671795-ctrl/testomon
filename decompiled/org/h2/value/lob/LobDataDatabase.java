/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value.lob;

import java.io.IOException;
import java.io.InputStream;
import org.h2.message.DbException;
import org.h2.store.DataHandler;
import org.h2.value.ValueLob;
import org.h2.value.lob.LobData;

public final class LobDataDatabase
extends LobData {
    private final DataHandler handler;
    private final int tableId;
    private final long lobId;

    public LobDataDatabase(DataHandler dataHandler, int n, long l) {
        this.handler = dataHandler;
        this.tableId = n;
        this.lobId = l;
    }

    @Override
    public void remove(ValueLob valueLob) {
        if (this.handler != null) {
            this.handler.getLobStorage().removeLob(valueLob);
        }
    }

    @Override
    public boolean isLinkedToTable() {
        return this.tableId >= 0;
    }

    public int getTableId() {
        return this.tableId;
    }

    public long getLobId() {
        return this.lobId;
    }

    @Override
    public InputStream getInputStream(long l) {
        try {
            return this.handler.getLobStorage().getInputStream(this.lobId, this.tableId, l);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, this.toString());
        }
    }

    @Override
    public DataHandler getDataHandler() {
        return this.handler;
    }

    public String toString() {
        return "lob-table: table: " + this.tableId + " id: " + this.lobId;
    }
}

