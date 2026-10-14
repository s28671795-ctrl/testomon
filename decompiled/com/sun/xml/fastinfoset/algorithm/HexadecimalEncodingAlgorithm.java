/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.fastinfoset.algorithm;

import com.sun.xml.fastinfoset.CommonResourceBundle;
import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jvnet.fastinfoset.EncodingAlgorithmException;

public class HexadecimalEncodingAlgorithm
extends BuiltInEncodingAlgorithm {
    private static final char[] NIBBLE_TO_HEXADECIMAL_TABLE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int[] HEXADECIMAL_TO_NIBBLE_TABLE = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15};

    @Override
    public final Object decodeFromBytes(byte[] b, int start2, int length) throws EncodingAlgorithmException {
        byte[] data = new byte[length];
        System.arraycopy(b, start2, data, 0, length);
        return data;
    }

    @Override
    public final Object decodeFromInputStream(InputStream s2) throws IOException {
        throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.notImplemented"));
    }

    @Override
    public void encodeToOutputStream(Object data, OutputStream s2) throws IOException {
        if (!(data instanceof byte[])) {
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotByteArray"));
        }
        s2.write((byte[])data);
    }

    @Override
    public final Object convertFromCharacters(char[] ch, int start2, int length) {
        if (length == 0) {
            return new byte[0];
        }
        StringBuilder encodedValue = this.removeWhitespace(ch, start2, length);
        int encodedLength = encodedValue.length();
        if (encodedLength == 0) {
            return new byte[0];
        }
        int valueLength = encodedValue.length() / 2;
        byte[] value = new byte[valueLength];
        int encodedIdx = 0;
        for (int i = 0; i < valueLength; ++i) {
            int nibble1 = HEXADECIMAL_TO_NIBBLE_TABLE[encodedValue.charAt(encodedIdx++) - 48];
            int nibble2 = HEXADECIMAL_TO_NIBBLE_TABLE[encodedValue.charAt(encodedIdx++) - 48];
            value[i] = (byte)(nibble1 << 4 | nibble2);
        }
        return value;
    }

    @Override
    public final void convertToCharacters(Object data, StringBuffer s2) {
        if (data == null) {
            return;
        }
        byte[] value = (byte[])data;
        if (value.length == 0) {
            return;
        }
        s2.ensureCapacity(value.length * 2);
        for (int i = 0; i < value.length; ++i) {
            s2.append(NIBBLE_TO_HEXADECIMAL_TABLE[value[i] >>> 4 & 0xF]);
            s2.append(NIBBLE_TO_HEXADECIMAL_TABLE[value[i] & 0xF]);
        }
    }

    @Override
    public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
        return octetLength * 2;
    }

    @Override
    public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
        return primitiveLength / 2;
    }

    @Override
    public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start2) {
        System.arraycopy((byte[])array, astart, b, start2, alength);
    }
}

