/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.InputStream;
import org.h2.engine.SessionRemote;
import org.h2.message.DbException;
import org.h2.mvstore.DataUtils;

public class LobStorageRemoteInputStream
extends InputStream {
    private final SessionRemote sessionRemote;
    private final long lobId;
    private final byte[] hmac;
    private long pos;

    public LobStorageRemoteInputStream(SessionRemote sessionRemote, long l, byte[] byArray) {
        this.sessionRemote = sessionRemote;
        this.lobId = l;
        this.hmac = byArray;
    }

    @Override
    public int read() throws IOException {
        byte[] byArray = new byte[1];
        int n = this.read(byArray, 0, 1);
        return n < 0 ? n : byArray[0] & 0xFF;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        assert (n2 >= 0);
        if (n2 == 0) {
            return 0;
        }
        try {
            n2 = this.sessionRemote.readLob(this.lobId, this.hmac, this.pos, byArray, n, n2);
        }
        catch (DbException dbException) {
            throw DataUtils.convertToIOException(dbException);
        }
        if (n2 == 0) {
            return -1;
        }
        this.pos += (long)n2;
        return n2;
    }
}

