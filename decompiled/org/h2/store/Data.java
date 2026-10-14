/*
 * Decompiled with CFR 0.152.
 */
package org.h2.store;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import org.h2.util.Bits;
import org.h2.util.MathUtils;
import org.h2.util.Utils;

public class Data {
    private byte[] data;
    private int pos;

    private Data(byte[] byArray) {
        this.data = byArray;
    }

    public void writeInt(int n) {
        Bits.writeInt(this.data, this.pos, n);
        this.pos += 4;
    }

    public int readInt() {
        int n = Bits.readInt(this.data, this.pos);
        this.pos += 4;
        return n;
    }

    private void writeStringWithoutLength(char[] cArray, int n) {
        int n2 = this.pos;
        byte[] byArray = this.data;
        for (int i = 0; i < n; ++i) {
            char c = cArray[i];
            if (c < '\u0080') {
                byArray[n2++] = (byte)c;
                continue;
            }
            if (c >= '\u0800') {
                byArray[n2++] = (byte)(0xE0 | c >> 12);
                byArray[n2++] = (byte)(c >> 6 & 0x3F);
                byArray[n2++] = (byte)(c & 0x3F);
                continue;
            }
            byArray[n2++] = (byte)(0xC0 | c >> 6);
            byArray[n2++] = (byte)(c & 0x3F);
        }
        this.pos = n2;
    }

    public static Data create(int n) {
        return new Data(new byte[n]);
    }

    public int length() {
        return this.pos;
    }

    public byte[] getBytes() {
        return this.data;
    }

    public void reset() {
        this.pos = 0;
    }

    public void write(byte[] byArray, int n, int n2) {
        System.arraycopy(byArray, n, this.data, this.pos, n2);
        this.pos += n2;
    }

    public void read(byte[] byArray, int n, int n2) {
        System.arraycopy(this.data, this.pos, byArray, n, n2);
        this.pos += n2;
    }

    public void setPos(int n) {
        this.pos = n;
    }

    public byte readByte() {
        return this.data[this.pos++];
    }

    public void checkCapacity(int n) {
        if (this.pos + n >= this.data.length) {
            this.expand(n);
        }
    }

    private void expand(int n) {
        this.data = Utils.copyBytes(this.data, (this.data.length + n) * 2);
    }

    public void fillAligned() {
        int n;
        this.pos = n = MathUtils.roundUpInt(this.pos + 2, 16);
        if (this.data.length < n) {
            this.checkCapacity(n - this.data.length);
        }
    }

    public static void copyString(Reader reader, OutputStream outputStream2) throws IOException {
        int n;
        char[] cArray = new char[4096];
        Data data = new Data(new byte[12288]);
        while ((n = reader.read(cArray)) >= 0) {
            data.writeStringWithoutLength(cArray, n);
            outputStream2.write(data.data, 0, data.pos);
            data.reset();
        }
    }
}

