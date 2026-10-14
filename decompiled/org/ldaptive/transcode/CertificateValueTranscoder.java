/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transcode;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import org.ldaptive.LdapUtils;
import org.ldaptive.transcode.ValueTranscoder;

public class CertificateValueTranscoder
implements ValueTranscoder<Certificate> {
    private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----" + System.getProperty("line.separator");
    private static final String END_CERT = System.getProperty("line.separator") + "-----END CERTIFICATE-----";

    @Override
    public Certificate decodeStringValue(String value) {
        return this.decodeBinaryValue(LdapUtils.utf8Encode(value));
    }

    @Override
    public Certificate decodeBinaryValue(byte[] value) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return cf.generateCertificate(new ByteArrayInputStream(value));
        }
        catch (CertificateException e) {
            throw new IllegalArgumentException("Attribute value could not be decoded as a certificate", e);
        }
    }

    @Override
    public String encodeStringValue(Certificate value) {
        StringBuilder sb = new StringBuilder();
        sb.append(BEGIN_CERT);
        sb.append(LdapUtils.base64Encode(this.encodeBinaryValue(value)));
        sb.append(END_CERT);
        return sb.toString();
    }

    @Override
    public byte[] encodeBinaryValue(Certificate value) {
        try {
            return value.getEncoded();
        }
        catch (CertificateEncodingException e) {
            throw new IllegalArgumentException("Certificate could not be encoded", e);
        }
    }

    @Override
    public Class<Certificate> getType() {
        return Certificate.class;
    }
}

