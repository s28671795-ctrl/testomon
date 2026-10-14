/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.fastinfoset.algorithm;

import com.sun.xml.fastinfoset.CommonResourceBundle;
import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
import com.sun.xml.fastinfoset.algorithm.IEEE754FloatingPointEncodingAlgorithm;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import org.jvnet.fastinfoset.EncodingAlgorithmException;

public class DoubleEncodingAlgorithm
extends IEEE754FloatingPointEncodingAlgorithm {
    @Override
    public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
        if (octetLength % 8 != 0) {
            throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthIsNotMultipleOfDouble", new Object[]{8}));
        }
        return octetLength / 8;
    }

    @Override
    public int getOctetLengthFromPrimitiveLength(int primitiveLength) {
        return primitiveLength * 8;
    }

    @Override
    public final Object decodeFromBytes(byte[] b, int start2, int length) throws EncodingAlgorithmException {
        double[] data = new double[this.getPrimtiveLengthFromOctetLength(length)];
        this.decodeFromBytesToDoubleArray(data, 0, b, start2, length);
        return data;
    }

    @Override
    public final Object decodeFromInputStream(InputStream s2) throws IOException {
        return this.decodeFromInputStreamToDoubleArray(s2);
    }

    @Override
    public void encodeToOutputStream(Object data, OutputStream s2) throws IOException {
        if (!(data instanceof double[])) {
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotDouble"));
        }
        double[] fdata = (double[])data;
        this.encodeToOutputStreamFromDoubleArray(fdata, s2);
    }

    @Override
    public final Object convertFromCharacters(char[] ch, int start2, int length) {
        final CharBuffer cb = CharBuffer.wrap(ch, start2, length);
        final ArrayList doubleList = new ArrayList();
        this.matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener(){

            @Override
            public void word(int start2, int end) {
                String fStringValue = cb.subSequence(start2, end).toString();
                doubleList.add(Double.valueOf(fStringValue));
            }
        });
        return this.generateArrayFromList(doubleList);
    }

    @Override
    public final void convertToCharacters(Object data, StringBuffer s2) {
        if (!(data instanceof double[])) {
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotDouble"));
        }
        double[] fdata = (double[])data;
        this.convertToCharactersFromDoubleArray(fdata, s2);
    }

    public final void decodeFromBytesToDoubleArray(double[] data, int fstart, byte[] b, int start2, int length) {
        int size = length / 8;
        for (int i = 0; i < size; ++i) {
            long bits = (long)(b[start2++] & 0xFF) << 56 | (long)(b[start2++] & 0xFF) << 48 | (long)(b[start2++] & 0xFF) << 40 | (long)(b[start2++] & 0xFF) << 32 | (long)(b[start2++] & 0xFF) << 24 | (long)(b[start2++] & 0xFF) << 16 | (long)(b[start2++] & 0xFF) << 8 | (long)(b[start2++] & 0xFF);
            data[fstart++] = Double.longBitsToDouble(bits);
        }
    }

    public final double[] decodeFromInputStreamToDoubleArray(InputStream s2) throws IOException {
        ArrayList<Double> doubleList = new ArrayList<Double>();
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
            long bits = (long)(b[0] & 0xFF) << 56 | (long)(b[1] & 0xFF) << 48 | (long)(b[2] & 0xFF) << 40 | (long)(b[3] & 0xFF) << 32 | (long)((b[4] & 0xFF) << 24) | (long)((b[5] & 0xFF) << 16) | (long)((b[6] & 0xFF) << 8) | (long)(b[7] & 0xFF);
            doubleList.add(Double.longBitsToDouble(bits));
        }
        return this.generateArrayFromList(doubleList);
    }

    public final void encodeToOutputStreamFromDoubleArray(double[] fdata, OutputStream s2) throws IOException {
        for (int i = 0; i < fdata.length; ++i) {
            long bits = Double.doubleToLongBits(fdata[i]);
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
        this.encodeToBytesFromDoubleArray((double[])array, astart, alength, b, start2);
    }

    public final void encodeToBytesFromDoubleArray(double[] fdata, int fstart, int flength, byte[] b, int start2) {
        int fend = fstart + flength;
        for (int i = fstart; i < fend; ++i) {
            long bits = Double.doubleToLongBits(fdata[i]);
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

    public final void convertToCharactersFromDoubleArray(double[] fdata, StringBuffer s2) {
        int end = fdata.length - 1;
        for (int i = 0; i <= end; ++i) {
            s2.append(Double.toString(fdata[i]));
            if (i == end) continue;
            s2.append(' ');
        }
    }

    public final double[] generateArrayFromList(List array) {
        double[] fdata = new double[array.size()];
        for (int i = 0; i < fdata.length; ++i) {
            fdata[i] = (Double)array.get(i);
        }
        return fdata;
    }
}

