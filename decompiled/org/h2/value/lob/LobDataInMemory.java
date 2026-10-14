/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value.lob;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.h2.value.lob.LobData;

public final class LobDataInMemory
extends LobData {
    private final byte[] small;

    public LobDataInMemory(byte[] byArray) {
        if (byArray == null) {
            throw new IllegalStateException();
        }
        this.small = byArray;
    }

    @Override
    public InputStream getInputStream(long l) {
        return new ByteArrayInputStream(this.small);
    }

    public byte[] getSmall() {
        return this.small;
    }

    @Override
    public int getMemory() {
        return this.small.length + 127;
    }
}

