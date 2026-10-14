/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value.lob;

import java.io.BufferedInputStream;
import java.io.InputStream;
import org.h2.engine.SessionRemote;
import org.h2.store.DataHandler;
import org.h2.store.LobStorageRemoteInputStream;
import org.h2.value.lob.LobData;

public final class LobDataFetchOnDemand
extends LobData {
    private SessionRemote handler;
    private final int tableId;
    private final long lobId;
    private final byte[] hmac;

    public LobDataFetchOnDemand(DataHandler dataHandler, int n, long l, byte[] byArray) {
        this.hmac = byArray;
        this.handler = (SessionRemote)dataHandler;
        this.tableId = n;
        this.lobId = l;
    }

    @Override
    public boolean isLinkedToTable() {
        throw new IllegalStateException();
    }

    public int getTableId() {
        return this.tableId;
    }

    public long getLobId() {
        return this.lobId;
    }

    @Override
    public InputStream getInputStream(long l) {
        return new BufferedInputStream(new LobStorageRemoteInputStream(this.handler, this.lobId, this.hmac));
    }

    @Override
    public DataHandler getDataHandler() {
        return this.handler;
    }

    public String toString() {
        return "lob-table: table: " + this.tableId + " id: " + this.lobId;
    }
}

