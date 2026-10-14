/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.cms;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.util.io.Streams;

public class CMSTypedStream {
    private static final int BUF_SIZ = 32768;
    private final ASN1ObjectIdentifier _oid;
    protected InputStream _in;

    public CMSTypedStream(InputStream inputStream2) {
        this(PKCSObjectIdentifiers.data.getId(), inputStream2, 32768);
    }

    public CMSTypedStream(String string, InputStream inputStream2) {
        this(new ASN1ObjectIdentifier(string), inputStream2, 32768);
    }

    public CMSTypedStream(String string, InputStream inputStream2, int n) {
        this(new ASN1ObjectIdentifier(string), inputStream2, n);
    }

    public CMSTypedStream(ASN1ObjectIdentifier aSN1ObjectIdentifier, InputStream inputStream2) {
        this(aSN1ObjectIdentifier, inputStream2, 32768);
    }

    public CMSTypedStream(ASN1ObjectIdentifier aSN1ObjectIdentifier, InputStream inputStream2, int n) {
        this._oid = aSN1ObjectIdentifier;
        this._in = new FullReaderStream(new BufferedInputStream(inputStream2, n));
    }

    protected CMSTypedStream(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this._oid = aSN1ObjectIdentifier;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this._oid;
    }

    public InputStream getContentStream() {
        return this._in;
    }

    public void drain() throws IOException {
        Streams.drain(this._in);
        this._in.close();
    }

    private static class FullReaderStream
    extends FilterInputStream {
        FullReaderStream(InputStream inputStream2) {
            super(inputStream2);
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            if (n2 == 0) {
                return 0;
            }
            int n3 = Streams.readFully(this.in, byArray, n, n2);
            return n3 > 0 ? n3 : -1;
        }
    }
}

