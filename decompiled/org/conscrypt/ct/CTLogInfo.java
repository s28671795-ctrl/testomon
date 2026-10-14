/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt.ct;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;
import org.conscrypt.ct.CertificateEntry;
import org.conscrypt.ct.SerializationException;
import org.conscrypt.ct.SignedCertificateTimestamp;
import org.conscrypt.ct.VerifiedSCT;

public class CTLogInfo {
    private final byte[] logId;
    private final PublicKey publicKey;
    private final String description;
    private final String url;

    public CTLogInfo(PublicKey publicKey, String description, String url2) {
        try {
            this.logId = MessageDigest.getInstance("SHA-256").digest(publicKey.getEncoded());
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.publicKey = publicKey;
        this.description = description;
        this.url = url2;
    }

    public byte[] getID() {
        return this.logId;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CTLogInfo)) {
            return false;
        }
        CTLogInfo that = (CTLogInfo)other;
        return this.publicKey.equals(that.publicKey) && this.description.equals(that.description) && this.url.equals(that.url);
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.publicKey.hashCode();
        hash = hash * 31 + this.description.hashCode();
        hash = hash * 31 + this.url.hashCode();
        return hash;
    }

    public VerifiedSCT.Status verifySingleSCT(SignedCertificateTimestamp sct, CertificateEntry entry) {
        Signature signature;
        byte[] toVerify;
        if (!Arrays.equals(sct.getLogID(), this.getID())) {
            return VerifiedSCT.Status.UNKNOWN_LOG;
        }
        try {
            toVerify = sct.encodeTBS(entry);
        }
        catch (SerializationException e) {
            return VerifiedSCT.Status.INVALID_SCT;
        }
        try {
            String algorithm = sct.getSignature().getAlgorithm();
            signature = Signature.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            return VerifiedSCT.Status.INVALID_SCT;
        }
        try {
            signature.initVerify(this.publicKey);
        }
        catch (InvalidKeyException e) {
            return VerifiedSCT.Status.INVALID_SCT;
        }
        try {
            signature.update(toVerify);
            if (!signature.verify(sct.getSignature().getSignature())) {
                return VerifiedSCT.Status.INVALID_SIGNATURE;
            }
            return VerifiedSCT.Status.VALID;
        }
        catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}

