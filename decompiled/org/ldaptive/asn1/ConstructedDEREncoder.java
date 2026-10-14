/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;

public class ConstructedDEREncoder
extends AbstractDERType
implements DEREncoder {
    private final DEREncoder[] derEncoders;

    public ConstructedDEREncoder(DERTag tag, DEREncoder ... encoders) {
        super(tag);
        if (!tag.isConstructed()) {
            throw new IllegalArgumentException("DER tag must be constructed");
        }
        if (encoders == null || encoders.length == 0) {
            throw new IllegalArgumentException("Encoders cannot be null or empty");
        }
        this.derEncoders = encoders;
    }

    @Override
    public byte[] encode() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ByteArrayOutputStream byteArrayOutputStream = bytes;){
            for (DEREncoder encoder : this.derEncoders) {
                bytes.write(encoder.encode());
            }
        }
        catch (IOException e) {
            throw new IllegalStateException("Encode failed", e);
        }
        return this.encode(new byte[][]{bytes.toByteArray()});
    }
}

