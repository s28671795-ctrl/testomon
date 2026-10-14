/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.BERGenerator;

public class BERSequenceGenerator
extends BERGenerator {
    public BERSequenceGenerator(OutputStream outputStream2) throws IOException {
        super(outputStream2);
        this.writeBERHeader(48);
    }

    public BERSequenceGenerator(OutputStream outputStream2, int n, boolean bl) throws IOException {
        super(outputStream2, n, bl);
        this.writeBERHeader(48);
    }

    public void addObject(ASN1Encodable aSN1Encodable) throws IOException {
        aSN1Encodable.toASN1Primitive().encodeTo(this._out);
    }

    public void addObject(ASN1Primitive aSN1Primitive) throws IOException {
        aSN1Primitive.encodeTo(this._out);
    }

    public void close() throws IOException {
        this.writeBEREnd();
    }
}

