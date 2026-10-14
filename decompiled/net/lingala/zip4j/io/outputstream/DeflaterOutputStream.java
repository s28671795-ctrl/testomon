/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.io.outputstream;

import java.io.IOException;
import java.util.zip.Deflater;
import net.lingala.zip4j.io.outputstream.CipherOutputStream;
import net.lingala.zip4j.io.outputstream.CompressedOutputStream;
import net.lingala.zip4j.model.enums.CompressionLevel;

class DeflaterOutputStream
extends CompressedOutputStream {
    private byte[] buff;
    protected Deflater deflater;

    public DeflaterOutputStream(CipherOutputStream<?> cipherOutputStream, CompressionLevel compressionLevel, int bufferSize) {
        super(cipherOutputStream);
        this.deflater = new Deflater(compressionLevel.getLevel(), true);
        this.buff = new byte[bufferSize];
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(int bval) throws IOException {
        byte[] b = new byte[]{(byte)bval};
        this.write(b, 0, 1);
    }

    @Override
    public void write(byte[] buf, int off, int len) throws IOException {
        this.deflater.setInput(buf, off, len);
        while (!this.deflater.needsInput()) {
            this.deflate();
        }
    }

    private void deflate() throws IOException {
        int len = this.deflater.deflate(this.buff, 0, this.buff.length);
        if (len > 0) {
            super.write(this.buff, 0, len);
        }
    }

    @Override
    public void closeEntry() throws IOException {
        if (!this.deflater.finished()) {
            this.deflater.finish();
            while (!this.deflater.finished()) {
                this.deflate();
            }
        }
        this.deflater.end();
        super.closeEntry();
    }
}

