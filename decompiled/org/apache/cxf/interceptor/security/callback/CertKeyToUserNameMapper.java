/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security.callback;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import org.apache.cxf.interceptor.security.callback.CertificateToNameMapper;

public class CertKeyToUserNameMapper
implements CertificateToNameMapper {
    private String key;

    @Override
    public String getUserName(Certificate cert) {
        X509Certificate certificate = (X509Certificate)cert;
        String dn = certificate.getSubjectDN().getName();
        LdapName ldapDn = this.getLdapName(dn);
        if (this.key == null) {
            throw new IllegalArgumentException("Must set a key");
        }
        for (Rdn rdn : ldapDn.getRdns()) {
            if (!this.key.equalsIgnoreCase(rdn.getType())) continue;
            return (String)rdn.getValue();
        }
        throw new IllegalArgumentException("No " + this.key + " key found in certificate DN: " + dn);
    }

    private LdapName getLdapName(String dn) {
        try {
            return new LdapName(dn);
        }
        catch (InvalidNameException e) {
            throw new IllegalArgumentException("Invalid DN", e);
        }
    }

    public void setKey(String key) {
        this.key = key;
    }
}

