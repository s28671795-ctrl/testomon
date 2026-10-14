/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.tukaani.xz.LZMA2Options
 *  org.tukaani.xz.LZMAOutputStream
 */
package org.apache.commons.compress.compressors.lzma;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.LZMAOutputStream;

public class LZMACompressorOutputStream
extends CompressorOutputStream {
    private final LZMAOutputStream out;

    public LZMACompressorOutputStream(OutputStream outputStream2) throws IOException {
        this.out = new LZMAOutputStream(outputStream2, new LZMA2Options(), -1L);
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }

    public void finish() throws IOException {
        this.out.finish();
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void write(byte[] buf, int off, int len) throws IOException {
        this.out.write(buf, off, len);
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
    }
}

