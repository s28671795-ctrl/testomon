/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;

public interface OutputStreamWithSplitZipSupport {
    public long getFilePointer() throws IOException;

    public int getCurrentSplitFileCounter();
}

