/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.Transferable;

public class LoadingByteArrayOutputStream
extends ByteArrayOutputStream {
    public LoadingByteArrayOutputStream() {
        super(1024);
    }

    public LoadingByteArrayOutputStream(int i) {
        super(i);
    }

    public ByteArrayInputStream createInputStream() {
        return new LoadedByteArrayInputStream(this.buf, this.count);
    }

    public void setSize(int i) {
        this.count = i;
    }

    @Override
    public byte[] toByteArray() {
        if (this.count != this.buf.length) {
            this.buf = super.toByteArray();
        }
        return this.buf;
    }

    public byte[] getRawBytes() {
        return this.buf;
    }

    private static class LoadedByteArrayInputStream
    extends ByteArrayInputStream
    implements Transferable {
        LoadedByteArrayInputStream(byte[] buf, int length) {
            super(buf, 0, length);
        }

        public String toString() {
            return IOUtils.newStringFromBytes(this.buf, 0, this.count);
        }

        @Override
        public void transferTo(File file) throws IOException {
            OutputStream out = Files.newOutputStream(file.toPath(), new OpenOption[0]);
            WritableByteChannel channel = Channels.newChannel(out);
            ByteBuffer bb = ByteBuffer.wrap(this.buf, 0, this.count);
            while (bb.hasRemaining()) {
                channel.write(bb);
            }
            channel.close();
            out.close();
        }
    }
}

