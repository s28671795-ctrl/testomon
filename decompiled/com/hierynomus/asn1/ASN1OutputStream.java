/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.asn1;

import com.hierynomus.asn1.ASN1Serializer;
import com.hierynomus.asn1.encodingrules.ASN1Encoder;
import com.hierynomus.asn1.types.ASN1Object;
import com.hierynomus.asn1.types.ASN1Tag;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ASN1OutputStream
extends FilterOutputStream {
    private final ASN1Encoder encoder;

    public ASN1OutputStream(ASN1Encoder encoder, OutputStream out) {
        super(out);
        this.encoder = encoder;
    }

    public void writeObject(ASN1Object asn1Object) throws IOException {
        ASN1Tag tag = asn1Object.getTag();
        this.writeTag(tag);
        ASN1Serializer<ASN1Object> asn1Serializer = asn1Object.getTag().newSerializer(this.encoder);
        this.writeLength(asn1Serializer.serializedLength(asn1Object));
        asn1Serializer.serialize(asn1Object, this);
    }

    private void writeLength(int length) throws IOException {
        if (length < 127) {
            this.write(length);
        } else {
            int nrBytes;
            this.write(0x80 | nrBytes);
            for (nrBytes = this.lengthBytes(length); nrBytes > 0; --nrBytes) {
                this.write(length >> (nrBytes - 1) * 8);
            }
        }
    }

    private int lengthBytes(int length) {
        int nrBytes = 1;
        for (int l = length; l > 255; l >>= 8) {
            ++nrBytes;
        }
        return nrBytes;
    }

    private void writeTag(ASN1Tag tag) throws IOException {
        byte tagByte = (byte)(tag.getAsn1TagClass().getValue() | tag.getAsn1Encoding().getValue() | tag.getTag());
        this.write(tagByte);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
    }
}

