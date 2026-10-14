/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.asn1.types.string;

import com.hierynomus.asn1.ASN1InputStream;
import com.hierynomus.asn1.ASN1OutputStream;
import com.hierynomus.asn1.ASN1ParseException;
import com.hierynomus.asn1.ASN1Parser;
import com.hierynomus.asn1.ASN1Serializer;
import com.hierynomus.asn1.encodingrules.ASN1Decoder;
import com.hierynomus.asn1.encodingrules.ASN1Encoder;
import com.hierynomus.asn1.types.ASN1Tag;
import com.hierynomus.asn1.types.string.ASN1String;
import com.hierynomus.asn1.util.Checks;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

public class ASN1BitString
extends ASN1String<boolean[]> {
    private int unusedBits;
    private boolean[] bits;

    private ASN1BitString(ASN1Tag<ASN1BitString> tag, byte[] bytes, int unusedBits) {
        super(tag, bytes);
        this.unusedBits = unusedBits;
        this.bits = this.constructBits();
    }

    public ASN1BitString(byte[] bytes, int unusedBits) {
        this(ASN1Tag.BIT_STRING, bytes, unusedBits);
    }

    public ASN1BitString(boolean[] bits) {
        super(ASN1Tag.BIT_STRING, ASN1BitString.constructBytes(bits));
        this.bits = bits;
        this.unusedBits = 8 - bits.length % 8;
    }

    public ASN1BitString(BitSet bitSet) {
        this(ASN1Tag.BIT_STRING, ASN1BitString.constructBytes(bitSet), 0);
    }

    private static byte[] constructBytes(boolean[] bits) {
        byte[] bytes = new byte[bits.length / 8 + (bits.length % 8 > 0 ? 1 : 0)];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = 0;
            for (int j = 0; j < 8; ++j) {
                boolean v = i * 8 + j < bits.length && bits[i * 8 + j];
                int n = i;
                bytes[n] = (byte)(bytes[n] + ((v ? 1 : 0) << 8 - j - 1));
            }
        }
        return bytes;
    }

    private static byte[] constructBytes(BitSet bitSet) {
        byte[] bytes = new byte[bitSet.length() / 8 + 1];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = 0;
            for (int j = 0; j < 8; ++j) {
                int n = i;
                bytes[n] = (byte)(bytes[n] + ((bitSet.get(i * 8 + j) ? 1 : 0) << 8 - j - 1));
            }
        }
        return bytes;
    }

    private boolean[] constructBits() {
        boolean[] bits = new boolean[this.length()];
        for (int i = 0; i < bits.length; ++i) {
            bits[i] = this.isSet(i);
        }
        return bits;
    }

    @Override
    public boolean[] getValue() {
        return Arrays.copyOf(this.bits, this.bits.length);
    }

    @Override
    protected String valueString() {
        return Arrays.toString(this.bits);
    }

    public boolean isSet(int x) {
        int toCheck = x / 8;
        byte theByte = this.valueBytes[toCheck];
        int index = x % 8;
        int mask = 1 << 7 - index;
        int masked = theByte & mask;
        return masked != 0;
    }

    @Override
    public int length() {
        return this.valueBytes.length * 8 - this.unusedBits;
    }

    public static class Serializer
    extends ASN1Serializer<ASN1BitString> {
        public Serializer(ASN1Encoder encoder) {
            super(encoder);
        }

        @Override
        public int serializedLength(ASN1BitString asn1Object) {
            return asn1Object.valueBytes.length + 1;
        }

        @Override
        public void serialize(ASN1BitString asn1Object, ASN1OutputStream stream) throws IOException {
            stream.write(asn1Object.unusedBits);
            stream.write(asn1Object.valueBytes);
        }
    }

    public static class Parser
    extends ASN1Parser<ASN1BitString> {
        public Parser(ASN1Decoder decoder) {
            super(decoder);
        }

        @Override
        public ASN1BitString parse(ASN1Tag<ASN1BitString> asn1Tag, byte[] value) {
            if (asn1Tag.isConstructed()) {
                ASN1BitString aSN1BitString;
                ASN1InputStream stream = new ASN1InputStream(this.decoder, value);
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int unusedBits = 0;
                    while (stream.available() > 0) {
                        ASN1Tag subTag = stream.readTag();
                        Checks.checkState(subTag.getTag() == asn1Tag.getTag(), "Expected an ASN.1 BIT STRING as Constructed object, got: %s", subTag);
                        int i = stream.readLength();
                        byte[] subValue = stream.readValue(i);
                        baos.write(subValue, 1, subValue.length - 1);
                        if (stream.available() > 0) continue;
                        unusedBits = subValue[0];
                    }
                    aSN1BitString = new ASN1BitString(asn1Tag, baos.toByteArray(), unusedBits);
                }
                catch (Throwable baos) {
                    try {
                        try {
                            stream.close();
                        }
                        catch (Throwable throwable) {
                            baos.addSuppressed(throwable);
                        }
                        throw baos;
                    }
                    catch (IOException e) {
                        throw new ASN1ParseException(e, "Unable to parse Constructed ASN.1 BIT STRING", new Object[0]);
                    }
                }
                stream.close();
                return aSN1BitString;
            }
            byte unusedBits = value[0];
            byte[] bits = Arrays.copyOfRange(value, 1, value.length);
            return new ASN1BitString(asn1Tag, bits, unusedBits);
        }
    }
}

