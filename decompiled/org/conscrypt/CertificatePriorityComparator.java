/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class CertificatePriorityComparator
implements Comparator<X509Certificate> {
    private static final Map<String, Integer> ALGORITHM_OID_PRIORITY_MAP;
    private static final Integer PRIORITY_MD5;
    private static final Integer PRIORITY_SHA1;
    private static final Integer PRIORITY_SHA224;
    private static final Integer PRIORITY_SHA256;
    private static final Integer PRIORITY_SHA384;
    private static final Integer PRIORITY_SHA512;
    private static final Integer PRIORITY_UNKNOWN;

    @Override
    public int compare(X509Certificate lhs, X509Certificate rhs) {
        boolean rhsSelfSigned;
        boolean lhsSelfSigned = lhs.getSubjectDN().equals(lhs.getIssuerDN());
        if (lhsSelfSigned != (rhsSelfSigned = rhs.getSubjectDN().equals(rhs.getIssuerDN()))) {
            return rhsSelfSigned ? 1 : -1;
        }
        int result = this.compareStrength(rhs, lhs);
        if (result != 0) {
            return result;
        }
        Date lhsNotAfter = lhs.getNotAfter();
        Date rhsNotAfter = rhs.getNotAfter();
        result = rhsNotAfter.compareTo(lhsNotAfter);
        if (result != 0) {
            return result;
        }
        Date lhsNotBefore = lhs.getNotBefore();
        Date rhsNotBefore = rhs.getNotBefore();
        return rhsNotBefore.compareTo(lhsNotBefore);
    }

    private int compareStrength(X509Certificate lhs, X509Certificate rhs) {
        PublicKey rhsPublicKey;
        PublicKey lhsPublicKey = lhs.getPublicKey();
        int result = this.compareKeyAlgorithm(lhsPublicKey, rhsPublicKey = rhs.getPublicKey());
        if (result != 0) {
            return result;
        }
        result = this.compareKeySize(lhsPublicKey, rhsPublicKey);
        if (result != 0) {
            return result;
        }
        return this.compareSignatureAlgorithm(lhs, rhs);
    }

    private int compareKeyAlgorithm(PublicKey lhs, PublicKey rhs) {
        String rhsAlgorithm;
        String lhsAlgorithm = lhs.getAlgorithm();
        if (lhsAlgorithm.equalsIgnoreCase(rhsAlgorithm = rhs.getAlgorithm())) {
            return 0;
        }
        if ("EC".equalsIgnoreCase(lhsAlgorithm)) {
            return 1;
        }
        return -1;
    }

    private int compareKeySize(PublicKey lhs, PublicKey rhs) {
        String rhsAlgorithm;
        String lhsAlgorithm = lhs.getAlgorithm();
        if (!lhsAlgorithm.equalsIgnoreCase(rhsAlgorithm = rhs.getAlgorithm())) {
            throw new IllegalArgumentException("Keys are not of the same type");
        }
        int lhsSize = this.getKeySize(lhs);
        int rhsSize = this.getKeySize(rhs);
        return lhsSize - rhsSize;
    }

    private int getKeySize(PublicKey pkey) {
        if (pkey instanceof ECPublicKey) {
            return ((ECPublicKey)pkey).getParams().getCurve().getField().getFieldSize();
        }
        if (pkey instanceof RSAPublicKey) {
            return ((RSAPublicKey)pkey).getModulus().bitLength();
        }
        throw new IllegalArgumentException("Unsupported public key type: " + pkey.getClass().getName());
    }

    private int compareSignatureAlgorithm(X509Certificate lhs, X509Certificate rhs) {
        Integer lhsPriority = ALGORITHM_OID_PRIORITY_MAP.get(lhs.getSigAlgOID());
        Integer rhsPriority = ALGORITHM_OID_PRIORITY_MAP.get(rhs.getSigAlgOID());
        if (lhsPriority == null) {
            lhsPriority = PRIORITY_UNKNOWN;
        }
        if (rhsPriority == null) {
            rhsPriority = PRIORITY_UNKNOWN;
        }
        return rhsPriority - lhsPriority;
    }

    static {
        PRIORITY_MD5 = 6;
        PRIORITY_SHA1 = 5;
        PRIORITY_SHA224 = 4;
        PRIORITY_SHA256 = 3;
        PRIORITY_SHA384 = 2;
        PRIORITY_SHA512 = 1;
        PRIORITY_UNKNOWN = -1;
        ALGORITHM_OID_PRIORITY_MAP = new HashMap<String, Integer>();
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.13", PRIORITY_SHA512);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.12", PRIORITY_SHA384);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.11", PRIORITY_SHA256);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.14", PRIORITY_SHA224);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.5", PRIORITY_SHA1);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.4", PRIORITY_MD5);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.4", PRIORITY_SHA512);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.3", PRIORITY_SHA384);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.2", PRIORITY_SHA256);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.1", PRIORITY_SHA224);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.1", PRIORITY_SHA1);
    }
}

