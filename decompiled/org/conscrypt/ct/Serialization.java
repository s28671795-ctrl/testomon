/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.ct;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.conscrypt.ct.SerializationException;

public class Serialization {
    private static final int DER_TAG_MASK = 63;
    private static final int DER_TAG_OCTET_STRING = 4;
    private static final int DER_LENGTH_LONG_FORM_FLAG = 128;

    private Serialization() {
    }

    public static byte[] readDEROctetString(byte[] input) throws SerializationException {
        return Serialization.readDEROctetString(new ByteArrayInputStream(input));
    }

    public static byte[] readDEROctetString(InputStream input) throws SerializationException {
        int tag = Serialization.readByte(input) & 0x3F;
        if (tag != 4) {
            throw new SerializationException("Wrong DER tag, expected OCTET STRING, got " + tag);
        }
        int width = Serialization.readNumber(input, 1);
        int length = (width & 0x80) != 0 ? Serialization.readNumber(input, width & 0xFFFFFF7F) : width;
        return Serialization.readFixedBytes(input, length);
    }

    public static byte[][] readList(byte[] input, int listWidth, int elemWidth) throws SerializationException {
        return Serialization.readList(new ByteArrayInputStream(input), listWidth, elemWidth);
    }

    public static byte[][] readList(InputStream input, int listWidth, int elemWidth) throws SerializationException {
        ArrayList<byte[]> result = new ArrayList<byte[]>();
        byte[] data = Serialization.readVariableBytes(input, listWidth);
        input = new ByteArrayInputStream(data);
        try {
            while (input.available() > 0) {
                result.add(Serialization.readVariableBytes(input, elemWidth));
            }
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
        return (byte[][])result.toArray((T[])new byte[result.size()][]);
    }

    public static byte[] readVariableBytes(InputStream input, int width) throws SerializationException {
        int length = Serialization.readNumber(input, width);
        return Serialization.readFixedBytes(input, length);
    }

    public static byte[] readFixedBytes(InputStream input, int length) throws SerializationException {
        try {
            if (length < 0) {
                throw new SerializationException("Negative length: " + length);
            }
            byte[] data = new byte[length];
            int count = input.read(data);
            if (count < length) {
                throw new SerializationException("Premature end of input, expected " + length + " bytes, only read " + count);
            }
            return data;
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static int readNumber(InputStream input, int width) throws SerializationException {
        if (width > 4 || width < 0) {
            throw new SerializationException("Invalid width: " + width);
        }
        int result = 0;
        for (int i = 0; i < width; ++i) {
            result = result << 8 | Serialization.readByte(input) & 0xFF;
        }
        return result;
    }

    public static long readLong(InputStream input, int width) throws SerializationException {
        if (width > 8 || width < 0) {
            throw new IllegalArgumentException("Invalid width: " + width);
        }
        long result = 0L;
        for (int i = 0; i < width; ++i) {
            result = result << 8 | (long)(Serialization.readByte(input) & 0xFF);
        }
        return result;
    }

    public static byte readByte(InputStream input) throws SerializationException {
        try {
            int b = input.read();
            if (b == -1) {
                throw new SerializationException("Premature end of input, could not read byte.");
            }
            return (byte)b;
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static void writeVariableBytes(OutputStream output, byte[] data, int width) throws SerializationException {
        Serialization.writeNumber(output, data.length, width);
        Serialization.writeFixedBytes(output, data);
    }

    public static void writeFixedBytes(OutputStream output, byte[] data) throws SerializationException {
        try {
            output.write(data);
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static void writeNumber(OutputStream output, long value, int width) throws SerializationException {
        if (width < 0) {
            throw new SerializationException("Negative width: " + width);
        }
        if (width < 8 && value >= 1L << 8 * width) {
            throw new SerializationException("Number too large, " + value + " does not fit in " + width + " bytes");
        }
        try {
            while (width > 0) {
                long shift = (long)(width - 1) * 8L;
                if (shift < 64L) {
                    output.write((byte)(value >> (int)shift & 0xFFL));
                } else {
                    output.write(0);
                }
                --width;
            }
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}

