/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.h2.value.ValueBlob;
import org.h2.value.ValueClob;
import org.h2.value.ValueLob;

public interface LobStorageInterface {
    public ValueClob createClob(Reader var1, long var2);

    public ValueBlob createBlob(InputStream var1, long var2);

    public ValueLob copyLob(ValueLob var1, int var2);

    public InputStream getInputStream(long var1, long var3) throws IOException;

    public InputStream getInputStream(long var1, int var3, long var4) throws IOException;

    public void removeLob(ValueLob var1);

    public void removeAllForTable(int var1);

    public boolean isReadOnly();

    default public void close() {
    }
}

