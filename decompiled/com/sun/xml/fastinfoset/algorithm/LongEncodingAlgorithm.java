/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.fastinfoset.algorithm;

import com.sun.xml.fastinfoset.CommonResourceBundle;
import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
import com.sun.xml.fastinfoset.algorithm.IntegerEncodingAlgorithm;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import org.jvnet.fastinfoset.EncodingAlgorithmException;

public class LongEncodingAlgorithm
extends IntegerEncodingAlgorithm {
    @Override
    public int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
        if (octetLength % 8 != 0) {
            throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthNotMultipleOfLong", new Object[]{8}));
        }
        return octetLength / 8;
    }

    @Override
    public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
        return primitiveLength * 8;
    }

    @Override
    public final Object decodeFromBytes(byte[] b, int start2, int length) throws EncodingAlgorithmException {
        long[] data = new long[this.getPrimtiveLengthFromOctetLength(length)];
        this.decodeFromBytesToLongArray(data, 0, b, start2, length);
        return data;
    }

    @Override
    public final Object decodeFromInputStream(InputStream s2) throws IOException {
        return this.decodeFromInputStreamToIntArray(s2);
    }

    @Override
    public void encodeToOutputStream(Object data, OutputStream s2) throws IOException {
        if (!(data instanceof long[])) {
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotLongArray"));
        }
        long[] ldata = (long[])data;
        this.encodeToOutputStreamFromLongArray(ldata, s2);
    }

    @Override
    public Object convertFromCharacters(char[] ch, int start2, int length) {
        final CharBuffer cb = CharBuffer.wrap(ch, start2, length);
        final ArrayList longList = new ArrayList();
        this.matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener(){

            @Override
            public void word(int start2, int end) {
                String lStringValue = cb.subSequence(start2, end).toString();
                longList.add(Long.valueOf(lStringValue));
            }
        });
        return this.generateArrayFromList(longList);
    }

    @Override
    public void convertToCharacters(Object data, StringBuffer s2) {
        if (!(data instanceof long[])) {
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotLongArray"));
        }
        long[] ldata = (long[])data;
        this.convertToCharactersFromLongArray(ldata, s2);
    }

    public final void decodeFromBytesToLongArray(long[] ldata, int istart, byte[] b, int start2, int length) {
        int size = length / 8;
        for (int i = 0; i < size; ++i) {
            ldata[istart++] = (long)(b[start2++] & 0xFF) << 56 | (long)(b[start2++] & 0xFF) << 48 | (long)(b[start2++] & 0xFF) << 40 | (long)(b[start2++] & 0xFF) << 32 | (long)(b[start2++] & 0xFF) << 24 | (long)(b[start2++] & 0xFF) << 16 | (long)(b[start2++] & 0xFF) << 8 | (long)(b[start2++] & 0xFF);
        }
    }

    public final long[] decodeFromInputStreamToIntArray(InputStream s2) throws IOException {
        ArrayList<Long> longList = new ArrayList<Long>();
        byte[] b = new byte[8];
        while (true) {
            int n;
            if ((n = s2.read(b)) != 8) {
                if (n == -1) break;
                while (n != 8) {
                    int m4 = s2.read(b, n, 8 - n);
                    if (m4 == -1) {
                        throw new EOFException();
                    }
                    n += m4;
                }
            }
            long l = ((long)b[0] << 56) + ((long)(b[1] & 0xFF) << 48) + ((long)(b[2] & 0xFF) << 40) + ((long)(b[3] & 0xFF) << 32) + ((long)(b[4] & 0xFF) << 24) + (long)((b[5] & 0xFF) << 16) + (long)((b[6] & 0xFF) << 8) + (long)((b[7] & 0xFF) << 0);
            longList.add(l);
        }
        return this.generateArrayFromList(longList);
    }

    public final void encodeToOutputStreamFromLongArray(long[] ldata, OutputStream s2) throws IOException {
        for (int i = 0; i < ldata.length; ++i) {
            long bits = ldata[i];
            s2.write((int)(bits >>> 56 & 0xFFL));
            s2.write((int)(bits >>> 48 & 0xFFL));
            s2.write((int)(bits >>> 40 & 0xFFL));
            s2.write((int)(bits >>> 32 & 0xFFL));
            s2.write((int)(bits >>> 24 & 0xFFL));
            s2.write((int)(bits >>> 16 & 0xFFL));
            s2.write((int)(bits >>> 8 & 0xFFL));
            s2.write((int)(bits & 0xFFL));
        }
    }

    @Override
    public final void encodeToBytes(Object array, int astart, int alength, byte[] b, int start2) {
        this.encodeToBytesFromLongArray((long[])array, astart, alength, b, start2);
    }

    public final void encodeToBytesFromLongArray(long[] ldata, int lstart, int llength, byte[] b, int start2) {
        int lend = lstart + llength;
        for (int i = lstart; i < lend; ++i) {
            long bits = ldata[i];
            b[start2++] = (byte)(bits >>> 56 & 0xFFL);
            b[start2++] = (byte)(bits >>> 48 & 0xFFL);
            b[start2++] = (byte)(bits >>> 40 & 0xFFL);
            b[start2++] = (byte)(bits >>> 32 & 0xFFL);
            b[start2++] = (byte)(bits >>> 24 & 0xFFL);
            b[start2++] = (byte)(bits >>> 16 & 0xFFL);
            b[start2++] = (byte)(bits >>> 8 & 0xFFL);
            b[start2++] = (byte)(bits & 0xFFL);
        }
    }

    public final void convertToCharactersFromLongArray(long[] ldata, StringBuffer s2) {
        int end = ldata.length - 1;
        for (int i = 0; i <= end; ++i) {
            s2.append(Long.toString(ldata[i]));
            if (i == end) continue;
            s2.append(' ');
        }
    }

    public final long[] generateArrayFromList(List array) {
        long[] ldata = new long[array.size()];
        for (int i = 0; i < ldata.length; ++i) {
            ldata[i] = (Long)array.get(i);
        }
        return ldata;
    }
}

