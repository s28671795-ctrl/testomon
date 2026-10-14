/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.conscrypt.NativeCrypto;
import org.conscrypt.OpenSSLX509CRL;
import org.conscrypt.OpenSSLX509CertificateFactory;

final class OpenSSLX509CRLEntry
extends X509CRLEntry {
    private final long mContext;
    private final Date revocationDate;

    OpenSSLX509CRLEntry(long ctx) throws OpenSSLX509CertificateFactory.ParsingException {
        this.mContext = ctx;
        this.revocationDate = OpenSSLX509CRL.toDate(NativeCrypto.get_X509_REVOKED_revocationDate(this.mContext));
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        String[] critOids = NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1);
        if (critOids.length == 0 && NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 0).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(critOids));
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return NativeCrypto.X509_REVOKED_get_ext_oid(this.mContext, oid);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        String[] critOids = NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 0);
        if (critOids.length == 0 && NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(critOids));
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        String[] criticalOids;
        for (String oid : criticalOids = NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1)) {
            long extensionRef = NativeCrypto.X509_REVOKED_get_ext(this.mContext, oid);
            if (NativeCrypto.X509_supported_extension(extensionRef) == 1) continue;
            return true;
        }
        return false;
    }

    @Override
    public byte[] getEncoded() throws CRLException {
        return NativeCrypto.i2d_X509_REVOKED(this.mContext);
    }

    @Override
    public BigInteger getSerialNumber() {
        return new BigInteger(NativeCrypto.X509_REVOKED_get_serialNumber(this.mContext));
    }

    @Override
    public Date getRevocationDate() {
        return (Date)this.revocationDate.clone();
    }

    @Override
    public boolean hasExtensions() {
        return NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 0).length != 0 || NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1).length != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String toString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        long bioCtx = NativeCrypto.create_BIO_OutputStream(os);
        try {
            NativeCrypto.X509_REVOKED_print(bioCtx, this.mContext);
            String string = os.toString();
            return string;
        }
        finally {
            NativeCrypto.BIO_free_all(bioCtx);
        }
    }
}

