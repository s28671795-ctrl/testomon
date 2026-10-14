/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.Zip4jUtil;

public class RawIO {
    private final byte[] shortBuff = new byte[2];
    private final byte[] intBuff = new byte[4];
    private final byte[] longBuff = new byte[8];

    public long readLongLittleEndian(RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.readFully(this.longBuff);
        return this.readLongLittleEndian(this.longBuff, 0);
    }

    public long readLongLittleEndian(RandomAccessFile randomAccessFile, int readLen) throws IOException {
        this.resetBytes(this.longBuff);
        randomAccessFile.readFully(this.longBuff, 0, readLen);
        return this.readLongLittleEndian(this.longBuff, 0);
    }

    public long readLongLittleEndian(InputStream inputStream2) throws IOException {
        this.readFully(inputStream2, this.longBuff, this.longBuff.length);
        return this.readLongLittleEndian(this.longBuff, 0);
    }

    public long readLongLittleEndian(InputStream inputStream2, int readLen) throws IOException {
        this.resetBytes(this.longBuff);
        this.readFully(inputStream2, this.longBuff, readLen);
        return this.readLongLittleEndian(this.longBuff, 0);
    }

    public long readLongLittleEndian(byte[] array, int pos) {
        if (array.length - pos < 8) {
            this.resetBytes(this.longBuff);
        }
        System.arraycopy(array, pos, this.longBuff, 0, Math.min(array.length - pos, 8));
        long temp = 0L;
        temp |= (long)(this.longBuff[7] & 0xFF);
        temp <<= 8;
        temp |= (long)(this.longBuff[6] & 0xFF);
        temp <<= 8;
        temp |= (long)(this.longBuff[5] & 0xFF);
        temp <<= 8;
        temp |= (long)(this.longBuff[4] & 0xFF);
        temp <<= 8;
        temp |= (long)(this.longBuff[3] & 0xFF);
        temp <<= 8;
        temp |= (long)(this.longBuff[2] & 0xFF);
        temp <<= 8;
        temp |= (long)(this.longBuff[1] & 0xFF);
        temp <<= 8;
        return temp |= (long)(this.longBuff[0] & 0xFF);
    }

    public int readIntLittleEndian(RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.readFully(this.intBuff);
        return this.readIntLittleEndian(this.intBuff);
    }

    public int readIntLittleEndian(InputStream inputStream2) throws IOException {
        this.readFully(inputStream2, this.intBuff, 4);
        return this.readIntLittleEndian(this.intBuff);
    }

    public int readIntLittleEndian(byte[] b) {
        return this.readIntLittleEndian(b, 0);
    }

    public int readIntLittleEndian(byte[] b, int pos) {
        return b[pos] & 0xFF | (b[1 + pos] & 0xFF) << 8 | (b[2 + pos] & 0xFF | (b[3 + pos] & 0xFF) << 8) << 16;
    }

    public int readShortLittleEndian(RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.readFully(this.shortBuff);
        return this.readShortLittleEndian(this.shortBuff, 0);
    }

    public int readShortLittleEndian(InputStream inputStream2) throws IOException {
        this.readFully(inputStream2, this.shortBuff, this.shortBuff.length);
        return this.readShortLittleEndian(this.shortBuff, 0);
    }

    public int readShortLittleEndian(byte[] buff, int position) {
        return buff[position] & 0xFF | (buff[1 + position] & 0xFF) << 8;
    }

    public void writeShortLittleEndian(OutputStream outputStream2, int value) throws IOException {
        this.writeShortLittleEndian(this.shortBuff, 0, value);
        outputStream2.write(this.shortBuff);
    }

    public void writeShortLittleEndian(byte[] array, int pos, int value) {
        array[pos + 1] = (byte)(value >>> 8);
        array[pos] = (byte)(value & 0xFF);
    }

    public void writeIntLittleEndian(OutputStream outputStream2, int value) throws IOException {
        this.writeIntLittleEndian(this.intBuff, 0, value);
        outputStream2.write(this.intBuff);
    }

    public void writeIntLittleEndian(byte[] array, int pos, int value) {
        array[pos + 3] = (byte)(value >>> 24);
        array[pos + 2] = (byte)(value >>> 16);
        array[pos + 1] = (byte)(value >>> 8);
        array[pos] = (byte)(value & 0xFF);
    }

    public void writeLongLittleEndian(OutputStream outputStream2, long value) throws IOException {
        this.writeLongLittleEndian(this.longBuff, 0, value);
        outputStream2.write(this.longBuff);
    }

    public void writeLongLittleEndian(byte[] array, int pos, long value) {
        array[pos + 7] = (byte)(value >>> 56);
        array[pos + 6] = (byte)(value >>> 48);
        array[pos + 5] = (byte)(value >>> 40);
        array[pos + 4] = (byte)(value >>> 32);
        array[pos + 3] = (byte)(value >>> 24);
        array[pos + 2] = (byte)(value >>> 16);
        array[pos + 1] = (byte)(value >>> 8);
        array[pos] = (byte)(value & 0xFFL);
    }

    private void readFully(InputStream inputStream2, byte[] buff, int readLen) throws IOException {
        int actualReadLength = Zip4jUtil.readFully(inputStream2, buff, 0, readLen);
        if (actualReadLength != readLen) {
            throw new ZipException("Could not fill buffer");
        }
    }

    private void resetBytes(byte[] b) {
        Arrays.fill(b, (byte)0);
    }
}

