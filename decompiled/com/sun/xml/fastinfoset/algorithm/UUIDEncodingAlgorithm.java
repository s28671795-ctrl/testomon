/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.fastinfoset.algorithm;

import com.sun.xml.fastinfoset.CommonResourceBundle;
import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
import com.sun.xml.fastinfoset.algorithm.LongEncodingAlgorithm;
import java.nio.CharBuffer;
import java.util.ArrayList;
import org.jvnet.fastinfoset.EncodingAlgorithmException;

public class UUIDEncodingAlgorithm
extends LongEncodingAlgorithm {
    private long _msb;
    private long _lsb;

    @Override
    public final int getPrimtiveLengthFromOctetLength(int octetLength) throws EncodingAlgorithmException {
        if (octetLength % 16 != 0) {
            throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.lengthNotMultipleOfUUID", new Object[]{16}));
        }
        return octetLength / 8;
    }

    @Override
    public final Object convertFromCharacters(char[] ch, int start2, int length) {
        final CharBuffer cb = CharBuffer.wrap(ch, start2, length);
        final ArrayList longList = new ArrayList();
        this.matchWhiteSpaceDelimnatedWords(cb, new BuiltInEncodingAlgorithm.WordListener(){

            @Override
            public void word(int start2, int end) {
                String uuidValue = cb.subSequence(start2, end).toString();
                UUIDEncodingAlgorithm.this.fromUUIDString(uuidValue);
                longList.add(UUIDEncodingAlgorithm.this._msb);
                longList.add(UUIDEncodingAlgorithm.this._lsb);
            }
        });
        return this.generateArrayFromList(longList);
    }

    @Override
    public final void convertToCharacters(Object data, StringBuffer s2) {
        if (!(data instanceof long[])) {
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.dataNotLongArray"));
        }
        long[] ldata = (long[])data;
        int end = ldata.length - 2;
        for (int i = 0; i <= end; i += 2) {
            s2.append(this.toUUIDString(ldata[i], ldata[i + 1]));
            if (i == end) continue;
            s2.append(' ');
        }
    }

    final void fromUUIDString(String name) {
        String[] components = name.split("-");
        if (components.length != 5) {
            throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.invalidUUID", new Object[]{name}));
        }
        for (int i = 0; i < 5; ++i) {
            components[i] = "0x" + components[i];
        }
        this._msb = Long.parseLong(components[0], 16);
        this._msb <<= 16;
        this._msb |= Long.parseLong(components[1], 16);
        this._msb <<= 16;
        this._msb |= Long.parseLong(components[2], 16);
        this._lsb = Long.parseLong(components[3], 16);
        this._lsb <<= 48;
        this._lsb |= Long.parseLong(components[4], 16);
    }

    final String toUUIDString(long msb, long lsb) {
        return this.digits(msb >> 32, 8) + "-" + this.digits(msb >> 16, 4) + "-" + this.digits(msb, 4) + "-" + this.digits(lsb >> 48, 4) + "-" + this.digits(lsb, 12);
    }

    final String digits(long val, int digits) {
        long hi = 1L << digits * 4;
        return Long.toHexString(hi | val & hi - 1L).substring(1);
    }
}

