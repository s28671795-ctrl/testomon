/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLBIOInputStream;
import org.conscrypt.OpenSSLX509Certificate;
import org.conscrypt.OpenSSLX509CertificateFactory;

final class OpenSSLX509CertPath
extends CertPath {
    private static final long serialVersionUID = -3249106005255170761L;
    private static final byte[] PKCS7_MARKER = new byte[]{45, 45, 45, 45, 45, 66, 69, 71, 73, 78, 32, 80, 75, 67, 83, 55};
    private static final int PUSHBACK_SIZE = 64;
    private static final List<String> ALL_ENCODINGS = Collections.unmodifiableList(Arrays.asList(Encoding.access$000(Encoding.PKI_PATH), Encoding.access$000(Encoding.PKCS7)));
    private static final Encoding DEFAULT_ENCODING = Encoding.PKI_PATH;
    private final List<? extends X509Certificate> mCertificates;

    static Iterator<String> getEncodingsIterator() {
        return ALL_ENCODINGS.iterator();
    }

    OpenSSLX509CertPath(List<? extends X509Certificate> certificates) {
        super("X.509");
        this.mCertificates = certificates;
    }

    @Override
    public List<? extends Certificate> getCertificates() {
        return Collections.unmodifiableList(this.mCertificates);
    }

    private byte[] getEncoded(Encoding encoding) throws CertificateEncodingException {
        OpenSSLX509Certificate[] certs = new OpenSSLX509Certificate[this.mCertificates.size()];
        long[] certRefs = new long[certs.length];
        int i = 0;
        for (int j = certs.length - 1; j >= 0; --j) {
            X509Certificate cert = this.mCertificates.get(i);
            certs[j] = cert instanceof OpenSSLX509Certificate ? (OpenSSLX509Certificate)cert : OpenSSLX509Certificate.fromX509Der(cert.getEncoded());
            certRefs[j] = certs[j].getContext();
            ++i;
        }
        switch (encoding) {
            case PKI_PATH: {
                return NativeCrypto.ASN1_seq_pack_X509(certRefs);
            }
            case PKCS7: {
                return NativeCrypto.i2d_PKCS7(certRefs);
            }
        }
        throw new CertificateEncodingException("Unknown encoding");
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.getEncoded(DEFAULT_ENCODING);
    }

    @Override
    public byte[] getEncoded(String encoding) throws CertificateEncodingException {
        Encoding enc = Encoding.findByApiName(encoding);
        if (enc == null) {
            throw new CertificateEncodingException("Invalid encoding: " + encoding);
        }
        return this.getEncoded(enc);
    }

    @Override
    public Iterator<String> getEncodings() {
        return OpenSSLX509CertPath.getEncodingsIterator();
    }

    private static CertPath fromPkiPathEncoding(InputStream inStream) throws CertificateException {
        long[] certRefs;
        OpenSSLBIOInputStream bis = new OpenSSLBIOInputStream(inStream, true);
        boolean markable = inStream.markSupported();
        if (markable) {
            inStream.mark(64);
        }
        try {
            certRefs = NativeCrypto.ASN1_seq_unpack_X509_bio(bis.getBioContext());
        }
        catch (Exception e) {
            if (markable) {
                try {
                    inStream.reset();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            throw new CertificateException(e);
        }
        finally {
            bis.release();
        }
        if (certRefs == null) {
            return new OpenSSLX509CertPath(Collections.emptyList());
        }
        ArrayList<OpenSSLX509Certificate> certs = new ArrayList<OpenSSLX509Certificate>(certRefs.length);
        for (int i = certRefs.length - 1; i >= 0; --i) {
            if (certRefs[i] == 0L) continue;
            try {
                certs.add(new OpenSSLX509Certificate(certRefs[i]));
                continue;
            }
            catch (OpenSSLX509CertificateFactory.ParsingException e) {
                throw new CertificateParsingException(e);
            }
        }
        return new OpenSSLX509CertPath(certs);
    }

    private static CertPath fromPkcs7Encoding(InputStream inStream) throws CertificateException {
        try {
            if (inStream == null || inStream.available() == 0) {
                return new OpenSSLX509CertPath(Collections.emptyList());
            }
        }
        catch (IOException e) {
            throw new CertificateException("Problem reading input stream", e);
        }
        boolean markable = inStream.markSupported();
        if (markable) {
            inStream.mark(64);
        }
        PushbackInputStream pbis = new PushbackInputStream(inStream, 64);
        try {
            byte[] buffer = new byte[PKCS7_MARKER.length];
            int len = pbis.read(buffer);
            if (len < 0) {
                throw new OpenSSLX509CertificateFactory.ParsingException("inStream is empty");
            }
            pbis.unread(buffer, 0, len);
            if (len == PKCS7_MARKER.length && Arrays.equals(PKCS7_MARKER, buffer)) {
                return new OpenSSLX509CertPath(OpenSSLX509Certificate.fromPkcs7PemInputStream(pbis));
            }
            return new OpenSSLX509CertPath(OpenSSLX509Certificate.fromPkcs7DerInputStream(pbis));
        }
        catch (Exception e) {
            if (markable) {
                try {
                    inStream.reset();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            throw new CertificateException(e);
        }
    }

    private static CertPath fromEncoding(InputStream inStream, Encoding encoding) throws CertificateException {
        switch (encoding) {
            case PKI_PATH: {
                return OpenSSLX509CertPath.fromPkiPathEncoding(inStream);
            }
            case PKCS7: {
                return OpenSSLX509CertPath.fromPkcs7Encoding(inStream);
            }
        }
        throw new CertificateEncodingException("Unknown encoding");
    }

    static CertPath fromEncoding(InputStream inStream, String encoding) throws CertificateException {
        if (inStream == null) {
            throw new CertificateException("inStream == null");
        }
        Encoding enc = Encoding.findByApiName(encoding);
        if (enc == null) {
            throw new CertificateException("Invalid encoding: " + encoding);
        }
        return OpenSSLX509CertPath.fromEncoding(inStream, enc);
    }

    static CertPath fromEncoding(InputStream inStream) throws CertificateException {
        if (inStream == null) {
            throw new CertificateException("inStream == null");
        }
        return OpenSSLX509CertPath.fromEncoding(inStream, DEFAULT_ENCODING);
    }

    private static enum Encoding {
        PKI_PATH("PkiPath"),
        PKCS7("PKCS7");

        private final String apiName;

        private Encoding(String apiName) {
            this.apiName = apiName;
        }

        static Encoding findByApiName(String apiName) throws CertificateEncodingException {
            for (Encoding element : Encoding.values()) {
                if (!element.apiName.equals(apiName)) continue;
                return element;
            }
            return null;
        }

        static /* synthetic */ String access$000(Encoding x0) {
            return x0.apiName;
        }
    }
}

