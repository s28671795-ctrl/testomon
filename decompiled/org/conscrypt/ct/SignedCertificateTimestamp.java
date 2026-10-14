/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.ct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.conscrypt.ct.CertificateEntry;
import org.conscrypt.ct.DigitallySigned;
import org.conscrypt.ct.Serialization;
import org.conscrypt.ct.SerializationException;

public class SignedCertificateTimestamp {
    private final Version version;
    private final byte[] logId;
    private final long timestamp;
    private final byte[] extensions;
    private final DigitallySigned signature;
    private final Origin origin;

    public SignedCertificateTimestamp(Version version, byte[] logId, long timestamp, byte[] extensions, DigitallySigned signature, Origin origin) {
        this.version = version;
        this.logId = logId;
        this.timestamp = timestamp;
        this.extensions = extensions;
        this.signature = signature;
        this.origin = origin;
    }

    public Version getVersion() {
        return this.version;
    }

    public byte[] getLogID() {
        return this.logId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public byte[] getExtensions() {
        return this.extensions;
    }

    public DigitallySigned getSignature() {
        return this.signature;
    }

    public Origin getOrigin() {
        return this.origin;
    }

    public static SignedCertificateTimestamp decode(InputStream input, Origin origin) throws SerializationException {
        int version = Serialization.readNumber(input, 1);
        if (version != Version.V1.ordinal()) {
            throw new SerializationException("Unsupported SCT version " + version);
        }
        return new SignedCertificateTimestamp(Version.V1, Serialization.readFixedBytes(input, 32), Serialization.readLong(input, 8), Serialization.readVariableBytes(input, 2), DigitallySigned.decode(input), origin);
    }

    public static SignedCertificateTimestamp decode(byte[] input, Origin origin) throws SerializationException {
        return SignedCertificateTimestamp.decode(new ByteArrayInputStream(input), origin);
    }

    public void encodeTBS(OutputStream output, CertificateEntry certEntry) throws SerializationException {
        Serialization.writeNumber(output, this.version.ordinal(), 1);
        Serialization.writeNumber(output, SignatureType.CERTIFICATE_TIMESTAMP.ordinal(), 1);
        Serialization.writeNumber(output, this.timestamp, 8);
        certEntry.encode(output);
        Serialization.writeVariableBytes(output, this.extensions, 2);
    }

    public byte[] encodeTBS(CertificateEntry certEntry) throws SerializationException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.encodeTBS(output, certEntry);
        return output.toByteArray();
    }

    public static enum Origin {
        EMBEDDED,
        TLS_EXTENSION,
        OCSP_RESPONSE;

    }

    public static enum SignatureType {
        CERTIFICATE_TIMESTAMP,
        TREE_HASH;

    }

    public static enum Version {
        V1;

    }
}

