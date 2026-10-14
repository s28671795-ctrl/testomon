/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.h2.engine.SessionRemote;
import org.h2.store.LobStorageInterface;
import org.h2.value.ValueBlob;
import org.h2.value.ValueClob;
import org.h2.value.ValueLob;

public class LobStorageFrontend
implements LobStorageInterface {
    public static final int TABLE_ID_SESSION_VARIABLE = -1;
    public static final int TABLE_TEMP = -2;
    public static final int TABLE_RESULT = -3;
    private final SessionRemote sessionRemote;

    public LobStorageFrontend(SessionRemote sessionRemote) {
        this.sessionRemote = sessionRemote;
    }

    @Override
    public void removeLob(ValueLob valueLob) {
    }

    @Override
    public InputStream getInputStream(long l, long l2) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public InputStream getInputStream(long l, int n, long l2) throws IOException {
        throw new IllegalStateException();
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public ValueLob copyLob(ValueLob valueLob, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAllForTable(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValueBlob createBlob(InputStream inputStream2, long l) {
        return ValueBlob.createTempBlob(inputStream2, l, this.sessionRemote);
    }

    @Override
    public ValueClob createClob(Reader reader, long l) {
        return ValueClob.createTempClob(reader, l, this.sessionRemote);
    }
}

