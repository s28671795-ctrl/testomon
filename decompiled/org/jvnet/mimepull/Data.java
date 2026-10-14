/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.nio.ByteBuffer;
import org.jvnet.mimepull.DataFile;
import org.jvnet.mimepull.DataHead;

interface Data {
    public int size();

    public byte[] read();

    public long writeTo(DataFile var1);

    public Data createNext(DataHead var1, ByteBuffer var2);
}

