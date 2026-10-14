/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import org.apache.activemq.util.ByteSequence;

public final class ByteSequenceData {
    private ByteSequenceData() {
    }

    public static byte[] toByteArray(ByteSequence packet) {
        if (packet.offset == 0 && packet.length == packet.data.length) {
            return packet.data;
        }
        byte[] rc = new byte[packet.length];
        System.arraycopy(packet.data, packet.offset, rc, 0, packet.length);
        return rc;
    }

    private static void spaceNeeded(ByteSequence packet, int i) {
        assert (packet.offset + i <= packet.length);
    }

    public static int remaining(ByteSequence packet) {
        return packet.length - packet.offset;
    }

    public static int read(ByteSequence packet) {
        return packet.data[packet.offset++] & 0xFF;
    }

    public static void readFully(ByteSequence packet, byte[] b) throws IOException {
        ByteSequenceData.readFully(packet, b, 0, b.length);
    }

    public static void readFully(ByteSequence packet, byte[] b, int off, int len) throws IOException {
        ByteSequenceData.spaceNeeded(packet, len);
        System.arraycopy(packet.data, packet.offset, b, off, len);
        packet.offset += len;
    }

    public static int skipBytes(ByteSequence packet, int n) throws IOException {
        int rc = Math.min(n, ByteSequenceData.remaining(packet));
        packet.offset += rc;
        return rc;
    }

    public static boolean readBoolean(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 1);
        return ByteSequenceData.read(packet) != 0;
    }

    public static byte readByte(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 1);
        return (byte)ByteSequenceData.read(packet);
    }

    public static int readUnsignedByte(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 1);
        return ByteSequenceData.read(packet);
    }

    public static short readShortBig(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        return (short)((ByteSequenceData.read(packet) << 8) + (ByteSequenceData.read(packet) << 0));
    }

    public static short readShortLittle(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        return (short)((ByteSequenceData.read(packet) << 0) + (ByteSequenceData.read(packet) << 8));
    }

    public static int readUnsignedShortBig(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        return (ByteSequenceData.read(packet) << 8) + (ByteSequenceData.read(packet) << 0);
    }

    public static int readUnsignedShortLittle(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        return (ByteSequenceData.read(packet) << 0) + (ByteSequenceData.read(packet) << 8);
    }

    public static char readCharBig(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        return (char)((ByteSequenceData.read(packet) << 8) + (ByteSequenceData.read(packet) << 0));
    }

    public static char readCharLittle(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        return (char)((ByteSequenceData.read(packet) << 0) + (ByteSequenceData.read(packet) << 8));
    }

    public static int readIntBig(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 4);
        return (ByteSequenceData.read(packet) << 24) + (ByteSequenceData.read(packet) << 16) + (ByteSequenceData.read(packet) << 8) + (ByteSequenceData.read(packet) << 0);
    }

    public static int readIntLittle(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 4);
        return (ByteSequenceData.read(packet) << 0) + (ByteSequenceData.read(packet) << 8) + (ByteSequenceData.read(packet) << 16) + (ByteSequenceData.read(packet) << 24);
    }

    public static long readLongBig(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 8);
        return ((long)ByteSequenceData.read(packet) << 56) + ((long)ByteSequenceData.read(packet) << 48) + ((long)ByteSequenceData.read(packet) << 40) + ((long)ByteSequenceData.read(packet) << 32) + ((long)ByteSequenceData.read(packet) << 24) + (long)(ByteSequenceData.read(packet) << 16) + (long)(ByteSequenceData.read(packet) << 8) + (long)(ByteSequenceData.read(packet) << 0);
    }

    public static long readLongLittle(ByteSequence packet) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 8);
        return (long)((ByteSequenceData.read(packet) << 0) + (ByteSequenceData.read(packet) << 8) + (ByteSequenceData.read(packet) << 16)) + ((long)ByteSequenceData.read(packet) << 24) + ((long)ByteSequenceData.read(packet) << 32) + ((long)ByteSequenceData.read(packet) << 40) + ((long)ByteSequenceData.read(packet) << 48) + ((long)ByteSequenceData.read(packet) << 56);
    }

    public static double readDoubleBig(ByteSequence packet) throws IOException {
        return Double.longBitsToDouble(ByteSequenceData.readLongBig(packet));
    }

    public static double readDoubleLittle(ByteSequence packet) throws IOException {
        return Double.longBitsToDouble(ByteSequenceData.readLongLittle(packet));
    }

    public static float readFloatBig(ByteSequence packet) throws IOException {
        return Float.intBitsToFloat(ByteSequenceData.readIntBig(packet));
    }

    public static float readFloatLittle(ByteSequence packet) throws IOException {
        return Float.intBitsToFloat(ByteSequenceData.readIntLittle(packet));
    }

    public static void write(ByteSequence packet, int b) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 1);
        packet.data[packet.offset++] = (byte)b;
    }

    public static void write(ByteSequence packet, byte[] b) throws IOException {
        ByteSequenceData.write(packet, b, 0, b.length);
    }

    public static void write(ByteSequence packet, byte[] b, int off, int len) throws IOException {
        ByteSequenceData.spaceNeeded(packet, len);
        System.arraycopy(b, off, packet.data, packet.offset, len);
        packet.offset += len;
    }

    public static void writeBoolean(ByteSequence packet, boolean v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 1);
        ByteSequenceData.write(packet, v ? 1 : 0);
    }

    public static void writeByte(ByteSequence packet, int v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 1);
        ByteSequenceData.write(packet, v);
    }

    public static void writeShortBig(ByteSequence packet, int v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        ByteSequenceData.write(packet, v >>> 8 & 0xFF);
        ByteSequenceData.write(packet, v >>> 0 & 0xFF);
    }

    public static void writeShortLittle(ByteSequence packet, int v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        ByteSequenceData.write(packet, v >>> 0 & 0xFF);
        ByteSequenceData.write(packet, v >>> 8 & 0xFF);
    }

    public static void writeCharBig(ByteSequence packet, int v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        ByteSequenceData.write(packet, v >>> 8 & 0xFF);
        ByteSequenceData.write(packet, v >>> 0 & 0xFF);
    }

    public static void writeCharLittle(ByteSequence packet, int v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 2);
        ByteSequenceData.write(packet, v >>> 0 & 0xFF);
        ByteSequenceData.write(packet, v >>> 8 & 0xFF);
    }

    public static void writeIntBig(ByteSequence packet, int v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 4);
        ByteSequenceData.write(packet, v >>> 24 & 0xFF);
        ByteSequenceData.write(packet, v >>> 16 & 0xFF);
        ByteSequenceData.write(packet, v >>> 8 & 0xFF);
        ByteSequenceData.write(packet, v >>> 0 & 0xFF);
    }

    public static void writeIntLittle(ByteSequence packet, int v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 4);
        ByteSequenceData.write(packet, v >>> 0 & 0xFF);
        ByteSequenceData.write(packet, v >>> 8 & 0xFF);
        ByteSequenceData.write(packet, v >>> 16 & 0xFF);
        ByteSequenceData.write(packet, v >>> 24 & 0xFF);
    }

    public static void writeLongBig(ByteSequence packet, long v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 8);
        ByteSequenceData.write(packet, (int)(v >>> 56) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 48) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 40) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 32) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 24) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 16) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 8) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 0) & 0xFF);
    }

    public static void writeLongLittle(ByteSequence packet, long v) throws IOException {
        ByteSequenceData.spaceNeeded(packet, 8);
        ByteSequenceData.write(packet, (int)(v >>> 0) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 8) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 16) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 24) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 32) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 40) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 48) & 0xFF);
        ByteSequenceData.write(packet, (int)(v >>> 56) & 0xFF);
    }

    public static void writeDoubleBig(ByteSequence packet, double v) throws IOException {
        ByteSequenceData.writeLongBig(packet, Double.doubleToLongBits(v));
    }

    public static void writeDoubleLittle(ByteSequence packet, double v) throws IOException {
        ByteSequenceData.writeLongLittle(packet, Double.doubleToLongBits(v));
    }

    public static void writeFloatBig(ByteSequence packet, float v) throws IOException {
        ByteSequenceData.writeIntBig(packet, Float.floatToIntBits(v));
    }

    public static void writeFloatLittle(ByteSequence packet, float v) throws IOException {
        ByteSequenceData.writeIntLittle(packet, Float.floatToIntBits(v));
    }

    public static void writeRawDoubleBig(ByteSequence packet, double v) throws IOException {
        ByteSequenceData.writeLongBig(packet, Double.doubleToRawLongBits(v));
    }

    public static void writeRawDoubleLittle(ByteSequence packet, double v) throws IOException {
        ByteSequenceData.writeLongLittle(packet, Double.doubleToRawLongBits(v));
    }

    public static void writeRawFloatBig(ByteSequence packet, float v) throws IOException {
        ByteSequenceData.writeIntBig(packet, Float.floatToRawIntBits(v));
    }

    public static void writeRawFloatLittle(ByteSequence packet, float v) throws IOException {
        ByteSequenceData.writeIntLittle(packet, Float.floatToRawIntBits(v));
    }
}

