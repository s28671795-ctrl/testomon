/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import java.security.cert.X509Certificate;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PKIPathSecurity
extends BinarySecurity {
    public static final String PKI_TYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1";

    public PKIPathSecurity(Element elem, BSPEnforcer bspEnforcer) throws WSSecurityException {
        super(elem, bspEnforcer);
        if (!PKI_TYPE.equals(this.getValueType())) {
            bspEnforcer.handleBSPRule(BSPRule.R5214);
        }
    }

    public PKIPathSecurity(Document doc) {
        super(doc);
        this.setValueType(PKI_TYPE);
    }

    public X509Certificate[] getX509Certificates(Crypto crypto) throws WSSecurityException {
        byte[] data = this.getToken();
        if (data == null) {
            return null;
        }
        if (crypto == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noSigCryptoFile");
        }
        return crypto.getCertificatesFromBytes(data);
    }

    public void setX509Certificates(X509Certificate[] certs, Crypto crypto) throws WSSecurityException {
        if (certs == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCert");
        }
        byte[] data = crypto.getBytesFromCertificates(certs);
        this.setToken(data);
    }

    public static String getType() {
        return PKI_TYPE;
    }
}

